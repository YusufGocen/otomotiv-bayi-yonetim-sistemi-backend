package com.YusufGocen.service;

import java.util.List;

import com.YusufGocen.dto.DtoCarSaled;
import com.YusufGocen.dto.DtoSaledCarIU;

public interface ISaledCarService {

	public DtoCarSaled buyCar(DtoSaledCarIU dtoSaledCarIU);
	
	public List<DtoCarSaled>getAllSaledCars();
	
	public DtoCarSaled getSaledCarById(Long id);
}
