package br.com.curso.spring.response;

import java.util.List;

import br.com.curso.spring.request.AddressData;

public class UserControllerResponse {
	private String userId;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private List<AddressData> addresses;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<AddressData> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressData> addresses) {
		this.addresses = addresses;
	}
}
