FROM openjdk:8-jre-slim
ARG ARTIFACT_TO_DEPLOY
VOLUME /seidev
ENV APP_PKG_DIR=${APP_PKG_DIR:-target}
ADD ${APP_PKG_DIR}/${ARTIFACT_TO_DEPLOY} app.war
RUN sh -c 'touch /app.war'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.war" ]
