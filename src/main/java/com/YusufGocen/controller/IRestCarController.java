package com.YusufGocen.controller;

import java.util.List;

import com.YusufGocen.dto.DtoCar;
import com.YusufGocen.dto.DtoCarIU;

public interface IRestCarController {
	
	public RootEntity<DtoCar>saveCar(DtoCarIU dtoCarIU);
	
	public RootEntity<List<DtoCar>>getAllCars();

	public RootEntity<DtoCar>getCarById(Long id);
	
	public RootEntity<DtoCar>updateCar(Long id,DtoCarIU dtoCarIU);
	
	public RootEntity<Void>deleteCar(Long id);
	
}
