source ./config.sh
application_name=$1
docker tag ${application_name}-server:${image_version} ${docker_repo}/${application_name}-server:${image_version}
docker push ${docker_repo}/${application_name}-server:${image_version}