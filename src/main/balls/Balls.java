package main.balls;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Une liste de balle sur laquelle il est possible d'intéragir simultanément
 */
class Balls extends ArrayList<Ball> {
    /**
     * Crée une liste de balles
     *
     * @param balls Les balles à ajouter à la nouvelle liste
     */
    public Balls(Ball... balls) {
        super(Arrays.asList(balls));
    }

    /**
     * Translate l'ensemble des balles de la liste
     *
     * @param dx Décalage en X
     * @param dy Décalage en Y
     */
    public void translate(int dx, int dy) {
        for (Ball ball : this) {
            int x = ball.x;
            int y = ball.y;
            Point orientation = ball.getOrientation();
            int radius = ball.getRadius();
            // hit right side
            if (ball.x + dx + radius > Config.SCREEN_WIDTH && orientation.x == 1) {
                x = Config.SCREEN_WIDTH - radius;
                orientation.setLocation(-1 * orientation.x, orientation.y); // change orientation
                // hit left side
            } else if (ball.x - dx - radius < 0 && orientation.x == -1) {
                x = radius;
                orientation.setLocation(-1 * orientation.x, orientation.y);
            } else {
                x += orientation.x * dx;
            }
            // hit bottom side
            if (ball.y + dy + radius > Config.SCREEN_HEIGHT && orientation.y == 1) {
                y = Config.SCREEN_HEIGHT - radius;
                orientation.setLocation(orientation.x, -1 * orientation.y);
                // hit top side
            } else if (ball.y - dy - radius < 0 && orientation.y == -1) {
                y = radius;
                orientation.setLocation(orientation.x, -1 * orientation.y);
            } else {
                y += orientation.y * dy;
            }
            ball.setLocation(x, y);
        }
    }

    /**
     * Réinitialise les balles
     */
    public void reInit() {
        for (Ball ball : this) {
            ball.reInit();
        }
    }

    /**
     * Représentation textuelle de toutes les balles
     *
     * @return Une chaîne de caractère représentant les balles de la liste
     */
    @Override
    public String toString() {
        StringBuilder ballPositions = new StringBuilder("Ball positions :\n");
        for (int i = 0; i < this.size(); i++) {
            ballPositions.append(i).append(": ").append(this.get(i).toString());
        }
        return ballPositions.toString();
    }
}
