FROM openjdk:17
VOLUME /tmp
WORKDIR /app
ARG JAR_FILE=target/config-server-1.0.0.jar
ADD ${JAR_FILE} Application.jar
ENTRYPOINT ["java","-jar","/app/Application.jar"]
HEALTHCHECK --interval=60s --timeout=30s CMD curl -f http://localhost/actuator/health || exit 1