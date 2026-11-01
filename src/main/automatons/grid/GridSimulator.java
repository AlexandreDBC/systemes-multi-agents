package main.automatons.grid;

import main.events.EventManager;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;

/**
 * Implémente un simulateur de balles
 */
public class GridSimulator implements Simulable {
    /**
     * La grille à simuler
     */
    private final Grid grid;
    /**
     * Agent graphique
     */
    private final GUISimulator gui;
    /**
     * Règles d'évolution de la simulation
     */
    private final EvolutionRules evolutionRules;
    /**
     * Hauteur de la grille
     */
    private final int gridHeight;
    /**
     * Largeur de la grille
     */
    private final int gridWidth;
    /**
     * La taille d'une cellule, en pixels
     */
    private final int cellSize;
    /**
     * Gestionnaire d'évènements
     */
    private final EventManager manager;

    /**
     * Crée un simulateur de grille pour automate cellulaire
     *
     * @param evolutionRules Les règles d'évolution
     * @param gui            L'agent graphique
     * @param gridHeight     Hauteur de la grille
     * @param gridWidth      Largeur de la grille
     * @param cellSize       La taille des cellules, en pixels
     */
    public GridSimulator(EvolutionRules evolutionRules, GUISimulator gui, int gridHeight, int gridWidth, int cellSize) {
        this.evolutionRules = evolutionRules;
        this.gui = gui;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.cellSize = cellSize;

        int[] initialStates = evolutionRules.generateNewStateValues(gridHeight * gridWidth);
        this.grid = new Grid(gridHeight, gridWidth, initialStates);

        this.evolutionRules.reset(grid);

        this.manager = new EventManager();
        this.manager.addEvent(new GridEvent(0, this, manager));

        this.draw();
    }

    /**
     * Calcule et met à jour les futurs états des cellules en fonction de leur état actuel, puis redessine.
     */
    @Override
    public void next() {
        manager.next();
    }

    public void updateState() {
        // Calculs et stockage des prochains états
        for (int i = 0; i < this.gridHeight; i++) {
            for (int j = 0; j < this.gridWidth; j++) {
                Cell cell = this.grid.getCell(i, j);

                cell.setNextState(evolutionRules.computeNextCellState(i, j, this.grid));
            }
        }
        // Mise à jour des états des cellules
        for (int i = 0; i < this.gridHeight; i++) {
            for (int j = 0; j < this.gridWidth; j++) {
                Cell cell = this.grid.getCell(i, j);
                cell.changeState();
            }
        }
        // Mise à jour graphique
        this.draw();
    }


    /**
     * Réinitialise la simulation à son état d'origine.
     */
    @Override
    public void restart() {
        this.grid.reinit();
        this.evolutionRules.reset(grid);

        manager.restart();
        manager.addEvent(new GridEvent(0, this, manager));

        this.draw();
    }

    /**
     * Dessine les cellules et leur état
     */
    public void draw() {
        gui.reset();
        for (int i = 0; i < this.gridHeight; i++) {
            for (int j = 0; j < this.gridWidth; j++) {
                Cell cell = this.grid.getCell(i, j);
                Color cellColor = evolutionRules.getStateColor(cell.getState());
                gui.addGraphicalElement(new Rectangle(j * this.cellSize, i * this.cellSize, Color.black, cellColor, cellSize, cellSize));
            }
        }
    }
}
