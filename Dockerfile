FROM openjdk:jre-alpine
WORKDIR /workspace
COPY /target/zuul-1.0.0.jar /workspace/zuul-proxy.jar
COPY /starter.sh /workspace/starter.sh
#ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=stage", "-Dspring.cloud.config.uri=http://config-server:8888", "zuul-proxy.jar"]
EXPOSE 8400
RUN apk add curl
RUN apk add --update --no-cache netcat-openbsd
RUN ["chmod", "+x", "/workspace/starter.sh"]
ENTRYPOINT ["sh","/workspace/starter.sh"]