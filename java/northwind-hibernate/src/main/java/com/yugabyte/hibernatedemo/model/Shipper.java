package com.yugabyte.hibernatedemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shippers")
public class Shipper {
	
	@Id
	@Column(name = "shipper_id")
	int shipperId;
	
	@Column(name = "company_name", length = 40)
	String companyName;
	
	@Column(name = "phone", length = 24)
	String phone;
	
	public Shipper() {
		
	}

	public Shipper(int shipperId, String companyName, String phone) {
		super();
		this.shipperId = shipperId;
		this.companyName = companyName;
		this.phone = phone;
	}

	public int getShipperId() {
		return shipperId;
	}

	public void setShipperId(int shipperId) {
		this.shipperId = shipperId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Shipper [companyName=" + companyName + ", phone=" + phone + "]";
	}
}
