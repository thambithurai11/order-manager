# ☕ order-manager
<a href="https://foojay.io/works-with-openjdk"><img align="right" src="https://github.com/foojayio/badges/raw/main/works_with_openjdk/Works-with-OpenJDK.png" width="100"></a>


![Java](https://img.shields.io/badge/-Java-000?&logo=Java&logoColor=007396)
![Spring](https://img.shields.io/badge/-Spring-000?&logo=Spring)


## Description:
The Order Manager API Application allows performing the following operations for the customers exists in https://reqres.in/api/users.

  * Create an order for a given productId and emailId
  * Get all the available orders 


## :shield:	 Code coverage
<img width="653" alt="junitCoverage" src="https://user-images.githubusercontent.com/45259611/162827644-a7f97805-e141-4346-ac09-aefc79be6a82.png">


## :balance_scale:	Validation Assumptions :
- For Order creation,
  - Both the request elements emailId and productId are mandatory
  - emailId should be a valid email address
  - productId should be a negative or zero number
 
## :hammer_and_wrench:	Tech-Stack
![Java](https://img.shields.io/badge/-Java-000?&logo=Java&logoColor=007396)
![Spring](https://img.shields.io/badge/-Spring-000?&logo=Spring)	
- Java 8 
- Spring-Boot
- JPA
- In-Memory Database H2
- Open Api 3.0 (swagger)
- Maven
- Git Hub
- Docker

## :memo: Running the application locally
- Option 1:
  - One way is to execute the main method in the de.codecentric.springbootsample.Application class from your IDE.
- Option 2: Maven way of running
  - after checkout project open command prompt(cmd) or terminal
  - navigate to the project folder
  - run command `mvn clean install`
  - once its successfully build run command `mvn spring-boot: run`

Now application is up and running on http://localhost:8080/

## :memo: Deploying the application In Docker
- Checkout / Download the code from git repo()
	- checkout : open git bash and run command `git clone https://github.com/thambithurai11/order-manager.git`
- Install the docker if not installed on machine
    - Open terminal and run docker-build-run.sh


## :grey_question:	How to test endpoints
### :spider_web:  order-manager
 - Open the URL in your browser : http://localhost:8080
 - User will see a swagger page with all the defined specs of the service.
 - There will have 2 endpoints you can see.


### 1. Order-controller
#### Description:
- Endpoint 1: `POST /v1/orders`
  - Allows user to create order with post request body
- Endpoint 2: `GET /v1/orders`
  - Get all the orders with pagination support 


### :test_tube: Testing using Swagger UI
![swagger copy](https://user-images.githubusercontent.com/45259611/162643934-9f371589-4eb7-4a4e-9a96-780734b6fd89.png)

### 🗄️: view/download openapi yaml file 
- application: orderApi/OrderApi/src/main/resources/api-docs.yaml
- download: Open the URL in your browser :http://localhost:8080/v3/api-docs.yaml



