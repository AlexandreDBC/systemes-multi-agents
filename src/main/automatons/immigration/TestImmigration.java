package main.automatons.immigration;

import gui.GUISimulator;
import main.automatons.grid.GridSimulator;
import main.utils.Utils;

import java.awt.*;

/**
 * Test du jeu de l'main.automatons.immigration
 */
public class TestImmigration {
    public static void main(String[] args) {
        Color[] stateColors = Utils.getKColors(Config.NB_STATE);

        ImmigrationEvolutionRules rule = new ImmigrationEvolutionRules(Config.NB_STATE, stateColors);
        GUISimulator gui = new GUISimulator(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, Color.WHITE);
        GridSimulator gridSimulator = new GridSimulator(rule, gui, Config.GRID_HEIGHT, Config.GRID_WIDTH, Config.CELL_SIZE);

        gui.setSimulable(gridSimulator);
    }
}
