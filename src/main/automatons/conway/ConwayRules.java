package main.automatons.conway;

import main.automatons.grid.Cell;
import main.automatons.grid.EvolutionRules;
import main.automatons.grid.Grid;

import java.awt.*;
import java.util.Random;

import main.automatons.grid.Cell;
import main.automatons.grid.EvolutionRules;
import main.automatons.grid.Grid;

/**
 * Implémentation des règles du jeu de Conway
 */
public class ConwayRules extends EvolutionRules {
    int aliveCellCount;

    /**
     * Dans ce constructeur j'initialise le tableau de cell de façon random
     *
     * @param aliveCellCount Nombre de cellules vivantes à l'initialisation et placées dans la grille de manière random
     * @param stateColors    Les couleurs selon les états
     */
    public ConwayRules(int aliveCellCount, Color[] stateColors) {
        super(2, stateColors);
        this.aliveCellCount = aliveCellCount;
    }

    /**
     * Une cellule morte possédant exactement trois voisines vivantes, devient vivante
     * Si une cellule vivante possède deux ou trois voisines vivantes, elle vit, sinon elle meurt.
     *
     * @param i    Ordonnée de la cellule
     * @param j    Abscisse de la cellule
     * @param grid La grille à considérer
     * @return Le prochain état de la cellule
     */
    @Override
    public int computeNextCellState(int i, int j, Grid grid) {
        Cell cell = grid.getCell(i, j);
        Cell[] cellsVoisine = grid.getCellNeighbors(i, j);
        int nbVoisins = 0;
        for (int k = 0; k < 8; k++) {
            if (cellsVoisine[k].getState() == 1) nbVoisins++;
        }
        // Le cas ou la cellule est déjà vivante
        if (cell.getState() == 1) {
            // Le cas où la cellule est vivante et à soit plus de 3 voisines ou moins de 2 voisines --> LA MORT AAAAAAAH !!!!
            if (nbVoisins < 2 || nbVoisins > 3) {
                return 0;
            }
            // Le cas ou la cellule est déjà vivante et reste vivante --> rien change trop nuuul
            else {
                return 1;
            }
        }

        // Le cas où la cellule est morte et à exactement 3 voisines --> vous entendez ??? UN BéBé QUI JAILLIT !!!
        if (cell.getState() == 0 && nbVoisins == 3) {
            return 1;
        }
        // Le cas ou la cellule est déjà morte et reste morte --> la honte elle est morte 2 fois
        else return 0;

    }

    /**
     * Génère un ensemble d'état par défaut
     * Ici, on choisit aliveCellCount états à rendre vivant
     *
     * @param statesToGenerate Le nombre d'états à générer
     * @return Un tableau d'entier représentant les états générés
     */
    @Override
    public int[] generateNewStateValues(int statesToGenerate) {
        if (aliveCellCount > statesToGenerate) {
            throw new Error("Plus de cellules à rendre vivantes que de cellules.");
        }
        Random rand = new Random();
        // Les éléments par défaut du tableau sont 0
        int[] generatedStates = new int[statesToGenerate];
        int stateIndex;
        for (int i = 0; i < aliveCellCount; i++) {
            do {
                stateIndex = rand.nextInt(statesToGenerate);
            } while (generatedStates[stateIndex] != 0);
            generatedStates[stateIndex] = 1;
        }
        return generatedStates;
    }
}
