package br.com.curso.spring.response;

public class EmailVerificationResponse {
	public String token;
	
	public Boolean verified;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
}
