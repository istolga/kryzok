#!/bin/bash

result=$(curl -s http://localhost:8080/api/)

if [[ "$result" =~ "API" ]]; then
    echo "Everything is success"
    exit 0
else
    echo "Something failed"
    exit 1
fi