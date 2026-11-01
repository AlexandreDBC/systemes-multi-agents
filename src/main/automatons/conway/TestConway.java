package main.automatons.conway;

import gui.GUISimulator;
import main.automatons.grid.GridSimulator;

import java.awt.*;

/**
 * Test du jeu de la vie de Conway
 */
public class TestConway {
    public static void main(String[] args) {
        ConwayRules conwayRules = new ConwayRules(Config.CELL_ALIVE, Config.COLORS);
        GUISimulator gui = new GUISimulator(Config.GRID_WIDTH*Config.CELL_SIZE, Config.GRID_HEIGHT*Config.CELL_SIZE, Color.BLACK);
        GridSimulator gridSimulator = new GridSimulator(conwayRules, gui, Config.GRID_HEIGHT, Config.GRID_WIDTH, Config.CELL_SIZE);
        gui.setSimulable(gridSimulator);
    }
}