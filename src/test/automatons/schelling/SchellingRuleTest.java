package test.automatons.schelling;

import main.automatons.grid.Grid;
import main.automatons.schelling.Config;
import main.automatons.schelling.SchellingRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests unitaires de la classe SchellingRule
 */
public class SchellingRuleTest {
    private SchellingRule rule;
    private Grid grid;

    @BeforeEach
    void setUp() {
        rule = new SchellingRule(3, 2);
        int[] states = rule.generateNewStateValues(25);
        grid = new Grid(5, 5, states);
    }

    @Test
    void testComputeNextCellStateVacantCell() {
        // Vérifie qu'une cellule vide reste vide lors du calcul de son prochain état
        grid.getCell(0, 0).setState(0);
        int nextState = rule.computeNextCellState(0, 0, grid);
        assertEquals(0, nextState);
    }

    @Test
    void testComputeNextCellStateNoMigration() {
        // Vérifie que la cellule avec au maximum deux voisins différents ne migre pas
        grid.getCell(0, 0).setState(1);
        grid.getCell(-1, -1).setState(1);
        grid.getCell(-1, 0).setState(1);
        grid.getCell(0, -1).setState(1);
        grid.getCell(0, 1).setState(1);
        grid.getCell(1, 0).setState(1);
        grid.getCell(1, 1).setState(1);

        rule.reset(grid);
        int nextState = rule.computeNextCellState(0, 0, grid);
        assertEquals(1, nextState);

        grid.reinit();
    }

    @Test
    void testComputeNextCellStateMigration() {
        // Vérifie qu'une cellule avec plus de deux voisins différents migre
        grid.getCell(1, 1).setState(1);
        grid.getCell(0, 0).setState(2);
        grid.getCell(0, 1).setState(2);
        grid.getCell(0, 2).setState(2);
        grid.getCell(1, 0).setState(2);
        grid.getCell(1, 2).setState(2);
        rule.reset(grid);
        int nextState = rule.computeNextCellState(1, 1, grid);
        assertEquals(0, nextState);
    }

    @Test
    void testGenerateNewStateValues() {
        // Vérifier que les states sont dans la bonne plage de valeur
        // Vérifier que la proportion de cellules vides à créer est respectée
        int[] states = rule.generateNewStateValues(25);
        assertEquals(25, states.length);
        int emptyCount = 0;
        for (int state : states) {
            assertTrue(state >= 0 && state <= Config.NB_STATE);
            if (state == 0) emptyCount++;
        }
        int expectedVacancy = (int) (25 * Config.VACANCY_RATE);
        assertEquals(expectedVacancy, emptyCount, 1);
    }

    @Test
    void testNoVacantCellsForMigration() {
        // Tester qu'une cellule ne change pas, s'il n'y a plus de cellule pour migrer
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid.getCell(i, j).setState(1);
            }
        }
        grid.getCell(2, 2).setState(2);
        rule.reset(grid);
        int nextState = rule.computeNextCellState(2, 2, grid);
        assertEquals(2, nextState);
    }
}
