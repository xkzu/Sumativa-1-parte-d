FROM amazoncorretto:21

WORKDIR /app
COPY target/parted-0.0.1-SNAPSHOT.jar app.jar
COPY Wallet_FULLSTACK /app/oracle_wallet
EXPOSE 8090

CMD [ "java", "-jar", "app.jar" ]