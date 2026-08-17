package com.YusufGocen.service;

import java.util.List;

import com.YusufGocen.dto.DtoAccount;
import com.YusufGocen.dto.DtoAccountIU;

public interface IAccountService {
	
	public DtoAccount saveAccount(DtoAccountIU dtoAccountIU);
	
	public List<DtoAccount>getAllAccounts();
	
	public DtoAccount getAccountById(Long id);
	
	public void deleteAccount(Long id);

}
