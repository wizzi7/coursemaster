FROM maven:3.8.4-openjdk-17 as builder
ENV SPRING_BOOT_HOME=/opt/spring-boot
USER root
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn clean install -f /usr/src/app/pom.xml -DskipTests
RUN mv /usr/src/app/target/*.jar /usr/src/app/app.jar

FROM eclipse-temurin:17
ENV SPRING_BOOT_HOME=/opt/spring-boot
USER root
RUN mkdir $SPRING_BOOT_HOME
COPY --from=builder /usr/src/app/app.jar $SPRING_BOOT_HOME/app.jar
COPY docker/conf/application.yml $SPRING_BOOT_HOME/conf/application.yml
COPY docker/entrypoint.sh $SPRING_BOOT_HOME/
ENTRYPOINT ["/bin/bash", "/opt/spring-boot/entrypoint.sh"]
