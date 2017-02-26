package pe.com.dbs.beerapp.models;

import java.io.Serializable;

public class Customer implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private Integer				customerId;

	private String				age;

	private String				email;

	private String				pass;

	private Integer				state;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
