package main.automatons.grid;

/**
 * Implémente une Cellule
 */
public class Cell {
    /**
     * L'état de la cellule
     */
    private int state = 0;
    private int nextState = 0;

    /**
     * Instancie une cellule avec un état
     */
    public Cell(int state) {
        this.state = state;
        this.nextState = state;
    }

    /**
     * @return L'état actuel de la cellule
     */
    public int getState() {
        return this.state;
    }

    /**
     * Modifie l'état de la cellule
     *
     * @param state Le nouvel état, doit être positif ou nul
     */
    public void setState(int state) {
        if (state < 0) {
            throw new IllegalArgumentException("State cannot be negative.");
        }
        this.state = state;
        this.nextState = state;
    }

    /**
     * @return L'état actuel de la cellule
     */
    public int getNextState() {
        return this.nextState;
    }

    /**
     * Modifie l'état suivant de la cellule
     *
     * @param nextState L'état suivant doit être positif ou nul
     */
    public void setNextState(int nextState) {
        if (nextState < 0) {
            throw new IllegalArgumentException("State cannot be negative.");
        }
        this.nextState = nextState;
    }

    /**
     * Modifie l'état de la cellule en fonction de l'état marqué
     * comme le suivant à appliquer.
     */
    public void changeState() {
        this.setState(this.nextState);
    }

    /**
     * Représentation textuelle d'une cellule
     *
     * @return Chaîne de caractère indiquant l'état de la cellule
     */
    @Override
    public String toString() {
        return "Cell [state=" + state + "]";
    }
}
