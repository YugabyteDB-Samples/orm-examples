package com.yugabyte.hibernatedemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "us_states")
public class USState {

	@Id
	@Column(name = "state_id")
	int stateId;
	
	@Column(name = "state_name", length = 100)
	String stateName;
	
	@Column(name = "state_abbr", length = 2)
	String stateAbbr;
	
	@Column(name = "state_region", length = 50)
	String stateRegion;
	
	public USState () {
		
	}

	public USState(int stateId, String stateName, String stateAbbr, String stateRegion) {
		super();
		this.stateId = stateId;
		this.stateName = stateName;
		this.stateAbbr = stateAbbr;
		this.stateRegion = stateRegion;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateAbbr() {
		return stateAbbr;
	}

	public void setStateAbbr(String stateAbbr) {
		this.stateAbbr = stateAbbr;
	}

	public String getStateRegion() {
		return stateRegion;
	}

	public void setStateRegion(String stateRegion) {
		this.stateRegion = stateRegion;
	}
	
}
