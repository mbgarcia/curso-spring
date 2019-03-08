package br.com.curso.spring.exception;

public class TokenNotValidException extends RuntimeException{
	private static final long serialVersionUID = -3620406226656570955L;
	
	public TokenNotValidException(String token){
		super(String.format("Token [%s] is not valid", token));
	}

}
