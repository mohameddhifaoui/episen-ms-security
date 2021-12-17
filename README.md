
# Generate Private Key

Je parle ici de certificat auto-généré et par conséquent non-vérifié par une autorité de certification. C'est une approche tout à fait valable pour des tests mais non recommandée en production.

Un fichier .p12 combine à la fois un certificat et une clé privé. Il faut donc commencer par générer ces 2 éléments:


$ openssl req -x509 -newkey rsa:3072 -keyout rsa_private.pem -nodes -out rsa_cert.pem -subj "/CN=unsused"

Cela génère 2 fichiers:

    rsa_private.pem: la clé privée
    rsa_cert.pem: le certificat

Il ne reste plus qu'à combiner les 2 dans un seul ficher .p12 avec la commande suivante:

$ openssl pkcs12 -inkey rsa_private.pem -in rsa_cert.pem -export -out server.p12 -name episen


Une fois le fichier server.p12 générer on peut le vérifier avec la commande:

$ openssl pkcs12 -in server.p12 -noout -info

#### Docker

Dans cette partie on commence par ouvrir un terminal et on accède au répertoire du projet "cd episen-ms-security/".

On commencer par créer l'image docker qui sera tagée "episen-ms-security" :

$ docker build -t episen-ms-security .

Ensuite, on vérifie que l'image a été bien construite et on récupère son ID :

$ docker images | grep episen-ms-security

Après, on lance un conteneur nommé "episen-ms-security" sur le port 9000 en utilisant l'ID de l'image récupéré à l'étape précédente:

$ docker run -d --name episen-ms-security -p 9000:9000 ID-IMAGE

Maintenant, on peut vérifier que le conteneur a été bien construit et on récupère son ID :

$ docker ps -a | grep episen-ms-security

Enfin, on peut lire les logs du conteneur :

$ docker logs -f episen-ms-security

#### Test Rest API

Dans cette partie on va utiliser Postman pour réaliser les tests.

On commence par faire une requête POST qui va tester le premier endpoint qui utilise un username et password pour génèrer un JWT :

	URL : localhost:9000/authenticate
	Dans la partie "Body" chosir "raw" et le type "json" et on saisit : { "subject": "mohamed", "password": "mohamed" }

Résultat : { "jwt": "Header.Payload.Signature" }

Ensuite on fait une requête POST qui teste le second endpoint qui utilise le JWT généré précédemment pour récupérer le "username" : 

	URL : localhost:9000/getUser
	Dans la partie "Authorization" on choisit le type "Bearer Token" et on saisit dans la case "Token" : Header.Payload.Signature
	Dans la partie "Body" chosir "raw" et le type "json" et on saisit : { "jwt": "Header.Payload.Signature" }

Résultat : "username : mohamed"

N.B: Header.Payload.Signature est le jwt de la partie précédente.

