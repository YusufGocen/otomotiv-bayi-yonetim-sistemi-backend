package com.YusufGocen.controller;

import java.util.List;

import com.YusufGocen.dto.DtoGalleristCar;
import com.YusufGocen.dto.DtoGalleristCarIU;

public interface IRestGalleristCarController {

	public RootEntity<DtoGalleristCar> saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU);
	
	public RootEntity<List<DtoGalleristCar>> getAllGalleristCars();
	
	public RootEntity<DtoGalleristCar>getGalleristCarById(Long id);
	
	public RootEntity<Void>deleteGalleristCar(Long id);
}
