package com.YusufGocen.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.YusufGocen.dto.DtoAddress;
import com.YusufGocen.dto.DtoGallerist;
import com.YusufGocen.dto.DtoGalleristIU;
import com.YusufGocen.exception.BaseException;
import com.YusufGocen.exception.ErrorMessage;
import com.YusufGocen.exception.MessageType;
import com.YusufGocen.model.Address;
import com.YusufGocen.model.Gallerist;
import com.YusufGocen.repository.AddressRepository;
import com.YusufGocen.repository.GalleristRepository;
import com.YusufGocen.service.IGalleristService;

@Service
public class GalleristServiceİmpl implements IGalleristService{

	@Autowired
	private GalleristRepository galleristRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	private Gallerist createGallerist(DtoGalleristIU dtoGalleristIU) {
		
		Optional<Address> optAddress=addressRepository.findById(dtoGalleristIU.getAddressId());
		if (optAddress.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoGalleristIU.getAddressId().toString()));
		}
		
		Gallerist gallerist=new Gallerist();
		gallerist.setCreateTime(new Date());
		
		BeanUtils.copyProperties(dtoGalleristIU, gallerist);
		gallerist.setAddress(optAddress.get());
		
		return gallerist;
	}
	
	
	@Override
	public DtoGallerist saveGallerist(DtoGalleristIU dtoGalleristIU) {
		DtoGallerist dtoGallerist=new DtoGallerist();
		DtoAddress dtoAddress=new DtoAddress();
		
		Gallerist savedGallerist=galleristRepository.save(createGallerist(dtoGalleristIU));
		
		BeanUtils.copyProperties(savedGallerist, dtoGallerist);
		BeanUtils.copyProperties(savedGallerist.getAddress(), dtoAddress);
		
		dtoGallerist.setAddress(dtoAddress);
		
		return dtoGallerist;
	}


	@Override
	public List<DtoGallerist> getAllGallerist() {
		
		List<Gallerist>galleristList=galleristRepository.findAll();
		
		List<DtoGallerist>dtoGalleristList=new java.util.ArrayList<>(); 
		
	    for (Gallerist gallerist : galleristList) {

	        DtoGallerist dtoGallerist = new DtoGallerist();
	        DtoAddress dtoAddress = new DtoAddress();

	        BeanUtils.copyProperties(gallerist, dtoGallerist);

	        if (gallerist.getAddress() != null) {
	            BeanUtils.copyProperties(
	                    gallerist.getAddress(),
	                    dtoAddress
	            );

	            dtoGallerist.setAddress(dtoAddress);
	        }

	        dtoGalleristList.add(dtoGallerist);
	    }
		
		
		return dtoGalleristList;
	}


	@Override
	public DtoGallerist getGalleristById(Long id) {
	    Gallerist gallerist = galleristRepository.findById(id)
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            id.toString()
	                    )
	            ));

	    DtoGallerist dtoGallerist = new DtoGallerist();
	    DtoAddress dtoAddress = new DtoAddress();

	    BeanUtils.copyProperties(gallerist, dtoGallerist);

	    if (gallerist.getAddress() != null) {
	        BeanUtils.copyProperties(
	                gallerist.getAddress(),
	                dtoAddress
	        );

	        dtoGallerist.setAddress(dtoAddress);
	    }

	    return dtoGallerist;
	}


	@Override
	public void deleteGallerist(Long id) {
	    Gallerist gallerist = galleristRepository.findById(id)
	            .orElseThrow(() -> new BaseException(
	                    new ErrorMessage(
	                            MessageType.NO_RECORD_EXIST,
	                            id.toString()
	                    )
	            ));
	    galleristRepository.delete(gallerist);
	}
	
}



