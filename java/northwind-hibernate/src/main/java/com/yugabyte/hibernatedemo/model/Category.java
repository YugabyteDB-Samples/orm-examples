package com.yugabyte.hibernatedemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "category_id")
    private int categoryID;

    @Column(name = "category_name", nullable = false, length = 15)
    private String categoryName;

    @Column(name = "description", nullable = false, length = 100)
    private String description;
    
    @Column(name = "picture")
    private byte[] picture;

    public Category() {
    }

    public Category(int categoryID, String categoryName, String description, byte[] picture) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.description = description;
        this.picture = picture;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	@Override
    public String toString() {
        return this.getCategoryName();
    }

}