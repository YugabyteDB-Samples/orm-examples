package com.yugabyte.hibernatedemo.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private int orderId;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;
    
    @Column(name = "order_date")
    Date orderDate;
    
    @Column(name = "required_date")
    Date requiredDate;
    
    @Column(name = "shipped_date")
    Date shippedDate;
    
    @ManyToOne
    @JoinColumn(name = "ship_via", referencedColumnName = "shipper_id" )
    Shipper shipVia;
    
    @Column(name = "freight")
    float freight;
    
    @Column(name = "ship_name", length = 40)
    String shipName;
    
    @Column(name = "ship_address", length = 60)
    String shipAddress;
    
    @Column(name = "ship_city", length = 15)
    String shipCity;
    
    @Column(name = "ship_region", length = 15)
    String shipRegion;
    
    @Column(name = "ship_postal_code", length = 10)
    String shipPostalCode;
    
    @Column(name = "ship_country", length = 15)
    String shipCountry;
    
    public Order() {
    	
    }

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public Shipper getShipVia() {
		return shipVia;
	}

	public void setShipVia(Shipper shipVia) {
		this.shipVia = shipVia;
	}

	public float getFreight() {
		return freight;
	}

	public void setFreight(float freight) {
		this.freight = freight;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String getShipCity() {
		return shipCity;
	}

	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getShipRegion() {
		return shipRegion;
	}

	public void setShipRegion(String shipRegion) {
		this.shipRegion = shipRegion;
	}

	public String getShipPostalCode() {
		return shipPostalCode;
	}

	public void setShipPostalCode(String shipPostalCode) {
		this.shipPostalCode = shipPostalCode;
	}

	public String getShipCountry() {
		return shipCountry;
	}

	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + "]";
	}
}
