package Rewards;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Entity.Player;

import java.util.Arrays;
import java.util.Random;
import tile.TileManager;



public class bonus extends rewards {
    private BufferedImage[] sprites;
    private boolean pickedUp;
    private int x,y;
    private int currentFrame;
    private TileManager tileManager;

    private int respawnTime;
    private int lifespan;
    private int timeLeftToRespawn;
    private boolean isRespawning;
    private int[] currentTile;
    private long creationTime;

    public bonus(int x, int y, int rewardWidth, int rewardHeight, int value, int lifespan, int respawnTime, TileManager tileManager) {
        super(x, y, rewardWidth, rewardHeight, value);
        this.x=x;
        this.y=y;
        this.lifespan = lifespan;
        this.respawnTime = respawnTime;
        this.isRespawning = false;
        this.tileManager= tileManager;
        this.creationTime = System.currentTimeMillis();
        this.currentTile = new int[]{x, y};
        currentFrame = 0;
        pickedUp = false;

        sprites = new BufferedImage[1];
        try {
            sprites[0] = ImageIO.read(new File("assets/rewards/coin.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove() {
        this.setHeight(0);
        this.setWidth(0);
    }

    public void update() {
        if (isRespawning) {
            timeLeftToRespawn--;
            if (timeLeftToRespawn == 0) {
                respawn();
            }
        } else {
            if (isExpired()) {
                startRespawn();
            }
        }
    }
    private void startRespawn() {
        isRespawning = true;
        timeLeftToRespawn = respawnTime;
        currentTile = new int[]{getX(), getY()};
    }
    public boolean isExpired() {
        return System.currentTimeMillis() - creationTime >= lifespan;

    }


    private void respawn() {
        int[][] map = tileManager.getMapTileNum();
        int mapWidth = map[0].length;
        int mapHeight = map.length;
        isRespawning = false;
        timeLeftToRespawn = respawnTime;
        int[] newPosition = findRandomValidTile();
        setX(newPosition[0] * mapWidth);
        setY(newPosition[1] * mapHeight);
        currentTile = new int[]{getX(), getY()};
        setHitbox(new Rectangle(x,y,getWidth(),getHeight()));
    }

    private int[] findRandomValidTile() {
        int[][] map = tileManager.getMapTileNum();
        int mapWidth = map[0].length;
        int mapHeight = map.length;
        int[] position = new int[2];
        do {
            position[0] = (int) (Math.random() * mapWidth);
            position[1] = (int) (Math.random() * mapHeight);
        } while (map[position[1]][position[0]] != 0);
        System.out.println(Arrays.toString(position));
        return position;
    }
    public boolean isRespawning() {
        return isRespawning;
    }

    public int[] getCurrentTile() {
        return currentTile;
    }

    @Override
    public void handleCollision(Player player){
        if(super.getHitbox().intersects(player.getHitbox())){
            pickedUp = true;
            System.out.println("player picked up item");
            // TODO: remove reward from screen
            this.remove();
        }
    }



    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x, y, null);
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void pickUp() {
        pickedUp = true;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }


}
