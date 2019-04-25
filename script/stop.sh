#!/bin/bash 

# 软件包名
PACKAGE_NAME=redis5-0.0.1-SNAPSHOT.jar

# 进程的管理端口
MANAGEMENT_PORT=8556

# 平滑停止和强制停止之前的时间间隔，单位秒
SLEEP_TIME=5

# curl超时时间，单位秒
MAX_TIME=300

function soft_stop() {
    local pid=$(ps -ef | grep -i "${PACKAGE_NAME}" | grep -v grep | awk '{ print $2 }')
    if [ "x${pid}" != "x" ] 
    then 
        [[ "x${MANAGEMENT_PORT}" != "x" ]] && curl -X POST -m "${MAX_TIME}" "http://127.0.0.1:${MANAGEMENT_PORT}/shutdown"
    else 
        echo "process has been shutdown"
    fi 
}

function hard_stop() {
    local pid=$(ps -ef | grep -i "${PACKAGE_NAME}" | grep -v grep | awk '{ print $2 }')
    if [ "x${pid}" != "x" ] 
    then 
        kill -9 "${pid}"
    else 
        echo "process has been shutdown"
    fi 
}

soft_stop

sleep "${SLEEP_TIME}"

hard_stop