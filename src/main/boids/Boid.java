package main.boids;

import java.util.HashSet;
import java.util.Set;

/**
 * Définit les attributs et méthodes d'un boid
 */
public class Boid {
    private final Set<Boid> neighbors = new HashSet<>();
    private final Set<Boid> friends = new HashSet<>();
    private final Vector acceleration;
    private Vector velocity;
    private Vector position; //Considered as a vector from the origin (0,0)

    /**
     * Instancie un boid
     *
     * @param position Sa position à l'écran
     */
    public Boid(Vector position) {
        this.position = position;
        this.acceleration = new Vector(0, 0);
        //this.velocity = new Vector(0,0);
        // Vitesse initiale aléatoire
        double angle = Math.random() * 2 * Math.PI;
        double speed = 4; // entre 2 et 4
        this.velocity = new Vector(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }

    /**
     * On applique une force au boid. L'ajouter à son accélération permet l'accumulation des forces
     *
     * @param force La force à appliquer
     */
    public void applyForce(Vector force) {
        this.acceleration.add(force);
    }

    /**
     * Met à jour la vitesse et la position du boid.
     * Réinitialise l'accélération à zéro.
     *
     * @param boids Boids de la simulation
     */
    public void update(Boids boids) {
        updateNeighbors(boids);
        //compute the different steering forces
        Vector cohesion = cohesionForce(boids);
        Vector alignment = alignmentForce(boids);
        Vector separation = separationForce(boids);
        Vector boundary = boundaryForce(boids);
        //apply ponderation to the different forces
        cohesion.mult(boids.getCohesionMultiplier());
        alignment.mult(boids.getAlignmentMultiplier());
        separation.mult(boids.getSeparationMultiplier());
        //apply them
        applyForce(cohesion);
        applyForce(alignment);
        applyForce(separation);
        applyForce(boundary);

        this.velocity.add(this.acceleration); //update the velocity
        this.velocity.setMagnitude(boids.getMaxSpeed()); //same velocity for each boid (maxSpeed)
        //this.velocity.limit(main.boids.getMaxSpeed());  //limit the velocity to maxSpeed
        this.position.add(this.velocity); //update the position
        this.acceleration.mult(0);  //resets the acceleration
    }

    public void updateNeighbors(Boids boids) {
        //update the neighbor list
        neighbors.clear();
        friends.clear();
        //compute the angle from the velocity
        double angle = this.getVelocity().getAngle();
        //loop through all boid to find all neighbors and update the neighbor list
        for (Boids group : boids.getBoidGroups()) {
            for (Boid boid : group) {
                if (boid == this) continue;
                //compute the distance vector between the 2 main.boids
                Vector distance = new Vector(boid.getPosition());
                distance.sub(this.position);

                if (!boid.equals(this) && distance.magnitude() <= boids.getNeighborDistance()) {
                    //if the current boid is not our boid and if the distance between the 2 main.boids is valid
                    //compute the angle of the vector from our boid to the neighbor
                    double neighborAngle = distance.getAngle();
                    double diff = Math.abs(angle - neighborAngle);
                    if (diff > Math.PI) {
                        //if the diff angle is greater than 180 degrees
                        diff = 2 * Math.PI - diff;
                    }
                    //check if it is in the vision field
                    if (diff <= boids.getVisionField()) {
                        //if in the vision field we add it to the neighbor list
                        this.neighbors.add(boid);
                        if (group == boids) {
                            this.friends.add(boid);
                        }
                    }
                }
            }
        }
    }

    /**
     * Renvoie la force de direction (steering force) pour se déplacer vers la position
     * moyenne de ses voisins
     *
     * @param boids Les boids de la simulation
     * @return La force de direction (steering force)
     */
    public Vector cohesionForce(Boids boids) {
        Vector mean = new Vector(0, 0);
        //loop through all neighbours
        for (Boid friend : friends) {
            mean.add(friend.position);
        }
        //compute the final means
        if (!this.friends.isEmpty()) {
            //if there are friends
            mean.div(this.friends.size());
        } else {
            return new Vector(0, 0);
        }
        //Compute the desired velocity vector
        mean.sub(this.position);
        //Adapt it to the max speed
        mean.setMagnitude(Math.min(boids.getMaxSpeed(), mean.magnitude()));
        //compute and return the steering force
        mean.sub(this.velocity);
        //limit the steering force
        mean.limitMagnitude(boids.getMaxForce());
        return mean;
    }

    /**
     * Renvoie le vecteur de force de direction pour se déplacer dans
     * la même direction globale que le vecteur. Pour ce faire, nous
     * calculons la moyenne de la vitesse de chaque voisin.
     *
     * @param boids Les boids
     * @return La force de direction
     */
    public Vector alignmentForce(Boids boids) {
        Vector mean = new Vector(0, 0);
        //we loop throuh all neighbors to add their speed to the mean
        for (Boid friend : friends) {
            //Add the friend speed to the mean
            Vector boidVelocity = new Vector(friend.getVelocity());
            //normalize the velocity to max speed (we want to consider only the direction not the velocity of the friends (each friend has the same influence in that case)
            boidVelocity.setMagnitude(boids.getMaxSpeed());
            //boidVelocity.setLength(10);
            mean.add(boidVelocity);
        }

        //compute the final means
        if (!this.friends.isEmpty()) {
            //if there are friends
            mean.div(this.friends.size());
        } else {
            return new Vector(0, 0);
        }
        //normalize the desired force to max speed (we redo it here to be as close to the max speed as possible, as there may have been approximations)
        //the boid wants to go in that direction at max speed
        mean.setMagnitude(boids.getMaxSpeed());
        //compute and return the steering force
        mean.sub(this.velocity);
        //limit the steering force
        mean.limitMagnitude(boids.getMaxForce());
        return mean;
    }

    /**
     * Calcule le vecteur de force de direction pour s'éloigner des voisins.
     * Plus un voisin est proche, plus le boid veut s'enfuir.
     *
     * @param boids Les boids de la simulation
     * @return Le vecteur de force de direction d'éloignement
     */
    public Vector separationForce(Boids boids) {
        Vector mean = new Vector(0, 0);
        //Loop through neighbors and add the inverse of their distance from our boid to the mean
        for (Boid neighbor : neighbors) {
            //compute the distance between the boid and it's neighbor
            Vector distance = new Vector(this.getPosition());
            distance.sub(neighbor.getPosition());
            if (distance.magnitude() <= boids.getSeparationFieldRadius()) {
                //The closer is a neighbor, the more the boid should flee. So the length is set to be inversely proportional to the distance
                double d = distance.magnitude();
                distance.setMagnitude(100000000 / (d * d));
                //we add it to the mean
                mean.add(distance);
            }
        }
        //compute the final mean
        if (!this.neighbors.isEmpty()) {
            //if there are neighbors
            mean.div(this.neighbors.size());
        } else {
            //if there is no neighbor
            return new Vector(0, 0);
        }
        //normalize the desired force to max speed, the boid wants to flee at maxspeed
        //mean.setLength(main.boids.getMaxSpeed());
        //compute and return the steering force
        mean.sub(this.velocity);
        //limit the steering force
        mean.limitMagnitude(boids.getMaxForce());
        return mean;
    }

    /**
     * Calcule la force de direction nécessaire pour maintenir le boid entre les murs
     *
     * @param boids Les boids de la simulation
     * @return La force de direction d'éloignement des murs
     */
    public Vector boundaryForce(Boids boids) {
        Vector desired = null;
        if (this.position.getX() < 20) {
            //the boid is close to the left wall
            desired = new Vector(boids.getMaxSpeed(), this.getVelocity().getY());
        } else if (position.getX() > Config.SCREEN_WIDTH - 20) {
            //the boid is close to the right wall
            desired = new Vector(-boids.getMaxSpeed(), this.getVelocity().getY());
        }
        if (position.getY() < 20) {
            //the boid is close to the upper wall
            desired = new Vector(this.getVelocity().getX(), boids.getMaxSpeed());
        } else if (position.getY() > Config.SCREEN_HEIGHT - 20) {
            //the boid is close to the bottom wall
            desired = new Vector(this.getVelocity().getX(), -boids.getMaxSpeed());
        }

        Vector steering = new Vector(0, 0);

        if (desired != null) {
            //if we are near a border
            //the boid wants to go away from the wall at max speed
            desired.setMagnitude(boids.getMaxSpeed());
            //compute the steering force
            steering = new Vector(desired);
            steering.sub(this.velocity);
            //limit the steering force
            steering.limitMagnitude(boids.getMaxForce());
        }

        return steering;
    }

    /**
     * @return La vitesse
     */
    public Vector getVelocity() {
        return velocity;
    }

    /**
     * Met à jour la vitesse
     *
     * @param velocity Nouvelle vitesse
     */
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    /**
     * @return Le vecteur de position
     */
    public Vector getPosition() {
        return position;
    }

    /**
     * Met à jour la position de le boid
     *
     * @param position Nouvelle position
     */
    public void setPosition(Vector position) {
        this.position = position;
    }

    /**
     * @return La représentation textuelle d'un boid
     */
    @Override
    public String toString() {
        return "Boid : Position: " + this.position + " Velocity: " + this.velocity + " Acceleration: " + this.acceleration;
    }
}
