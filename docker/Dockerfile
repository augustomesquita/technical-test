FROM openjdk:8-jdk-alpine

LABEL maintainer="Augusto Mesquita"

RUN apk update && apk add ca-certificates wget && update-ca-certificates

ADD technical-test*.jar /technical-test.jar

CMD ["java", "-jar", "/technical-test.jar"]
