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
-  Open http://localhost:8089/swagger-ui/index.html on a browser
-  Create the user for authentication by using the payload
  {
  "userName": "User1",
  "email": "User1@gmail.com",
  "password": "Abcd#1234"
 } on the end point - http://localhost:8089/swagger-ui/index.html#/user-controller/register
- Copy the bearer token returned and authorize using the Lock symbol in swagger page
- Then after APIs can be tested
- Either create the Account with any of the prepopulated Customer IDs 100001,100002, 100003
- Else  please create the New customer using the API --> 'http://localhost:8089/api/v1/customer/create-customer'
-  with sample payload
-  {
-    "firstName": "John",
-    "lastName": "Doe",
-    "email": "abcd@gmail.com",
-    "address": "HIG 143",
-    "phone": 123456789
- }
-  Account details can be retrieved using the API --> example like http://localhost:8089/api/v1/customer/453814212'
-  Money transfer can be done between two account numbers using the API -- > 'http://localhost:8089/api/v1/customer/money-transfer' 
  
## To Do
-  Implement input validation on API calls
-  Write unit tests
-  Use persistance database like mysql
-  externalise the configuration to Config server
-  Use token-based authentication using vault server
-  Enchance the services to use Service-Registry and Discovery
-  Enhance the services to use Circuit breaker -Netflix Hystrix
-  Include Zuul proxy as API GW
-  Distributed tracing with Sleuth and Zipkin
