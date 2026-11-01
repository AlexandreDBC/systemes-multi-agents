package test.automatons.conway;

import main.automatons.conway.ConwayRules;
import main.automatons.grid.Cell;
import main.automatons.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests unitaires de la classe ConwayRules
 * On vérifie :
 * - les règles de survie et de mort pour une cellule vivante ;
 * - les règles de naissance pour une cellule morte ;
 * - la génération des états initiaux: nombre de cellules vivantes, valeurs possibles, erreurs
 */
class ConwayRulesTest {
    private ConwayRules conwayRules;
    private Grid grid;
    private Color[] stateColors;

    @BeforeEach
    void setUp() {
        stateColors = new Color[]{Color.BLACK, Color.WHITE};
        conwayRules = new ConwayRules(5, stateColors);
        int[] states = conwayRules.generateNewStateValues(25);
        grid = new Grid(5, 5, states);
    }

    @Test
    void testLiveCellWithTwoNeighborsSurvives() {
        // cellule vivante avec exactement 2 voisins vivants ===> survie

        // On place la cellule 2,2 vivante
        grid.getCell(2, 2).setState(1);

        // Deux voisins
        grid.getCell(1, 1).setState(1);
        grid.getCell(1, 2).setState(1);

        // Autres voisins
        grid.getCell(2, 1).setState(0);
        grid.getCell(2, 3).setState(0);
        grid.getCell(3, 1).setState(0);
        grid.getCell(3, 2).setState(0);
        grid.getCell(3, 3).setState(0);
        grid.getCell(1, 3).setState(0);

        int result = conwayRules.computeNextCellState(2, 2, grid);
        assertEquals(1, result);
    }

    @Test
    void testLiveCellWithThreeNeighborsSurvives() {
        // cellule vivante avec exactement 3 voisins vivants ===> survie
        grid.getCell(2, 2).setState(1);

        // Trois voisins
        grid.getCell(1, 1).setState(1);
        grid.getCell(1, 2).setState(1);
        grid.getCell(2, 1).setState(1);

        // Autres voisins
        grid.getCell(2, 3).setState(0);
        grid.getCell(3, 1).setState(0);
        grid.getCell(3, 2).setState(0);
        grid.getCell(3, 3).setState(0);
        grid.getCell(1, 3).setState(0);

        int result = conwayRules.computeNextCellState(2, 2, grid);
        assertEquals(1, result);
    }

    @Test
    void testLiveCellWithOneNeighborDies() {
        // cellule vivante avec exactement 1 voisin vivant ===> mort

        // cellule
        grid.getCell(2, 2).setState(1);

        // cellule voisine
        grid.getCell(1, 1).setState(1);

        // Autres voisins
        grid.getCell(1, 2).setState(0);
        grid.getCell(2, 1).setState(0);
        grid.getCell(2, 3).setState(0);
        grid.getCell(3, 1).setState(0);
        grid.getCell(3, 2).setState(0);
        grid.getCell(3, 3).setState(0);
        grid.getCell(1, 3).setState(0);

        int result = conwayRules.computeNextCellState(2, 2, grid);
        assertEquals(0, result);
    }

    @Test
    void testLiveCellWithFourNeighborsDies() {
        // cellule vivante avec >= 3 voisins vivants ===> mort

        // Cellule
        grid.getCell(2, 2).setState(1);

        // 4 voisins vivants
        grid.getCell(1, 1).setState(1);
        grid.getCell(1, 2).setState(1);
        grid.getCell(2, 1).setState(1);
        grid.getCell(2, 3).setState(1);

        // Autres voisins
        grid.getCell(3, 1).setState(0);
        grid.getCell(3, 2).setState(0);
        grid.getCell(3, 3).setState(0);
        grid.getCell(1, 3).setState(0);

        int result = conwayRules.computeNextCellState(2, 2, grid);
        assertEquals(0, result);
    }

    @Test
    void testDeadCellWithExactlyThreeNeighborsBecomesAlive() {
        // cellule morte avec extactement 3 voisins vivants => vivre

        // cellule cible
        grid.getCell(2, 2).setState(0);

        // Trois voisins vivants
        grid.getCell(1, 1).setState(1);
        grid.getCell(1, 2).setState(1);
        grid.getCell(2, 1).setState(1);

        // Autres voisins morts
        grid.getCell(2, 3).setState(0);
        grid.getCell(3, 1).setState(0);
        grid.getCell(3, 2).setState(0);
        grid.getCell(3, 3).setState(0);
        grid.getCell(1, 3).setState(0);

        int result = conwayRules.computeNextCellState(2, 2, grid);
        assertEquals(1, result);
    }

    @Test
    void testDeadCellWithTwoNeighborsStaysDead() {
        // cellule morte ave < 3 voisins vivants ====> mort

        // cellule cible
        grid.getCell(2, 2).setState(0);

        // Deux voisins morts
        grid.getCell(1, 1).setState(1);
        grid.getCell(1, 2).setState(1);

        // Autres voisins morts
        grid.getCell(2, 1).setState(0);
        grid.getCell(2, 3).setState(0);
        grid.getCell(3, 1).setState(0);
        grid.getCell(3, 2).setState(0);
        grid.getCell(3, 3).setState(0);
        grid.getCell(1, 3).setState(0);

        int result = conwayRules.computeNextCellState(2, 2, grid);
        assertEquals(0, result);
    }

    @Test
    void testDeadCellWithFourNeighborsStaysDead() {
        // cellule morte avec > 3 voisins vivants ====> mort

        // cellule cible
        grid.getCell(2, 2).setState(0);

        // 4 voisins vivants
        grid.getCell(1, 1).setState(1);
        grid.getCell(1, 2).setState(1);
        grid.getCell(2, 1).setState(1);
        grid.getCell(2, 3).setState(1);

        // Autres voisins morts
        grid.getCell(3, 1).setState(0);
        grid.getCell(3, 2).setState(0);
        grid.getCell(3, 3).setState(0);
        grid.getCell(1, 3).setState(0);

        int result = conwayRules.computeNextCellState(2, 2, grid);
        assertEquals(0, result);
    }

    @Test
    void testGenerateNewStateValuesWithValidCount() {
        // Tester que le bon nombre de cellule est généré
        int[] states = conwayRules.generateNewStateValues(20);
        assertEquals(20, states.length);
    }

    @Test
    void testGenerateNewStateValuesThrowsErrorWhenAliveCellCountTooHigh() {
        // Vérifier qu'il n'y a plus de cellules vivantes que de cellules disponibles
        assertThrows(Error.class, () -> conwayRules.generateNewStateValues(3));
    }

    @Test
    void testGenerateNewStateValuesOnlyContainsZerosAndOnes() {
        // Tester que les états des cellules ne sont que des 0 ou des 1
        int[] states = conwayRules.generateNewStateValues(50);
        for (int state : states) {
            assertTrue(state == 0 || state == 1);
        }
    }

    @Test
    void testGenerateNewStateValuesRespectsAliveCellCount() {
        // Tester si le nombre de cellules vivantes initialisé est respecté
        int[] states = conwayRules.generateNewStateValues(100);
        int aliveCount = 0;
        for (int state : states) {
            if (state == 1) aliveCount++;
        }
        assertEquals(5, aliveCount);
    }
}