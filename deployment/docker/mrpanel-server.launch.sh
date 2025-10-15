#!/bin/sh

JAVA_OPTS="${JAVA_OPTS:-}"
TIMEZONE="${TIMEZONE:-Europe/Warsaw}"
MAX_MEM="${MAX_MEM:-2048m}"

java "$JAVA_OPTS" "-DXmx${MAX_MEM}" "-Duser.timezone=${TIMEZONE}" org.springframework.boot.loader.launch.JarLauncher