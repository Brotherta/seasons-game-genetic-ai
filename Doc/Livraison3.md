#Livraison 3 06/10/2021

### Description du jeu :

- Les joueurs vont jouer avec des cartes dont le coup est en adéquation avec les règles du jeu.
  C'est à dire un cout d'une à plusieurs énergies, d'un ou plusieurs cristaux, ou bien alors un mélange des deux.
- Une carte qui coute des cristaux pour son invocation peut voir son cout varier en fonction du nombre de joueurs de la partie en cours.
- Des cartes à effets sont introduites dans le jeu.
- les joueurs répartissent leurs cartes en fonction des années
- les joueurs peuvent utiliser des bonus en cours de partie

###Fonctionnalités prévues :

- Implémentation des Cartes dans un JSON
- envoi des données entre le client et le server
- création des cartes en fonction du JSON, plus d'aléatoire dans la création des cartes
- ajout du coût des cartes en accord avec les Cartes du jeu
- ajout de bonus utilisable au cours de la partie qui ajoutent des malus de points à chaque utilisation
- répartition des cartes de manière circulaire 
- répartition des cartes dans des réserves pour les 3 années 
- création d'effets Basique
- application des effets en jeu 
- 
- 
- 


###Fonctionnalités implémentées :

- Implémentation des Cartes dans un JSON
- envoi des données entre le client et le server
- création des cartes en fonction du JSON, plus d'aléatoire dans la création des cartes
- ajout du coût des cartes en accord avec les Cartes du jeu
- ajout de bonus utilisable au cours de la partie qui ajoutent des malus de points à chaque utilisation
- répartition des cartes de manière circulaire
- répartition des cartes dans des réserves pour les 3 années
- création d'effets Basique
- intégration de SonarQub
- 

###Fonctionnalités à venir:

- Jauge d'invocation
- Façade IA / PersonalBoard
- Façade IA / GameEngine
- Differentes stratégie Pour les IA 
- application des effets en jeu
- 


### Dettes techniques
- nous avons besoin de façade entre l'IA et son personal board et le gameEngine, en effet actuellement il semblerait que l'IA puisse tricher en ayant accès a des setteur du gameEngine  

### Tests
- Ajout de tests Cucumber, utilisation de Mockito et ajouts de tests unitaires correspondants aux nouvelles classes