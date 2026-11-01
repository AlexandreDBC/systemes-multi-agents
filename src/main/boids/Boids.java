package main.boids;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Représente un ensemble d'un boid
 */
public class Boids extends HashSet<Boid> {
    private final double neighborDistance;  //radius of the circle encapsulating all neighbors
    private final double visionField; //angle of the vision field, in radians
    private final double maxSpeed;
    private final double maxForce;
    private final Color boidColor;
    private final double cohesionMultiplier;
    private final double alignmentMultiplier;
    private final double separationMultiplier;
    private final int separationFieldRadius;
    private final int boidSize;
    private final int eventDelay; // delai pour la planification
    private HashSet<Boids> boidGroups = new HashSet<>();

    /**
     * Instancie un ensemble d'un boid à partir d'une liste
     *
     * @param boids                 Liste des boids
     * @param neighborDistance      Distance à partir de laquelle considérer ses voisins
     * @param visionField           Champ de vision des boids
     * @param maxSpeed              Vitesse max
     * @param maxForce              Max des forces de la simulation
     * @param color                 Couleur du groupe d'boids
     * @param cohesionMultiplier    Multiplicateur de la force de cohésion
     * @param alignmentMultiplier   Multiplicateur de la force d'alignement
     * @param separationMultiplier  Multiplicateur de la force de séparation
     * @param delay                 Délai entre deux événements
     * @param separationFieldRadius Rayon à partir duquel les boids se repoussent
     * @param boidSize              Taille des boids
     * @param initialBoidsCount     Nombre initial d'boids
     */
    public Boids(Set<Boid> boids, double neighborDistance, double visionField, double maxSpeed, double maxForce, Color color, double cohesionMultiplier, double alignmentMultiplier, double separationMultiplier, int delay, int separationFieldRadius, int boidSize, int initialBoidsCount) {
        this.addAll(boids);
        this.boidColor = color;
        this.cohesionMultiplier = cohesionMultiplier;
        this.alignmentMultiplier = alignmentMultiplier;
        this.separationMultiplier = separationMultiplier;
        this.neighborDistance = neighborDistance;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;
        this.visionField = visionField;
        this.eventDelay = delay;
        this.separationFieldRadius = separationFieldRadius;
        this.boidSize = boidSize;
        addNBoids(initialBoidsCount);
    }

    /**
     * Génère N boids et les ajoute dans le groupe
     *
     * @param N Nombre de boids à générer
     */
    public void addNBoids(int N) {
        for (int i = 0; i < N; i++) {
            Random rand = new Random();
            Vector position = new Vector(rand.nextInt(Config.SCREEN_WIDTH), rand.nextInt(Config.SCREEN_HEIGHT));
            add(new Boid(position));
        }
    }

    /**
     * @return La distance à partir de laquelle considérer ses voisins
     */
    public double getNeighborDistance() {
        return this.neighborDistance;
    }

    /**
     * @return Le champ de vision des boids
     */
    public double getVisionField() {
        return this.visionField;
    }

    /**
     * @return La vitesse maximale des boids
     */
    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    /**
     * @return La force maximale dans la simulation
     */
    public double getMaxForce() {
        return this.maxForce;
    }

    /**
     * @return Le délai de planification
     */
    public int getEventDelay() {
        return this.eventDelay;
    }

    /**
     * @return La couleur des boids
     */
    public Color getBoidColor() {
        return this.boidColor;
    }

    /**
     * @return Le coefficient de cohesion du groupe
     */
    public double getCohesionMultiplier() {
        return this.cohesionMultiplier;
    }

    /**
     * @return Le coefficient d'alignement du groupe
     */
    public double getAlignmentMultiplier() {
        return this.alignmentMultiplier;
    }

    /**
     * @return Le coefficient de séparation du groupe
     */
    public double getSeparationMultiplier() {
        return this.separationMultiplier;
    }

    /**
     * @return Le rayon à partir duquel deux boids veulent s'éloigner
     */
    public int getSeparationFieldRadius() {
        return this.separationFieldRadius;
    }

    /**
     * @return La taille des boids
     */
    public int getBoidSize() {
        return this.boidSize;
    }

    /**
     * @return Les groupes de boids de la simulation
     */
    public HashSet<Boids> getBoidGroups() {
        return this.boidGroups;
    }

    /**
     * Met à jour les groupes de boids de la simulation
     */
    public void setBoidGroups(HashSet<Boids> groups) {
        this.boidGroups = groups;
    }
}
