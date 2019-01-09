package br.com.curso.spring.exception;

public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -3620406226656570955L;
	
	public UserNotFoundException(String publicId){
		super(String.format("User [%s] not found", publicId));
	}

}
