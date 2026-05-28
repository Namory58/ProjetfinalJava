# Projet Final - API & Base de Données

Ce projet contient une API connectée à une base de données relationnelle, le tout configuré pour démarrer rapidement grâce à Docker.

# Prérequis

Avant de commencer, assurez-vous d'avoir installé sur votre machine :
---> Docker et Docker Compose

# Lancement de la Base de Données (Docker)

La configuration de la base de données se trouve dans le fichier docker-compose.yml.

## Étape 1 : Accéder au dossier du projet

Ouvrez votre terminal et déplacez-vous dans le dossier racine du projet (là où se trouve le fichier docker-compose.yml) :

cd /chemin/vers/votre/ProjetFinal


## Étape 2 : Lancement de la commande

Exécutez la commande suivante pour démarrer la base de données en arrière-plan (mode détaché) :

--->docker compose up -d
💡 Enfin  créer La base de données shop_db 