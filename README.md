# **Simulation Orientée-Objet de Systèmes Multiagents**

## 1. Vue d'ensemble du projet

Ce projet, réalisé dans le cadre du cours de Programmation Orientée Objet (POO) à l'Ensimag, est une application Java permettant de simuler graphiquement divers systèmes multiagents. L'accent a été mis sur une conception modulaire et extensible, en appliquant les principes fondamentaux de la POO tels que l'héritage, l'abstraction et l'encapsulation.

L'application permet de simuler deux grandes familles de systèmes :
*   **Les automates cellulaires** : Des systèmes où les agents sont positionnés sur une grille fixe et évoluent en fonction de l'état de leurs voisins (Jeu de la Vie, Jeu de l'Immigration, Modèle de Schelling).
*   **Les systèmes d'essaims (Boids)** : Des systèmes où les agents se déplacent librement dans un espace 2D, leur comportement étant régi par des règles d'interaction locales (cohésion, alignement, séparation).

## 2. Architecture et Conception

L'architecture a été pensée pour être la plus générique possible, afin de faciliter l'ajout de nouvelles simulations avec un minimum de duplication de code.

#### **Cœur de la Simulation : `GUISimulator` et `Simulable`**
L'interaction entre la logique de simulation et l'interface graphique fournie (`gui.jar`) est assurée par l'interface `Simulable`. Toute classe de simulation implémente cette interface, fournissant les méthodes `next()` pour avancer d'un pas de temps et `restart()` pour réinitialiser l'état initial. Cela permet à la `GUISimulator` de piloter n'importe quelle simulation de manière totalement agnostique.

#### **Gestion des automates cellulaires (Package `main.automatons.grid`)**
*   **`Grid` et `Cell`** : Gèrent la structure de données de la grille et l'état de chaque cellule.
*   **`EvolutionRules` (Classe abstraite)** : Elle définit le contrat que toute règle de jeu doit respecter, notamment la méthode `computeNextCellState()`.
*   **`GridSimulator`** : Ce simulateur prend en paramètre une instance de `EvolutionRules`. Il orchestre la simulation (mise à jour des cellules, dessin) sans connaître les règles spécifiques du jeu qu'il exécute. 

#### **Système de Boids (Package `main.boids`)**
* Les boids ont un comportement basé sur des règles de **cohésion, d'alignement et de séparation**
*   **`Boid`** : Représente un agent individuel. Sa méthode `update()` calcule les forces basées sur les trois règles de comportement classiques.
*   **`Boids`** : Représente un groupe de boids partageant les mêmes paramètres (couleur, vitesse, perception, ...)
*   **`BoidsGroupEvent`** : À intervalles réguliers, il demande à chaque boid d'un groupe de se mettre à jour, puis il se re-planifie pour le prochain pas de temps.

## 3. Structure des Fichiers et Dossiers

Le code source est organisé en packages reflétant l'architecture décrite ci-dessus.

```
.
├── lib/
│   └── gui.jar           # Bibliothèque graphique fournie
├── Makefile              # Pour compiler, exécuter et nettoyer le projet
├── readme.md             # Ce fichier
└── src/
    ├── main.balls/            # Simulation simple de balles rebondissantes
    ├── main.automatons.conway/           # Règles spécifiques au Jeu de la Vie de Conway
    ├── main.automatons.grid/             # Framework générique pour les automates cellulaires
    ├── main.automatons.immigration/      # Règles spécifiques au Jeu de l'Immigration
    ├── main.invader/          # Code d'exemple fourni
    ├── main.automatons.schelling/        # Règles spécifiques au modèle de ségrégation de Schelling
    ├── main.boids/            # Implémentation du modèle de Boids
    ├── main.events           # Package qui définit la gestion des actions planifiées 
    ├── mains.utils/            # Classes utilitaires (génération de couleurs, etc.)
    └── test/
```

## 4. Compilation et Exécution

#### **Prérequis**
*   Java Development Kit (JDK) version 17 ou supérieure.
*   GNU Make.
*   [junit-platform-console-standalone-6.0.1.jar](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/6.0.1/junit-platform-console-standalone-6.0.1.jar)


#### **Compiler le projet**
Pour compiler l'ensemble des fichiers source :
```bash
make
```
Cette commande créera un dossier `bin/` contenant tous les fichiers `.class`.

#### **Exécuter le projet**

```bash
make run_invader
```
ou

```bash
make run_balls
```
ou

```bash
make run_conway
```
ou

```bash
make run_immigration
```
ou
 
```bash
make run_schelling
```

ou 
```bash
make run_boids
```

### **Execution des tests unitaires**
```bash
make tests
```


#### **Autres commandes Makefile**

*   **Générer la documentation (Doxyfile) :**
    ```bash
    make doc
    ```
    La documentation sera générée dans le dossier `doc/` et accessible en ouvrant `doc/html/index.html`.

*   **Nettoyer le projet :**
    ```bash
    make clean
    ```
    Cette commande supprime les dossiers `bin/` et `doc/`.