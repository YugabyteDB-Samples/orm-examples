package com.yugabyte.hibernatedemo.model;

import java.io.Serializable;

public class OrderDetailPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -756050979641657152L;
	private int orderID;
    private int productID;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.orderID;
        hash = 59 * hash + this.productID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderDetailPK other = (OrderDetailPK) obj;
        if (this.orderID != other.orderID) {
            return false;
        }
        if (this.productID != other.productID) {
            return false;
        }
        return true;
    }

}//End class

