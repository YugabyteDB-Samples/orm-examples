package main 

import (
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/gorilla/mux"
	"net/http"
	"fmt"
	"controller"
	"config"
	"strconv"
) 

func main() {
	router := mux.NewRouter()
	
	router.HandleFunc("/users", controller.SelectUsers).Methods("GET")
	router.HandleFunc("/users/{userId}", controller.SelectUser).Methods("GET")
	router.HandleFunc("/users", controller.CreateUser).Methods("POST")
	router.HandleFunc("/users/{userId}", controller.UpdateUser).Methods("PUT")
	router.HandleFunc("/users/{userId}", controller.DeleteUser).Methods("DELETE")
	
	router.HandleFunc("/products", controller.SelectProducts).Methods("GET")
	router.HandleFunc("/products/{productId}", controller.SelectProduct).Methods("GET")
	router.HandleFunc("/products", controller.CreateProduct).Methods("POST")
	router.HandleFunc("/products/{productId}", controller.UpdateProduct).Methods("PUT")
	router.HandleFunc("/products/{productId}", controller.DeleteProduct).Methods("DELETE")
	
	router.HandleFunc("/orders", controller.SelectOrders).Methods("GET")
	router.HandleFunc("/orders/{orderId}", controller.SelectOrder).Methods("GET")
	router.HandleFunc("/orders", controller.CreateOrder).Methods("POST")
	router.HandleFunc("/orders/{orderId}", controller.UpdateOrder).Methods("GET")
	router.HandleFunc("/orders/{orderId}", controller.DeleteOrder).Methods("DELETE")
	
	fmt.Println(fmt.Sprintf("Listening: port = %d", config.Config.BindPort))
	http.ListenAndServe(":" + strconv.Itoa(config.Config.BindPort), router)
	fmt.Println("Shutting down...")
}
