package Rewards;

import Entity.Player;
import Item.Items;

import java.awt.*;

/**
 * The reward class represent a generic reward object
 */
public class rewards extends Items {
    protected int value;
    private int x,y;
    private int width,height;
    private Rectangle hitbox;

    /**
     * rewards constructor generate rewards
     * @param x dimension x
     * @param y dimension y
     * @param rewardWidth reward width
     * @param rewardHeight reward height
     * @param value reward value
     */
    public rewards(int x, int y, int rewardWidth, int rewardHeight, int value){
        this.x = x;
        this.y = y;
        this.value = value;
        width = rewardWidth;
        height = rewardHeight;
        hitbox = new Rectangle(x,y,width,height);
    }

    /**
     * reward remove class
     */
    public void remove() {

    }

    /**
     * rewards value getter
     * @return value
     */
    // Getters and setters
    public int getValue() {
        return value;
    }

    /**
     * reward value setter
     * @param value value to value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * reward dimension x getter
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * reward dimension x setter
     * @param x x to x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * reward dimension y getter
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * reward dimension y setter
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * reward width getter
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * reward width setter
     * @param width width to width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * reward height getter
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * reward height setter
     * @param height height to height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * reward hit box getter
     * @return hit box
     */
    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     * reward hit box setter
     * @param hitbox hitbox to hitbox
     */
    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    /**
     * reward drawer class
     * @param g2d items
     */
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
