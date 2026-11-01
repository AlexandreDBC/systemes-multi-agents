package main.utils;

import java.awt.*;
import java.util.Random;

/**
 * Implémente quelques fonctions utiles.
 */
public class Utils {
    /**
     * Implémente le modulo mathématique
     *
     * @param a Entier a
     * @param b Entier b
     * @return a modulo b
     */
    public static int mod(int a, int b) {
        return ((a % b) + b) % b;
    }

    /**
     * Génère un tableau de k couleurs distinctes.
     * WHITE étant toujours la couleur du premier état
     *
     * @param k Le nombre de couleurs à générer
     * @return Un tableau de k couleurs où la première est le blanc
     */
    public static Color[] getKColors(int k) {
        Color[] colors = new Color[k];
        colors[0] = Color.WHITE;

        float hueOffset = new Random().nextFloat();
        float hueStep = 1.0f / k;

        for (int i = 1; i < k; i++) {
            float hue = (hueOffset + i * hueStep) % 1.0f;
            colors[i] = Color.getHSBColor(hue, 0.9f, 0.9f);
        }

        return colors;
    }
}
