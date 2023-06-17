FROM maven:3.9.2-eclipse-temurin-17 AS MAVEN_BUILD
RUN mkdir project
WORKDIR /project
COPY . .
RUN mvn clean package

FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app
WORKDIR /app
ARG PORT=8080
ENV APP_KEY=1ipo2uawmve3408muv45c328muj
ENV PORT=$PORT
EXPOSE $PORT
COPY --from=MAVEN_BUILD /project/target/blockchain.jar app.jar
RUN apk add dumb-init
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
RUN chown -R javauser:javauser /app
USER javauser
CMD ["dumb-init", "java", "-jar", "app.jar"]