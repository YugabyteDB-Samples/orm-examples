package controller

import (
	"encoding/json"
	"fmt"
	"gorm-example/src/model"
	"net/http"

	"github.com/google/uuid"
	"github.com/gorilla/mux"
)

/* handler function for GET method */
var SelectOrders = func(w http.ResponseWriter, r *http.Request) {
	orders := []model.Order{}
	err := GetDB().Table("orders").Find(&orders).Error
	if err != nil {
		msg := map[string]interface{}{"status": false, "message": err}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
	} else {
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(orders)
	}
}

/* handler function for GET method */
var SelectOrder = func(w http.ResponseWriter, r *http.Request) {
	orderId := mux.Vars(r)["orderId"]
	fmt.Printf("finding order with order_id = %s", orderId)

	order := &model.Order{}
	err := GetDB().Table("orders").Where("order_id = ?", orderId).First(order).Error
	if err != nil {
		msg := map[string]interface{}{"status": false, "message": "Failed to find order"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
	} else {
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(order)
	}
}

/* handler function for POST method */
var CreateOrder = func(w http.ResponseWriter, r *http.Request) {
	order := &model.Order{}
	err1 := json.NewDecoder(r.Body).Decode(order)
	if err1 != nil {
		msg := map[string]interface{}{"status": false, "message": "Error while decoding request body"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	/* calculate the total price using products table and product_id(s) given in the request */
	var orderTotal float64
	for _, orderline := range order.Products {
		product := &model.Product{}
		err := GetDB().Table("products").Where("product_id = ?", orderline.ProductId).First(product).Error
		if err == nil {
			orderTotal += product.Price * float64(orderline.Units)
		}
	}

	/* set the calculated total in the order structure and create the order */
	order.OrderTotal = orderTotal
	err2 := GetDB().Create(&order).Error
	if err2 != nil {
		msg := map[string]interface{}{"status": false, "message": err2}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	/* once the order is created, also store products ordered for that order in orderline table */
	for _, orderline := range order.Products {
		orderline.OrderId = order.OrderId
		err := GetDB().Create(&orderline).Error
		if err != nil {
			msg := map[string]interface{}{"status": false, "message": err}
			w.Header().Add("Content-Type", "application/json")
			json.NewEncoder(w).Encode(msg)
			return
		}
	}

	w.Header().Add("Content-Type", "application/json")
	json.NewEncoder(w).Encode(order)
}

/* handler function for DELETE method */
var DeleteOrder = func(w http.ResponseWriter, r *http.Request) {
	orderId := mux.Vars(r)["orderId"]
	fmt.Printf("deleting order with order_id = %s", orderId)

	order := &model.Order{}
	err1 := GetDB().Table("orders").Where("order_id = ?", orderId).First(order).Error
	if err1 != nil {
		msg := map[string]interface{}{"status": false, "message": "Failed to find order"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	err2 := GetDB().Delete(&order).Error
	if err2 != nil {
		msg := map[string]interface{}{"status": false, "message": err2}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	msg := map[string]interface{}{"status": true, "message": "Order deleted successfully"}
	w.Header().Add("Content-Type", "application/json")
	json.NewEncoder(w).Encode(msg)
}

/* handler function for PUT method */
var UpdateOrder = func(w http.ResponseWriter, r *http.Request) {
	order := &model.Order{}
	err1 := json.NewDecoder(r.Body).Decode(order)
	if err1 != nil {
		msg := map[string]interface{}{"status": false, "message": "Error while decoding request body"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	order.OrderId, _ = uuid.Parse(mux.Vars(r)["orderId"])
	fmt.Printf("deleting order with order_id = %s", order.OrderId)

	err2 := GetDB().Save(&order).Error
	if err2 != nil {
		msg := map[string]interface{}{"status": false, "message": err2}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	w.Header().Add("Content-Type", "application/json")
	json.NewEncoder(w).Encode(order)
}
