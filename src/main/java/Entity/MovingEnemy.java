package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Entity.Enemy;
import Entity.Player;
import astar.PathFinder;
import tile.TileManager;
import astar.Node;

public class MovingEnemy extends Enemy {
    private int x; // x coordinate of enemy
    private int y; // y coordinate of enemy
    private int speed; // speed of enemy
    private int prevX, prevY;

    public boolean onPath = false;
    public TileManager tileManager;

    public PathFinder pFinder = new PathFinder();

    private BufferedImage[] sprites;

    private Rectangle hitbox;

    private int currentFrame;

    public MovingEnemy(int x, int y, int enemyWidth, int enemyHeight, int speed, int damage) {
        super(x, y, enemyWidth, enemyHeight, damage);
        this.speed = speed;
        currentFrame = 0;
        sprites = new BufferedImage[1];
        hitbox = new Rectangle(x, y, enemyWidth, enemyHeight);
        tileManager = new TileManager(null, 60, 33, 32);
        this.x=x;
        this.y=y;
        this.prevX = x;
        this.prevY =y;

        onPath = true;

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





    // getters and setters

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

    public void searchPath( int goalCol, int goalRow){
        int startCol =  getX()/32;
        int startRow =  getY()/32;

        pFinder.setNodes(startCol,startRow,goalCol,goalRow);

        if(pFinder.search()){
            //next worldx and worldy
            int nextX = pFinder.pathList.get(0).col * 32;
            int nextY = pFinder.pathList.get(0).row * 32;
            int enLeft = (int) x;
            int enTop = (int) y;
            int enRight = (int) (x + getWidth());
            int enBottom = (int) (y + getHeight());

            if(enTop > nextY && enLeft >= nextX && enRight <nextX + 32){
                // can go up todo
                y = y - speed;
            }

            else if(enTop < nextY && enLeft >= nextX && enRight <nextX + 32){
                // can go down todo
                y = y+ speed;
            }

            else if(enTop >= nextY && enBottom < nextY + 32){
                // can go right or left
                if(enLeft > nextX){
                    // left todo
                    x = x- speed;
                }
                if(enLeft < nextX){
                    //right todo
                    x = x + speed;
                }
            }
            else if (enTop >nextY && enLeft >nextX){
                //up or left
                // we set up todo
                y = y - speed;
                if (tileManager.isSolid(getX(), getY(), getWidth(), getHeight())) {
                    // left todo
                    x = x - speed;
                }
            }
            else if (enTop > nextY && enLeft < nextX){
                //up or right
                // we set up todo
                y = y - speed;
                if (tileManager.isSolid(getX(), getY(), getWidth(), getHeight())) {
                    // right todo
                    x = x + speed;
                }
            }
            else if( enTop < nextY && enLeft > nextX){
                // down or left
                // set down todo
                y = y+speed;
                if (tileManager.isSolid(getX(), getY(), getWidth(), getHeight())) {
                    // left todo
                    x = x - speed;
                }
            }
            else if( enTop < nextY && enLeft < nextX){
                // down or tight
                // set down todo
                y = y+speed;
                if (tileManager.isSolid(getX(), getY(), getWidth(), getHeight())) {
                    // right todo
                    x = x + speed;
                }
            }

        }
    }
    //
}
