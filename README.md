# Projet SAE 3.1 � Paysages

Projet r�alis� dans le cadre de la **SA� 3.1** du **BUT Informatique (semestre 3)**.  
Il s'agit d'un jeu de placement de tuiles hexagonales, inspir� librement de *Dorfromantik*. Le but est de composer un paysage harmonieux tout en maximisant son score.

- Lien du sujet : https://iut-fbleau.fr/sitebp/sae3/31_2024/M67XNS5HAF8M5DAN.php


## Membres du groupe

- Abed BRIDJA
- Christopher DUBREUIL
- Wilfried BRIGITTE  


## Objectifs p�dagogiques

- D�veloppement d'une application Java compl�te en bin�me ou trin�me
- Structuration du code en packages et classes avec Javadoc
- Impl�mentation d'algorithmes de d�tection de structures (poches)
- Int�gration d'un serveur distant pour la gestion des scores
- G�n�ration de documentation et d�une archive `.jar` via un `Makefile`


## Pr�sentation du jeu

Le joueur construit un paysage en posant des tuiles hexagonales comportant un ou deux des terrains suivants :

- Mer  
- Champ  
- Pr�  
- For�t  
- Montagne

### Types de tuiles

- Tuiles simples : 1 seul terrain (6 c�t�s identiques)
- Tuiles mixtes : 2 terrains r�partis selon les sch�mas 1+5, 2+4 ou 3+3

### D�roulement d�une partie

1. La premi�re tuile est pos�e automatiquement.
2. � chaque tour, une nouvelle tuile est tir�e d�une **s�rie pr�d�finie**.
3. Le joueur choisit la **position** et l�**orientation** de la tuile, � condition qu�elle soit **adjacente** � une tuile d�j� pos�e.
4. La partie se termine apr�s **50 tuiles pos�es**.

### Calcul du score

Les tuiles connect�es entre elles par des **terrains identiques** forment des **poches**.  
Chaque poche rapporte un score �gal au **carr� du nombre de tuiles** qui la composent.

Exemple :

2� + 2� + 2� + 2� + 1� = 17 points

yaml
Copier
Modifier

Une tuile avec 2 terrains appartient toujours � 2 poches diff�rentes.


## Int�gration serveur

Un **serveur distant** stocke :

- Les s�ries de tuiles disponibles
- Les scores associ�s � chaque s�rie (anonymes)

### Fonctionnement :

- Lors du lancement, le joueur choisit une **s�rie** (sans conna�tre son contenu).
- � la fin, son **score est compar�** � ceux des autres joueurs ayant utilis� la m�me s�rie.

> L'ajout de nouvelles s�ries n�est pas � impl�menter, mais **plusieurs s�ries doivent exister sur le serveur** pour les tests.


## Organisation technique

- **Langage :** Java (sans biblioth�ques externes)
- **API Java officielle uniquement**
- **Structure :** Un fichier source par classe, code dans des packages
- **Documentation :** Commentaires Javadoc pour chaque classe et m�thode
- **Compilation :** Fichier `Makefile` g�n�rant une archive `.jar`


## Technologies et outils utilis�s

- Java (standard)
- Makefile (compilation, packaging `.jar`)
- Gitea (gestion de version)
- StarUML (diagrammes)
- PDF (rapport)
