package Entity;

import Entity.Entity;
import Entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Enemy extends Entity {
    private int x;
    private int y;
    private int width, height;
    private Rectangle hitbox;

    private int prevX, prevY;
    protected int damage; // amount of damage the enemy does to the player

    public Enemy(int x, int y,int enemyWidth, int enemyHeight,int damage) {
        this.damage = damage;
        this.x =x;
        this.y=y;
        width = enemyWidth;
        height = enemyHeight;
        hitbox = new Rectangle(x, y, width, height);

    }



    public int getDamage() {
        return damage;
    }

    // override the abstract method to handle moving enemy behavior
    public void handleCollision(Player player) {
        //abstract
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getHitbox(){
        return hitbox;

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

    public void draw(Graphics2D g2d) {
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
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
}





