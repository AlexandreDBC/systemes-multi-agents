package main.automatons.grid;

import java.awt.*;

/**
 * Décrit les règles d'évolution des cellules d'un automate cellulaire
 */
public abstract class EvolutionRules {
    /**
     * Le nombre d'états de l'automate cellulaire
     * Si stateCount = 3, on a 4 états : 0, 1, 2 et 3.
     */
    private final int stateCount;
    /**
     * Les couleurs de chaque état
     */
    private final Color[] stateColors;

    /**
     * Instancie des règles d'évolution des cellules d'un automate cellulaire
     *
     * @param stateCount  Le nombre d'états de l'automate
     * @param stateColors Les couleurs pour chaque état
     */
    public EvolutionRules(int stateCount, Color[] stateColors) {
        if (stateCount < 1) {
            throw new IllegalArgumentException("Le nombre d'états doit être strictement positif.");
        }
        if (stateColors.length != stateCount) {
            throw new IllegalArgumentException("Le nombre d'états ne correspond pas au nombre de couleurs d'états.");
        }
        this.stateCount = stateCount;
        this.stateColors = stateColors;
    }

    /**
     * Calcule l'état suivant d'une cellule en fonction de sa grille
     *
     * @param i    Ordonnée de la cellule
     * @param j    Abscisse de la cellule
     * @param grid La grille à considérer
     * @return Le prochain état de la cellule
     * @implNote Ne mets pas à jour l'état
     */
    public abstract int computeNextCellState(int i, int j, Grid grid);

    /**
     * Renvoie la couleur associée à un état
     *
     * @param state L'état
     * @return La couleur associée
     */
    public Color getStateColor(int state) {
        if (state < 0 || state >= this.stateCount) {
            throw new IllegalArgumentException("L'état donné n'est un pas un état de ces règles.");
        }
        return this.stateColors[state];
    }

    /**
     * Génère un ensemble d'état par défaut
     *
     * @param statesToGenerate Le nombre d'états à générer
     * @return Un tableau d'entier représentant les états générés
     */
    public abstract int[] generateNewStateValues(int statesToGenerate);

    public int getStateCount() {
        return this.stateCount;
    }

    /**
     * Fonction qui peut être appelée pour réinitialiser par exemple les variables présentes dans une règle
     *
     * @param grid La nouvelle grille pour réinitialiser lesdites variables.
     */
    public void reset(Grid grid) {
    }
}
