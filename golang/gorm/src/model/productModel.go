package model

import (

)

/* Model that represent database 'products' table and REST json for 'product' */

type Product struct {
	ProductId 	int64 	`gorm:"primary_key";gorm:"AUTO_INCREMENT"`
	ProductName	string 	`gorm:"size:50" json:"productName"`
	Description string  `gorm:"size:255" json:"description"`
	Price 		float64 `gorm:"type:numeric(10,2)"`
}