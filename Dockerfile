FROM openjdk:11
MAINTAINER Yago Maia
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} user-service-0.0.1.jar
ENTRYPOINT ["java","-jar","user-service-0.0.1.js ar"]