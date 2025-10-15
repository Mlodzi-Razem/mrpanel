FROM maven:3.9-eclipse-temurin-25 AS builder
ENV NODE_ENV=production
ENV NEXT_TELEMETRY_DISABLED=1

RUN mkdir /mrpanel
COPY . /mrpanel

WORKDIR /mrpanel
RUN --mount=type=cache,target=$MAVEN_CONFIG \
    --mount=type=cache,target=/mrpanel/containers/mrpanel-ui-web/node_modules \
      mvn --batch-mode \
          --update-snapshots \
          -e \
          -DskipTests \
          --projects containers/mrpanel-ui-web \
          --also-make \
          install

FROM node:22-alpine
LABEL vendor="Stowarzyszenie Młodzi Razem"
LABEL org.mlodzirazem.mrpanel.container.id="mrpanel-ui-web"
LABEL org.mlodzirazem.mrpanel.container.version="0.0.1-SNAPSHOT"

RUN apk add --no-cache g++ make py3-pip libc6-compat curl

RUN addgroup mrpanel;\
    adduser  --ingroup mrpanel --disabled-password mrpanel

USER mrpanel
WORKDIR /application

COPY --from=builder --chmod=544 --chown=mrpanel:mrpanel /mrpanel/deployment/docker/mrpanel-ui-web.launch.sh .
COPY --from=builder --chown=mrpanel:mrpanel /mrpanel/containers/mrpanel-ui-web/.next/standalone .
COPY --from=builder --chown=mrpanel:mrpanel /mrpanel/containers/mrpanel-ui-web/.next/stati[c] ./.next/static

EXPOSE 8081
ENTRYPOINT ["/bin/sh", "-c", "./mrpanel-ui-web.launch.sh"]
HEALTHCHECK --interval=10s \
            --timeout=5s \
            --retries=10 \
            CMD curl -s -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8081/api/health | grep ok || exit 1
