package config

import (
	"encoding/json"
	"io/ioutil"
)

var Config struct {
	Platform    string `json:"platform"`
	Host        string `json:"host"`
	DbPort      int    `json:"dbport"`
	Username    string `json:"username"`
	Password    string `json:"password"`
	Dbname      string `json:"dbname"`
	BindPort    int    `json:"bindport"`
	LoadBalance bool   `json:"load_balance"`
}

func init() {
	cfgJson, err := ioutil.ReadFile("src/config/config.json")
	if err != nil {
		panic(err)
	}

	json.Unmarshal(cfgJson, &Config)
}
