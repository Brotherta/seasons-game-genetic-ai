#Livraison 6 25/10/2021

### Description du jeu :

- Le jeu est quasiment complet, il dispose des 30 premières cartes.

###Fonctionnalités prévues :

- Suppression de l'accès aux bonus des IA permettant d'assurer qu'une IA ne puisse pas tricher.
- Ajout des effets manquants pour que toutes les cartes fonctionnent.
- Que les IA puissent effectuer plusieurs actions pendant leur tour de jeu.
- Finir la récupération des statistiques du jeu.
- Implémentation des différentes mécaniques d'effets.
- Implémentation des stratégies.

###Fonctionnalités implémentées :

- [x] Les IA ne peuvent plus accéder aux bonus directement.
- [x] Les IA peuvent effectuer plusieurs actions durant leur tour de jeu.
- [x] Les effets manquants ont été ajoutés.
- [x] Les statistiques sont terminées avec les pourcentages totaux.
- [x] Ajout des effets manquants.
- [x] Implémentation des stratégies.
- [x] Nous avons ajouté un système de logger.

### Dettes techniques

- Nous avons essayé de réduire les codes smells. Nous avons supprimé les issues les plus importantes.
- La complexité cyclomatique de nos différentes classes manager est élevée. Par exemple : PersonalBoard, PlayerCentralManager ...
- Nous avons effectué un refactor du code pour "ranger" le code.
- Nous utilisons une version out-dated de socket-io. Mettre à jour la dépendance demanderait un refactor du code.

### Test

- 83,2% de coverage sur nos tests.
- Nous avons ajouté des tests cucumber pour chaque effet.

