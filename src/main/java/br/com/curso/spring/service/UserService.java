package br.com.curso.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.curso.spring.model.UserEntity;
import br.com.curso.spring.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	public UserEntity createUser(UserEntity user){
		if (repository.existsByEmail(user.getEmail()))
			throw new RuntimeException("User already exists");
		
		user.setUserId("testeUserID");
		user.setEncryptedPassword("password");
		UserEntity storedUser = repository.save(user);
		
		return storedUser;
	}
}