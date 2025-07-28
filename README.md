# Projet SAE 3.1 – Paysages

Projet réalisé dans le cadre de la **SAÉ 3.1** du **BUT Informatique (semestre 3)**.  
Il s'agit d'un jeu de placement de tuiles hexagonales, inspiré librement de *Dorfromantik*. Le but est de composer un paysage harmonieux tout en maximisant son score.

- Lien du sujet : https://iut-fbleau.fr/sitebp/sae3/31_2024/M67XNS5HAF8M5DAN.php


## Membres du groupe

- Abed BRIDJA
- Christopher DUBREUIL
- Wilfried BRIGITTE  


## Objectifs pédagogiques

- Développement d'une application Java complète en binôme ou trinôme
- Structuration du code en packages et classes avec Javadoc
- Implémentation d'algorithmes de détection de structures (poches)
- Intégration d'un serveur distant pour la gestion des scores
- Génération de documentation et d’une archive `.jar` via un `Makefile`


## Présentation du jeu

Le joueur construit un paysage en posant des tuiles hexagonales comportant un ou deux des terrains suivants :

- Mer  
- Champ  
- Pré  
- Forêt  
- Montagne

### Types de tuiles

- Tuiles simples : 1 seul terrain (6 côtés identiques)
- Tuiles mixtes : 2 terrains répartis selon les schémas 1+5, 2+4 ou 3+3

### Déroulement d’une partie

1. La première tuile est posée automatiquement.
2. À chaque tour, une nouvelle tuile est tirée d’une **série prédéfinie**.
3. Le joueur choisit la **position** et l’**orientation** de la tuile, à condition qu’elle soit **adjacente** à une tuile déjà posée.
4. La partie se termine après **50 tuiles posées**.

### Calcul du score

Les tuiles connectées entre elles par des **terrains identiques** forment des **poches**.  
Chaque poche rapporte un score égal au **carré du nombre de tuiles** qui la composent.

Exemple :

2² + 2² + 2² + 2² + 1² = 17 points

yaml
Copier
Modifier

Une tuile avec 2 terrains appartient toujours à 2 poches différentes.


## Intégration serveur

Un **serveur distant** stocke :

- Les séries de tuiles disponibles
- Les scores associés à chaque série (anonymes)

### Fonctionnement :

- Lors du lancement, le joueur choisit une **série** (sans connaître son contenu).
- À la fin, son **score est comparé** à ceux des autres joueurs ayant utilisé la même série.

> L'ajout de nouvelles séries n’est pas à implémenter, mais **plusieurs séries doivent exister sur le serveur** pour les tests.


## Organisation technique

- **Langage :** Java (sans bibliothèques externes)
- **API Java officielle uniquement**
- **Structure :** Un fichier source par classe, code dans des packages
- **Documentation :** Commentaires Javadoc pour chaque classe et méthode
- **Compilation :** Fichier `Makefile` générant une archive `.jar`


## Technologies et outils utilisés

- Java (standard)
- Makefile (compilation, packaging `.jar`)
- Gitea (gestion de version)
- StarUML (diagrammes)
- PDF (rapport)
