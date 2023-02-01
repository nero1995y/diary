#!/usr/bin/env bash

# 쉬고 있는 profile 찾기: real1이 사용 중이면 real2 쉬고있음

function find_idle_profile()
{
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}"
  http_code://localhost/profile)

  #400 보다 큰 에러
  if [ ${RESPONSE_CODE} -ge 400 ]

  then
      CURENT_PROFILE=real2
  else
      CURENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURENT_PROFILE} == real1 ]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"
}
# 쉬고 있는 profile port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ $[IDLE_PROFILE] == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}