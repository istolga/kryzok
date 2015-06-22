#!/bin/sh

war_file=/etc/kryzok/api.war
tomcat_dir=/usr/local/tomcat7/webapps

if [ -f  "${war_file}" ]; then
  echo "Copy new file: $war_file to tomcat: $tomcat_dir"

  cp -fv ${war_file} $tomcat_dir/api.war
  chmod 777 $tomcat_dir/api.war
fi
#todo add elif to exit 1 when no war file