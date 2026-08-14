package com.YusufGocen.controller;

import java.util.List;

import com.YusufGocen.dto.DtoGallerist;
import com.YusufGocen.dto.DtoGalleristIU;

public interface IRestGalleristController {

	public RootEntity<DtoGallerist> saveGallerist(DtoGalleristIU dtoGalleristIU);
	
	public RootEntity<List<DtoGallerist>>getAllGallerist();
	
	public RootEntity<DtoGallerist>getGalleristById(Long id);
	
	public RootEntity<Void>deleteGallerist(Long id);
}
