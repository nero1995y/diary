#!/usr/bin/env bash

PROJECT_ROOT="/home/ec2-user/app/step2"
PROJECT_NAME=diary

DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

echo "현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID= $(pgrep -f ${PROJECT_NAME}.*.jar)

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_LOG
else
        echo "> kill -15 $CURRENT_PID" >> $DEPLOY_LOG
        kill -9 $CURRENT_PID
        sleep 5
fi
