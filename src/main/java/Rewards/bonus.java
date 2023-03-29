package Rewards;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Entity.Player;

import java.util.Arrays;
import tile.TileManager;

/**
 * The bonus class represent a generic bonus reward object
 */
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

    /**
     * bonus reward class constructor build bonus rewards
     * @param x dimension x
     * @param y dimension y
     * @param rewardWidth bonus reward width
     * @param rewardHeight bonus reward height
     * @param value bonus reward value
     * @param lifespan bonus reward span
     * @param respawnTime bonus reward spawn time
     * @param tileManager bonus reward build tile manager
     */
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

    /**
     * bonus reward remove class
     */
    @Override
    public void remove() {
        this.setHeight(0);
        this.setWidth(0);
    }

    /**
     * bonus reward update class
     */
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

    /**
     * bonus reward start spawn setter
     */
    private void startRespawn() {

        try {
            AudioInputStream spawnSound = AudioSystem.getAudioInputStream(new File("assets/audio/coinspawn.wav"));
            Clip spawnSoundClip = AudioSystem.getClip();
            spawnSoundClip.open(spawnSound);
            spawnSoundClip.start();
        } catch (Exception e2) {
            System.out.println("Error playing sound: " + e2.getMessage());
        }
        isRespawning = true;
        timeLeftToRespawn = respawnTime;
        currentTile = new int[]{getX(), getY()};
    }
    public boolean isExpired() {
        return System.currentTimeMillis() - creationTime >= lifespan;

    }

    /**
     * bonus reward respawn setter
     */
    private void respawn() {
        int[][] map = tileManager.getMapTileNum();
        int tileSize = tileManager.getTileSize();
        isRespawning = false;
        timeLeftToRespawn = respawnTime;
        int[] newPosition = findRandomValidTile();
        int xOffset = (tileSize - getWidth()) / 2;
        int yOffset = (tileSize - getHeight()) / 2;
        setX(newPosition[0] * tileSize + xOffset);
        setY(newPosition[1] * tileSize + yOffset);
        currentTile = new int[]{newPosition[0], newPosition[1]};
        setHitbox(new Rectangle(x, y, getWidth(), getHeight()));
    }




    /**
     * bonus reward random spawn finder
     * @return
     */
    private int[] findRandomValidTile() {
        int[][] map = tileManager.getMapTileNum();
        int mapWidth = map[0].length;
        int mapHeight = map.length;
        int[] position = new int[2];
        while (map[position[1]][position[0]] != 0 && position[1] < 31 && position[0] < 58){
            position[0] = (int) (Math.random() * mapWidth);
            position[1] = (int) (Math.random() * mapHeight);
        }
        System.out.println(Arrays.toString(position));
        return position;
    }

    /**
     * abandon method
     * @return yes or no
     */
    public boolean isRespawning() {
        return isRespawning;
    }

    /**
     * abandon method
     * @return yes or no
     */
    public int[] getCurrentTile() {
        return currentTile;
    }

    /**
     * abandon method
     * @param player
     */
    @Override
    public void handleCollision(Player player){
        if(super.getHitbox().intersects(player.getHitbox())){
            pickedUp = true;
            System.out.println("player picked up item");
            // TODO: remove reward from screen
            this.remove();
        }
    }


    /**
     * abandon method
     * @param g2d items
     */
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[currentFrame], x+8, y+8, null);
    }

    /**
     * abandon method
     * @return
     */
    public boolean isPickedUp() {
        return pickedUp;
    }

    /**
     * abandon class
     */
    public void pickUp() {
        pickedUp = true;
    }

    /**
     * abandon class
     * @param x dimension x
     * @param y dimension y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * abandon class
     * @return x
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * abandon class
     * @param x x to x
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /**
     * abandon class
     * @return y
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * abandon setter
     * @param y y = y
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }

    /**
     * abandon getter
     * @return frame
     */
    public int getCurrentFrame() {
        return currentFrame;
    }

    /**
     * abandon class
     * @param currentFrame frame to frame
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * abandon class
     * @param pickedUp frame to frame
     */
    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }


}
