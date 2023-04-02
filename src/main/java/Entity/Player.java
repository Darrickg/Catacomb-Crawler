package Entity;

import tile.TileManager;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * The player class extends from entity represent a generic player object
 */
public class Player extends Entity{
    // Attributes
    private int score = 100;
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

    public long lastDamageTime = 0;
    private long durationBeforeDamage = 1000;
    private BufferedImage[] sprites;
    private int currentFrame;
    public TileManager tileManager;
    private int[][] gameBoard;

    /**
     * Player constructor with all fields as parameters
     * @param x dimension x
     * @param y dimension y
     * @param playerWidth width of player model
     * @param PlayerHeight height of player model
     * @param lives lives of player model
     */
    public Player(int x, int y , int playerWidth, int PlayerHeight, int lives , int[][] board) {
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
        tileManager = new TileManager(null, 25, 19, 32);
        gameBoard =  tileManager.getMapTileNum();


        //Load Sprite Images
        sprites = new BufferedImage[4];
        try {
            sprites[0] = ImageIO.read(new File("assets/entity/playerright.png"));
            sprites[1] = ImageIO.read(new File("assets/entity/playerleft.png"));
            sprites[2] = ImageIO.read(new File("assets/entity/playerdown.png"));
            sprites[3] = ImageIO.read(new File("assets/entity/playerup.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Player score getter
     * @return player score
     */
    public int  getScore(){ return this.score;}

    /**
     * Player lives getter
     * @return player lives
     */
    public int getLives(){ return this.lives;}

    /**
     * player score setter
     * @param value set value to score
     */
    public void setScore(int value){ this.score = value;}

    /**
     * player lives setter
     * @param value set value to lives
     */
    public void setLives(int value){ this.lives = value;}

    /**
     * player score adder
     * @param dif add dif to score
     */
    public void addScore(int dif){ this.score += dif;}

    /**
     * player score decreaser
     * @param diff decrease score to player
     */
    public void decreaseScore(int diff){ this.score-=diff;}

    /**
     * player lives adder
     * @param dif add dif to player lives
     */
    public void addLives(int dif){ this.lives += dif;}

    /**
     * store and update player positiom
     */
    public void update() {
        // Store previous position
        prevX = x;
        prevY = y;

        hitbox.setLocation(getX(),getY());
        // Update position based on velocity
        x += vx;
        y += vy;
    }

    /**
     * player position setter
     * @param x set x to x
     * @param y set y to y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * player revert position
     */
    public void revertPosition() {
        // Revert the player's position to the previous position
        x = prevX;
        y = prevY;
    }

    /**
     * player hitBox getter
     * @return player model hitBox
     */
    public Rectangle getHitbox(){
        return hitbox;
    }

    /**
     * move player up
     */
    public void moveUp() {
        vy = -2;
    }

    /**
     * move player down
     */
    public void moveDown() {
        vy = 2;
    }

    /**
     * move player left
     */
    public void moveLeft() {
        vx = -2;
        currentFrame = 1;
    }

    /**
     * move player right
     */
    public void moveRight() {
        vx = 2;
        currentFrame = 0;
    }

    /**
     * stop player at x
     */
    public void stopX() {
        vx = 0;
    }

    /**
     * stop player at y
     */
    public void stopY() {
        vy = 0;
    }

    /**
     * player x direction getter
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * player x direction setter
     * @param x set x to x
     */
    public void setX(int x) {

        this.x = x;
    }

    /**
     * player y direction getter
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * player y direction setter
     * @param y set y to y
     */
    public void setY(int y) {

        this.y = y;
    }

    /**
     * abandon method
     * @return vx
     */
    public int getVx() {
        return vx;
    }

    /**
     * abandon method
     * @param vx to vx
     */
    public void setVx(int vx) {
        this.vx = vx;
    }

    /**
     * abandon method
     * @return vy
     */
    public int getVy() {
        return vy;
    }

    /**
     * player previous vx getter
     * @return prev vx
     */
    public int getPrevX() {
        return prevX;
    }

    /**
     * player previous vx setters
     * @param prevX set vx = vx
     */
    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    /**
     * player previous vy getter
     * @return prev vy
     */
    public int getPrevY() {
        return prevY;
    }

    /**
     * player prev vy setter
     * @param prevY vy = vy
     */
    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    /**
     * player current frame setter
     * @param currentFrame frane = currentFrame
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Draw player
     * @param g2d draw
     */
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }

    /**
     * whether play can damage
     * @return can or can't damage
     */
    public boolean canDamage(){
        return System.currentTimeMillis() - this.lastDamageTime > this.durationBeforeDamage;
    }

    /**
     * Player take damage
     * @param damage take damage
     */
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

    /**
     * player width getter
     * @return player width
     */
    public int getWidth() {
        return width;
    }

    /**
     * player width setter
     * @param width width to width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * player height getter
     * @return player height
     */
    public int getHeight() {
        return height;
    }

    /**
     * player height setter
     * @param height height to height
     */
    public void setHeight(int height) {
        this.height = height;
    }

}


