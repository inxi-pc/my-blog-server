#!/bin/bash

cur_dir=`pwd`
conf_dir=$cur_dir/src/main/resources
env='development'

i=0
while [ $i -le $# ];do
    case ${!i} in
        -env)
            i=$(expr $i + 1)
            env=${!i}
            env=${env:-development}
        ;;
    esac
    i=$(expr $i + 1)
done

if [ $env = 'development' ]; then
    echo 'Begin set development config......'
    grunt build:dev
    mvn clean package
else
    echo 'Begin set production config......'
    grunt build:prod
    mvn clean package
fi

echo 'Build Successful'
exit



