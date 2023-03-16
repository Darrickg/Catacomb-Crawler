package HealthBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class HealthBar extends JPanel {
    private int health = 3;
    private BufferedImage[] healthIcons;

    public HealthBar(){
        healthIcons = new BufferedImage[2];
        getImage();
    }

    public void getImage() {
        try {
            healthIcons[0] = ImageIO.read(new File("assets/objects/heart_full.png"));
            healthIcons[1] = ImageIO.read(new File("assets/objects/heart_blank.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void decreaseHealth(int k) {
        health = health -k;
        repaint();
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public BufferedImage[] getHealthIcons() {
        return healthIcons;
    }

    public void setHealthIcons(BufferedImage[] healthIcons) {
        this.healthIcons = healthIcons;
    }
}
