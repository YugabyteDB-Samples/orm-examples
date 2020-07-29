package model

import ()

type User struct {
	UserId    int64  `gorm:"primary_key;AUTO_INCREMENT"`
	FirstName string `gorm:"size:255" json:"firstName"`
	LastName  string `gorm:"size:255" json:"lastName"`
	UserEmail string `gorm:"size:255" json:"email"`
}
