package com.YusufGocen.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.YusufGocen.dto.DtoAccount;
import com.YusufGocen.dto.DtoAccountIU;
import com.YusufGocen.exception.BaseException;
import com.YusufGocen.exception.ErrorMessage;
import com.YusufGocen.model.Account;
import com.YusufGocen.repository.AccountRepository;
import com.YusufGocen.service.IAccountService;
import com.YusufGocen.exception.MessageType;

@Service
public class AccountServiceImpl implements IAccountService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	private Account createAccount(DtoAccountIU dtoAccountIU) {
		
		Account account=new Account();
		account.setCreateTime(new Date());
		
		BeanUtils.copyProperties(dtoAccountIU, account);
		
		return account;
		
	}

	@Override
	public DtoAccount saveAccount(DtoAccountIU dtoAccountIU) {
		
		DtoAccount dtoAccount=new DtoAccount();
		
		Account savedAccount=accountRepository.save(createAccount(dtoAccountIU));
		BeanUtils.copyProperties(savedAccount, dtoAccount);
		
		return dtoAccount;
	}

	@Override
	public List<DtoAccount> getAllAccounts() {
		
		List<Account>accountList = accountRepository.findAll();
		List<DtoAccount>dtoAccountsList=new ArrayList<>();

		for (Account account : accountList) {
			DtoAccount dtoAccount=new DtoAccount();
			
			BeanUtils.copyProperties(account, dtoAccount);
			
			dtoAccountsList.add(dtoAccount);
		}
		
		
		return dtoAccountsList;
	}
	
	@Override
	public DtoAccount getAccountById(Long id) {

	    Account account = accountRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

	    DtoAccount dtoAccount = new DtoAccount();

	    BeanUtils.copyProperties(account, dtoAccount);

	    return dtoAccount;
	}

	
	@Override
	public void deleteAccount(Long id) {
	    Account account = accountRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));
	    
	    accountRepository.delete(account);
	}


}
