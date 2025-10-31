#!/bin/sh

curl -s \
     -H "Accept: application/json" \
     -H "Content-Type: application/json" \
     -X GET http://localhost:8081/api/health \
| grep ok || exit 1
