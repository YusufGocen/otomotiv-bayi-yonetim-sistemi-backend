package com.YusufGocen.service;

import java.util.List;

import com.YusufGocen.dto.DtoCar;
import com.YusufGocen.dto.DtoCarIU;

public interface ICarService {
	
	public DtoCar saveCar(DtoCarIU dtoCarIU);

	public List<DtoCar>getAllCars();
	
	public DtoCar getCarbyId(Long id);
	
	public DtoCar updateCar(Long id , DtoCarIU dtoCarIU);
	
	public void deleteCar(Long id);
}
