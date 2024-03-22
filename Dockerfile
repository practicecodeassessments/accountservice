FROM amazoncorretto:17.0.7-alpine
MAINTAINER RC
COPY target/account-service.jar account-service.jar
ENTRYPOINT ["java","-jar","/account-service.jar"]