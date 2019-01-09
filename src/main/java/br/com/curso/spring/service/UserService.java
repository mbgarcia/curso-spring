package br.com.curso.spring.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.curso.spring.exception.UserNotFoundException;
import br.com.curso.spring.model.UserEntity;
import br.com.curso.spring.repository.UserRepository;
import br.com.curso.spring.shared.Utils;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	public UserEntity createUser(UserEntity user){
		if (repository.existsByEmail(user.getEmail()))
			throw new RuntimeException("User already exists");
		
		user.setUserId(utils.generateUserId(30));
		user.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
		UserEntity storedUser = repository.save(user);
		
		return storedUser;
	}
	
	public UserEntity findUserByPublicId(String publicId) {
		UserEntity user =  repository.findByUserId(publicId);
		
		if (user == null)
			throw new UserNotFoundException(publicId);
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = repository.findByEmail(email);
		
		if (user == null) throw new UsernameNotFoundException(email);
		
		return new User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
	}
}