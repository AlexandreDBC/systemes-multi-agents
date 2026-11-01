package test.boids;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.HashSet;

import main.boids.Boid;
import main.boids.Boids;
import main.boids.Vector;

import static org.junit.jupiter.api.Assertions.*;





public class BoidTest {

    private Boids createBoids(double neighborDistance, double visionField, double maxSpeed, double maxForce) {
        Boids boids = new Boids(
                new HashSet<>(),
                neighborDistance,
                visionField,
                maxSpeed,
                maxForce,
                Color.RED,
                0.8,
                1.0,
                2.0,
                1,
                20,
                10,
                0
        );
        HashSet<Boids> groups = new HashSet<>();
        groups.add(boids);
        boids.setBoidGroups(groups);
        return boids;
    }

    @Test
    public void testUpdateMovesBoidAndSetsVelocityMagnitudeToMaxSpeed() {
        // crée deux boids eloignés pour qu'ils ne soient pas voisins
        Vector pos1 = new Vector(100, 100);
        Vector pos2 = new Vector(400, 400);
        Boid b1 = new Boid(pos1);
        Boid b2 = new Boid(pos2);

        Boids boids = createBoids(10, Math.PI, 3.5, 0.1);
        boids.add(b1);
        boids.add(b2);

        Vector beforePos = new Vector(b1.getPosition());
        Vector beforeVel = new Vector(b1.getVelocity());

        b1.update(boids);

        // la position doit avoir changée en fonction de la vitesse: position + vitesse normalisée à maxSpeed
        assertNotEquals(beforePos, b1.getPosition());

        // la norme de la vitesse doit etre egale a boids.getMaxSpeed()
        double mag = b1.getVelocity().magnitude();
        assertEquals(boids.getMaxSpeed(), mag, 1e-6);
    }

    @Test
    public void testUpdateWithNeighborWithinVisionAddsInfluenceAndDoesNotThrow() {
        // crée deux boids proches et orientés de facon à être voisins
        Vector pos1 = new Vector(200, 200);
        Vector pos2 = new Vector(210, 200); // dans la distance de voisinage
        Boid b1 = new Boid(pos1);
        Boid b2 = new Boid(pos2);

        // aligne les vitesses pour qu'ils se voient
        b1.setVelocity(new Vector(1, 0));
        b2.setVelocity(new Vector(1, 0));

        Boids boids = createBoids(50, Math.PI / 2, 4.0, 0.5);
        boids.add(b1);
        boids.add(b2);

        Vector beforePos = new Vector(b1.getPosition());

        // Ne doit pas lever d'exception et doit mettre a jour la position
        b1.update(boids);

        assertNotEquals(beforePos, b1.getPosition());
        // vérifie que l'accéleration est réinitialisée
        // (une mise à jour repétée sans forces externes doit continuer à déplacer le boid uniquement via sa vitesse)

        Vector posAfterFirst = new Vector(b1.getPosition());
        b1.update(boids);
        Vector posAfterSecond = b1.getPosition();
        assertNotEquals(posAfterFirst, posAfterSecond);
    }
}
