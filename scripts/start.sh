#!/usr/bin/env bash

PROJECT_ROOT="/home/ec2-user/app/step2"
PROJECT_NAME=diary
JAR_FILE="$PROJECT_ROOT/$PROJECT_NAME.jar"

# $REPOSITORY/$PROJECT_NAME/build/libs/*.jar

APP_LOG="$PROJECT_ROOT/applications.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%3)

# build
echo "$TIME_NOW > $JAR_FILE" 파일 복사 >> $DEPLOY_LOG
cp $PROJECT_ROOT/zip/build/libs/*.jar JAR_FILE

JAR_NAME=$(ls -tr $PROJECT_ROOT/ | grep jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

# jar 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar \
  -Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/application-oauth.yml,/home/ec2-user/app/application-real-db.yml,classpath:/application-real.yml -Dspring.profiles.active=real $PROJECT_ROOT/$JAR_NAME 2>&1 &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG
