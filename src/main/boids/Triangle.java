package main.boids;

import gui.GraphicalElement;

import java.awt.*;

/**
 * Classe permettant le dessin d'un triangle
 */
public class Triangle implements GraphicalElement {
    private final int x;
    private final int y;
    private final double angle; // is in RADIANS
    private final int size;
    private final Color color;

    /**
     * Instancie un triangle pour son dessin
     *
     * @param x     Abcisse de la position
     * @param y     Ordonnée de la position
     * @param angle Angle du triangle
     * @param size  Taille du triangle
     * @param color Couleur du triangle
     */
    public Triangle(int x, int y, double angle, int size, Color color) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.size = size;
        this.color = color;
    }

    /**
     * Dessine le triangle
     *
     * @param g Agent graphique
     */
    @Override
    public void paint(Graphics2D g) {
        //go to right coordinates
        g.translate(x, y);
        //set the right color
        g.setColor(color);
        //rotate to the right angle
        g.rotate(angle);
        //Save the points of the triangle around the origin
        int[] xPoints = {size, (int) (-size * 0.5), (int) (-size * 0.5)};
        int[] yPoints = {0, (int) (-size * 0.4), (int) (size * 0.4)};
        //draw the triangle
        g.fillPolygon(xPoints, yPoints, 3);
        //resets position
        g.rotate(-angle);
        g.translate(-x, -y);
    }
}
