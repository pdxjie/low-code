#依赖jdk8环境
FROM openjdk:8

#对外暴露8085
EXPOSE 8088
#复制server-1.0-SNAPSHOT到docker容器中并命名为app.jar
ADD low-code-tools-0.0.1-SNAPSHOT.jar app.jar
#执行命令
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8089", "-jar", "/app.jar"]
