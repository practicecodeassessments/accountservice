# Account-Service
This is part of a microservice based demo bank application developed for CGI. It consists of 2 microservices:
1. account-service
2. transaction-service

## Initialization
Upon startup, the account-service is loaded with 3 customer records having customerId: 100001, 100002 and 100003


## Running the services
1. Build the two services using maven command: mvn clean package.
2. Run the account-service jar file with Java command: java -jar target/account-service.jar
3. Run the transaction-service jar file with Java command: java -jar target/transaction-service.jar

## Testing the APIS
1. Create account API: http://localhost:8089/api/v1/customer/create-account
    - Request method: PUT
    - Sample Request Body: {"customerId": 100002, "initialCredit": 2000}
2. Get account API: http://localhost:8089/api/v1/customer/{accountNumber}
    -   Request method: GET
   
## Accessing the UI
-  Open http://localhost:8089/account/create on a browser
-  Enter 100001, 100002 or 100003 in "Customer ID" failed
-  Enter 0 or any number value in "Initial balance" field and click submit

## To Do
-  Implement input validation on API calls
-  Write unit tests
-  Add authentication to APIs
-  Containerize the services
-  Use persistance database like mysql
-  Protect APIs using JWT
-  Generate Swagger documentation
-  Implement log appenders to write to logs
-  externalise the configuration to Config server
-  Use token-based authentication using vault server
-  Enchance the services to use Service-Registry and Discovery
-  Enhance the services to use Circuit breaker -Netflix Hystrix
-  Include Zuul proxy as API GW
-  Distributed tracing with Sleuth and Zipkin