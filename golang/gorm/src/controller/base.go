package controller

import (
	"config"
	"fmt"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"model"
)

var dbConn *gorm.DB

func init() {
	dbUri := fmt.Sprintf("host=%s port=%d user=%s dbname=%s sslmode=disable password=%s", config.Config.Host, config.Config.DbPort, config.Config.Username, config.Config.Dbname, config.Config.Password)
	fmt.Println("Connecting: " + dbUri)

	var err error 
	dbConn, err = gorm.Open("postgres", dbUri)
	if err != nil {
		panic(err)
	}

	err = dbConn.Exec("SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SERIALIZABLE").Error
	if err != nil {
		panic(err)
	}

	dbConn.Debug().AutoMigrate(&model.User{}, &model.Product{}, &model.Order{}, &model.OrderLine{})

	dbConn.Model(&model.OrderLine{}).AddForeignKey("order_id", "orders(order_id)", "CASCADE", "CASCADE")
	dbConn.Model(&model.OrderLine{}).AddForeignKey("product_id", "products(product_id)", "CASCADE", "CASCADE")
	dbConn.Model(&model.Order{}).AddForeignKey("user_id", "users(user_id)", "CASCADE", "CASCADE")
}

func GetDB() *gorm.DB { return dbConn }
