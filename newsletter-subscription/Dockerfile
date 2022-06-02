FROM openjdk:11-oraclelinux7
#RUN adduser -S admin
#USER admin
ARG JAR_FILE
RUN mkdir -p /workspace
WORKDIR /workspace
COPY ${JAR_FILE} newsletter-subscription.jar
ENTRYPOINT ["java","-jar","newsletter-subscription.jar"]
#ENTRYPOINT ["./mvnw", "spring-boot:run"]