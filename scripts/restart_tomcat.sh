#!/bin/sh
#
# This script sets up local tomcat env

tomcat_dir=/usr/local/tomcat7
echo "Tomcat_dir :: " $tomcat_dir

echo "Stopping tomcat"
$tomcat_dir/bin/shutdown.sh
sleep 10

echo "Starting tomcat"
$tomcat_dir/bin/startup.sh
