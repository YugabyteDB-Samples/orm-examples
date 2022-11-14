package com.yugabyte.mybatis.model.response;

import com.yugabyte.mybatis.model.User;
import com.yugabyte.mybatis.model.response.*;

import java.util.*;

public class DeleteUserResponse {
    private User user;
    private List < CreateOrderResponse > ordersPlacedbyUser;

    public User getUser() {
        return this.user;
    }
    public List < CreateOrderResponse > getOrdersPlacedByUser() {
        return this.ordersPlacedbyUser;
    }
    public void setOrdersPlacedByUser(List < CreateOrderResponse > ordersPlacedbyUser) {
        this.ordersPlacedbyUser = ordersPlacedbyUser;
    }
    public void setUser(User user) {
        this.user = user;
    }

}