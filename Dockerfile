FROM openjdk:17

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} UserManagement.jar

EXPOSE 9090

HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
  CMD curl --fail http://localhost:9090/actuator/health || exit 1

ENTRYPOINT ["java","-jar","UserManagement.jar"]
