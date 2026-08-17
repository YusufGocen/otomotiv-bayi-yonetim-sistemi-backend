package com.YusufGocen.service;

import java.util.List;

import com.YusufGocen.dto.DtoGalleristCar;
import com.YusufGocen.dto.DtoGalleristCarIU;

public interface IGalleristCarService {

	public DtoGalleristCar saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU);
	
	public List<DtoGalleristCar>getAllGaleristCars();
	
	public DtoGalleristCar getGalleristCarById(Long id);
	
	public void deleteGalleristCar(Long id);
}
