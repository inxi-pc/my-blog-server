#!/bin/bash

env='development'
VALID_ENV=('production' 'development')

function parse_param {
    paramIndex=0
    while [ $paramIndex -le $# ];do
        case ${!paramIndex} in
            -env)
                shift
                env=${!paramIndex}
                check_env $env
            ;;
        esac
        paramIndex=$(expr $paramIndex + 1)
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

function check_grunt {
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

function check_mvn {
    echoLog 'INFO' 'check maven'
    if [[ `which mvn` != "" ]]; then
        echoLog 'INFO' 'PASS'
    else
        echoLog 'ERROR' 'maven is not installed'
        exit
    fi
}

function echoLog {
    echo "`date "+%Y-%m-%d %H:%M:%S"` [$1]: $2"
}

echoLog 'INFO' 'begin build'
parse_param $@
check_grunt
check_mvn

echoLog 'INFO' 'begin grunt build & mvn build'
echo $env;
if [ $env = 'development' ]; then
    grunt build:dev
    mvn clean package
else
    grunt build:prod
    mvn clean package
fi
echoLog 'INFO' 'end build'
exit
