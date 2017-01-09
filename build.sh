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
        error_exit "`date "+%Y-%m-%d% %H:%M:%S"` Error: Not valid env, Got $1"
    fi
}

function error_exit {
    echo $1 1>&2
    exit 1
}

function echo_with_date {
    echo "`date "+%Y-%m-%d% %H:%M:%S"` Info: $1"
}

paramIndex=0
while [ $paramIndex -le $# ];do
    case ${!paramIndex} in
        -env)
            shift
            env=${!paramIndex}
            check_env $env
            env=${env:-development}
        ;;
    esac
    paramIndex=$(expr $paramIndex + 1)
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



