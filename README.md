Demo Java & Spring Boot web application for creating bank cards.

Postman collection for local testing available at src/main/resources/postman.

Features and request samples:

1) Create client

```
curl -X POST \
  http://localhost:8080/clients \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "John",
    "lastName": "Smith",
    "oib": "26345676540",
    "status": "PENDING"
}'
```

2) Fetch client by oib

```
curl -X GET \
  http://localhost:8080/clients/16345676540 \
```

3) Delete client by oib

```
curl -X DELETE \
  http://localhost:8080/clients/26345676540 \
```

4) Create card for client
   
```
curl -X POST \
  http://localhost:8080/clients/cards \
  -H 'Content-Type: application/json' \
  -d '{
	"firstName":"John",
	"lastName":"Smith",
	"oib":"26345676540",
	"status":"PENDING"
}'
```





