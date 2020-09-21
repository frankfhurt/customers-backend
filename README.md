# Customers backend
RESTful API to make CRUD operations for Customers

## Stack

- Java 11
- Spring Boot
- Spring Security
- Spring Data
- JWT Token
- H2 in memory database
- JUnit
- Mockito
- Lombok
- Mapstruct
- Maven

## How to run

You will need apache maven and Java 11

1. Generate resources running: 
```mvn clean install ```
1. Run the fat jar:
```java -jar target/customers-backend.jar ```

If everything went well, the API will be running on http://localhost:8080

## Using the API

In order to use the API you first have to create your user and then make login to get your JWT token.

### Creating your user
POST : http://localhost:8080/api/users

Request Payload Example:
```sh
{
    "name": "Franklyn Vieira",
    "username": "franklyn",
    "email": "franklyn@gmail.com",
    "password": "test123"
}
```

Response:
```sh
{
    "id": 1,
    "name": "Franklyn Vieira",
    "username": "franklyn",
    "email": "franklyn@gmail.com",
    "password": "$2a$10$EPcn9KvXl1Fcy4S1iuwhTO85EfrSwJ.1oqGG7ldeIwGOi4ExtbdtC"
}
```

### Login
POST : http://localhost:8080/api/login

Request Payload Example:
```sh
{
    "username": "franklyn",
    "password": "test123"
}
```

On the Header you should the value from key ```auth-token```

It will contain a toke similar to this: ```eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmcmFua2x5biIsImV4cCI6MTYwMDc1MTk3NX0.TNNNNVcOcYkLqsf_5k9qWG2fJ0GK-b2YZ0YD9XLVdm3l2jBoFpNYK7_CbPukyfWiMpyi3lpe6NoqAtR4S18rbA```

You will need to set this on the Header for all next requests.

Key: ```auth-token```

Token (example): ```eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmcmFua2x5biIsImV4cCI6MTYwMDc1MTk3NX0.TNNNNVcOcYkLqsf_5k9qWG2fJ0GK-b2YZ0YD9XLVdm3l2jBoFpNYK7_CbPukyfWiMpyi3lpe6NoqAtR4S18rbA```

### Creating a Customer
POST : http://localhost:8080/api/customers

Request Payload Example:
```sh
{
    "name": "Tech Mahindra",
    "email": "tech_mahindra@gmail.com",
    "cpf": "12345678912",
	"address": {
		"address": "Rua Dna Maria",
		"city": "Curitiba",
		"state": "Paraná",
		"country": "Brasil"
	}	
}
```

Response:
```sh
{
    "id": 2,
    "name": "Tech Mahindra",
    "email": "tech_mahindra@gmail.com",
    "cpf": "12345678912",
    "address": {
        "id": 2,
        "address": "Rua Dna Maria",
        "city": "Curitiba",
        "state": "Paraná",
        "country": "Brasil"
    }
}
```


### Retrieving a Customer
GET : http://localhost:8080/api/customers/2

Response:
```sh
{
    "id": 2,
    "name": "Tech Mahindra",
    "email": "tech_mahindra@gmail.com",
    "cpf": "12345678912",
    "address": {
        "id": 2,
        "address": "Rua Dna Maria",
        "city": "Curitiba",
        "state": "Paraná",
        "country": "Brasil"
    }
}
```


### Updating a Customer
PUT : http://localhost:8080/api/customers

Request Payload Example:
```sh
{
    "id": 2,
    "name": "Tech Mahindra India",
    "email": "tech_mahindra@gmail.com",
    "cpf": "12345678912",
    "address": {
        "id": 2,
        "address": "Rua Dna Maria",
        "city": "Bangalore",
        "state": "Karnataka",
        "country": "India"
    }
}
```

Response:
```sh
{
    "id": 2,
    "name": "Tech Mahindra India",
    "email": "tech_mahindra@gmail.com",
    "cpf": "12345678912",
    "address": {
        "id": 2,
        "address": "Rua Dna Maria",
        "city": "Bangalore",
        "state": "Karnataka",
        "country": "India"
    }
}
```

### Deleting a Customer
DELETE : http://localhost:8080/api/customers/2

Response: HTTP 200