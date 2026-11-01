package test.automatons.immigration;

import main.automatons.immigration.ImmigrationEvolutionRules;
import main.automatons.grid.Cell;
import main.automatons.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;





/**
 * Test unitaires de la classe ImmigrationEvolutionRules
 */
class ImmigrationEvolutionRulesTest {

    private ImmigrationEvolutionRules rules;
    private Color[] stateColors;
    private static final int STATE_COUNT = 4;

    @BeforeEach
    void setUp() {
        // Initialise les couleurs pour chaque état
        stateColors = new Color[STATE_COUNT];
        stateColors[0] = Color.WHITE;
        stateColors[1] = Color.RED;
        stateColors[2] = Color.BLUE;
        stateColors[3] = Color.GREEN;

        rules = new ImmigrationEvolutionRules(STATE_COUNT, stateColors);
    }

    @Test
    void testComputeNextCellState_WithEnoughNeighbors() {
        // Teste que la cellule change d'état si elle a au moins 3 voisins dans l'état suivant
        Grid grid = createGrid(5, 5);
        grid.getCell(2, 2).setState(0);
        grid.getCell(1, 2).setState(1);
        grid.getCell(2, 1).setState(1);
        grid.getCell(3, 2).setState(1);

        int nextState = rules.computeNextCellState(2, 2, grid);
        assertEquals(1, nextState, "La cellule doit passer à l'état suivant");
    }

    @Test
    void testComputeNextCellState_WithoutEnoughNeighbors() {
        // Teste que la cellule reste dans son état si elle n'a pas assez de voisins
        Grid grid = createGrid(5, 5);
        grid.getCell(2, 2).setState(0);
        grid.getCell(1, 2).setState(1);
        grid.getCell(2, 1).setState(1);

        int nextState = rules.computeNextCellState(2, 2, grid);
        assertEquals(0, nextState, "La cellule doit rester dans son état actuel");
    }

    @Test
    void testComputeNextCellState_StateTransition() {
        // Teste le passage d'état correct
        Grid grid = createGrid(5, 5);
        grid.getCell(2, 2).setState(2);
        grid.getCell(1, 2).setState(3);
        grid.getCell(2, 1).setState(3);
        grid.getCell(3, 2).setState(3);

        int nextState = rules.computeNextCellState(2, 2, grid);
        assertEquals(3, nextState, "La cellule doit passer à l'état 3");
    }

    @Test
    void testComputeNextCellState_CycleBack() {
        // Teste que le dernier état revient à 0
        Grid grid = createGrid(5, 5);
        grid.getCell(2, 2).setState(3);
        grid.getCell(1, 2).setState(0);
        grid.getCell(2, 1).setState(0);
        grid.getCell(3, 2).setState(0);

        int nextState = rules.computeNextCellState(2, 2, grid);
        assertEquals(0, nextState, "La cellule doit revenir à l'état 0");
    }

    private Grid createGrid(int height, int width) {
        return new Grid(height, width, new int[height * width]);
    }

    @Test
    void testGenerateNewStateValues_Length() {
        // Teste que le nombre d'états générés correspond au nombre demandé
        int[] generatedStates = rules.generateNewStateValues(10);
        assertEquals(10, generatedStates.length, "Le nombre d'états générés doit être 10");
    }

    @Test
    void testGenerateNewStateValues_ValidStates() {
        // Teste que tous les états générés sont valides (entre 0 et NB_STATE-1)
        int[] generatedStates = rules.generateNewStateValues(50);
        for (int state : generatedStates) {
            assertTrue(state >= 0 && state < STATE_COUNT,
                    "L'état généré doit être entre 0 et " + (STATE_COUNT - 1));
        }
    }
}
