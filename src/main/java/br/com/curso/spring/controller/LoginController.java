package br.com.curso.spring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.curso.spring.request.UserLoginRequest;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
public class LoginController {
	
	@ApiResponses({
        @ApiResponse(code = 200, message = "Success"
        		, responseHeaders = {@ResponseHeader(name="authorization", description="Bearer JWT Token", response = String.class)
        							,@ResponseHeader(name="userId", description="Public User ID ", response = String.class)}
        )
    })	
	@PostMapping("/users/login")
	public void login(@RequestBody UserLoginRequest request) {
		throw new IllegalStateException("Apenas para documentacao no Swagger. Implementado com Spring Security");
	}
}
