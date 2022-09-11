FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8081
ADD target/Conditional-0.0.1-SNAPSHOT.jar prod.jar
ENTRYPOINT ["java", "-jar", "prod.jar"]