package br.com.curso.spring.request;

import java.util.List;

public class UserControllerPutRequest {
	private String firstName;

	private String lastName;
	
	private List<AddressData> addresses;

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

	public List<AddressData> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressData> addresses) {
		this.addresses = addresses;
	}
}
