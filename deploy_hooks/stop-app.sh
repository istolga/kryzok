#!/bin/sh

tomcat_dir=/usr/local/tomcat7
echo "Tomcat_dir :: " $tomcat_dir

echo "Stopping tomcat"
$tomcat_dir/bin/shutdown.sh