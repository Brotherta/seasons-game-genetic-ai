
#Livraison 2 01/10/2021

### Description du jeu :

- Le jeu possède un système de dé  permettant certaines actions
- Le tirage des dés en début de partie est aléatoire
- Durant la phase de jeu, chaque joueur choisit son dé l'un après l'autre
- Les joueurs jouent à travers 12 mois, 4 saisons, et ce durant 3 années.
- Chaque joueur au fil des saisons a la possibilité de récupérer 4 types d'énergies différentes qu'il pourra transformer en cristaux si son dé le permet uniquement lors de son tour, ou les défausser afin d'invoquer une carte depuis son jeu si son stock le lui permet.
- Les joueurs obtiennent plus ou moins de cristaux lors de la cristallisation selon la saison en cours
- Les joueurs reçoivent chacun 9 cartes en début de partie.
- Ils ont la possibilité de piocher des cartes grâce aux dés.
- les énergies restantes en fin de partie n'apportent plus de points.

###Fonctionnalités prévues :

- Implémentation d'un class carte et d'un gestionnaire de cartes
- Les joueurs peuvent invoquer des cartes par action de dé
- débuter la partie par une phase de distribution de cartes
- Gagner des énergies de tous les types
- Avoir des cartes en main
- Chaque saison a son court de cristallisation des énergies
- Implémentaion du personnal board, entité gérant les énergies et les cartes
- ajout d'arguments pour sélectionner un nb de parties, de joueurs et mode verbeux
- Les dés permettent d'obtenir de l'énergie, de piocher ou de cristalliser
- Les cartes ont un cout en énergie, celles-ci différent
- Ajout d'un serveur de statistiques


###Fonctionnalités implémentées :

- Implémentation d'un class carte et d'un gestionnaire de cartes
- Les joueurs peuvent invoquer des cartes par action de dé
- débuter la partie par une phase de distribution de cartes
- Gagner des énergies de tous les types
- Avoir des cartes en main
- Chaque saison a son court de cristallisation des énergies
- Implémentaion du personnal board, entité gérant les énergies et les cartes
- ajout d'arguments pour sélectionner un nb de parties, de joueurs et mode verbeux
- Les dés permettent d'obtenir de l'énergie, de piocher ou de cristalliser
- Les cartes ont un cout en énergie, celles-ci différent
- Ajout d'un serveur de statistiques



###Fonctionnalités à venir:

- Premiers effets de cartes
- cout en cristaux pour les cartes
- cout en cristaux variable selon les joueurs
- manière de distribution des cartes
- répartition de cartes pour les trois années
- bonus

### Dettes techniques

 - Le serveur ne fait rien à part le check ends
 - Au lancement du serveur on utilise un thread.sleep afin d'attendre la connection du client au lieu d'utiliser des notify
 - GameEngine est trop lourd et ne respecte pas les fondements de G.R.A.P.S.
 - Le code du bot commence à être lourd



### Tests

 - Nous avons des tests unitaires pour la plupart des classes
 - un scénario Gherkin.

### Etude de Pattern
 - Méthode GRASP:

 - Jusqu'à présent et dans la mesure du possible nous avons essayé de respecter les fondements de GRASP.
 - Nous avons mis en place une logique de Controller, afin de diviser les tâches et d'enlever la gestionnairedes attributs de la mains des joueurs.
 - Nos controllers sont les classes telles que : GameEngine, GameLoop.
 - L'expert est notre IA. Pour le moment son comportement n'est pas très bien encadré par la méthode GRASP.

 - Méthode SOLID:

 - Nous avons aussi essayé de maintenir une seule responsabilité par classe comme il est enoncé dans le principe SOLID
 - Les classes GameEngine, GameLoop et PersonalBoard commencent à avoir trop de responsabilités.
