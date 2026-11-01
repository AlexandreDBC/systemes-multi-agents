package main.events.test;


import main.events.Event;

/**
 * Implément un événement contenant un message simple
 */
public class MessageEvent extends Event {
    private final String message;

    /**
     * Instancie un événement doté d'un message
     * @param date La date à laquelle exécuter l'événement
     * @param message Le message de l'événement
     */
    public MessageEvent(long date, String message) {
        super(date);
        this.message = message;
    }

    /**
     * Exécution de l'événement : affichage du message
     */
    @Override
    public void execute() {
        System.out.println(this.getDate() + this.message);
    }
}