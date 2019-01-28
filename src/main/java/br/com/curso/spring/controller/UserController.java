package br.com.curso.spring.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.curso.spring.model.AddressEntity;
import br.com.curso.spring.model.UserEntity;
import br.com.curso.spring.request.AddressData;
import br.com.curso.spring.request.UserControllerPostRequest;
import br.com.curso.spring.request.UserControllerPutRequest;
import br.com.curso.spring.response.EmailVerificationResponse;
import br.com.curso.spring.response.UserControllerResponse;
import br.com.curso.spring.service.AddressService;
import br.com.curso.spring.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;


@RestController
@RequestMapping("/users")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;
	
	@GetMapping(path="/{publicId}", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public Resource<UserControllerResponse>  getUser(@PathVariable String publicId){
		UserEntity user = userService.findUserByPublicId(publicId);
		
		UserControllerResponse response = new ModelMapper().map(user, UserControllerResponse.class);
		
		Link self = linkTo(UserControllerResponse.class).slash(publicId).withSelfRel();
		
		Link addresses = linkTo(methodOn(UserController.class).getUserAddresses(publicId)).withRel("addresses");
		
		response.add(self, addresses);
		
		return new Resource<>(response);
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="Authorization", value="Bearer JWT Token", paramType="header")
	})
	@GetMapping(produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<UserControllerResponse> getUsers(@RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="10") int limit){
		List<UserEntity> users = userService.getUsers(page, limit);
		
		Type listType = new TypeToken<List<UserControllerResponse>>() {}.getType();
		
		return new ModelMapper().map(users, listType);
	}
	
	@PostMapping(consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			, produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserControllerResponse createUser(@RequestBody UserControllerPostRequest userDetails){
		ModelMapper mapper = new ModelMapper();
		
		UserEntity user = mapper.map(userDetails, UserEntity.class);
		
		return mapper.map(userService.createUser(user), UserControllerResponse.class);
	}

	@PutMapping(path="/{publicId}"
			, consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			, produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public UserControllerResponse updateUser(@PathVariable String publicId, @RequestBody UserControllerPutRequest request){
		return new ModelMapper().map(userService.updateUser(publicId, request), UserControllerResponse.class);
	}
	
	@DeleteMapping(path="/{publicId}")
	public void deleteUser(@PathVariable String publicId){
		userService.deleteUser(publicId);;
	}
	
	@GetMapping(path="/{publicId}/addresses", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public Resources<AddressData> getUserAddresses(@PathVariable String publicId){
		List<AddressEntity> list = addressService.listUserAddresses(publicId);
		
		Type listType = new TypeToken<List<AddressData>>() {}.getType();
		
		List<AddressData> addresses = new ModelMapper().map(list, listType);
		
		return new Resources<>(addresses);
	}
	
	@GetMapping(path="/email-verification", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@CrossOrigin(origins="*")
	public EmailVerificationResponse emailVerification(@RequestParam String token) {
		logger.info("Verification of token: " + token);
		EmailVerificationResponse response = new EmailVerificationResponse();
		response.setVerified(userService.verifyEmailToken(token));
		response.setToken(token);
		return response;
	}
}
