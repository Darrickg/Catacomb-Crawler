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
    private List<Integer> downedKeyList = new ArrayList<>();

    private Clip gameMusicClip;

    /**
     * game running state initializer
     */
    public void init() {
        // Initialize the running state.
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        // adjust the TileManager accordingly
        tileManager = new TileManager(this, 25, 19, TILE_SIZE);
        // Set up player
        player = new Player(400, 300, 20,25,3, tileManager.getMapTileNum());
        // Set up enemies
        enemies = new ArrayList<>();
        enemies.add(new MovingEnemy(600, 500, 30,14, 1, 10000));
        enemies.add(new TrapEnemy(160,40,30,17,75));
        enemies.add(new TrapEnemy(460,60,30,17,75));
        enemies.add(new TrapEnemy(400,850,30,17,75));
        enemies.add(new TrapEnemy(150,180,30,17,75));
        enemies.add(new TrapEnemy(700,400,30,17,75));
        items = new ArrayList<>();
        items.add(new regular(740,550,18,15,75));
        items.add(new regular(40,550,18,15,75));
        items.add(new regular(80,70,18,15,75));
        items.add(new bonus(300,340,16,16,200,100,500,tileManager));
        items.add(new bonus(700,100,16,16,200,100,100,tileManager));
        items.add(new bonus(100,530,16,16,200,100,700,tileManager));


        numRegularRewards = 3; // IMPORTANT TODO: initialize to total number of regular rewards
        doorOpen = false;

        healthBar = new HealthBar();
        add(healthBar);

        gameThread = new Thread(this);
        gameThread.start();


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 650);
        frame.setResizable(true);
        frame.addKeyListener(this);
        frame.add(this);
        frame.setVisible(true);

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
     * game running state runner
     */
    public void run() {
        // Render the running state.
        while (running) {
            for(Enemy enemy : enemies){
                if(enemy instanceof MovingEnemy){
                    if(enemy.getHitbox().intersects(player.getHitbox())){
                        //TODO: Remove debug test
                        if(!player.canDamage())
                            continue;
                        player.lastDamageTime = System.currentTimeMillis();
                        System.out.println(" player collided with moving enemy");

                        try {
                            AudioInputStream damageSound = AudioSystem.getAudioInputStream(new File("assets/audio/damage.wav"));
                            Clip damageSoundClip = AudioSystem.getClip();
                            damageSoundClip.open(damageSound);
                            damageSoundClip.start();
                        } catch (Exception e2) {
                            System.out.println("Error playing sound: " + e2.getMessage());
                        }

                        player.decreaseScore(enemy.getDamage());
                        healthBar.decreaseHealth(3);
                        if (healthBar.isDead()) {
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

                }

                if(enemy instanceof TrapEnemy){
                    if(enemy.getHitbox().intersects(player.getHitbox())) {
                        System.out.println(" player collided with trap enemy");

                        try {
                            AudioInputStream damageSound = AudioSystem.getAudioInputStream(new File("assets/audio/damage.wav"));
                            Clip damageSoundClip = AudioSystem.getClip();
                            damageSoundClip.open(damageSound);
                            damageSoundClip.start();
                        } catch (Exception e2) {
                            System.out.println("Error playing sound: " + e2.getMessage());
                        }

                        player.decreaseScore(enemy.getDamage());
                        healthBar.decreaseHealth(1);

                        if (healthBar.isDead() || player.getScore() <= 0) {
                            // Player is dead, end game
                            this.gameMusicClip.stop();
                            stateManager.setState(new DeathScreenState());
                            frame.dispose();
                            running = false;
                        }
                        ((TrapEnemy) enemy).activate();
                        enemy.setHitbox(new Rectangle(enemy.getX(),enemy.getY(),0,0));
                    }
                }
            }
            for( Items item : items){
                if(item instanceof regular){
                    if(((regular) item).getHitbox().intersects(player.getHitbox())){

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
                }
                if(item instanceof bonus){
                    if(((bonus) item).getHitbox().intersects(player.getHitbox())){

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
                }
            }
            // Check for collision with solid tiles

            if (tileManager.isSolid(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
                // Player is colliding with a solid tile, so revert to previous position
                System.out.println("wall collide");
                synchronized (player) {
                    player.setPosition(player.getPrevX(), player.getPrevY());
                    player.update();
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
        //player.draw(g2d);
        // set the color of the hitbox
        g.setColor(Color.RED);

      // draw the hitbox
        g.drawRect(player.getHitbox().x, player.getHitbox().y, player.getWidth(), player.getHeight());

        // Draw enemies
        g.setColor(Color.BLUE);
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

    /**
     * key pressed detect user keyboard pressed
     * @param e detect keyEvent e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                player.setY(player.getY()-10);
                break;
            case KeyEvent.VK_DOWN:
                player.setY(player.getY()+10);
                break;
            case KeyEvent.VK_LEFT:
                player.setX(player.getX()-10);
                break;
            case KeyEvent.VK_RIGHT:
                player.setX(player.getX()+10);
                break;
        }
        repaint();
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
