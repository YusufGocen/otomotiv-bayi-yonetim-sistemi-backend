package com.YusufGocen.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.YusufGocen.dto.DtoAddress;
import com.YusufGocen.dto.DtoCar;
import com.YusufGocen.dto.DtoGallerist;
import com.YusufGocen.dto.DtoGalleristCar;
import com.YusufGocen.dto.DtoGalleristCarIU;
import com.YusufGocen.exception.BaseException;
import com.YusufGocen.exception.ErrorMessage;
import com.YusufGocen.exception.MessageType;
import com.YusufGocen.model.Car;
import com.YusufGocen.model.Gallerist;
import com.YusufGocen.model.GalleristCar;
import com.YusufGocen.repository.CarRepository;
import com.YusufGocen.repository.GalleristCarRepository;
import com.YusufGocen.repository.GalleristRepository;
import com.YusufGocen.service.IGalleristCarService;

@Service
public class GalleristCarServiceİmpl implements IGalleristCarService{

	@Autowired
	private GalleristRepository galleristRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private GalleristCarRepository galleristCarRepository;
	
	private GalleristCar createGalleristCar(DtoGalleristCarIU dtoGalleristCarIU) {
		
		Optional<Gallerist> optGallerist=galleristRepository.findById(dtoGalleristCarIU.getGalleristId());
		if (optGallerist.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoGalleristCarIU.getGalleristId().toString()));
		}
		
		Optional<Car>optCar= carRepository.findById(dtoGalleristCarIU.getCarId());
		if (optCar.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoGalleristCarIU.getCarId().toString()));

		}
		
		GalleristCar galleristCar =new GalleristCar();
		galleristCar.setCreateTime(new Date());
		galleristCar.setGallerist(optGallerist.get());
		galleristCar.setCar(optCar.get());
		return galleristCar;
	}
	
	@Override
	public DtoGalleristCar saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU) {
		
		DtoGalleristCar dtoGalleristCar=new DtoGalleristCar();
		DtoGallerist dtoGallerist=new DtoGallerist();
		DtoCar dtoCar=new DtoCar();
		
		DtoAddress dtoAddress=new DtoAddress();
		
		GalleristCar savedGalleristCar =galleristCarRepository.save(createGalleristCar(dtoGalleristCarIU));
		
		BeanUtils.copyProperties(savedGalleristCar, dtoGalleristCar);
		BeanUtils.copyProperties(savedGalleristCar.getGallerist(), dtoGallerist);
		BeanUtils.copyProperties(savedGalleristCar.getGallerist().getAddress(), dtoAddress);
		BeanUtils.copyProperties(savedGalleristCar.getCar(), dtoCar);
		
		
		dtoGallerist.setAddress(dtoAddress);
		dtoGalleristCar.setDtoGallerist(dtoGallerist);
		dtoGalleristCar.setDtoCar(dtoCar);
		
		
		
		return dtoGalleristCar;
	}

	@Override
	public List<DtoGalleristCar> getAllGaleristCars() {
	
	    List<GalleristCar> galleristCarList =
	            galleristCarRepository.findAll();
	
	    List<DtoGalleristCar> dtoGalleristCarList =
	            new ArrayList<>();
	
	    for (GalleristCar galleristCar : galleristCarList) {
	
	        DtoGalleristCar dtoGalleristCar =
	                new DtoGalleristCar();
	
	        DtoGallerist dtoGallerist =
	                new DtoGallerist();
	
	        DtoCar dtoCar =
	                new DtoCar();
	
	        DtoAddress dtoAddress =
	                new DtoAddress();
	
	        BeanUtils.copyProperties(
	                galleristCar,
	                dtoGalleristCar
	        );
	
	        BeanUtils.copyProperties(
	                galleristCar.getGallerist(),
	                dtoGallerist
	        );
	
	        if (galleristCar.getGallerist().getAddress() != null) {
	
	            BeanUtils.copyProperties(
	                    galleristCar.getGallerist().getAddress(),
	                    dtoAddress
	            );
	
	            dtoGallerist.setAddress(dtoAddress);
	        }
	
	        BeanUtils.copyProperties(
	                galleristCar.getCar(),
	                dtoCar
	        );
	
	        dtoGalleristCar.setDtoGallerist(dtoGallerist);
	        dtoGalleristCar.setDtoCar(dtoCar);
	
	        dtoGalleristCarList.add(dtoGalleristCar);
	    }
	
	    return dtoGalleristCarList;
	}

	@Override
	public DtoGalleristCar getGalleristCarById(Long id) {
	    GalleristCar galleristCar = galleristCarRepository.findById(id)
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            id.toString()
	                    )
	            ));

	    DtoGalleristCar dtoGalleristCar = new DtoGalleristCar();
	    DtoGallerist dtoGallerist = new DtoGallerist();
	    DtoCar dtoCar = new DtoCar();
	    DtoAddress dtoAddress = new DtoAddress();

	    BeanUtils.copyProperties(galleristCar, dtoGalleristCar);

	    BeanUtils.copyProperties(
	            galleristCar.getGallerist(),
	            dtoGallerist
	    );

	    BeanUtils.copyProperties(
	            galleristCar.getGallerist().getAddress(),
	            dtoAddress
	    );

	    BeanUtils.copyProperties(
	            galleristCar.getCar(),
	            dtoCar
	    );

	    dtoGallerist.setAddress(dtoAddress);
	    dtoGalleristCar.setDtoGallerist(dtoGallerist);
	    dtoGalleristCar.setDtoCar(dtoCar);

	    return dtoGalleristCar;
	}

	@Override
	public void deleteGalleristCar(Long id) {
	    GalleristCar galleristCar = galleristCarRepository.findById(id)
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            id.toString()
	                    )
	            ));
		galleristCarRepository.delete(galleristCar);
	}
	
	
	
	
	}
