package com.YusufGocen.controller;

import java.util.List;

import com.YusufGocen.dto.DtoCarSaled;
import com.YusufGocen.dto.DtoSaledCarIU;

public interface IRestSaledCarController {
	public RootEntity<DtoCarSaled> buyCar(DtoSaledCarIU dtoSaledCarIU);
	
	public RootEntity<List<DtoCarSaled>>getAllSaledCars();
	
	public RootEntity<DtoCarSaled>getSaledCarById(Long id);
}
