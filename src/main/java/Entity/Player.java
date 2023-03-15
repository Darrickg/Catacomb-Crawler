package Entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;


public class Player extends Entity{
    // Attributes
    private int score = 0;
    private int lives = 5;
    //private ArrayList<Weapon> inventory;
    private int x; // x coordinate of player
    private int y; // y coordinate of player
    private int vx; // velocity along x axis
    private int vy; // velocity along y axis
    private int width;
    private int height;
    private Rectangle hitbox;

    private int prevX, prevY;

    public long lastDamageTime = 0;     //TODO: change back to private
    private long durationBeforeDamage = 1000;
    private BufferedImage[] sprites;
    private int currentFrame;


    // constructor
    public Player(int x, int y , int playerWidth, int PlayerHeight, int lives ) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.prevX = x;
        this.prevY =y;
        this.lives = lives;
        currentFrame =0;
        width = playerWidth;
        height = PlayerHeight;
        hitbox = new Rectangle(x,y,width,height);

        //Load Sprite Images
        sprites = new BufferedImage[2];
        try {
            sprites[0] = ImageIO.read(new File("assets/entity/runRight.gif"));
            sprites[1] = ImageIO.read(new File("assets/entity/runLeft.gif"));

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
    public void decreaseScore(int diff){ this.score-=diff;}
    public void addLives(int dif){ this.lives += dif;}

    // TODO: Hitbox config

    // TODO: Movement
    // move the player
    public void move() {
        x += vx;
        y += vy;
        hitbox.setLocation(getX(),getY());
    }

    public void update() {
        // Store previous position
        prevX = x;
        prevY = y;

        hitbox.setLocation(getX(),getY());
        // Update position based on velocity
        x += vx;
        y += vy;
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void revertPosition() {
        // Revert the player's position to the previous position
        x = prevX;
        y = prevY;
    }

    public Rectangle getHitbox(){
        return hitbox;
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

    public int getPrevX() {
        return prevX;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    // Draw player
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }

    public boolean canDamage(){
        return System.currentTimeMillis() - this.lastDamageTime > this.durationBeforeDamage;
    }

    public void takeDamage(int damage) {
        if(!canDamage())
            return;
        lives -= damage;
        lastDamageTime = System.currentTimeMillis();
        if (lives <= 0) {
            // TODO: player has died, handle game over condition
            //  change to deaath frame
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // TODO : see if dead




}


