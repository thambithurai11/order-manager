# 📦 order-manager
<a href="https://foojay.io/works-with-openjdk"><img align="right" src="https://github.com/foojayio/badges/raw/main/works_with_openjdk/Works-with-OpenJDK.png" width="100"></a>


![Java](https://img.shields.io/badge/-Java-000?&logo=Java&logoColor=007396)
![Spring](https://img.shields.io/badge/-Spring-000?&logo=Spring)


## 📔 Description:
The Order Manager API Application allows the [existing customers](https://reqres.in/api/users) to perform the below operations

  * Create an order for a given *emailId* and *productId*
  * Get all the available orders 

## ✔️ Request Validations
- The below validations are performed for Order creation ,
  - Both the request elements *emailId* and *productId* are mandatory
  - *emailId* should be a valid email address
  - *productId* should be a negative or zero number
 
## :hammer_and_wrench:	Tech-Stack
![Java](https://img.shields.io/badge/-Java-000?&logo=Java&logoColor=007396)
![Spring](https://img.shields.io/badge/-Spring-000?&logo=Spring)	
- Java 8 
- Maven
- Spring-Boot
- Spring-Data-JPA
- In-Memory Database H2
- Open Api 3.0 (swagger)
- Git Hub
- Docker

## :memo: Steps to Run the application
### Checkout the code
- Git Checkout : open git bash and run command `git clone https://github.com/thambithurai11/order-manager.git`
- Or download the zip file from the repository
	
### Running the application 
#### Option 1: Running the application from IDE
 - One way is to execute the main method in the com.vodafoneziggo.ordermanager.OrderManagerApplication class from your IDE.
#### Option 2: Maven way of running
 - Open command prompt(cmd) or terminal
 - Navigate to the project folder
 - Run command `mvn clean install`
 - Once the build is successful, then run command `mvn spring-boot:run` to run the application
#### Option 3: Deployment using Docker
 - Install the docker if not installed on machine
 - Open terminal, Navigate to the project directory and run docker-build-run.sh

Now application is up and running on http://localhost:8080/v1/orders

## :test_tube: How to Test the application
### order-controller
- Endpoint 1: `POST /v1/orders`
  - Allows user to create order with post request body
- Endpoint 2: `GET /v1/orders`
  - Get all the orders with pagination support 

### :spider_web:  order-manager Swagger UI
 - After the application is started, Open the URL in your browser : [http://localhost:8080](http://localhost:8080/swagger-ui/index.html)
 - The below swagger UI will be displayed with all the application specification
 
  ![swagger copy](https://user-images.githubusercontent.com/114624820/198870450-e96cb8bf-d890-41d1-bd7f-db44253563ed.png)

### Postman Collection
 - This [Postman_Collection](https://github.com/thambithurai11/order-manager/files/9895566/Test.Collection.-.Orders.Manager.zip) can be used to test the application using Postman 

### 🗄️: view/download openapi yaml file 
- Source-code: /order-manager/src/main/resources/order-manager-api-docs.yaml
- Download: Open the URL in your browser :http://localhost:8080/v3/api-docs.yaml

## :shield: Code coverage
The below is the JUnit code coverage of the application

<img width="653" alt="junitCoverage" src="https://user-images.githubusercontent.com/114624820/199344704-8b2a5f83-fad9-4d63-94ba-dcca8ab97b11.PNG">

