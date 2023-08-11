ARG DOCKER_REGISTRY=nexus-ci.corp.dev.vtb
FROM ${DOCKER_REGISTRY}/openjdk/openjdk-11-rhel8:latest

USER root
RUN rm -f /etc/localtime && ln -s /usr/share/zoneinfo/Europe/Moscow /etc/localtime
ENV LANG="en_US.UTF-8"

WORKDIR /app
COPY rfrm-pay/target/rfrm-pay*.jar rfrm-pay.jar
RUN chown -R jboss:jboss /app/*
USER jboss
ENTRYPOINT ["java", "-jar", "rfrm-pay.jar"]
EXPOSE 8080
