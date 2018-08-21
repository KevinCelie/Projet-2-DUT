# Projet-2-DUT
Projet de fin d'année de première année de DUT informatique

Pour lancer le projet : 
- telecharger le projet 
- éxécuter le fichier iut_rampage.jar

Projet de Groupe de 8 semaines 

Consigne : 

- Le sujet : un Zelda-Like

Cette année, vous allez devoir inventer et programmer un Zelda à l’ancienne : en 2D avec vue du
dessus donc plutôt à l’image des jeux sur Game Boy comme par exemple A Link to the Past. Vous
avez le droit de vous inspirer largement des Zelda existants mais devez inventer votre propre
univers. 

- Les fonctionnalités prioritaires
Voici les fonctionnalités qui nous intéressent du point de vue pédagogique. Il faut donc que vous
vous y intéressiez en priorité.

  - Le terrain doit être une tileMap initialisée dynamiquement à partir d'un fichier ou d'un
tableau d'entiers. Il n'est pas obligatoire (et surtout pas au début) d'avoir une scrolling map
(une map qui se déplace en même temps que Link). On peut commencer avec une map
statique pas plus grande que l'écran sur laquelle tout se déroule.
Quelques références pour comprendre et utiliser les tilemaps :

  - Link sait pousser, lancer des objets, se battre se déplacer, parler. Il est commandé au moyen
du clavier (pas à la souris).

  - Link démarre avec une seule arme, il en découvre d'autres (au moins 1) qui changent
radicalement son comportement (pas seulement un peu plus de points d'attaque, par exemple
le boomerang ou l'arc ou toute autre arme qui lance des projectiles).

  - Link peut découvrir au moins 1 accessoire qui augmente ses possibilités (pas des armes).
Par exemple quelque chose qui permet de voler ou d'aller sur l'eau ou de casser des éléments
du décor ...

  - Gestion de la vie : les coeurs.
  
  - Il y a au moins 2 types d’ennemis avec des comportements (déplacement, attaque …) très
différents.

  - Un donjon avec un boss qui possède un schéma d’attaque élaboré. 
  
- Les contraintes techniques

  - Votre programme doit respecter l’architecture MVC. Ceci n’est pas une architecture
couramment utilisée dans les jeux vidéos donc attention à la copie de code trouvé sur
internet : il y a fort peu de chances qu’il trouve grâce à nos yeux…

  - Votre programme doit pouvoir fonctionner avec des maps différentes. Les personnages
doivent être capable de trouver leur chemin ou de se déplacer sans se cogner dans les
obstacles, quelque soit la map.

  - La partie statique de votre vue doit être spécifiée en utilisant FXML et le Scene Builder.
Bien sûr, cela ne concerne pas l’affichage de la map qui devra être construite
dynamiquement à partir d’un fichier ou d’un tableau d’entiers et des images pour les tuiles.

  - Il est interdit d’utiliser des API orientée Jeu ou des API pour la recherche de chemin dans la
map.

  - Votre programme doit être le plus « Objet » possible. Vous verrez plus bas au paragraphe
évaluation que le barème donne une place au moins aussi importante à la qualité du code
qu’à la jouabilité du produit fini.


