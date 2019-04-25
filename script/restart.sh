#!/bin/bash 

BASE_DIR=$(cd $(dirname "${BASH_SOURCE[0]}") && pwd)

bash "${BASE_DIR}"/stop.sh
bash "${BASE_DIR}"/start.sh
