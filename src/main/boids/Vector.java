package main.boids;

/**
 * Classe implémentant des vecteurs en 2 dimensions
 */
public class Vector {
    private double x;
    private double y;

    /**
     * Instancie un vecteur en deux dimensions
     *
     * @param x Abcisse
     * @param y Ordonnée
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Instancie un vecteur en deux dimensions à partir d'un autre vecteur
     *
     * @param vector Vecteur à copier
     */
    public Vector(Vector vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    /**
     * Ajoute un vecteur v à ce vecteur
     *
     * @param v Vecteur à ajouter
     */
    public void add(Vector v) {
        this.x += v.x;
        this.y += v.y;
    }

    /**
     * Soustrait un vecteur v à ce vecteur
     *
     * @param v Vecteur à soustraire
     */
    public void sub(Vector v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    /**
     * Multiplie le vecteur par une constante c
     *
     * @param c Constante multiplicative
     */
    public void mult(double c) {
        this.x = this.x * c;
        this.y = this.y * c;
    }

    /**
     * Divise le vecteur par une constante c
     *
     * @param c Constante
     */
    public void div(double c) {
        this.x = (this.x) / c;
        this.y = (this.y) / c;
    }

    /**
     * Calcule la norme d'un vecteur
     *
     * @return La norme du vecteur
     */
    public double magnitude() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    /**
     * Ajuste la norme d'un vecteur
     * @param newMagnitude Nouvelle norme
     */
    public void setMagnitude(double newMagnitude) {
        //normalize (set length to 1)
        double x = this.x;
        double y = this.y;
        double length = this.magnitude();
        if (length > 0) {
            x = x / length;
            y = y / length;
            //scale
            x *= newMagnitude;
            y *= newMagnitude;
            this.x = x;
            this.y = y;
        }

    }

    /**
     * Limite la norme d'un vecteur
     * @param max Norme max
     */
    public void limitMagnitude(double max) {
        if (this.magnitude() > max) {
            this.setMagnitude(max);
        }
    }

    /**
     * Calcule l'angle entre le vecteur et l'origine
     * @return Cet angle
     */
    public double getAngle() {
        return Math.atan2(this.x, this.y);
    }

    /**
     * @return Abcisse du vecteur
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return Ordonnée du vecteur
     */
    public double getY() {
        return this.y;
    }

    /**
     * @return Une représentation textuelle du vecteur, sous forme de ses coordonnées
     */
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
