FROM maven:3.9-eclipse-temurin-25 AS builder
RUN mkdir /mrpanel
RUN mkdir /mrpanel-out
COPY . /mrpanel

WORKDIR /mrpanel
RUN --mount=type=cache,target=$MAVEN_CONFIG \
      mvn --batch-mode \
          --update-snapshots \
          -e \
          -DskipTests \
          --projects containers/mrpanel-server \
          --also-make \
          install

WORKDIR /mrpanel/containers/mrpanel-server
RUN mv target/*.jar /mrpanel-out/application.jar

WORKDIR /mrpanel-out
RUN java -Djarmode=tools \
         -jar application.jar \
          extract --layers \
         --launcher \
         --destination extracted

# --------------------------------------------
FROM eclipse-temurin:25-alpine
LABEL vendor="Stowarzyszenie Młodzi Razem"
LABEL org.mlodzirazem.mrpanel.container.id="mrpanel-server"
LABEL org.mlodzirazem.mrpanel.container.version="0.0.0-MRPANEL-SNAPSHOT"

RUN apk --no-cache add curl jq; \
    addgroup mrpanel; \
    adduser  --ingroup mrpanel --disabled-password mrpanel

WORKDIR /application

# Default JAR layers
COPY --from=builder /mrpanel-out/extracted/dependencies .
COPY --from=builder /mrpanel-out/extracted/snapshot-dependencies .
COPY --from=builder /mrpanel-out/extracted/spring-boot-loader .
COPY --from=builder /mrpanel-out/extracted/application .

USER mrpanel
COPY --chmod=544 \
     --chown=mrpanel:mrpanel \
     --from=builder \
     /mrpanel/docker/mrpanel-server/*.sh ./

ENV JAVA_OPTS="-Dfile.encoding=UTF-8 \
               -XX:NativeMemoryTracking=summary \
               -XX:+HeapDumpOnOutOfMemoryError"

# API
EXPOSE 8080

# Actuator
EXPOSE 9080

ENTRYPOINT ["/bin/sh", "/application/launch.sh"]
HEALTHCHECK --interval=10s \
            --timeout=5s \
            --retries=10 \
    CMD /application/healthcheck.sh