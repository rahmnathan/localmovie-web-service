FROM library/openjdk:10

RUN mkdir /opt/localmovie-web && mkdir /opt/localmovie-web/config

ADD src/main/resources/vault.cer /opt/localmovie-web/vault.cer
RUN keytool -importcert -file /opt/localmovie-web/vault.cer -keystore /usr/lib/jvm/java-10-openjdk-amd64/lib/security/cacerts -storepass changeit -noprompt -alias "vault"

ARG JAR_FILE
ADD target/$JAR_FILE /opt/localmovie-web/localmovie-web.jar

WORKDIR /opt/localmovie-web/
ENTRYPOINT java -jar localmovie-web.jar