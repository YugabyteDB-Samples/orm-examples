package controller

import (
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/jinzhu/gorm"
	"fmt"
	"model"
	"config"
)

var db *gorm.DB

func init() {
	cfg := config.Config
	
	dbUri := fmt.Sprintf("host=%s port=%d user=%s dbname=%s sslmode=disable password=%s", cfg.Host, cfg.DbPort, cfg.Username, cfg.Dbname, cfg.Password)
	fmt.Println("Connecting: " + dbUri)

	conn, err := gorm.Open("postgres", dbUri)
	if err != nil {
		panic(err)
	}

	db = conn
	db.Debug().AutoMigrate(&model.User{}, &model.Product{}, &model.Order{}, &model.OrderLine{})
	
	db.Model(&model.OrderLine{}).AddForeignKey("order_id", "orders(order_id)", "CASCADE", "CASCADE")
	db.Model(&model.OrderLine{}).AddForeignKey("product_id", "products(product_id)", "CASCADE", "CASCADE")
	db.Model(&model.Order{}).AddForeignKey("user_id", "users(user_id)", "CASCADE", "CASCADE")
}

func GetDB() *gorm.DB {
	return db
}
