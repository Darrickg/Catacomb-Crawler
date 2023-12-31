package GameStates;
import Entity.Enemy;
import Entity.MovingEnemy;
import Entity.Player;
import Entity.TrapEnemy;
import Item.Items;
import Rewards.bonus;
import Rewards.regular;
import tile.TileManager;
import HealthBar.HealthBar;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RunScreen with all fields as parameters describe game running panel
 */
public class RunningState extends JPanel implements GameState, Runnable, KeyListener {

    private int numRegularRewards;
    private HealthBar healthBar;
    private GameStateManager stateManager = new GameStateManager();
    JFrame frame = new JFrame("Simple Game");
    private boolean doorOpen;
    private Player player;
    private ArrayList<Enemy> enemies;
    private Thread gameThread;
    private ArrayList<Items> items;
    private volatile boolean running = true;
    private TileManager tileManager;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int TILE_SIZE = 32;

    private long startingTime = System.currentTimeMillis();

    private Clip gameMusicClip;

    private void initEnemies(){
        enemies = new ArrayList<>();
        enemies.add(new MovingEnemy(608, 512, 32,32, 1, 10000));
        enemies.add(new TrapEnemy(160,40));
        enemies.add(new TrapEnemy(460,60));
        enemies.add(new TrapEnemy(400,850));
        enemies.add(new TrapEnemy(150,180));
        enemies.add(new TrapEnemy(700,400));
    }

    private void initRewards(){
        items = new ArrayList<>();
        items.add(new regular(736,544,18,15,75));
        items.add(new regular(64,544,18,15,75));
        items.add(new regular(96,64,18,15,75));
        items.add(new bonus(320,352,32,32,200,100,500,tileManager));
        items.add(new bonus(704,128,32,32,200,100,100,tileManager));
        items.add(new bonus(128,480,32,32,200,100,200,tileManager));
    }

