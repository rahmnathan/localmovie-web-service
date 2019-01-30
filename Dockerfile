FROM library/openjdk:10-jre

RUN mkdir /opt/localmovie-web && mkdir /opt/localmovie-web/config

ADD src/main/resources/vault.crt /opt/localmovie-web/vault.crt
RUN keytool -importcert -file /opt/localmovie-web/vault.cer -keystore /usr/lib/jvm/java-10-openjdk-amd64/lib/security/cacerts -storepass changeit -noprompt -alias "vault"

ADD src/main/resources/keycloak.crt /opt/localmovie-web/keycloak.crt
RUN keytool -importcert -file /opt/localmovie-web/keycloak.crt -keystore /usr/lib/jvm/java-10-openjdk-amd64/lib/security/cacerts -storepass changeit -noprompt -alias "keycloak"

ARG JAR_FILE
ADD target/$JAR_FILE /opt/localmovie-web/localmovie-web.jar

WORKDIR /opt/localmovie-web/
ENTRYPOINT java -jar localmovie-web.jar