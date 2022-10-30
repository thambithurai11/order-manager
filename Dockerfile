FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/order-manager-1.0.0.jar order-manager.jar
ENTRYPOINT ["java","-jar","/order-manager.jar"]