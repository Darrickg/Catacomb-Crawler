import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Entity.Player;

public class MovingEnemy extends Enemy {
    private int x; // x coordinate of enemy
    private int y; // y coordinate of enemy
    private int speed; // speed of enemy
    private BufferedImage[] sprites;

    private Rectangle hitbox;

    private int currentFrame;

    public MovingEnemy(int x, int y, int enemyWidth, int enemyHeight, int speed, int damage) {
        super(x, y, enemyWidth, enemyHeight, damage);
        this.speed = speed;
        currentFrame = 0;
        sprites = new BufferedImage[1];
        hitbox = new Rectangle(x, y, enemyWidth, enemyHeight);
        this.x=x;
        this.y=y;
        try {
            sprites[0] = ImageIO.read(new File("assets/entity/enemyLeft.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // override the abstract method to handle moving enemy behavior
    @Override
    public void handleCollision(Player player) {
        player.takeDamage(damage);
        moveTowardsPlayer(player);
        // check for additional collisions or interactions with other game objects
    }

    // move the enemy towards the player
    public void moveTowardsPlayer(Player player) {
        int dx = player.getX() - x;
        int dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            double directionX = dx / distance;
            double directionY = dy / distance;
            x += (int) (directionX * speed);
            y += (int) (directionY * speed);
        }
        hitbox.setLocation(getX(),getY());

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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }
    public Rectangle getHitbox(){
        return hitbox;
    }


    // TODO: other methods for moving enemies
    //
}
