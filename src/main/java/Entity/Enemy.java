package Entity;

import HealthBar.HealthBar;

import java.awt.*;

/**
 * The Enemy class represent a generic enemy object
 */
public abstract class Enemy extends Entity {
    private int x;
    private int y;
    private int width, height;
    private Rectangle hitbox;
    protected int damage; // amount of damage the enemy does to the player

    /**
     *
     *
     *Enemy constructor with all fields as parameters
     * @param x dimension x
     * @param y dimension y
     * @param enemyWidth the enemy's width in game
     * @param enemyHeight the enemy's height in game
     * @param damage the enemy's damage in game
     */
    public Enemy(int x, int y,int enemyWidth, int enemyHeight,int damage) {
        this.damage = damage;
        this.x =x;
        this.y=y;
        width = enemyWidth;
        height = enemyHeight;
        hitbox = new Rectangle(x, y, width, height);

    }

    /**
     * get enemy's damage
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    // override the abstract method to handle moving enemy behavior
    public boolean handleCollision(Player player, HealthBar healthBar) {
        //abstract
        return false;
    }

    /**
     * x dimension getter
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * y dimension getter
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * x dimension setter
     * @param x set x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * y dimension setter
     * @param y set y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * HitBox getter
     * @return HitBox
     */
    public Rectangle getHitbox(){
        return hitbox;
    }

    /**
     * Enemy width getter
     * @return enemy's width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Enemy height getter
     * @return enemy's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * 2d object drawer
     * @param g2d draw the object
     */
    public void draw(Graphics2D g2d) {
    }

    /**
     * hit box setter
     * @param hitbox set hitBox to hitBoxx
     */
    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
}





