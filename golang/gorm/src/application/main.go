package main

import (
	"gorm-example/src/config"
	"gorm-example/src/controller"
	"log"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
	_ "github.com/jinzhu/gorm/dialects/postgres"
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

	log.Println("Listening: port = " + strconv.Itoa(config.Config.BindPort))
	log.Fatal(http.ListenAndServe(":"+strconv.Itoa(config.Config.BindPort), router))
	log.Println("Shutting down...")
}
