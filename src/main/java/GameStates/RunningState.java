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
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RunningState extends JPanel implements GameState, Runnable, KeyListener {

    private int numRegularRewards;
    private HealthBar healthBar;
    private GameStateManager stateManager = new GameStateManager();
    JFrame frame = new JFrame("Simple Game");
    private boolean doorOpen;
    private Player player;
    private ArrayList<Enemy> enemies;
    private Thread gameThread;
    private BufferedImage playerImage;
    private ArrayList<Items> items;
    private volatile boolean running = true;
    private TileManager tileManager;

    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private static final int TILE_SIZE = 32;

    private long startingTime = System.currentTimeMillis();
    private List<Integer> downedKeyList = new ArrayList<>();

    private Clip gameMusicClip;

    public void init() {
        // Initialize the running state.
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        // adjust the TileManager accordingly
        tileManager = new TileManager(this, 60, 33, TILE_SIZE);
        // Set up player
        player = new Player(35, 35, 25,50,3);
        // Set up enemies
        enemies = new ArrayList<>();
        enemies.add(new MovingEnemy(500, 500, 30,14, 2, 10000));
        enemies.add(new TrapEnemy(400,400,30,17,75));
        items = new ArrayList<>();
        items.add(new regular(250,200,18,15,75));
        items.add(new bonus(300,300,16,16,200,100,500,tileManager));


        numRegularRewards = 1; // IMPORTANT TODO: initialize to total number of regular rewards
        doorOpen = false;

        healthBar = new HealthBar();
        add(healthBar);

        gameThread = new Thread(this);
        gameThread.start();


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(false);
        frame.addKeyListener(this);
        frame.add(this);
        frame.setVisible(true);
        centerFrame();

        try {
            AudioInputStream gameMusic = AudioSystem.getAudioInputStream(new File("assets/audio/gamemusic.wav"));
            this.gameMusicClip = AudioSystem.getClip();
            this.gameMusicClip.open(gameMusic);
            this.gameMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    private void centerFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = screenSize.width / 2;
        int centerY = screenSize.height / 2;
        int halfWidth = getWidth() / 2;
        int halfHeight = getHeight() / 2;
        setLocation(centerX - halfWidth, centerY - halfHeight);
    }

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
                        healthBar.setHealth(healthBar.getHealth()-1);
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
                }
            }
            player.update();


            // Player position update with keyboard input
            int speedFactor = 3;
            player.setPrevX(player.getX());
            player.setX(player.getX() + getKBInputX() * speedFactor);
            player.setPrevY(player.getY());
            player.setY(player.getY() + getKBInputY() * speedFactor);

            // Left and Right Sprite change
            if(getKBInputX() == -1)
                player.setCurrentFrame(1);
            if(getKBInputX() == 1)
                player.setCurrentFrame(0);
            // Left and Right Sprite change
            if(getKBInputY() == -1)
                player.setCurrentFrame(3);
            if(getKBInputY() == 1)
                player.setCurrentFrame(2);

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
            if (i <= healthBar.getHealth()) {
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


    @Override
    public void keyPressed(KeyEvent e) {
        // Use this for pressing the left and right keys simultaneously
        int keyCode = e.getKeyCode();
        if (!downedKeyList.contains(keyCode)) {
            downedKeyList.add(Integer.valueOf(keyCode));
        }


        //DEBUG:  Edit 0314, Not using anymore
//        switch (keyCode) {
//            case KeyEvent.VK_UP:
//                player.setPrevY(player.getY());
//                player.setY(player.getY()-5);
//
//                break;
//            case KeyEvent.VK_DOWN:
//                player.setPrevY(player.getY());
//                player.setY(player.getY()+5);
//
//                break;
//            case KeyEvent.VK_LEFT:
//                player.setPrevX(player.getX());
//                player.setX(player.getX()-5);
//                player.setCurrentFrame(1);
//
//                break;
//            case KeyEvent.VK_RIGHT:
//                player.setPrevX(player.getX());
//                player.setX(player.getX()+5);
//                player.setCurrentFrame(0);
//
//                break;
//        }
        repaint();
    }

    public int getKBInputX(){
        int inputX = 0;
        inputX += (downedKeyList.contains(KeyEvent.VK_LEFT) ? -1 : 0);
        inputX += (downedKeyList.contains(KeyEvent.VK_RIGHT) ? 1 : 0);
        inputX += (downedKeyList.contains(KeyEvent.VK_A) ? -1 : 0);
        inputX += (downedKeyList.contains(KeyEvent.VK_D) ? 1 : 0);
        return inputX;
    }

    public int getKBInputY(){
        int inputY = 0;
        inputY += (downedKeyList.contains(KeyEvent.VK_UP) ? -1 : 0);
        inputY += (downedKeyList.contains(KeyEvent.VK_DOWN) ? 1 : 0);
        inputY += (downedKeyList.contains(KeyEvent.VK_W) ? -1 : 0);
        inputY += (downedKeyList.contains(KeyEvent.VK_S) ? 1 : 0);
        return inputY;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        int keyCode = e.getKeyCode();
//
//        inputX = KeyEvent.VK_LEFT == keyCode || KeyEvent.VK_RIGHT == keyCode ? 0 : inputX;
//        inputY = KeyEvent.VK_UP == keyCode || KeyEvent.VK_DOWN == keyCode? 0 : inputY;

        int keyCode = e.getKeyCode();
        if (downedKeyList.contains(keyCode)) {
            downedKeyList.remove(Integer.valueOf(keyCode));
        }
    }
}
