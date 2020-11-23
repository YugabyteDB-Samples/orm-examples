package com.yugabyte.hibernatedemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "region")
public class Region {
	
	@Id
	@Column(name = "region_id")
	int regionId;
	
	@Column(name = "region_description")
	String regionDescription;
	
	public Region() {
		
	}

	public Region(int regionId, String regionDescription) {
		super();
		this.regionId = regionId;
		this.regionDescription = regionDescription;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getRegionDescription() {
		return regionDescription;
	}

	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}

	@Override
	public String toString() {
		return "Region [regionDescription=" + regionDescription + "]";
	}

}
