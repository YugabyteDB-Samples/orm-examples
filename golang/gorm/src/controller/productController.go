package controller

import (
	"encoding/json"
	"fmt"
	"gorm-example/src/model"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

/* handler function for GET method */
var SelectProducts = func(w http.ResponseWriter, r *http.Request) {
	products := []model.Product{}
	err := GetDB().Table("products").Find(&products).Error
	if err != nil {
		msg := map[string]interface{}{"status": false, "message": err}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
	} else {
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(products)
	}
}

/* handler function for GET method */
var SelectProduct = func(w http.ResponseWriter, r *http.Request) {
	productId := mux.Vars(r)["productId"]
	fmt.Printf("finding product with product_id = %s", productId)

	product := &model.Product{}
	err := GetDB().Table("products").Where("product_id = ?", productId).First(product).Error
	if err != nil {
		msg := map[string]interface{}{"status": false, "message": "Failed to find product"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
	} else {
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(product)
	}
}

/* handler function for POST method */
var CreateProduct = func(w http.ResponseWriter, r *http.Request) {
	product := &model.Product{}
	err1 := json.NewDecoder(r.Body).Decode(product)
	if err1 != nil {
		msg := map[string]interface{}{"status": false, "message": "Error while decoding request body"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	err2 := GetDB().Create(&product).Error
	if err2 != nil {
		msg := map[string]interface{}{"status": false, "message": err2}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	w.Header().Add("Content-Type", "application/json")
	json.NewEncoder(w).Encode(product)
}

/* handler function for DELETE method */
var DeleteProduct = func(w http.ResponseWriter, r *http.Request) {
	productId := mux.Vars(r)["productId"]
	fmt.Printf("deleting product with product_id = %s", productId)

	product := &model.Product{}
	err1 := GetDB().Table("products").Where("product_id = ?", productId).First(product).Error
	if err1 != nil {
		msg := map[string]interface{}{"status": false, "message": "Failed to find product"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	err2 := GetDB().Delete(&product).Error
	if err2 != nil {
		msg := map[string]interface{}{"status": false, "message": err2}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	msg := map[string]interface{}{"status": true, "message": "Product deleted successfully"}
	w.Header().Add("Content-Type", "application/json")
	json.NewEncoder(w).Encode(msg)
}

/* handler function for PUT method */
var UpdateProduct = func(w http.ResponseWriter, r *http.Request) {
	product := &model.Product{}
	err1 := json.NewDecoder(r.Body).Decode(product)
	if err1 != nil {
		msg := map[string]interface{}{"status": false, "message": "Error while decoding request body"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	product.ProductId, _ = strconv.ParseInt(mux.Vars(r)["productId"], 10, 64)
	fmt.Printf("deleting product with product_id = %d", product.ProductId)

	err2 := GetDB().Save(&product).Error
	if err2 != nil {
		msg := map[string]interface{}{"status": false, "message": err2}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	w.Header().Add("Content-Type", "application/json")
	json.NewEncoder(w).Encode(product)
}
