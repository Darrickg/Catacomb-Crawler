import java.util.ArrayList;

public class Player extends Entity {
    // Attributes
    private int score = 0;
    private int lives = 5;
    private int x; // x coordinate of player
    private int y; // y coordinate of player
    private int vx; // velocity along x axis
    private int vy; // velocity along y axis


    // constructor
    public Player(int x, int y, int lives) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.lives= lives;
    }

    // Getters
    public int  getScore(){ return this.score;}
    public int getLives(){ return this.lives;}

    // Setters
    public void setScore(int value){ this.score = value;}
    public void setLives(int value){ this.lives = value;}

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
        vy = -1;
    }

    // move player down
    public void moveDown() {
        vy = 1;
    }

    // move player left
    public void moveLeft() {
        vx = -1;
    }

    // move player right
    public void moveRight() {
        vx = 1;
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

    // TODO: Draw player

    // TODO : Object collision

    // TODO : enemy collision
    public void takeDamage(int damage) {
        lives -= damage;
        if (lives <= 0) {
            // TODO: player has died, handle game over condition
            //
        }
    }

    // TODO : see if dead





}


