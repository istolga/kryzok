#!/bin/sh

echo "remove unneccessary logs"

#rm -rf $tomcat_dir/webapps/api
rm $tomcat_dir/logs/*.*
#rm /var/log/jigsaw/web.log

tomcat_dir=/usr/local/tomcat7
echo "Tomcat_dir :: " $tomcat_dir

echo "Starting tomcat"
$tomcat_dir/bin/startup.sh