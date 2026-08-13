package com.YusufGocen.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.YusufGocen.dto.DtoAccount;
import com.YusufGocen.dto.DtoAddress;
import com.YusufGocen.dto.DtoCustomer;
import com.YusufGocen.dto.DtoCustomerIU;
import com.YusufGocen.exception.BaseException;
import com.YusufGocen.exception.ErrorMessage;
import com.YusufGocen.exception.MessageType;
import com.YusufGocen.model.Account;
import com.YusufGocen.model.Address;
import com.YusufGocen.model.Customer;
import com.YusufGocen.repository.AccountRepository;
import com.YusufGocen.repository.AddressRepository;
import com.YusufGocen.repository.CustomerRepository;
import com.YusufGocen.service.ICustomerService;

@Service
public class CustomerServiceİmpl implements ICustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	private Customer createCustomer(DtoCustomerIU dtoCustomerIU) {
		
		Optional<Address> optAdress=addressRepository.findById(dtoCustomerIU.getAddressId());
		if (optAdress.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoCustomerIU.getAddressId().toString()));
		}
		
		Optional<Account> optAccount=accountRepository.findById(dtoCustomerIU.getAccountId());
		if (optAccount.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoCustomerIU.getAccountId().toString()));
		}
		
		Customer customer=new Customer();
		customer.setCreateTime(new Date());
		
		BeanUtils.copyProperties(dtoCustomerIU, customer);
		
		customer.setAddress(optAdress.get());
		customer.setAccount(optAccount.get());
		
		return customer;
	}
	
	@Override
	public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU) {
		
		DtoCustomer dtoCustomer=new DtoCustomer();
		DtoAddress dtoAddress=new DtoAddress();
		DtoAccount dtoAccount=new DtoAccount();
		
		Customer savedCustomer =customerRepository.save(createCustomer(dtoCustomerIU));
		
		BeanUtils.copyProperties(savedCustomer, dtoCustomer);
		BeanUtils.copyProperties(savedCustomer.getAddress(), dtoAddress);
		BeanUtils.copyProperties(savedCustomer.getAccount(), dtoAccount);
		
		dtoCustomer.setAddress(dtoAddress);
		dtoCustomer.setAccount(dtoAccount);
		
		return dtoCustomer;
	}

	
	@Override
	public List<DtoCustomer> getAllCustomer() {
		
		List<Customer>customerList=customerRepository.findAll();
		
		List<DtoCustomer>dtoCustomerList=new java.util.ArrayList<>();
		
	    for (Customer customer : customerList) {

	        DtoCustomer dtoCustomer = new DtoCustomer();
	        DtoAddress dtoAddress = new DtoAddress();
	        DtoAccount dtoAccount = new DtoAccount();

	        BeanUtils.copyProperties(customer, dtoCustomer);

	        if (customer.getAddress() != null) {
	            BeanUtils.copyProperties(customer.getAddress(), dtoAddress);
	            dtoCustomer.setAddress(dtoAddress);
	        }

	        if (customer.getAccount() != null) {
	            BeanUtils.copyProperties(customer.getAccount(), dtoAccount);
	            dtoCustomer.setAccount(dtoAccount);
	        }

	        dtoCustomerList.add(dtoCustomer);
	    }
		
		return dtoCustomerList;
	}

	
	@Override
	public DtoCustomer getCustomerById(Long id) {
		
		Customer customer=customerRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));
		
		DtoCustomer dtoCustomer=new DtoCustomer();
		DtoAddress dtoAddress=new DtoAddress();
		DtoAccount dtoAccount=new DtoAccount();
		
		BeanUtils.copyProperties(customer, dtoCustomer);
		
	    if (customer.getAddress() != null) {
	        BeanUtils.copyProperties(customer.getAddress(), dtoAddress);
	        dtoCustomer.setAddress(dtoAddress);
	    }

	    if (customer.getAccount() != null) {
	        BeanUtils.copyProperties(customer.getAccount(), dtoAccount);
	        dtoCustomer.setAccount(dtoAccount);
	    }
		
		return dtoCustomer;
	}

	@Override
	public void deleteCustomer(Long id) {
		Customer customer=customerRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));
		customerRepository.delete(customer);
	}

}
