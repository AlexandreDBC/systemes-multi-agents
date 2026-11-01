package main.balls;

import main.events.EventManager;
import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

/**
 * Implémente un simulateur de balles
 */
class BallsSimulator implements Simulable {
    /**
     * La liste de balles à simuler
     */
    private final Balls balls;
    /**
     * Agent graphique
     */
    private final GUISimulator gui;
    /**
     * Gestionnaire d'evènements
     */
    private final EventManager manager;

    /**
     * Crée un simulateur de balleS
     *
     * @param gui   Agent graphique
     * @param balls La liste de balles à simuler
     */
    public BallsSimulator(GUISimulator gui, Balls balls) {
        this.gui = gui;
        this.balls = balls;
        this.manager = new EventManager();
        this.manager.addEvent(new BallEvent(0, balls, this, manager));
        this.draw();
    }

    @Override
    public void next() {
        manager.next();
    }

    @Override
    public void restart() {
        this.balls.reInit();

        //on reinitialise le gestionnaire et on aoute les evenements pour pouvoir debuter la simulation
        manager.restart();
        manager.addEvent(new BallEvent(0, balls, this, manager));

        this.draw();
    }

    /**
     * Dessine les balles
     */
    public void draw() {
        gui.reset();
        for (Ball ball : this.balls) {
            gui.addGraphicalElement(new Oval(ball.x, ball.y, ball.getBallColor(), ball.getBallColor(), ball.getRadius() * 2, ball.getRadius() * 2));
        }
    }
}
