FROM openjdk:11

# resynchronize the package index files from their sources
RUN apt-get update -y -q \

# installing git and maven to use in our project
&& apt-get install -y -q git maven \

# cloning the project from my github repository
&& git clone https://github.com/mohameddhifaoui/episen-ms-security \

# Accesing the directory of the project
&& cd episen-ms-security/ \

# Building the project
&& mvn clean install

# Defining the working directory for our docker project  
WORKDIR episen-ms-security/

# Running the project
ENTRYPOINT ["java","-jar","./target/episen-ms-security-1.0.0-SNAPSHOT.jar"] 