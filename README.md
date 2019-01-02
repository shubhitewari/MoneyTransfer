# Money transfer Rest API

A Java RESTful API for money transfers between users accounts

### Technologies
- JAX-RS API
- Log4j
- Jetty Container (for Test and Demo app)
- Apache HTTP Client



Application starts a jetty server on localhost port 8080.

- http://localhost:8080/account/create
- http://localhost:8080/account/1
- http://localhost:8080/account/2
- http://localhost:8080/account/transfer

### Sample JSON
 Account:
 ```sh
{
   "userName":"test1",
   "balance":10.0000,
   "accountId":"ID-1"
} 
```

#### User Transaction:
```sh
{  
   "amount":100000.0000,
   "fromAccountId":"ID-1",
   "toAccountId":"ID-2"
}
```
