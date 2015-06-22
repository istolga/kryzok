#!/bin/sh

war_file=/etc/kryzok/api.war

if [ -f  "${war_file}" ]; then
  echo "Removing old war file $war_file"
  rm $war_file
fi