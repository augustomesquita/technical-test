FROM openjdk:8-jdk-alpine

LABEL maintainer="Augusto Mesquita"

RUN apk update && apk add ca-certificates wget && update-ca-certificates

ADD stock-manager*.jar /stock-manager.jar

CMD ["java", "-jar", "/stock-manager.jar"]
