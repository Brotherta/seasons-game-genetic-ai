#Livraison 4 08/10/2021

### Description du jeu :

- Le but de cette milestone est l'amélioration du comportement de l'IA et la mise en place de stratégies afin de gagner.

###Fonctionnalités prévues :

- Les IA peuvent décider d'utiliser plusieurs bonus en un tour, dont plusieurs fois le même.
- Ajuster les timings des choix d'utilisation de bonus.
- Ajouter un compteur de bonus aux stats.
- Les bonus donnnent des malus en fin de partie.
- Les joueurs ont une jauge d'énergie.
- IA random et une IA simple. 
- Ajout d'effet simple aux cartes, effet de cristallisation lors de l'invocation de la carte.
- Ajout d'une jauge d'invocation.


###Fonctionnalités implémentées :

- Les IA peuvent décider d'utiliser plusieurs bonus en un tour, dont plusieurs fois le même.
- Ajuster les timings des choix d'utilisation de bonus.
- Ajouter un compteur de bonus aux stats.
- Les bonus donnnent des malus en fin de partie.
- Les joueurs ont une jauge d'énergies.
- IA random et une IA simple.
- Ajout d'effet simple aux cartes, effet de cristallisation lors de l'invocation de la carte.
- Ajout d'une jauge d'invocation.

###Fonctionnalités à venir:

- IA simple qui effectue des choix plus otpimisés à chaque occasion et pas que durant la sélection des cartes.
- Structures des effets de toutes les cartes.
- Refonte des choix du joueur et de leurs réalisations.
- Cartes conformes à celles du jeu.


### Dettes techniques

- Les players sont créés dans le Game Engine.
- Nous avons réduit notre dette technique par la mise en place d'une Façade entre les bots et le jeu.
- Grâce à SonarQ on a résolu plus facilement les codes smells afin de réduire notre dette technique globale.

### Tests
- Il manque encore des tests Cucumber liés aux user stories qu'on a déjà implémentées.
- La jauge d'invocation n'est pas testée.
- Plus globalement un certain nombre d'ajouts récents ne sont pas assez testés.
