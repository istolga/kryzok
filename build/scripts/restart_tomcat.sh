#!/bin/sh
#
tomcat_dir=/usr/local/tomcat7
echo "Tomcat_dir :: " $tomcat_dir

echo "Stopping tomcat"
$tomcat_dir/bin/shutdown.sh
sleep 10

#rm -rf $tomcat_dir/webapps/api
rm $tomcat_dir/logs/*.*
rm -rf $tomcat_dir/work/Catalina/localhost
#rm /var/log/jigsaw/web.log

echo "Starting tomcat"
$tomcat_dir/bin/startup.sh
