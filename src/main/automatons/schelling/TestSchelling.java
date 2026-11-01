package main.automatons.schelling;

import gui.GUISimulator;
import main.automatons.grid.GridSimulator;

import java.awt.*;

/**
 * Test du jeu de main.automatons.schelling
 */
public class TestSchelling {
    public static void main(String[] args) {
        SchellingRule rule = new SchellingRule(Config.NB_STATE + 1, Config.THRESHOLD_K);
        GUISimulator gui = new GUISimulator(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, Color.BLACK);
        GridSimulator gridSimulator = new GridSimulator(rule, gui, Config.GRID_HEIGHT, Config.GRID_WIDTH, Config.CELL_SIZE);

        gui.setSimulable(gridSimulator);
    }
}
