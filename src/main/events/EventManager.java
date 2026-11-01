package main.events;

import java.util.PriorityQueue;

/**
 * Gestionnaire d'évènements
 */
public class EventManager {

    /**
     * File d'attente des évènements deja ordonnée
     */
    private final PriorityQueue<Event> eventQueue;
    /**
     * Temps courant de la simulation
     */
    private long currentDate;

    /**
     * Constructeur
     */
    public EventManager() {
        this.currentDate = 0;
        this.eventQueue = new PriorityQueue<>();
    }

    /**
     * Ajoute un évènement dans la file d'attente
     *
     * @param e L'évènement à ajouter
     */
    public void addEvent(Event e) {
        this.eventQueue.add(e);
    }

    /**
     * Incrémente la date, puis exécute tous les évènements qui doivent se produire
     */
    public void next() {
        this.currentDate++;
        while (!this.eventQueue.isEmpty() && this.eventQueue.peek().getDate() <= this.currentDate) { //peek() n'enlève pas l'évènement en tete
            // poll() retire l'élément
            Event e = this.eventQueue.poll();
            // Execute l'évènement
            assert e != null;
            e.execute();
        }
    }

    /**
     * Vérifie si la simulation est finie
     *
     * @return true si la file est vide
     */
    public boolean isFinished() {
        return this.eventQueue.isEmpty();
    }

    /**
     * Vide la file et reinitiqlise le temps
     */
    public void restart() {
        this.currentDate = 0;
        this.eventQueue.clear();
    }
}