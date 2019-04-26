FROM adoptopenjdk/openjdk11:alpine-slim

#RUN addgroup -S localmovie && adduser -S localmovie -G localmovie &&

RUN mkdir -p /opt/localmovie/config

ADD src/main/resources/vault.crt /opt/localmovie/vault.crt
RUN keytool -importcert -file /opt/localmovie/vault.crt -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt -alias "vault"

ADD src/main/resources/keycloak.crt /opt/localmovie/keycloak.crt
RUN keytool -importcert -file /opt/localmovie/keycloak.crt -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt -alias "keycloak"

ARG JAR_FILE
ADD target/$JAR_FILE /opt/localmovie/localmovie-web.jar

#RUN chown -R localmovie:localmovie /opt/localmovie
#
#USER localmovie

WORKDIR /opt/localmovie/
ENTRYPOINT java -jar localmovie-web.jar