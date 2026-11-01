package main.boids;

import gui.GraphicalElement;

import java.awt.*;

/**
 * Classe permettant le dessin d'un poisson simple
 */
public class Fish implements GraphicalElement {
    private final int x;
    private final int y;
    private final double angle; // Angle en radians
    private final int size;
    private final Color color;

    /**
     * Instancie un poisson pour son dessin
     *
     * @param x     Abscisse
     * @param y     Ordonnée
     * @param angle Orientation du poisson
     * @param size  Taille globale du poisson
     * @param color Couleur du poisson
     */
    public Fish(int x, int y, double angle, int size, Color color) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.size = size;
        this.color = color;
    }

    /**
     * Dessine le poisson
     *
     * @param g Agent graphique
     */
    @Override
    public void paint(Graphics2D g) {
        g.translate(x, y);
        g.setColor(color);
        g.rotate(angle);

        // ---- Dessin du corps (ovale) ----
        int bodyWidth = size * 2;
        int bodyHeight = size;
        g.fillOval(-size, -bodyHeight / 2, bodyWidth, bodyHeight);

        // ---- Dessin de la queue (triangle) ----
        int[] tailX = {-(size + 15), -size, -(size + 15)};
        int[] tailY = {-bodyHeight / 2, 0, bodyHeight / 2};
        g.fillPolygon(tailX, tailY, 3);

        // Reset transformations
        g.rotate(-angle);
        g.translate(-x, -y);
    }
}
