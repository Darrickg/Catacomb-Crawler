package Rewards;

import Entity.Player;
import Item.Items;

import java.awt.*;

public class rewards extends Items {
    protected int value;
    private int x,y;
    private int width,height;
    private Rectangle hitbox;

    public rewards(int x, int y, int rewardWidth, int rewardHeight, int value){
        this.x = x;
        this.y = y;
        this.value = value;
        width = rewardWidth;
        height = rewardHeight;
        hitbox = new Rectangle(x,y,width,height);
    }

    // Getters and setters
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

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

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    // sprite
    public void draw(Graphics2D g2d) { //abstract
    }
    public void handleCollision(Player player) {
        //abstract
    }
    public void add_score() {
        // still need to do
        System.out.println("updated score");
    }
}
