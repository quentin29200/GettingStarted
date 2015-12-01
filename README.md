# Gestion de projet – Projet Développement Logiciel
## « OpenCompare et export HTML configurable »

### 1. Objectifs

Opencompare.org a pour but d’aider une communauté d’utilisateurs à importer, éditer, visualiser, et exploiter des matrices des produits dans un domaine donné.

Le but de ce projet est de générer, à partir d’un modèle de matrice de comparaison, une représentation en HTML. Cet export est hautement configurable car on souhaite jouer sur différents aspects graphiques de la matrice, par exemple la possibilité de:
* mettre des couleurs sur les en-têtes de caractéristiques
* de mettre des couleurs sur certains « types » de valeur (eg « yes » Boolean en vert)
* renverser la matrice (les produits sont affichés en haut au lieu d’être affichés sur la partie gauche)

Au niveau de la technologie Web, on utilise du HTML5, du CSS et du JavaScript. La solution fonctionne sur n’importe quels appareils (téléphones portables, tablettes, ordinateurs, etc.). La procédure prend en entrée une matrice de comparaison, quelques éléments de configuration, et génère statiquement un ensemble de fichiers HTML, CSS, JavaScript. Le résultat est exploitable sur n’importe quel navigateur.
