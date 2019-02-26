FROM adoptopenjdk/openjdk11:alpine

RUN mkdir /opt/localmovie-web && mkdir /opt/localmovie-web/config

ADD src/main/resources/vault.crt /opt/localmovie-web/vault.crt
RUN keytool -importcert -file /opt/localmovie-web/vault.crt -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt -alias "vault"

ADD src/main/resources/keycloak.crt /opt/localmovie-web/keycloak.crt
RUN keytool -importcert -file /opt/localmovie-web/keycloak.crt -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt -alias "keycloak"

ARG JAR_FILE
ADD target/$JAR_FILE /opt/localmovie-web/localmovie-web.jar

WORKDIR /opt/localmovie-web/
ENTRYPOINT java -jar localmovie-web.jar