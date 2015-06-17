#!/bin/sh
#
# This script sets up local tomcat env

tomcat_dir=/Users/mac/tomcat7
echo "Tomcat_dir :: " $tomcat_dir

echo "Stopping tomcat"
$tomcat_dir/bin/shutdown.sh
sleep 10

rm -rf $tomcat_dir/webapps/api
rm $tomcat_dir/logs/*.*
#rm /var/log/jigsaw/web.log

project=api

#mvn -Dmaven.test.skip=true clean package

# Get fresh file reference 
file=`find api/target -maxdepth 1 -name *${project}*.war`
echo Target War == ${file}

if [ -f  "${file}" ]; then
  cp -fv ${file} $tomcat_dir/webapps/api.war
  chmod 777 $tomcat_dir/webapps/api.war
fi

echo "Starting tomcat"
$tomcat_dir/bin/startup.sh
