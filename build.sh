#!/bin/bash

env='dev'
VALID_ENV=('prod' 'dev')

function parse_param {
    param_index=0
    while [ $param_index -le $# ];do
        case ${!param_index} in
            -env)
                shift
                env=${!param_index}
                check_env $env
            ;;
        esac
        param_index=$(expr $param_index + 1)
    done
}

function check_env {
    echo_log 'INFO' 'check env'
    valid=0
    for i in "${VALID_ENV[@]}"
    do
        if [[ $i = $1 ]]; then
            valid=1
        fi
    done

    if [[ $valid = 0 ]]; then
        echo_log 'ERROR' "invalid env '$1'"
        exit
    fi

    echo_log 'INFO' 'PASS'
}

function check_mvn {
    echo_log 'INFO' 'check maven'
    if [[ `which mvn` != "" ]]; then
        echo_log 'INFO' 'PASS'
    else
        echo_log 'ERROR' 'maven is not installed'
        exit
    fi
}

function check_npm {
    echo_log 'INFO' 'check npm'
    if [[ `which npm` != "" ]]; then
        echo_log 'INFO' 'PASS'
    else
        echo_log 'ERROR' 'npm is not installed'
        exit
    fi
}

function check_grunt {
    check_npm

    echo_log 'INFO' 'check global grunt'
    if [[ `npm list -g grunt | grep grunt` != "" ]]; then
        echo_log 'INFO' 'PASS'
    else
        npm install -g grunt
    fi

    echo_log 'INFO' 'check local grunt'
    if [[ `npm list grunt | grep grunt` != "" ]]; then
        echo_log 'INFO' 'PASS'
    else
        npm install
    fi
}

function echo_log {
    echo "`date "+%Y-%m-%d %H:%M:%S"` [$1]: $2"
}

echo_log 'INFO' 'begin build'
parse_param $@
check_grunt
check_mvn
grunt build:$env
mvn clean package

echo_log 'INFO' 'end build'
exit
