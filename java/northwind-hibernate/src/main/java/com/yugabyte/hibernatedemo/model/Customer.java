package com.yugabyte.hibernatedemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "customer_id")
    private String customerID;

    @Column(name = "company_name", nullable = false, length = 40)
    private String companyName;

    @Column(name = "contact_name", nullable = true, length = 30)
    private String contactName;
    
    @Column(name = "contact_title", nullable = true, length = 30)
    private String contactTitle;

    @Column(name = "address", nullable = true, length = 60)
    private String address;

    @Column(name = "city", nullable = true, length = 15)
    private String city;
    
    @Column(name = "region", nullable = true, length = 15)
    private String region;
    
    @Column(name = "postal_code", nullable = true, length = 10)
    private String postalCode;

    @Column(name = "country", nullable = true, length = 15)
    private String country;

    @Column(name = "phone", nullable = true, length = 24)
    private String phone;
    
    @Column(name = "fax", nullable = true, length = 24)
    private String fax;

    public Customer() {
    }

    public Customer(String customerID, String companyName, String contactName, String address, String city, String region,
			String postalCode, String country, String phone, String fax) {
		super();
		this.customerID = customerID;
		this.companyName = companyName;
		this.contactName = contactName;
		this.address = address;
		this.city = city;
		this.region = region;
		this.postalCode = postalCode;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
	}

	public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Override
    public String toString() {
        return this.getCompanyName() + " " + this.getContactName();
    }

}//End class
