# Livraison 5 15/10/2021

### Description du jeu :

- Le but de cette milestone est d'avoir un jeu presque complet.
- Les joueurs jouent avec un système de saisons, de dés et avec des cartes totalement 
  conforment à ce qu'on retrouve dans le jeu.


### Fonctionnalités Prévues et implémentés :

- Le jeu enverra désormais à chaque fois aux joueurs une liste de choix d'action possible durant leur tour. Le tour du
joueur ne se terminera pas tant qu'il n'aura pas choisi l'action de terminer son tour.

- Le jeu demandera juste quelle action veut réaliser l'IA, si elle a le choix de cristalliser de l'énergie, sur combien
  elle veut le faire, quelle carte elle préfère à partir d'une liste définie, etc. Mais tout ce qui touche a la gestion du
  déroulement du tour elle n'intervient jamais, sauf pour les bonus de cristallisation, d'invocation, et d'échange d'énergie pour l'instant, ou là elle appelle la Façade pour appeler le bonus.
- Le Game Engine a délégué beaucoup de ses fonctionnalités a plusieurs sous-classes, afin de rendre le code plus clair,
  lisible et organisé.
- Ce n'est plus l'IA qui réalise ses actions de jeu, mais l'IA qui demande au jeu, qui s'occupera de gérer le joueur en
  fonction de l'action demandée. (Important)
- On peut observer la phase de prélude et de choix des dés.
- [x] jouer avec toutes les cartes du jeu afin d'avoir une expérience de jeu optimale (30 premières)
- [ ] intégration des effets tout au long de la partie
- [ ] énumération de toutes les cartes dans le but de réaliser des combos de cartes optimum
- [ ] Tour permettant de faire plusieurs actions
- [x] Observer pour que les IA aient connaissance du jeu
- [x] Calcul Score en fin de partie, malus et bonus
- [x] Intelligence de Simple IA améliorée
- [ ] Implémentation de plusieurs effets :
    - [ ] Effet sacrifice de cartes
    - [x] Augmentation de la capacité d'invocation
    - [x] Piocher une carte
    - [x] Crystallisation
    - [x] Gain d'énergie
    - [x] Gain de Crystal
    - [ ] Modifier la saison
    - [ ] Invoquer sans coût

###Fonctionnalités à venir:

- Même si les outils sont là, nous n'avons encore mis qu'une intelligence trop classique. Il faudrait faire en sorte que l'IA puisse invoquer plusieurs fois par tour par exemple, et ne pas la faire cristalliser a chaque tour etc. (Le strict minimum, car il y a encore trop de soit elle fait tout le temps la même chose chaque tour, soit c'est un simple vulgaire Random). Tout en restant simple bien sûr, l'IA n'étant pas le but du projet.

-  intégration des effets tout au long de la partie
-  énumération de toutes les cartes dans le but de réaliser des combos de cartes optimum
-  Tour permettant de faire plusieurs actions
- Intégration du reste des effets

### Dettes techniques
- Des tests compliqués à réaliser autrefois à cause d'un GameEngine beaucoup trop surchargé sont maintenant réalisables. On a beaucoup de test, mais pas assez sur tout ce qui touche au cœur du Game Engine, ce qui est le plus important. Par exemple on lance souvent le jeu a la recherche d'incohérence dans le déroulement du jeu, mais tout cela est censé être le rôle des tests. On a un excellent coverage sur sonarqube, mais pas vraiment représentatif de la qualité de cette couverture.
- les IAs ont toujours accès à certaines parties du code dont elles devraient ignorer le fonctionnement (via les Bonus)
- Stats non exploités
### Tests

Nous avons supprimé des tests, car nous avons changé la façade et rendu des actions impossible à faire depuis la façade
comme Mr. Renevier nous a conseillé.
