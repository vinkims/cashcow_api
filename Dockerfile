FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:resolve
COPY src ./src

FROM base as development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*8000'"]

FROM base as build
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 4568
COPY --from=build /app/target/cashcow_api-0.0.1-SNAPSHOT.jar /cashcow_api.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "cashcow_api.jar"]
