package pe.com.dbs.beerapp.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;

public class Bar {

	private Integer				barId;

	private String				address;
/*
	private Time				hourClose;

	private Time				hourOpen;
*/
	private BigDecimal			latitude;

	private BigDecimal			longitude;

	private String				name;

	private String				phone;

	private Integer				state;

	public Integer getBarId() {
		return barId;
	}

	public void setBarId(Integer barId) {
		this.barId = barId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

/*
	public Time getHourClose() {
		return hourClose;
	}

	public void setHourClose(Time hourClose) {
		this.hourClose = hourClose;
	}

	public Time getHourOpen() {
		return hourOpen;
	}

	public void setHourOpen(Time hourOpen) {
		this.hourOpen = hourOpen;
	}
*/
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
