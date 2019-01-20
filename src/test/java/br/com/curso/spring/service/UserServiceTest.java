package br.com.curso.spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.curso.spring.exception.UserNotFoundException;
import br.com.curso.spring.model.UserEntity;
import br.com.curso.spring.repository.UserRepository;

public class UserServiceTest {
	@InjectMocks
	UserService service;
	
	@Mock
	UserRepository repository;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testExistsEmailUser() {
		String email = "john@wall.com";
		UserEntity entity = new UserEntity();
		entity.setEmail(email);
		entity.setFirstName("John");
		entity.setLastName("Snow");
		
		when(repository.findByEmail(email)).thenReturn(entity);
		
		UserEntity consult = service.findUserByEmail(email);
		
		assertEquals(consult.getFirstName(), entity.getFirstName());
		assertEquals(consult.getLastName(), entity.getLastName());
	}
	
	@Test
	public void testNotExistsEmailUser() {
		
		when(repository.findByEmail(anyString())).thenReturn(null);
		
		assertThrows(UserNotFoundException.class, () -> service.findUserByEmail("john@mail.com"));
	}	
}