# Compile, Deploy and Run
Install required components
```
go get github.com/lib/pq
go get github.com/satori/go.uuid
go get github.com/gorilla/mux
```
Append source code location to GOPATH environment variable
```
export GOPATH=$GOPATH: /orm-examples/golang/gorm
```
Configure environment in /orm-examples/golang/gorm/src/config/config.json
```
{
	"platform" : "postgres",
	"host" : "localhost",
	"dbport" : 5433,
	"username" : "postgres",
	"password" : "",
	"dbname" : "gormdemo",
	"sslmode" : "disable",
	"bindport" : 8080
}
```
Build and run using shell script
```
./build-and-run.sh
```

