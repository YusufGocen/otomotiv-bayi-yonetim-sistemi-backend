package com.YusufGocen.controller;

import java.util.List;

import com.YusufGocen.dto.DtoAccount;
import com.YusufGocen.dto.DtoAccountIU;

public interface IRestAccountController {

	public RootEntity<DtoAccount> saveAccount(DtoAccountIU dtoAccountIU);
	
	public RootEntity<List<DtoAccount>>getAllAccount();
	
	public RootEntity<DtoAccount>getAccountById(Long id);
	
	public RootEntity<Void>deleteAccount(Long id);
	
}
