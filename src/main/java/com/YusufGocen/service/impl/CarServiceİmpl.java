package com.YusufGocen.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.YusufGocen.dto.DtoCar;
import com.YusufGocen.dto.DtoCarIU;
import com.YusufGocen.model.Car;
import com.YusufGocen.repository.CarRepository;
import com.YusufGocen.service.ICarService;

@Service
public class CarServiceİmpl implements ICarService{

	@Autowired
	private CarRepository carRepository;
	
	private Car createCar(DtoCarIU dtoCarIU) {
		
		Car car=new Car();
		car.setCreateTime(new Date());
		
		BeanUtils.copyProperties(dtoCarIU, car);
		
		return car;
	}
 	
	@Override
	public DtoCar saveCar(DtoCarIU dtoCarIU) {
		
		DtoCar dtoCar=new DtoCar();
		
		Car savedCar = carRepository.save(createCar(dtoCarIU));
		
		BeanUtils.copyProperties(savedCar, dtoCar);
		
		return dtoCar;
	}
	

	@Override
	public List<DtoCar> getAllCars() {
		
		List<Car>carList=carRepository.findAll();
		
		List<DtoCar>dtoCarList=new ArrayList<>();
		
		for(Car car : carList) {
			DtoCar dtoCar=new DtoCar();
			BeanUtils.copyProperties(car, dtoCar);
			dtoCarList.add(dtoCar);
		}
		
		
		return dtoCarList;
	}

	
	@Override
	public DtoCar getCarbyId(Long id) {
		
		Car car=carRepository.findById(id).orElseThrow(()->new RuntimeException("Araç Bulunamadı"));
		
		DtoCar dtoCar=new DtoCar();
		
		BeanUtils.copyProperties(car, dtoCar);
		
		return dtoCar;
	}
	

	@Override
	public DtoCar updateCar(Long id, DtoCarIU dtoCarIU) {
		
		Car car=carRepository.findById(id).orElseThrow(()->new RuntimeException("Araç Bulunamadı"));
		
		BeanUtils.copyProperties(dtoCarIU, car);
		
		Car updateCar=carRepository.save(car);
		
		DtoCar dtoCar=new DtoCar();
		
		BeanUtils.copyProperties(updateCar, dtoCar);
		
		return dtoCar;
	}

	@Override
	public void deleteCar(Long id) {
		
		Car car=carRepository.findById(id).orElseThrow(()->new RuntimeException("Araç Bulunamadı"));
		
		carRepository.delete(car);
		
	}

}
