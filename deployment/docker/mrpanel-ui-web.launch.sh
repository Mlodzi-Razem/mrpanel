#!/bin/sh

export NEXT_TELEMETRY_DISABLED=1
export NODE_ENV=production
export PORT=8081
export HOSTNAME="0.0.0.0"

node server.js