package com.yugabyte.hibernatedemo.model.response;

import com.yugabyte.hibernatedemo.model.User;
import com.yugabyte.hibernatedemo.model.response.*;

import java.util.*;

public class DeleteUserResponse {
    private User user;
    private List < CreateOrderResponse > ordersPlacedbyUser;

    public User getUser() {
        return this.user;
    }
    public List < CreateOrderResponse > getordersPlacedbyUser() {
        return this.ordersPlacedbyUser;
    }
    public void setordersPlacedbyUser(List < CreateOrderResponse > ordersPlacedbyUser) {
        this.ordersPlacedbyUser = ordersPlacedbyUser;
    }
    public void setUser(User user) {
        this.user = user;
    }

}