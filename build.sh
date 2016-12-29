#!/bin/bash

env='development'

VALID_ENV=('production' 'development')

function check_env {
    exist=0
    for i in "${VALID_ENV[@]}"
    do
        if [[ $i = $1 ]]; then
            exist=1
        fi
    done
    if [[ $exist = 0 ]]; then
        error_exit "`date "+%Y-%m-%d% %H:%M:%S"` Error: Not valid region, Got $1"
    fi
}

function error_exit {
    echo $1 1>&2
    exit 1
}

function echo_with_date {
    echo "`date "+%Y-%m-%d% %H:%M:%S"` Info: $1"
}

i=0
while [ $i -le $# ];do
    case ${!i} in
        -env)
            shift
            env=${!i}
            check_env $env
            env=${env:-development}
        ;;
    esac
    i=$(expr $i + 1)
done

echo_with_date "Grunt.js task running..."
if [ $env = 'development' ]; then
    grunt build:dev
    mvn clean package
else
    grunt build:prod
    mvn clean package
fi

echo_with_date 'End task with success status'
exit



