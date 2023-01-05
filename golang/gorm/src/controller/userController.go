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
var SelectUsers = func(w http.ResponseWriter, r *http.Request) {
	users := []model.User{}
	err := GetDB().Table("users").Find(&users).Error
	if err != nil {
		msg := map[string]interface{}{"status": false, "message": err}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
	} else {
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(users)
	}
}

/* handler function for GET method */
var SelectUser = func(w http.ResponseWriter, r *http.Request) {
	userId := mux.Vars(r)["userId"]
	fmt.Printf("finding user with user_id = %s", userId)

	user := &model.User{}
	err := GetDB().Table("users").Where("user_id = ?", userId).First(user).Error
	if err != nil {
		msg := map[string]interface{}{"status": false, "message": "Failed to find user"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
	} else {
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(user)
	}
}

/* handler function for POST method */
var CreateUser = func(w http.ResponseWriter, r *http.Request) {
	user := &model.User{}
	err1 := json.NewDecoder(r.Body).Decode(user)

	if err1 != nil {
		msg := map[string]interface{}{"status": false, "message": "Error while decoding request body"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	err2 := GetDB().Create(&user).Error
	if err2 != nil {
		msg := map[string]interface{}{"status": false, "message": err2}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	w.Header().Add("Content-Type", "application/json")
	json.NewEncoder(w).Encode(user)
}

/* handler function for DELETE method */
var DeleteUser = func(w http.ResponseWriter, r *http.Request) {
	userId := mux.Vars(r)["userId"]
	fmt.Printf("deleting user with user_id = %s", userId)

	user := &model.User{}
	err1 := GetDB().Table("users").Where("user_id = ?", userId).First(user).Error
	if err1 != nil {
		msg := map[string]interface{}{"status": false, "message": "Failed to find user"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	err2 := GetDB().Delete(&user).Error
	if err2 != nil {
		msg := map[string]interface{}{"status": false, "message": err2}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	msg := map[string]interface{}{"status": true, "message": "User deleted successfully"}
	w.Header().Add("Content-Type", "application/json")
	json.NewEncoder(w).Encode(msg)
}

/* handler function for PUT method */
var UpdateUser = func(w http.ResponseWriter, r *http.Request) {
	user := &model.User{}
	err1 := json.NewDecoder(r.Body).Decode(user)
	if err1 != nil {
		msg := map[string]interface{}{"status": false, "message": "Error while decoding request body"}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	user.UserId, _ = strconv.ParseInt(mux.Vars(r)["userId"], 10, 64)
	fmt.Printf("deleting user with user_id = %d", user.UserId)

	err2 := GetDB().Save(&user).Error
	if err2 != nil {
		msg := map[string]interface{}{"status": false, "message": err2}
		w.Header().Add("Content-Type", "application/json")
		json.NewEncoder(w).Encode(msg)
		return
	}

	w.Header().Add("Content-Type", "application/json")
	json.NewEncoder(w).Encode(user)
}
