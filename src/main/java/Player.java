import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player {
    // Attributes
    private int score = 0;
    private int lives = 5;
    //private ArrayList<Weapon> inventory;
    private int x; // x coordinate of player
    private int y; // y coordinate of player
    private int vx; // velocity along x axis
    private int vy; // velocity along y axis

    private BufferedImage[] sprites;
    private int currentFrame;


    // constructor
    public Player(int x, int y, int lives) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.lives = lives;
        currentFrame =0;

        //Load Sprite Images
        sprites = new BufferedImage[2];
        try {
            sprites[0] = ImageIO.read(new File("assets/runRight.gif"));
            sprites[1] = ImageIO.read(new File("assets/runLeft.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public int  getScore(){ return this.score;}
    public int getLives(){ return this.lives;}

    //public ArrayList getInventory() {
        //return inventory;
    //}

    // Setters
    public void setScore(int value){ this.score = value;}
    public void setLives(int value){ this.lives = value;}
    public void setInventory(ArrayList inventory) {
        inventory = inventory;
    }
    // Adders
    public void addScore(int dif){ this.score += dif;}
    public void addLives(int dif){ this.lives += dif;}

    // TODO: Hitbox config

    // TODO: Movement
    // move the player
    public void move() {
        x += vx;
        y += vy;
    }

    // move player up
    public void moveUp() {
        vy = -2;
    }

    // move player down
    public void moveDown() {
        vy = 2;
    }

    // move player left
    public void moveLeft() {
        vx = -2;
        currentFrame = 1;
    }

    // move player right
    public void moveRight() {
        vx = 2;
        currentFrame = 0;
    }

    // stop player movement in x axis
    public void stopX() {
        vx = 0;
    }

    // stop player movement in y axis
    public void stopY() {
        vy = 0;
    }

    // getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    // TODO: Draw player
     public void draw(Graphics2D g2d){
         g2d.drawImage(sprites[currentFrame], x, y, null);
     }
    // TODO : Object collision

    // TODO : enemy collision
    public void takeDamage(int damage) {
        lives -= damage;
        if (lives <= 0) {
            // TODO: player has died, handle game over condition
            //  change to deaath frame
        }
    }



    // TODO : see if dead




}


