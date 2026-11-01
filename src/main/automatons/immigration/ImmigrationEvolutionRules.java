package main.automatons.immigration;

import java.awt.*;
import java.util.Random;

import main.automatons.grid.Cell;
import main.automatons.grid.EvolutionRules;
import main.automatons.grid.Grid;

/**
 * Implémentation des règles du jeu de l'main.automatons.immigration
 */
public class ImmigrationEvolutionRules extends EvolutionRules {


    /**
     * Implémente des règles pour le jeu de l'main.automatons.immigration
     *
     * @param stateCount  Nombre d'états du jeu
     * @param stateColors Couleurs correspondant à chaque état
     */
    public ImmigrationEvolutionRules(int stateCount, Color[] stateColors) {
        super(stateCount, stateColors);
    }

    /**
     * Calcule le prochain etat de la cellule (i,j)
     *
     * @param i Ordonnée de la cellule
     * @param j Abscisse de la cellule
     * @return prochain etat de la cellule
     */
    @Override
    public int computeNextCellState(int i, int j, Grid grid) {
        Cell currentCell;
        currentCell = grid.getCell(i, j);
        int currentState = currentCell.getState();
        int nextState = currentState + 1;
        if (nextState == getStateCount()) {
            nextState = 0;
        }
        int neighborCount = 0;

        // Compte le nombre de voisins dans l'état k+1
        Cell[] neighbors = grid.getCellNeighbors(i, j);
        for (Cell neighbor : neighbors) {
            if (neighbor.getState() == nextState) {
                neighborCount++;
            }
        }

        //Si le nombre de voisins dans l'état k est >= à 3, on passe à k+1
        if (neighborCount >= 3) {
            return nextState;
        } else {
            return currentState;
        }
    }

    /**
     * Génère un ensemble d'états par défaut
     *
     * @param statesToGenerate Le nombre d'états à générer
     * @return Un tableau d'entier représentant les états générés
     */
    @Override
    public int[] generateNewStateValues(int statesToGenerate) {
        int[] generatedStates = new int[statesToGenerate];
        Random rand = new Random();
        for (int i = 0; i < statesToGenerate; i++) {
            generatedStates[i] = rand.nextInt(Config.NB_STATE);
        }
        return generatedStates;
    }
}