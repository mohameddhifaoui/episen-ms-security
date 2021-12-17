FROM openjdk:11

RUN apt-get update -y -q \ # resynchronize the package index files from their sources

&& apt-get install -y -q git maven \ # install git and maven to use in project

&& git clone https://github.com/mohameddhifaoui/episen-ms-security \ # cloning the project from the github repository

&& cd episen-ms-security/ \ # Accesing the repository cloned

&& mvn clean install # Building the project

WORKDIR episen-ms-security/ # Defining the working directory for our docker project  

ENTRYPOINT ["java","-jar","./target/episen-ms-security-1.0.0-SNAPSHOT.jar"] # Running the project 