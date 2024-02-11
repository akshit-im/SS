FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=target/config-server-1.0.0.jar
ADD ${JAR_FILE} Application.jar
ENTRYPOINT ["java","-jar","/Application.jar"]
HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost/actuator/health || exit 1