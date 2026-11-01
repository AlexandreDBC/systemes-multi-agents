package test.automatons.grid;

import main.automatons.grid.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



/**
 * Tests des méthodes de {@link Grid}.
 */
public class GridTest {
    private Grid grid;
    private int height = 3;
    private int width = 4;
    private int[] states;

    @BeforeEach
    void setUp() {
        states = new int[height * width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                states[r * width + c] = r * width + c; 
            }
        }
        grid = new Grid(height, width, states);
    }

    @Test
    void testGetHeightAndWidth() {
        // Verification de l'initialisation
        assertEquals(height, grid.getHeight(), "Grid height should match constructor value");
        assertEquals(width, grid.getWidth(), "Grid width should match constructor value");
    }

    @Test
    void testGetCellCoordinates() {
        // Vérifie que les cellules ont été correctement initialisées
        assertEquals(0, grid.getCell(0, 0).getState());
        assertEquals(6, grid.getCell(1, 2).getState());
        assertEquals(11, grid.getCell(2, 3).getState());
    }

    @Test
    void testGetCellWrapping() {
        // Vérifie que la grille est proprement circulaire
        assertEquals(grid.getCell(0, 0).getState(), grid.getCell(height, width).getState());
        assertEquals(grid.getCell(0, 1).getState(), grid.getCell(-height, 1).getState());
        
        assertEquals(grid.getCell(0, 0).getState(), grid.getCell(-3, -4).getState());
        
        assertEquals((height - 1) * width + (width - 1), grid.getCell(-1, -1).getState());
    }

    @Test
    void testGetCellNeighborsOrderAndContents() {
        // Recuperer les voisins de la cellule (1, 1)
        Cell[] neighbors = grid.getCellNeighbors(1, 1);
        assertNotNull(neighbors);
        assertEquals(8, neighbors.length);

        // Voisins attendus
        // 0: (0,0) -> 0*4+0 = 0
        // 1: (1,0) -> 1*4+0 = 4
        // 2: (2,0) -> 2*4+0 = 8
        // 3: (0,1) -> 0*4+1 = 1
        // 4: (2,1) -> 2*4+1 = 9
        // 5: (0,2) -> 0*4+2 = 2
        // 6: (1,2) -> 1*4+2 = 6
        // 7: (2,2) -> 2*4+2 = 10
        int[] expected = {
                0, 4, 8,
                1, 9,
                2, 6, 10
        };
        for (int k = 0; k < 8; k++) {
            assertEquals(expected[k], neighbors[k].getState(), "Neighbor at index " + k + " has unexpected state");
        }
    }

    @Test
    void testReinitRestoresInitialStates() {
        //Verifie que la réinitialisation foctionnne
        
        Cell target = grid.getCell(0, 0);
        int original = target.getState();
        target.setState(9999); // modification
        assertEquals(9999, grid.getCell(0, 0).getState());
        grid.reinit();
        assertEquals(original, grid.getCell(0, 0).getState(), "reinit should restore the original cell state");
    }
}
