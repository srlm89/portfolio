#!/bin/bash

SLEEP=1
WAIT_START=true
WAIT_EXIT=true
APP_CONTAINER_NAME=deployable_docker_app

function log() {
    GRAY="\e[38;5;247m"
    NORMAL="\e[00m"
    echo -e "${GRAY}$@${NORMAL}"
}


log "\n[ Launching docker-compose ]"
docker-compose build
docker-compose up &

while $WAIT_START; do
    log "[ Waiting for container startup (sleep $SLEEP s) ]"
    sleep $SLEEP
    test -z $(docker ps -q --type=container --filter="name=$APP_CONTAINER_NAME")
    if [ $? > 0 ]; then
        WAIT_START=false
    fi
done

while $WAIT_EXIT; do
    log "[ Waiting for container termination (sleep $SLEEP s) ]"
    sleep $SLEEP
    WAIT_EXIT=$(docker inspect --type=container --format="{{.State.Running}}" $APP_CONTAINER_NAME)
done

log "\n[ Gracefully terminating other containers ]"
docker-compose stop && docker-compose rm -f --all

log "\n [ Removing docker image ]"
IMAGE_ID=$(docker inspect --type=image --format="{{.Id}}" $APP_CONTAINER_NAME)
docker rmi $IMAGE_ID

