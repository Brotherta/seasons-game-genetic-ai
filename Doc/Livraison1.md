#Livraison 1 24/09/2021

### Description du jeu :

Le programme commence par demander à l'utilisateur de choisir le nombre de bots entre 2 et 4.
Déroulement d'une partie :
- les bots tirent un dé chacun à leurs tours,
  ils gagnent de l'énergie en fonction du dé tiré.
- Dès qu'un bot détient 3 énergies, il a le choix de crystaliser son énergie ou non, en point de victoire.
  Le choix est déterminé aléatoirement (1 chance sur 2).
- Le jeu se termine au bout de 3 tours, le bots ayant le plus de points gagne.
- Les énergies restantes à la fin rajoutent 1 par énergie.


###Fonctionnalités prévues :

- Bots qui jouent au jeu
- Choix du nombre de bots
- Gagner des énergies
- Lancé de dé
- Bots qui peuvent crystaliser leurs énergies en points
- 3 tours de jeu
- Score de fin de partie


###Fonctionnalités implémentés :

- Bots qui jouent au jeu
- Choix du nombre de bots
- Gagner des énergies
- Lancé de dé
- Bots qui peuvent cristalliser leurs énergies en points
- 3 tours de jeu
- Score de fin de partie



###Fonctionnalités à venir:

- Consommer des énergies
- Carte qui coûte de l'énergie et donne des points
- Distribution de cartes
- Dés avec différentes faces
- Jouer dans différentes saisons
- Acquérir différents types d'énergies selon la saison
- Cristalliser les énergies en fonction des saisons
- Multiplieur de cristallisation

### Dettes techniques

- Class: java.com.game.playerassets.ia.IA.java : Le joueur s'occtroît lui-même les energies et certains points de victoire.
  Cela devrait être géré par le moteur du jeu.

- L'affichage n'est pas géré par une classe indépendante.

### Tests

Nous n'avons pas encore intégré de test mockito ou cucumber,
mais uniquement des tests unitaires.
Des tests mockito seraient nécessaires, afin de pouvoir simuler des parties, et vérifier les scores.

