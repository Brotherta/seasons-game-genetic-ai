# TER

Le but de ce projet est de refaire le jeu de plateau **SEASONS** 


## Pour lancer le jeu vous pouvez utiliser les arguments suivants : 
- -v pour un mode verbose
- -p n avec n le nombre de players compris entre 2 et 4.
- -g n avec n le nombre de parties compris entre 1 et 1000 exclus.
- -dna path/to/file.json avec le chemin de l'emplacement du JSON contenant un génome de joueur.
- -dnalist \[ file1.json file2.json ... ] de 2 a 4 fichier de génome JSON pour lancer une partie entre IA entrainée, les fichiers doivent se trouvber dans le répertoire ./genetic_data/genomes.

# COMMANDE POUR LANCER 
mvn exec:java -pl app

Liste des étudiants : 
<ul>
<li>Yann Brault </li>
<li>Antoine Vidal-Mazuy</li> 
<li>Antoine Cousson</li>
</ul>

# Définitions et encadrement du TER :

> ### IA garantie :
> Nous allons créer autant que possible, des stratégies différentes pour chaque possibilité de jeu offerte au joueur.
> Ensuite nous les composerons à la main pour chaque saison de chaque année.

> ### IA ambitieuse :
> Nous allons utiliser un algorithme génétique.
> Le génome des IA est défini par composition des différentes stratégies. Pour chaque saison de chaque année, un nouveau génome est défini.
> Un score de fitness définira quelle composition est la meilleure pour la saison X de l'année Y.
> Nous organiserons un tournoi pour chaque IA, afin de garder les meilleures stratégies à chaque instant.

> ### Règles du jeu :
> Les règles de notre jeu se basent sur les règles originales du jeu Seasons, avec les 30 premières cartes uniquement.
> Si un joueur doit piocher une carte, mais qu'il n'y en a plus, ou pas assez, le joueur n'en pioche pas ou moins que prévu.

