FROM openjdk:11-oraclelinux7
#RUN adduser -S admin
#USER admin
ARG JAR_FILE

RUN mkdir /workspace
RUN mkdir /workspace/app
RUN mkdir /workspace/files
RUN mkdir /workspace/files/log

WORKDIR /workspace/app
COPY ${JAR_FILE} newsletter-subscription.jar
ENTRYPOINT ["java","-jar","newsletter-subscription.jar"]
#ENTRYPOINT ["./mvnw", "spring-boot:run"]