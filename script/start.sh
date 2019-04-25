#!/bin/bash 

# 脚本所在的路径
BASE_DIR=$(cd $(dirname "${BASH_SOURCE[0]}") && pwd)

# 配置文件所在的路径
CONFIG_DIR="${BASE_DIR}/config"

# 软件包名
PACKAGE_NAME=redis5-0.0.1-SNAPSHOT.jar

# 在启动进程多少秒后检查进程状态
SLEEP_TIME=5

# 本地保留的最大GC归档日志文件个数
MAX_GC_LOG=3

# 本地保留的最大归档进程日志文件个数
MAX_PROCESS_LOG=3

# 日志文件前缀
PROCESS_LOG_PREFIX=redis5

# JVM内存参数
JAVA_OPTS=" -Xms3g -Xmx3g -Xmn1g -Xss256k -XX:PermSize=128m -XX:MaxPermSize=256m "

function set_java_opts() {
    JAVA_OPTS=" -server ${JAVA_OPTS} \
    -XX:+DisableExplicitGC \
    -XX:+UseG1GC \
    -XX:+CMSParallelRemarkEnabled \
    -XX:+UseCMSCompactAtFullCollection \
    -XX:LargePageSizeInBytes=128m \
    -XX:+UseFastAccessorMethods \
    -XX:+UseCMSInitiatingOccupancyOnly \
    -XX:+PrintGCDateStamps \
    -XX:+PrintGCDetails -Xloggc:${BASE_DIR}/logs/gc.log \
    -Djava.awt.headless=true \
    -Djava.net.preferIPv4Stack=true \
    -Dfile.encoding=UTF-8"
}

function start_process() {
    local pid=$(ps -ef | grep -i "${PACKAGE_NAME}" | grep -v grep | awk '{ print $2 }')
    if [ "x${pid}" != "x" ] 
    then 
        echo "process has already been started with pid ${pid}"
        exit 1
    else
        nohup java ${JAVA_OPTS} -Xbootclasspath/a:"${CONFIG_DIR}" -Dloader.path="${BASE_DIR}/lib" -jar "${BASE_DIR}/lib/${PACKAGE_NAME}" &>/dev/null &
    fi 
}

function archive_gc_logs() {
    if [ ! -d "${BASE_DIR}/logs" ]
    then 
        mkdir -p "${BASE_DIR}/logs"
    fi 

    if [ -f "${BASE_DIR}/logs/gc.log" ] 
    then 
        local time=$(date +%Y%m%d%H%M%S)
        cd ${BASE_DIR}/logs &>/dev/null
        tar -cjvf "gc.log.${time}.tar.bz2" gc.log
        echo -n "" > "${BASE_DIR}/logs/gc.log"
        cd - &>/dev/null
    fi
}

function delete_old_logs() {
    cd ${BASE_DIR}/logs &>/dev/null
    ls -t gc.log*.tar.gz | awk 'NR > '"${MAX_GC_LOG}"' { print }' | xargs -I '{}' rm -f '{}'
    ls -t "${PROCESS_LOG_PREFIX}".log.*.gz | awk 'NR > '"${MAX_PROCESS_LOG}"' { print }' | xargs -I '{}' rm -f '{}'
    ls -t "${PROCESS_LOG_PREFIX}-error".log.*.gz | awk 'NR > '"${MAX_PROCESS_LOG}"' { print }' | xargs -I '{}' rm -f '{}'
}

function check_process() {
    local pid=$(ps -ef | grep -i "${PACKAGE_NAME}" | grep -v grep | awk '{ print $2 }')
    if [ "x${pid}" != "x" ] 
    then 
        echo "start process sucessfully."
    else
        echo "start process failed."
    fi 
}


set_java_opts

archive_gc_logs

start_process

sleep "${SLEEP_TIME}"

check_process













