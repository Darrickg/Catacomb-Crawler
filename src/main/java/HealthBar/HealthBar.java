package HealthBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * The Health Bar class represent the health percentage of the player
 */
public class HealthBar extends JPanel {
    private int health = 3;
    private BufferedImage[] healthIcons;

    /**
     * health bar constructor get 2 kinds of heart
     */
    public HealthBar(){
        healthIcons = new BufferedImage[2];
        getImage();
    }

    /**
     * Image getter load heart image from disk
     */
    public void getImage() {
        try {
            healthIcons[0] = ImageIO.read(new File("assets/objects/heart_full.png"));
            healthIcons[1] = ImageIO.read(new File("assets/objects/heart_blank.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * decrease health decrease player's heart if needed
     * @param k healthy update
     */
    public void decreaseHealth(int k) {
        health = health -k;
        repaint();
    }

    /**
     * check player if dead (heart empty)
     * @return dead or not
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * player current healthy getter
     * @return current health
     */
    public int getHealth() {
        repaint();
        return health;
    }

    /**
     * player current healthy setter
     * @param health health to the health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * player heart image getter
     * @return bundle of hearts
     */
    public BufferedImage[] getHealthIcons() {
        return healthIcons;
    }

    /**
     * player heart image setter
     * @param healthIcons heart = icon
     */
    public void setHealthIcons(BufferedImage[] healthIcons) {
        this.healthIcons = healthIcons;
    }
}
