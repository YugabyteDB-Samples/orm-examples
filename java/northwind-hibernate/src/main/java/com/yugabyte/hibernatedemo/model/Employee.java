package com.yugabyte.hibernatedemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "employee_id")
    private int employeeID;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastname;

    @Column(name = "first_name", nullable = false, length = 10)
    private String firstname;

    @Column(name = "title", length = 30)
    private String title;
    
    @Column(name = "title_of_courtesy", length = 25)
    private String titleOfCourtesy;

    @Column(name = "birth_date")
    private String birthdate;

    @Column(name = "hire_date")
    private String hiredate;
    
    @Column(name = "address", length = 60)
    private String address;
    
    @Column(name = "city", length = 15)
    private String city;
    
    @Column(name = "region", length = 15)
    private String region;
    
    @Column(name = "postal_code", length = 10)
    private String postal_code;
    
    @Column(name = "country", length = 15)
    private String country;
    
    @Column(name = "home_phone", length = 24)
    private String homePhone;
    
    @Column(name = "extension", length = 4)
    private String extension;
    
    @Column(name = "photo")
    private byte[] photo;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "reports_to")
    private Integer reportsTo;
    
    @Column(name = "photo_path", length = 255)
    private String photoPath;
    
    

    public Employee() {
    }

    public Employee(int employeeID, String lastname, String firstname, String title, String titleOfCourtesy,
			String birthdate, String hiredate, String address, String city, String region, String postal_code,
			String country, String homePhone, String extension, byte[] photo, String notes, Integer reportsTo, String photoPath) {
		super();
		this.employeeID = employeeID;
		this.lastname = lastname;
		this.firstname = firstname;
		this.title = title;
		this.titleOfCourtesy = titleOfCourtesy;
		this.birthdate = birthdate;
		this.hiredate = hiredate;
		this.address = address;
		this.city = city;
		this.region = region;
		this.postal_code = postal_code;
		this.country = country;
		this.homePhone = homePhone;
		this.extension = extension;
		this.photo = photo;
		this.notes = notes;
		this.reportsTo = reportsTo;
		this.photoPath = photoPath;
	}

	public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public String getTitleOfCourtesy() {
		return titleOfCourtesy;
	}

	public void setTitleOfCourtesy(String titleOfCourtesy) {
		this.titleOfCourtesy = titleOfCourtesy;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(Integer reportsTo) {
		if (reportsTo != null) {
			this.reportsTo = reportsTo;
		}
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	@Override
    public String toString() {
        return "Employee{" + "lastname=" + lastname + ", firstname=" + firstname + '}';
    }

}
