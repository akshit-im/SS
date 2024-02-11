FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=target/enquiry-service-1.0.0.jar
ADD ${JAR_FILE} Application.jar
ENTRYPOINT ["java","-jar","/Application.jar"]
HEALTHCHECK --interval=5s --timeout=3s CMD curl -f http://config.test.com:9196/actuator/health || exit 1