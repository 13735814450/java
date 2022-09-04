#!/bin/sh

source ./config.sh
application_name=$1
port=$2
profile=$3
service_name="${application_name}-server"

echo "build success and container is starting"

docker run -p ${port}:${port} \
       --env PROFILE=${profile} \
       --env SERVER_HOSTNAME=${server_hostname} \
       --env SERVER_PORT=${port} \
       --env EUREKA_URL=${eureka_url} \
       --env EUREKA_PORT=${eureka_port} \
       --env APP_NAME=${service_name} \
       --env KAFKA_SERVERS=${kafka_servers} \
       --env ZK_SERVERS=${zk_servers} \
       --env GIT_URL=${git_url} \
       --env GIT_USERNAME=${git_username} \
       --env GIT_PWD=${git_pwd} \
       --name ${service_name} \
       -v /data/servers/logs/${service_name}/:/data/servers/logs/${service_name} \
       -t ${service_name}:1.0-SNAPSHOT
