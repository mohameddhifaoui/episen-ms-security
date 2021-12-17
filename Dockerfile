FROM openjdk:11

RUN apt-get update -y -q \

&& apt-get install -y -q git maven \

&& git clone https://github.com/mohameddhifaoui/episen-ms-security \

&& cd episen-ms-security/ \

&& mvn clean install

WORKDIR episen-ms-security/

ENTRYPOINT ["java","-jar","./target/episen-ms-security-1.0.0-SNAPSHOT.jar"]