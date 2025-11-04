#!/bin/sh

JAVA_OPTS="${JAVA_OPTS:-}"
TIMEZONE="${TIMEZONE:-Europe/Warsaw}"

java "-Dfile.encoding=UTF-8" \
     "-XX:NativeMemoryTracking=summary" \
     "-XX:+HeapDumpOnOutOfMemoryError" \
     "-XX:+UseContainerSupport" \
     "-XX:MaxRAMPercentage:90.0" \
     "-XX:+UseStringDeduplication" \
     "-Duser.timezone=${TIMEZONE}" \
     $JAVA_OPTS \
     org.springframework.boot.loader.launch.JarLauncher