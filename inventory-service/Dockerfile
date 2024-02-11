FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=target/inventory-service-1.0.0.jar
ADD ${JAR_FILE} Application.jar
ENTRYPOINT ["java","-jar","/Application.jar"]
HEALTHCHECK --interval=10s --timeout=5s CMD curl -f http://localhost/actuator/health || exit 1