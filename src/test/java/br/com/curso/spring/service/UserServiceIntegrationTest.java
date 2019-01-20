package br.com.curso.spring.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.curso.spring.model.UserEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceIntegrationTest {
	
	@Autowired
	UserService service;
	
	@BeforeEach
	public void setUp() {
		UserEntity user = new UserEntity();
		user.setFirstName("Marcelo");
		user.setLastName("Garcia");
		user.setEmail("marcelo@mail.com");
		user.setPassword("123456");
		
		service.createUser(user);
	}
	
	@Test
	public void listUsers() throws Exception{
		List<UserEntity> users = service.getUsers(1, 10);
		
		assertTrue(users.size() == 1);
	}
}
