package Entity;

import java.awt.*;

/**
 * The Enemy class represent a generic enemy object
 */
public abstract class Enemy extends Entity {
    private int x;
    private int y;
    private int width, height;
    private Rectangle hitbox;

    private int prevX, prevY;
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
    public void handleCollision(Player player) {
        //abstract
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
     * Enemy width setter
     * @param width set width to width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Enemy height getter
     * @return enemy's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Enemy height setter
     * @param height set height to height
     */
    public void setHeight(int height) {
        this.height = height;
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

    /**
     * object position setter
     * @param x set dimension x to x
     * @param y set dimension y to y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Previous x getter
     * @return previous x
     */
    public int getPrevX() {
        return prevX;
    }

    /**
     * Previous x setter
     * @param prevX set x to x
     */
    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    /**
     * Previous y getter
     * @return previous y
     */
    public int getPrevY() {
        return prevY;
    }

    /**
     * Previous y setter
     * @param prevY set y to y
     */
    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }
}





