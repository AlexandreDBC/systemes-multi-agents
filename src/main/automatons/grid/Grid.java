package main.automatons.grid;

import main.utils.Utils;

/**
 * Implémente une matrice de Cellules
 *           j
 *     _ _ _ _ _ _ _ >
 *    |
 * i  |   cells(i, j)
 *    |
 *   \/
 */
public class Grid {
    private final Cell[] cells;
    private final int[] initialCellStates;
    private final int height;
    private final int width;

    /**
     * Instancie une grille
     *
     * @param height La hauteur de la grille, en nombre de cellules
     * @param width  La largeur de la grille, en nombre de cellules
     * @param states Les états par défaut de la grille
     */
    public Grid(int height, int width, int[] states) {
        // Dimensions
        this.height = height;
        this.width = width;

        this.cells = new Cell[height * width];
        this.initialCellStates = new int[height * width];
        for (int i = 0; i < height * width; i++) {
            this.cells[i] = new Cell(states[i]);
            this.initialCellStates[i] = states[i];
        }
    }

    /**
     *
     * @return La hauteur de la grille
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return La largeur de la grille
     */
    public int getWidth() {
        return width;
    }

    /**
     * Renvoie la référence de la cellule aux coordonnées indiquées
     * On considère une grille circulaire. Les voisines des cellules tout en haut sont les cellules toutes en bas, etc.
     *
     * @param i Ordonnée
     * @param j Abscisse
     * @return La cellule aux coordonnées
     */
    public Cell getCell(int i, int j) {
        int position = Utils.mod(i, height) * width + Utils.mod(j, width);
        return this.cells[position];
    }

    /**
     * Réinitialise les états des cellules de la grille
     */
    public void reinit() {
        for (int i = 0; i < height * width; i++) {
            this.cells[i].setState(this.initialCellStates[i]);
        }
    }

    /**
     * Renvoie les huit voisins de la cellule, indexés comme ci-dessous :
     * 0 1 2
     * 3   4
     * 5 6 7
     * On rappelle que la grille est circulaire.
     *
     * @return Un tableau des huit voisins de la cellule aux coordonnées données
     */
    public Cell[] getCellNeighbors(int i, int j) {
        Cell[] neighbors = new Cell[8];
        int index = 0;
        for (int y = -1; y < 2; y++) {
            for (int x = -1; x < 2; x++) {
                if (x == 0 && y == 0) continue;
                neighbors[index++] = this.getCell(i + x, j + y);
            }
        }
        return neighbors;
    }
}
