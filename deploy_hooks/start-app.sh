#!/bin/sh

echo "remove unnecessary logs"

rm $tomcat_dir/logs/*.*
rm -rf $tomcat_dir/work/Catalina/localhost
#rm /var/log/jigsaw/web.log

tomcat_dir=/usr/local/tomcat7
echo "Tomcat_dir :: " $tomcat_dir

echo "Starting tomcat"
$tomcat_dir/bin/startup.sh