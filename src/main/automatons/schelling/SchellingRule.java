package main.automatons.schelling;

import main.automatons.grid.Cell;
import main.automatons.grid.EvolutionRules;
import main.automatons.grid.Grid;
import main.utils.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Implémente les règles du modèle de Schelling.
 * Chaque cellule représente une famille avec une couleur donnée.
 * Si une famille a plus de K voisins avec des couleurs différentes de la sienne,
 * elle migre vers une autre cellule vide.
 */
public class SchellingRule extends EvolutionRules {
    // Générateur de nombres aléatoires
    private final Random rand;
    // Liste des cellules vides
    private List<Point> vacantPositions;
    // Nombre maximal de voisins tolérés
    private int thresholdK;

    /**
     * Implémente des règles pour le jeu de main.automatons.schelling.
     *
     * @param stateCount nombre d'états possibles
     * @param thresholdK Nombre maximal de voisins tolérés
     */
    public SchellingRule(int stateCount, int thresholdK) {
        super(stateCount, Utils.getKColors(stateCount));
        this.thresholdK = thresholdK;
        rand = new Random();
    }

    /**
     * Calcule les états suivants.
     *
     * @param i ligne de la cellule
     * @param j colonne de la cellule
     * @return l'état suivant de la cellule
     */
    @Override
    public int computeNextCellState(int i, int j, Grid grid) {
        Cell currentCell = grid.getCell(i, j);
        // Si cellule vide, aucune evolution
        if (currentCell.getState() == 0) return currentCell.getNextState();
        // Récupération des voisins
        Cell[] neighbors = grid.getCellNeighbors(i, j);
        // Compter les voisins de couleur différente
        int differentNeighbors = 0;
        for (int x = 0; x < 8; x++) {
            if (neighbors[x].getState() != currentCell.getState() && neighbors[x].getState() != 0) {
                differentNeighbors += 1;
            }
        }
        // Si le nombre de voisin dépasse le seuil ======> déménagement
        if (differentNeighbors > thresholdK) {
            while (!vacantPositions.isEmpty()) {
                // choisir une cellule vide au hasard
                int index = rand.nextInt(vacantPositions.size());
                Point position = vacantPositions.remove(index);
                Cell cell = grid.getCell(position.x, position.y);
                // placer la famille dans cette cellule
                cell.setNextState(currentCell.getState());
                // ajouter l'ancienne position dans les cellules vacantes
                vacantPositions.add(new Point(i, j));
                return 0;
            }
        }
        // Retourner l'état acuel si pas assez de différents voisins ou pas de cellule pour se reloger
        return currentCell.getState();
    }

    /**
     * Génère un ensemble d'état par défaut
     *
     * @param statesToGenerate Le nombre d'états à générer
     * @return Un tableau d'entier représentant les états générés
     */
    @Override
    public int[] generateNewStateValues(int statesToGenerate) {
        int[] generatedStates = new int[statesToGenerate];
        Set<Integer> occupied = new HashSet<>();
        int population = statesToGenerate - (int) (statesToGenerate * Config.VACANCY_RATE);
        int state = 0;

        while (population > 0) {
            int position = rand.nextInt(statesToGenerate);
            while (occupied.contains(position)) {
                position = rand.nextInt(statesToGenerate);
            }
            occupied.add(position);
            state = Utils.mod(state, Config.NB_STATE) + 1;
            generatedStates[position] = state;
            population -= 1;
        }
        return generatedStates;
    }

    /**
     * Mise à jour des cellules vides dans la grille
     */
    @Override
    public void reset(Grid grid) {
        vacantPositions = new ArrayList<>();
        // Listing des positions vacantes
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                if (grid.getCell(i, j).getState() == 0) {
                    vacantPositions.add(new Point(i, j));
                }
            }
        }
    }
}
