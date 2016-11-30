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

dev_db='driver=com.mysql.cj.jdbc.Driver\n
url=jdbc:mysql://localhost:3306/test\n
username=root\n
password=qq1314521'
dev_app='debug=true\n
encryptKey=secret\n
jwtKey=secret\n
jwtExpiredTime=600000'

prod_db='driver=com.mysql.cj.jdbc.Driver\n
url=jdbc:mysql://localhost:3306/test\n
username=root\n
password=Root@01!'
prod_app='debug=false\n
encryptKey=secret\n
jwtKey=secret\n
jwtExpiredTime=600000'

cd $conf_dir
if [ $env = 'production' ]; then
    echo 'Begin set production config'
    echo -e $prod_db > $conf_dir/database.properties
    echo -e $prod_app > $conf_dir/config.properties
else
    echo 'Begin set development config'
    echo -e $dev_db > $conf_dir/database.properties
    echo -e $dev_app > $conf_dir/config.properties
fi



