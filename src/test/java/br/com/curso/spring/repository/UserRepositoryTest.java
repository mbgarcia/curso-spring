package br.com.curso.spring.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.curso.spring.model.UserEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {
	
	@Autowired
	UserRepository repository;
	
	@BeforeEach
	public void setUp() {
		repository.deleteAll();
		
		UserEntity user = new UserEntity();
		user.setFirstName("Marcelo");
		user.setLastName("Garcia");
		user.setEmail("marcelo@mail.com");
		user.setUserId("marcelo01");
		user.setEncryptedPassword("manually_encrypted");
		
		repository.save(user);
	}
	
	@Test
	public void findUserByFirstName() throws Exception{
		List<UserEntity> users = repository.findUsersByFirstName("Marcelo");
		
		assertTrue(users.size() == 1);
	}
	
	@Test
	public void findUserByFirstNameWithPagination() throws Exception{
		Pageable pageable = PageRequest.of(0, 10);
		
		List<UserEntity> users = repository.findSimilarUsersByFirstName(pageable, "Marcelo").getContent();
		
		assertTrue(users.size() == 1);
	}
}
