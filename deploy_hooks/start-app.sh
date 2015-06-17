#!/bin/sh

echo "remove unneccessary logs"

#rm -rf $tomcat_dir/webapps/api
rm $tomcat_dir/logs/*.*
#rm /var/log/jigsaw/web.log

echo "restart tomcat"
/etc/kryzok/scripts/restart_tomcat.sh