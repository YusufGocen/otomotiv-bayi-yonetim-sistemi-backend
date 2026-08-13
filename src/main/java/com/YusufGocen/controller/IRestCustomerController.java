package com.YusufGocen.controller;

import java.util.List;

import com.YusufGocen.dto.DtoCustomer;
import com.YusufGocen.dto.DtoCustomerIU;

public interface IRestCustomerController {

	public RootEntity<DtoCustomer> saveCustomer(DtoCustomerIU dtoCustomerIU);
	
	public RootEntity<List<DtoCustomer>>getAllCustomer();
	
	public RootEntity<DtoCustomer>getCustomerById(Long id);
	
	public RootEntity<Void>deleteCustomer(Long id);
}
