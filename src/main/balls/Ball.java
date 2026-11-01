package main.balls;

import java.awt.*;

/**
 * Une balle, un point avec une taille et une couleur
 */
class Ball extends Point {
    /**
     * La position initiale de la balle
     */
    private final Point initialPosition;
    /**
     * La couleur de la balle
     */
    private Color ballColor = Color.white;
    /**
     * Le rayon de la balle
     */
    private int radius = 30;
    /**
     * Orientation de la balle
     * 1 (Left to Right or Top to Bottom)
     * -1 (Right to Left or Bottom to Top)
     */
    private final Point orientation = new Point(1, 1);

    /**
     * Crée une nouvelle balle
     *
     * @param x         Position en X
     * @param y         Position en Y
     * @param radius    La rayon del la balle
     * @param ballColor La couleur de la balle
     */
    public Ball(int x, int y, int radius, Color ballColor) {
        super(x, y);
        this.ballColor = ballColor;
        this.radius = radius;
        this.initialPosition = new Point(x, y);
    }

    /**
     * Renvoie la couleur de la balle
     *
     * @return La couleur de la balle
     */
    public Color getBallColor() {
        return this.ballColor;
    }

    /**
     * Renvoie le diamètre de la balle
     *
     * @return Le diamètre de la balle
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * Renvoie l'orientation de la balle
     *
     * @return L'orientation de la balle
     */
    public Point getOrientation() {
        return this.orientation;
    }

    /**
     * Renvoie la position initiale de la balle
     *
     * @return La position initiale de la balle
     */
    public Point getInitialPosition() {
        return this.initialPosition;
    }

    /**
     * Réinitialisation la position et l'orientation de la balle
     */
    public void reInit() {
        this.orientation.setLocation(1, 1);
        this.setLocation(initialPosition);
    }

    /**
     * Renvoie une chaîne de caractère des coordonnées de la balle
     *
     * @return Une chaîne de caractère des coordonnées de la balle
     */
    @Override
    public String toString() {
        return String.format("(x: %d, y: %d)\n", this.x, this.y);
    }
}
