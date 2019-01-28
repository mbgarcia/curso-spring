package br.com.curso.spring.repository;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.curso.spring.model.AddressEntity;
import br.com.curso.spring.model.UserEntity;


import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AddressRepositoryTest {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressRepository addressRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		userRepository.deleteAll();
		
		UserEntity user = new UserEntity();
		user.setFirstName("Marcelo");
		user.setLastName("Garcia");
		user.setEmail("marcelo@mail.com");
		user.setUserId("marcelo01");
		user.setEncryptedPassword("manually_encrypted");
		
		AddressEntity address = new AddressEntity();
		address.setCity("BEL");
		address.setCountry("BR");
		address.setPostalCode("123456-789");
		address.setStreet("1st Street");
		address.setState("PA");
		address.setUser(user);
		
		user.getAddresses().add(address);
		
		userRepository.save(user);		
	}

	@Test
	void test() {
		List<AddressEntity> addresses = addressRepository.findByUserId("marcelo01");
		
		assertEquals("BEL", addresses.get(0).getCity());
	}
}
