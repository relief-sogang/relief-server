FROM openjdk:11-jdk
ARG JAR_FILE=relief-interfaces/build/libs/*.jar
COPY ${JAR_FILE} relief.jar
ENTRYPOINT ["java", "-jar", "/relief.jar"]