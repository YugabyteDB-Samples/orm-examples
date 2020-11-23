package com.yugabyte.hibernatedemo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@IdClass(EmployeeTerritoryPK.class)
@Table(name = "employee_territories")
public class EmployeeTerritory {
	
	@Id
	private int employeeId;
	
	@Id
	private String territoryId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
	private Employee employee;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "territory_id", referencedColumnName = "territory_id")
	private Territory territory;

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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory territory) {
		this.territory = territory;
	}

	@Override
	public String toString() {
		return "EmployeeTerritory [employee=" + employee.toString()
				+ ", territory=" + territory.toString() + "]";
	} 

}
