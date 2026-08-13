package com.YusufGocen.service;

import java.util.List;

import com.YusufGocen.dto.DtoCustomer;
import com.YusufGocen.dto.DtoCustomerIU;

public interface ICustomerService {

	public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU);
	
	public List<DtoCustomer>getAllCustomer();
	
	public DtoCustomer getCustomerById(Long id);
	
	public void deleteCustomer(Long id);
}
