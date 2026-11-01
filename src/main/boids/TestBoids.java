package main.boids;

import gui.GUISimulator;

import java.awt.*;
import java.util.HashSet;

/**
 * Test des boids
 */
public class TestBoids {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, Color.decode("#00114e"));

        HashSet<Boids> boidGroups = new HashSet<>();
        /**
         * Les petits rouges, verts et jaunes :
         * - Rapides
         * - Solidaires
         * - L'esprit de groupe
         */
        HashSet<Color> smallOnes = new HashSet<>();
        smallOnes.add(Color.RED);
        smallOnes.add(Color.GREEN);
        smallOnes.add(Color.YELLOW);

        for (Color color : smallOnes) {
            boidGroups.add(new Boids(new HashSet<>(), 80, Math.toRadians(100), 5, 0.4, color, 0.9, 1.0, 2.0, 1, 20, 10, 50));
        }

        /**
         * Les bleus :
         * - Plus gros
         * - Plus lent
         * - Moins de cohésion et plus de séparation
         * - Se font plus facilement séparer par d'autres groupes
         */
        Boids blueBoids = new Boids(new HashSet<>(), 80, Math.toRadians(100), 2, 0.2, Color.blue, 0.5, 1.0, 3.0, 1, 40, 20, 200);
        boidGroups.add(blueBoids);

        gui.setSimulable(new BoidsSimulator(gui, boidGroups));
    }
}
