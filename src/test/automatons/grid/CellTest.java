package test.automatons.grid;
import main.automatons.grid.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires de la classe {@link Cell}.
 * On vérifie ici :
 * - l'initialisation correcte de l'état et du prochain état,
 * - la modification des états courant et suivant,
 * - la gestion des valeurs négatives (erreurs),
 * - le changement d'état (passage de nextState à state),
 * - et la méthode toString().
 */
public class CellTest {

    @Test
    public void testCellConstructor() {

        // Vérifie que le constructeur initialise correctement l'état courant
        // et le prochain état avec la même valeur.
        Cell cell = new Cell(5);
        assertEquals(5, cell.getState());
        assertEquals(5, cell.getNextState());
    }

    @Test
    public void testGetState() {
        // Vérifie que getState() renvoie bien l'état courant de la cellule.
        Cell cell = new Cell(3);
        assertEquals(3, cell.getState());
    }

    @Test
    public void testSetState() {

        // Vérifie que setState() met à jour l'état courant
        // ET synchronise aussi le prochain état avec cette valeur.

        Cell cell = new Cell(0);
        cell.setState(10);
        assertEquals(10, cell.getState());
        assertEquals(10, cell.getNextState());
    }

    @Test
    public void testSetStateNegative() {
        // Vérifie que setState() refuse une valeur d'état négative
        // en levant une IllegalArgumentException.

        Cell cell = new Cell(0);
        assertThrows(IllegalArgumentException.class, () -> cell.setState(-1));
    }

    @Test
    public void testGetNextState() {
        // Tester que nextState == State à l'initialisation
        Cell cell = new Cell(2);
        assertEquals(2, cell.getNextState());
    }

    @Test
    public void testSetNextState() {

        // Tester que le nextState change après le set

        Cell cell = new Cell(0);
        cell.setNextState(7);
        assertEquals(0, cell.getState());
        assertEquals(7, cell.getNextState());
    }

    @Test
    public void testSetNextStateNegative() {

        // Tester IllegalArgumentException poir les nextState < 0 
        Cell cell = new Cell(0);
        assertThrows(IllegalArgumentException.class, () -> cell.setNextState(-1));
    }

    @Test
    public void testChangeState() {
        // Vérifie que changeState() recopie le nextState dans state
        // et que les deux sont ensuite identiques.
        Cell cell = new Cell(1);
        cell.setNextState(5);
        cell.changeState();
        assertEquals(5, cell.getState());
        assertEquals(5, cell.getNextState());
    }

    @Test
    public void testToString() {
        // Vérifie le format du toString
        Cell cell = new Cell(4);
        assertEquals("Cell [state=4]", cell.toString());
    }
}