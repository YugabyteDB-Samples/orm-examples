package com.yugabyte.hibernatedemo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "territories")
public class Territory {
	
	@Id
	@Column(name = "territory_id")
	String territoryId;
	
	@Column(name = "territory_description")
	String territoryDescription;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "region_id", referencedColumnName = "region_id")
	Region region;

	public String getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(String territoryId) {
		this.territoryId = territoryId;
	}

	public String getTerritoryDescription() {
		return territoryDescription;
	}

	public void setTerritoryDescription(String territoryDescription) {
		this.territoryDescription = territoryDescription;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "Territories [territoryDescription=" + territoryDescription + "]";
	}

}
