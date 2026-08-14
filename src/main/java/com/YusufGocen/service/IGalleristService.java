package com.YusufGocen.service;

import java.util.List;

import com.YusufGocen.dto.DtoGallerist;
import com.YusufGocen.dto.DtoGalleristIU;

public interface IGalleristService {
	public DtoGallerist saveGallerist(DtoGalleristIU dtoGalleristIU);
	
	public List<DtoGallerist>getAllGallerist();
	
	public DtoGallerist getGalleristById(Long id);
	
	public void deleteGallerist(Long id);

}
