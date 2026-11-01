package main.boids;

import gui.GUISimulator;
import gui.Simulable;
import main.events.EventManager;

import java.util.HashSet;
import java.util.Random;


/**
 * Classe permettant la simulation d'essaims d'boids
 */
public class BoidsSimulator implements Simulable {
    private final GUISimulator gui;
    //Gestionnaire d'événements
    private final EventManager manager;
    // Liste de tous les groupes
    private final HashSet<Boids> groups;

    /**
     * Instancie les differents groupes de boids
     *
     * @param gui Agent graphique
     * @param groups Groupes de boids
     */
    public BoidsSimulator(GUISimulator gui, HashSet<Boids> groups) {
        this.gui = gui;
        this.groups = groups;
        this.manager = new EventManager();

        for (Boids group : groups) {
            group.setBoidGroups(groups);
            manager.addEvent(new BoidsGroupEvent(0, group, this, manager));
        }

        draw();
    }

    /**
     * Implémente la méthode next du simulateur
     */
    @Override
    public void next() {
        manager.next();
    }

    /**
     * Implémente la méthode restart du simulateur
     */
    @Override
    public void restart() {//Moves back the main.balls to the beginning
        manager.next();

        Random rand = new Random();

        for (Boids group : groups) {
            for (Boid boid : group) {
                int x = rand.nextInt(Config.SCREEN_WIDTH);
                int y = rand.nextInt(Config.SCREEN_WIDTH);
                boid.setPosition(new Vector(x, y));
                boid.setVelocity(new Vector(0, 0));
            }
            manager.addEvent(new BoidsGroupEvent(0, group, this, manager));
        }

        draw();
    }

    /**
     * Dessin chaque boid
     */
    public void draw() {
        gui.reset(); //We clear the screen
        for (Boids group : groups) {
            for (Boid boid : group) {
                //compute the angle from the velocity
                double angle = Math.atan2(boid.getVelocity().getY(), boid.getVelocity().getX());
                //draw the triangle
                if (Config.USE_FISHS) {
                    gui.addGraphicalElement(new Fish((int) boid.getPosition().getX(), (int) boid.getPosition().getY(), angle, group.getBoidSize(), group.getBoidColor()));
                } else {
                    gui.addGraphicalElement(new Triangle((int) boid.getPosition().getX(), (int) boid.getPosition().getY(), angle, group.getBoidSize(), group.getBoidColor()));
                }
            }
        }

    }
}
