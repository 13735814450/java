FROM openjdk:8-jdk-alpine
# 维护者信息
MAINTAINER kubo "jim"
# 设置工作路径
RUN mkdir /app
WORKDIR /app
COPY practice-1.0-SNAPSHOT.jar practice.jar
#ENV database-host 192.168.110.252
#ENV database-host 192.168.38.128
EXPOSE 8080
# 容器启动时执行命令
ENTRYPOINT ["java", "-Xms512m", "-Xmx1024m", "-Dfile.encoding=UTF-8", "-XX:+PrintFlagsFinal", "-XX:+PrintGCDetails", "-jar", "practice.jar"]