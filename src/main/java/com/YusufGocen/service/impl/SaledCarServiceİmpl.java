package com.YusufGocen.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.YusufGocen.dto.CurrencyRatesResponse;
import com.YusufGocen.dto.DtoCar;
import com.YusufGocen.dto.DtoCarSaled;
import com.YusufGocen.dto.DtoCustomer;
import com.YusufGocen.dto.DtoGallerist;
import com.YusufGocen.dto.DtoSaledCarIU;
import com.YusufGocen.enums.CarStatusType;
import com.YusufGocen.exception.BaseException;
import com.YusufGocen.exception.ErrorMessage;
import com.YusufGocen.exception.MessageType;
import com.YusufGocen.model.Car;
import com.YusufGocen.model.Customer;
import com.YusufGocen.model.SaledCar;
import com.YusufGocen.repository.CarRepository;
import com.YusufGocen.repository.CustomerRepository;
import com.YusufGocen.repository.GalleristRepository;
import com.YusufGocen.repository.SaledCarRepository;
import com.YusufGocen.service.ICurrencyRatesService;
import com.YusufGocen.service.ISaledCarService;
import com.YusufGocen.utils.DateUtils;

@Service
public class SaledCarServiceİmpl implements ISaledCarService{
	
	@Autowired
	private SaledCarRepository saledCarRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private GalleristRepository galleristRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private ICurrencyRatesService currencyRatesService;
	
	
	public BigDecimal convertCustomerAmountUSD(Customer customer) {
		
		CurrencyRatesResponse currencyRatesResponse= currencyRatesService.getCurrencyRates("01-07-2026","01-07-2026");
		
		BigDecimal usd=new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
		
		BigDecimal customerUsd=customer.getAccount().getAmount().divide(usd,2,RoundingMode.HALF_UP);
		
		return customerUsd;
	}
	
	public boolean checkAmount(DtoSaledCarIU dtoSaledCarIU) {
		
		Optional<Customer>optCustomer=customerRepository.findById(dtoSaledCarIU.getCustomerId());
		
		if (optCustomer.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoSaledCarIU.getCustomerId().toString()));
		}
		
		Optional<Car>optCar=carRepository.findById(dtoSaledCarIU.getCarId());
		
		if (optCar.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoSaledCarIU.getCarId().toString()));
		}
		
		BigDecimal customerUsd=convertCustomerAmountUSD(optCustomer.get());
		
		if (customerUsd.compareTo(optCar.get().getPrice())==0 || customerUsd.compareTo(optCar.get().getPrice())>0) {
			return true;
		}
		return false;
		
	}
	
	private SaledCar createSaledCar(DtoSaledCarIU dtoSaledCarIU) {
		
		SaledCar saledCar=new SaledCar();
		saledCar.setCreateTime(new Date());
		
		saledCar.setCustomer(customerRepository.findById(dtoSaledCarIU.getCustomerId()).orElse(null));
		saledCar.setGallerist(galleristRepository.findById(dtoSaledCarIU.getGalleristId()).orElse(null));
		saledCar.setCar(carRepository.findById(dtoSaledCarIU.getCarId()).orElse(null));
		
		return saledCar;
	}
	
	public boolean checkCarStatus(Long carId) {
		Optional<Car>optCar=carRepository.findById(carId);
		if (optCar.isPresent() && optCar.get().getCarStatusType().name().equals(CarStatusType.SALED.name())) {
			return false;
		}
		return true;
	}
	
	public BigDecimal remaningCustomerAmount(Customer customer , Car car) {
		BigDecimal customerUsdAmount = convertCustomerAmountUSD(customer);
		BigDecimal remaningCustomerUsdAmount =customerUsdAmount.subtract(car.getPrice());
		
		CurrencyRatesResponse currencyRatesResponse=currencyRatesService.getCurrencyRates(DateUtils.getCurrentDate(new Date()), DateUtils.getCurrentDate(new Date()));
		
		BigDecimal usd=new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
		
		return remaningCustomerUsdAmount.multiply(usd);
	}
	
	
	@Override
	public DtoCarSaled buyCar(DtoSaledCarIU dtoSaledCarIU) {
		
		if (!checkAmount(dtoSaledCarIU)) {
			throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_AMOUNTH_IS_NOT_ENOUGH,""));
		}
		
		if (!checkCarStatus(dtoSaledCarIU.getCarId())) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_STATUS_ALREADY_SALED,dtoSaledCarIU.getCarId().toString()));
		}
		
		SaledCar savedSaledCar=saledCarRepository.save(createSaledCar(dtoSaledCarIU));
		
		Car car= savedSaledCar.getCar();
		car.setCarStatusType(CarStatusType.SALED);
		
		carRepository.save(car);
		
		Customer customer=savedSaledCar.getCustomer();
		customer.getAccount().setAmount(remaningCustomerAmount(customer, car));
		customerRepository.save(customer);
		
		return toDto(savedSaledCar);
	}
	
	public DtoCarSaled toDto(SaledCar saledCar) {
		
		DtoCarSaled dtoCarSaled=new DtoCarSaled();
		DtoCustomer dtoCustomer=new DtoCustomer();
		DtoGallerist dtoGallerist=new DtoGallerist();
		DtoCar dtoCar =new DtoCar();
		
		BeanUtils.copyProperties(saledCar, dtoCarSaled);
		BeanUtils.copyProperties(saledCar.getCustomer(), dtoCustomer);
		BeanUtils.copyProperties(saledCar.getGallerist(), dtoGallerist);
		BeanUtils.copyProperties(saledCar.getCar(), dtoCar);
		
		dtoCarSaled.setDtoCustomer(dtoCustomer);
		dtoCarSaled.setDtoGallerist(dtoGallerist);
		dtoCarSaled.setDtoCar(dtoCar);
		
		return dtoCarSaled;
		
		
	}

	@Override
	public List<DtoCarSaled> getAllSaledCars() {
		
		List<SaledCar>saledCarsList=saledCarRepository.findAll();
		
		List<DtoCarSaled>dtoCarSaledList=new ArrayList<>();
		
		for (SaledCar saledCar : saledCarsList) {
			dtoCarSaledList.add(toDto(saledCar));
		}
		
		return dtoCarSaledList;
	}

	@Override
	public DtoCarSaled getSaledCarById(Long id) {
		
		SaledCar saledCar=saledCarRepository.findById(id)
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            id.toString()
	                    )
	            ));
		
		return toDto(saledCar);
	}

}
