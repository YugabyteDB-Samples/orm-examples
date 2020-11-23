package com.yugabyte.hibernatedemo.model;

import java.io.Serializable;

public class EmployeeTerritoryPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2838358283335810703L;
	
	
	private int employeeId;
	private String territoryId;
	
	public EmployeeTerritoryPK() {
	}
	
	public EmployeeTerritoryPK(int employeeId, String territoryId) {
		super();
		this.employeeId = employeeId;
		this.territoryId = territoryId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getTerritoryId() {
		return territoryId;
	}
	public void setTerritoryId(String territoryId) {
		this.territoryId = territoryId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + employeeId;
		result = prime * result + ((territoryId == null) ? 0 : territoryId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeTerritoryPK other = (EmployeeTerritoryPK) obj;
		if (employeeId != other.employeeId)
			return false;
		if (territoryId == null) {
			if (other.territoryId != null)
				return false;
		} else if (!territoryId.equals(other.territoryId))
			return false;
		return true;
	}

}
