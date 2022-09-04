#!/bin/sh
source ./config.sh
application_name=$1
service_name="${application_name}-server"

echo "container is stoping and removing"

containerId=$(docker ps -a | grep -E "${service_name}" | awk '{print $1}')

if [ ! -z $containerId ]
  then docker stop $containerId && docker rm $containerId
fi

docker rmi --force $(docker images | grep "${service_name}" | awk '{print $3}')

echo "image and container ware removed and image is building"
cd ..
mvn clean compile
cd ${service_name}
mvn clean compile
mvn package -Dmaven.test.skip=true docker:build

echo "build success and container is starting"