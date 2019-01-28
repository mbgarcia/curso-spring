package br.com.curso.spring.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.curso.spring.model.UserEntity;
import br.com.curso.spring.response.UserControllerResponse;
import br.com.curso.spring.service.UserService;

class UserControllerTest {
	
	@InjectMocks
	UserController controller;
	
	@Mock
	UserService service;

	@BeforeEach
	public void setUpBefore(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testaApenasChamada() throws Exception {
		UserEntity entity = new UserEntity();
		entity.setEmail("user@mail.com");
		entity.setFirstName("User");
		entity.setLastName("Test");
		
		when(service.findUserByPublicId(anyString())).thenReturn(entity);
		
//		UserControllerResponse response = controller.getUser("qwerty123456");
//		
//		assertEquals(entity.getFirstName(), response.getFirstName());
	}
	
	@Test
	void getUserById() throws Exception {
		UserEntity entity = new UserEntity();
		entity.setEmail("user@mail.com");
		entity.setFirstName("User");
		entity.setLastName("Test");
		
		when(service.findUserByPublicId(anyString())).thenReturn(entity);
		
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(
				get("/users/qwerty123456").accept(MediaType.APPLICATION_JSON)
			)
		    .andExpect(jsonPath("$.firstName").value("User"));		
	}
}
