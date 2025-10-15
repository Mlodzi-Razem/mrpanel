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
RUN java -Djarmode=tools -jar application.jar extract --layers --launcher --destination extracted

# --------------------------------------------
FROM eclipse-temurin:25-alpine
LABEL vendor="Stowarzyszenie Młodzi Razem"
LABEL org.mlodzirazem.mrpanel.container.id="mrpanel-server"
LABEL org.mlodzirazem.mrpanel.container.version="0.0.1-SNAPSHOT"


RUN apk --no-cache add curl

RUN addgroup mrpanel;\
    adduser  --ingroup mrpanel --disabled-password mrpanel

WORKDIR /application

COPY --from=builder /mrpanel-out/extracted/dependencies .
COPY --from=builder /mrpanel-out/extracted/snapshot-dependencies .
COPY --from=builder /mrpanel-out/extracted/spring-boot-loader .
COPY --from=builder /mrpanel-out/extracted/application .

USER mrpanel
COPY --chmod=544 \
     --chown=mrpanel:mrpanel \
     --from=builder \
     /mrpanel/deployment/docker/mrpanel-server.launch.sh ./

ENV JAVA_OPTS="-Dfile.encoding=UTF-8 \
               -XX:NativeMemoryTracking=summary \
               -XX:+HeapDumpOnOutOfMemoryError"

EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c", "./mrpanel-server.launch.sh"]
HEALTHCHECK --interval=10s \
            --timeout=5s \
            --retries=10 \
            CMD curl -s -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/actuator/health | grep UP || exit 1