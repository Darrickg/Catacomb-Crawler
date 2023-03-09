package Main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //create screen panel
    final int defaultTileSize = 16; // tile:16*16
    final int scale = 3;
    final int tileSize = defaultTileSize * scale;

    //create common 4:3 screen, 16 cell col and 12 cell row
    final int cellCol = 16;
    final int cellRow = 12;

    //resolution set at 768 * 576 (pixels)
    final int screenWidth = tileSize * cellCol; //768 pixels
    final int screenHeight = tileSize * cellRow;//576 pixels

    //create game system clock for setting FPS(frame per second)
    Thread gameThread;

    //constructor for game panel
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        //TODO: call the game loop
    }


}
