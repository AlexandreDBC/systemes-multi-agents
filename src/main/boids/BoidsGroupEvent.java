package main.boids;


import main.events.Event;
import main.events.EventManager;

/**
 * Mise à jour d'un groupe spécifique de Boids
 */
public class BoidsGroupEvent extends Event {
    private final Boids boidGroup;
    private final BoidsSimulator simulator;
    private final EventManager manager;

    /**
     * Instancie un événement d'boids
     *
     * @param date      Date de l'événement
     * @param boidGroup Groupe d'un boid associé
     * @param simulator Simulateur
     * @param manager   Gestionnaire d'événements de la simulation
     */
    public BoidsGroupEvent(long date, Boids boidGroup, BoidsSimulator simulator, EventManager manager) {
        super(date);
        this.boidGroup = boidGroup;
        this.simulator = simulator;
        this.manager = manager;
    }

    /**
     * Met à jour les boids et ajoute un nouvel événement
     */
    @Override
    public void execute() {
        boidGroup.forEach(boid -> boid.update(boidGroup));
        simulator.draw();

        int delay = boidGroup.getEventDelay();
        manager.addEvent(new BoidsGroupEvent(getDate() + delay, boidGroup, simulator, manager));
    }
}
