FROM ghcr.io/graalvm/jdk-community:21
COPY target/signospbackend-0.0.1-SNAPSHOT.jar signosp-backend.jar
ENTRYPOINT  ["java","-jar","signosp-backend.jar"]