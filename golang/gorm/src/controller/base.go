package controller

import (
	"fmt"

	"gorm-example/src/config"
	"gorm-example/src/model"

	postgres "github.com/yugabyte/gorm-yugabytedb"
	"gorm.io/gorm"
)

var dbConn *gorm.DB

func init() {
	dbUri := fmt.Sprintf("host=%s port=%d user=%s dbname=%s sslmode=disable password=%s", config.Config.Host, config.Config.DbPort, config.Config.Username, config.Config.Dbname, config.Config.Password)
	fmt.Println("Connecting: " + dbUri)

	var err error
	dbConn, err = gorm.Open(postgres.Open(dbUri))
	if err != nil {
		panic(err)
	}

	err = dbConn.Exec("SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SERIALIZABLE").Error
	if err != nil {
		panic(err)
	}

	dbConn.Debug().AutoMigrate(&model.User{}, &model.Product{}, &model.Order{}, &model.OrderLine{})
}

func GetDB() *gorm.DB { return dbConn }
