package main.events;

/**
 * Classe abstraite représentant un évènement planifiable
 */
public abstract class Event implements Comparable<Event> {

    /**
     * La date à laquelle l'évènement doit être exécuté
     */
    private final long date;

    /**
     * Constructeur d'un évènement
     *
     * @param date Date à laquelle exécuter l'événement
     */
    public Event(long date) {
        this.date = date;
    }

    /**
     * @return la date (date de l'évènement)
     */
    public long getDate() {
        return this.date;
    }

    /**
     * Contient la logique de l'action à effectuer
     */
    public abstract void execute();

    /**
     * Compare deux évènements en fonction de leur date
     *
     * @param e L'autre évènement à comparer
     * @return un entier négatif, zéro, ou positif si cet évènement est avant,
     * au même moment, ou après l'évènement e
     */
    @Override
    public int compareTo(Event e) {
        return Long.compare(this.date, e.getDate());
    }
}