    private void gameFrameInit(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 650);
        frame.setResizable(true);
        frame.addKeyListener(this);
        frame.add(this);
        frame.setVisible(true);
    }

    /**
     * game running state initializer
     */
    public void init() {
        // Initialize the running state.
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        // adjust the TileManager accordingly
        tileManager = new TileManager(this, 25, 19, TILE_SIZE);
        // Set up player
        player = new Player(384, 320, 32,32,3, tileManager.getMapTileNum());
        // Set up enemies
        initEnemies();
        // set up rewards system
        initRewards();
        // set up game frame
        gameFrameInit();

        numRegularRewards = 3; // IMPORTANT TODO: initialize to total number of regular rewards
        doorOpen = false;

        healthBar = new HealthBar();
        add(healthBar);

        gameThread = new Thread(this);
        gameThread.start();


        try {
            AudioInputStream gameMusic = AudioSystem.getAudioInputStream(new File("assets/audio/gamemusic.wav"));
            this.gameMusicClip = AudioSystem.getClip();
            this.gameMusicClip.open(gameMusic);
            this.gameMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    /**
     * game running state updater
     */
    public void update() {
        // Update the running state.
        // Start game loop thread
        for( Items item : items){
            if(item instanceof bonus){
                ((bonus) item).update();
            }
        }
        // Move enemies towards player
        for (Enemy enemy : enemies) {
            if (enemy instanceof MovingEnemy ) {
                ((MovingEnemy) enemy).moveTowardsPlayer(player);
            }
            // Call other movement methods for other enemy types
        }

        if (numRegularRewards == 0 && !doorOpen) {
            try {
                AudioInputStream doorOpen = AudioSystem.getAudioInputStream(new File("assets/audio/dooropen.wav"));
                Clip doorOpenClip = AudioSystem.getClip();
                doorOpenClip.open(doorOpen);
                doorOpenClip.start();
            } catch (Exception e2) {
                System.out.println("Error playing sound: " + e2.getMessage());
            }
            int doorX = tileManager.getDoorX(tileManager.getDoorTileNum());
            int doorY = tileManager.getDoorY(tileManager.getDoorTileNum());
            int [][] map = tileManager.getMapTileNum();
            map[doorY][doorX] = 3;
            tileManager.setMapTileNum(map);
            doorOpen = true;
        }
    }

    /**
     * game running state render
     */
    @Override
    public void render() {
        if (tileManager.isDoor(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
            this.gameMusicClip.stop();
            try {
                AudioInputStream winSound = AudioSystem.getAudioInputStream(new File("assets/audio/gamewin.wav"));
                Clip winSoundClip = AudioSystem.getClip();
                winSoundClip.open(winSound);
                winSoundClip.start();
            } catch (Exception e) {
                System.out.println("Error playing sound: " + e.getMessage());
            }

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Player is colliding with a solid tile, so revert to previous position
            stateManager.setState(new WinState());

            frame.dispose();
            running = false;
        }
    }

    /**
     * check runner scenario of hitting moving enemy
     * @param enemy
     */
    private void hitMovingEnemy(Enemy enemy){
        if (enemy.handleCollision(player, healthBar) && healthBar.isDead()) {
            // Player is dead, end game
            this.gameMusicClip.stop();
            try {
                AudioInputStream deathSound = AudioSystem.getAudioInputStream(new File("assets/audio/gameover.wav"));
                Clip deathSoundClip = AudioSystem.getClip();
                deathSoundClip.open(deathSound);
                deathSoundClip.start();
            } catch (Exception e) {
                System.out.println("Error playing sound: " + e.getMessage());
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stateManager.setState(new DeathScreenState());
            frame.dispose();
            running = false;
        }
    }

    private void hitTrapEnemy(Enemy enemy){
        if (enemy.handleCollision(player, healthBar) && (healthBar.isDead() || player.getScore() <= 0)) {
            // Player is dead, end game
            this.gameMusicClip.stop();
            stateManager.setState(new DeathScreenState());
            frame.dispose();
            running = false;
        }
    }

    private void hitChestBox(Items item){
        if(!((regular) item).getHitbox().intersects(player.getHitbox())){
            return;
        }

        try {
            AudioInputStream collectSound = AudioSystem.getAudioInputStream(new File("assets/audio/openchest.wav"));
            Clip collectSoundClip = AudioSystem.getClip();
            collectSoundClip.open(collectSound);
            collectSoundClip.start();
        } catch (Exception e2) {
            System.out.println("Error playing sound: " + e2.getMessage());
        }

        System.out.println("player picked up regular reward");
        player.addScore(((regular) item).getValue());
        ((regular) item).pickUp();
        numRegularRewards--;
        ((regular) item).setHitbox(new Rectangle(((regular) item).getX(),((regular) item).getY(),0,0));
    }

    private void hitBonus(Items item){
        if(!((bonus) item).getHitbox().intersects(player.getHitbox())){
            return;
        }

        try {
            AudioInputStream collectSound = AudioSystem.getAudioInputStream(new File("assets/audio/coinsound.wav"));
            Clip collectSoundClip = AudioSystem.getClip();
            collectSoundClip.open(collectSound);
            collectSoundClip.start();
        } catch (Exception e2) {
            System.out.println("Error playing sound: " + e2.getMessage());
        }

        System.out.println("player picked up bonus reward");
        player.addScore(((bonus) item).getValue());
        ((bonus) item).pickUp();
        ((bonus) item).setHitbox(new Rectangle(((bonus) item).getX(),((bonus) item).getY(),0,0));
    }

    /**
     * game running state runner
     */
    public void run() {
        // Render the running state.
        while (running) {
            for(Enemy enemy : enemies){
                if(enemy instanceof MovingEnemy){
                    hitMovingEnemy(enemy);
                }

                if(enemy instanceof TrapEnemy){
                    hitTrapEnemy(enemy);
                }
            }
            for( Items item : items){
                if(item instanceof regular){
                    hitChestBox(item);
                }
                if(item instanceof bonus){
                    hitBonus(item);
                }
            }

            player.update();

            try {
                update();
                render();
                repaint();
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * paint component print objects on panel
     * @param g g to panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0,getWidth(),getHeight());

        tileManager.draw((Graphics2D) g);

        // Draw player
        player.draw(g2d);

        // Draw enemies
        for (Enemy enemy : enemies) {
            //g.fillRect(enemy.getX(), enemy.getY(), 20, 20);
            if(enemy instanceof TrapEnemy){
                if(!((TrapEnemy) enemy).isActivated()){
                    enemy.draw(g2d);
                }
            }else{
                enemy.draw(g2d);}
        }
        for( Items item: items){
            if(!item.isPickedUp()){
                item.draw(g2d);}
        }
        for (int i = 0; i <= healthBar.getHealthIcons().length; i++) {
            if (i < healthBar.getHealth()) {
                // Draw full heart
                g.drawImage(healthBar.getHealthIcons()[0], i * 30, 100, null);
            } else {
                // Draw empty heart
                g.drawImage(healthBar.getHealthIcons()[1], i * 30, 100, null);
            }
        }


        // Render the score on the screen
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Score: " + player.getScore(), 10, 30);

        // Render time
        long timeFromGameStart = System.currentTimeMillis() - startingTime;

        g2d.drawString("Time: " + timeFromGameStart / 1000 + "s", 10, 55);
    }

    // getters
    public boolean isDoorOpen() {
        return doorOpen;
    }

    public int[] getPlayerLocation() {
        int[] playerLoc = new int[2];
        playerLoc[0] = player.getX();
        playerLoc[1] = player.getY();
        return playerLoc;
    }

    public int getHealthbarValue()
    {
        return healthBar.getHealth();
    }

    // setter for injection purpose
    void setNumRegularRewards(int num)
    {
        numRegularRewards = num;
    }

    /**
     * key pressed detect user keyboard pressed
     * @param e detect keyEvent e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int nextX = player.getX();
        int nextY = player.getY();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                nextY -= 32;
                player.setCurrentFrame(3);
                break;
            case KeyEvent.VK_DOWN:
                nextY += 32;
                player.setCurrentFrame(2);
                break;
            case KeyEvent.VK_LEFT:
                nextX -= 32;
                player.setCurrentFrame(1);
                break;
            case KeyEvent.VK_RIGHT:
                nextX += 32;
                player.setCurrentFrame(0);
                break;
        }

        // Check if the next tile is solid before allowing the player to move
        if (!tileManager.isSolid(nextX, nextY, player.getWidth(), player.getHeight())) {
            player.setPosition(nextX, nextY);
            player.update();
            repaint();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // not used
    }

}
