package br.com.curso.spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.curso.spring.model.UserEntity;
import br.com.curso.spring.request.UserControllerPostRequest;
import br.com.curso.spring.response.UserControllerResponse;
import br.com.curso.spring.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public String getUser(){
		return "Retorno do método GET";
	}
	
	@PostMapping
	public UserControllerResponse createUser(@RequestBody UserControllerPostRequest userDetails){
		ModelMapper mapper = new ModelMapper();
		
		UserEntity user = mapper.map(userDetails, UserEntity.class);
		
		return mapper.map(userService.createUser(user), UserControllerResponse.class);
	}

	@PutMapping
	public String updateUser(){
		return "Retorno do metodo PUT";
	}
	
	@DeleteMapping
	public String deleteUser(){
		return "Retorno do metodo DELETE";
	}
}
