web: java $JAVA_OPTS -jar target/cashcow_api-0.0.1-SNAPSHOT.jar -Dserver.port=$PORT

release: ./mvnw flyway:baseline; ./mvnw flyway:migrate