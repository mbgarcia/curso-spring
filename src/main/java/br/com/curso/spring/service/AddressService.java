package br.com.curso.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.curso.spring.model.AddressEntity;
import br.com.curso.spring.model.UserEntity;
import br.com.curso.spring.repository.AddressRepository;

@Service
public class AddressService {
	@Autowired
	AddressRepository repository;
	
	@Autowired
	UserService userService;
	
	public List<AddressEntity> listUserAddresses(String publicId){
		UserEntity user = userService.findUserByPublicId(publicId);
		
		return repository.findByUser(user);
	}
}
