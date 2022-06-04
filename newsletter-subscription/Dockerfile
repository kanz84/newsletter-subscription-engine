FROM alpine:3.14

ARG JAR_FILE

RUN ping 8.8.8.8 -c 1
RUN ping google.com -c 1

RUN  apk update \
  && apk upgrade \
  && apk add --update openjdk11 tzdata curl unzip bash
#  && rm -rf /var/cache/apk/*

#RUN adduser -S admin
#USER admin

RUN mkdir /workspace
RUN mkdir /workspace/app
RUN mkdir /workspace/files
RUN mkdir /workspace/files/log

WORKDIR /workspace/app
COPY ${JAR_FILE} newsletter-subscription.jar
ENTRYPOINT ["java","-jar","newsletter-subscription.jar"]
#ENTRYPOINT ["./mvnw", "spring-boot:run"]