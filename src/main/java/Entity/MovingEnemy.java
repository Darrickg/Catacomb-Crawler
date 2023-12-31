package Entity;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import HealthBar.HealthBar;
import astar.PathFinder;
import tile.TileManager;

/**
 * The MovingEnemy class extends from Enemy class represent a generic moving-enemy object
 */
public class MovingEnemy extends Enemy {
    private int x; // x coordinate of enemy
    private int y; // y coordinate of enemy
    private int speed; // speed of enemy

    public boolean onPath;
    public TileManager tileManager;

    public PathFinder pFinder = new PathFinder();

    private BufferedImage[] sprites;

    private Rectangle hitbox;

    private int currentFrame;

    /**
     * MovingEnemy constructor with all fields as parameters
     * @param x dimension x
     * @param y dimension y
     * @param enemyWidth moving enemy's width
     * @param enemyHeight moving enemy's height
     * @param speed moving enemy's speed
     * @param damage moving enemy's damage
     */
    public MovingEnemy(int x, int y, int enemyWidth, int enemyHeight, int speed, int damage) {
        super(x, y, enemyWidth, enemyHeight, damage);
        this.speed = speed;
        currentFrame = 0;
        sprites = new BufferedImage[1];
        hitbox = new Rectangle(x, y, enemyWidth, enemyHeight);
        tileManager = new TileManager(null, 25, 19, 32);
        this.x=x;
        this.y=y;

        onPath = true;

        try {
            sprites[0] = ImageIO.read(new File("assets/entity/enemyLeft.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * override the abstract method to handle moving enemy behavior
     * @param player check for additional collisions or interactions with other game objects
     */
    @Override
    public boolean handleCollision(Player player, HealthBar healthBar) {
//        player.takeDamage(damage);
//        moveTowardsPlayer(player);

        if(!this.getHitbox().intersects(player.getHitbox())){
            return false;
        }

        System.out.println(" player collided with moving enemy");
        try {
            AudioInputStream damageSound = AudioSystem.getAudioInputStream(new File("assets/audio/damage.wav"));
            Clip damageSoundClip = AudioSystem.getClip();
            damageSoundClip.open(damageSound);
            damageSoundClip.start();
        } catch (Exception e2) {
            System.out.println("Error playing sound: " + e2.getMessage());
        }

        player.decreaseScore(damage);
        healthBar.decreaseHealth(3);
        return true;
    }

    /**
     * move the enemy towards the player
     * @param player input the player
     */
    public void moveTowardsPlayer(Player player) {
        if(onPath){
            int goalCol = player.getX()/tileManager.getTileSize();
            int goalRow = player.getY()/tileManager.getTileSize();
            searchPath(goalCol, goalRow);
            if(x/32 == player.getX()/tileManager.getTileSize() && y/32 == player.getY()/tileManager.getTileSize()){
                int dx = player.getX() - x;
                int dy = player.getY() - y;

                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance <= speed) {
                    x = player.getX();
                    y = player.getY();}
                //move enemy in that direction
                else {
                    x += (int) Math.round(dx * speed / distance);
                    y += (int) Math.round(dy * speed / distance);
                }
            }
        }
        hitbox.setLocation(getX(),getY());
    }

    /**
     * Moving enemy's x dimension getter
     * @return dimension x
     */
    public int getX() {
        return x;
    }

    /**
     * Moving enemy's x dimension setter
     * @param x set x to x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Moving enemy's y dimension getter
     * @return dimension y
     */
    public int getY() {
        return y;
    }

    /**
     * Moving enemy's y dimension setter
     * @param y set y to y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Moving object drawer
     * @param g2d draw the moving object
     */
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }

    /**
     * get moving hit box getter
     * @return hit box
     */
    public Rectangle getHitbox(){
        return hitbox;
    }

    public void setHitbox(Rectangle hit_box){
        this.hitbox = hit_box;
    }


    /**
     * Moving enemy auto-findPath & avoid barriers method
     * @param goalCol user col
     * @param goalRow user row
     */
    public void searchPath( int goalCol, int goalRow){
        int startCol =  getX()/32;
        int startRow =  getY()/32;

        pFinder.setNodes(startCol,startRow,goalCol,goalRow);

        if(pFinder.search()){
            //next world-x and world-y
            int nextX = pFinder.pathList.get(0).col * 32;
            int nextY = pFinder.pathList.get(0).row * 32;
            int enLeft = (int) x;
            int enTop = (int) y;
            int enRight = (int) (x + getWidth()-1);
            int enBottom = (int) (y + getHeight()-1);

            if(enTop > nextY && enLeft >= nextX && enRight <nextX + 32){
                // can go up
                y = y - speed;
            }

            else if(enTop < nextY && enLeft >= nextX && enRight <nextX + 32){
                // can go down
                y = y+ speed;
            }

            else if(enTop >= nextY && enBottom < nextY + 32){
                // can go right or left
                if(enLeft > nextX){
                    // left
                    x = x- speed;
                }
                if(enLeft < nextX){
                    //right
                    x = x + speed;
                }
            }
            else if (enTop >nextY && enLeft >nextX){
                //up or left
                // we set up
                y = y - speed;
                if (tileManager.isSolid(getX(), getY(), getWidth(), getHeight())) {
                    // left
                    x = x - speed;
                }
            }
            else if (enTop > nextY && enLeft < nextX){
                //up or right
                // we set up
                y = y - speed;
                if (tileManager.isSolid(getX(), getY(), getWidth(), getHeight())) {
                    // right
                    x = x + speed;
                }
            }
            else if( enTop < nextY && enLeft > nextX){
                // down or left
                // set down
                y = y+speed;
                if (tileManager.isSolid(getX(), getY(), getWidth(), getHeight())) {
                    // left
                    x = x - speed;
                }
            }
            else if( enTop < nextY && enLeft < nextX){
                // down or tight
                // set down
                y = y+speed;
                if (tileManager.isSolid(getX(), getY(), getWidth(), getHeight())) {
                    // right
                    x = x + speed;
                }
            }

        }
    }
    //
}
