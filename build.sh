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
    echoLog 'INFO' 'check env'
    valid=0
    for i in "${VALID_ENV[@]}"
    do
        if [[ $i = $1 ]]; then
            valid=1
        fi
    done

    if [[ $valid = 0 ]]; then
        echoLog 'ERROR' "invalid env '$1'"
        exit
    fi

    echoLog 'INFO' 'PASS'
}

function check_mvn {
    echoLog 'INFO' 'check maven'
    if [[ `which mvn` != "" ]]; then
        echoLog 'INFO' 'PASS'
    else
        echoLog 'ERROR' 'maven is not installed'
        exit
    fi
}

function check_npm {
    echoLog 'INFO' 'check npm'
    if [[ `which npm` != "" ]]; then
        echoLog 'INFO' 'PASS'
    else
        echoLog 'ERROR' 'npm is not installed'
        exit
    fi
}

function check_grunt {
    check_npm

    echoLog 'INFO' 'check global grunt'
    if [[ `npm list -g grunt | grep grunt` != "" ]]; then
        echoLog 'INFO' 'PASS'
    else
        npm install -g grunt
    fi

    echoLog 'INFO' 'check local grunt'
    if [[ `npm list grunt | grep grunt` != "" ]]; then
        echoLog 'INFO' 'PASS'
    else
        npm install
    fi
}

function echoLog {
    echo "`date "+%Y-%m-%d %H:%M:%S"` [$1]: $2"
}

echoLog 'INFO' 'begin build'
parse_param $@
check_grunt
check_mvn
grunt build:$env
mvn clean package

echoLog 'INFO' 'end build'
exit
