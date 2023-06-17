FROM openjdk:17

WORKDIR /app

ARG PORT=8080

ENV APP_KEY=1ipo2uawmve3408muv45c328muj
ENV PORT=$PORT

EXPOSE $PORT

COPY target/blockchain.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]