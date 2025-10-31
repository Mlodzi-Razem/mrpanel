#!/bin/sh

curl -s \
     -H "Accept: application/json" \
     -H "Content-Type: application/json" \
     -X GET http://localhost:9080/actuator/health \
| jq ".status" | grep UP || exit 1