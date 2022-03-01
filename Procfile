web: java $JAVA_OPTS -Dserver.port=$PORT -jar target/cashcow_api-0.0.1-SNAPSHOT.jar 

release: ./mvnw flyway:baseline; ./mvnw flyway:migrate
