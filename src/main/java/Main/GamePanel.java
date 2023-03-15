/*package Main;

import tile.TileManager;
import javax.swing.JFrame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JFrame{
    //create screen panel
    final int defaultTileSize = 16; // tile:16*16
    final int scale = 3;
    public final int tileSize = defaultTileSize * scale;

    //create common 4:3 screen, 16 cell col and 12 cell row
    public final int cellCol = 16;
    public final int cellRow = 12;

    //resolution set at 768 * 576 (pixels)
    public final int screenWidth = tileSize * cellCol; //768 pixels
    public final int screenHeight = tileSize * cellRow;//576 pixels

    //create game system clock for setting FPS(frame per second)
    TileManager tileM = new TileManager(this);
    Thread gameThread;


    //constructor for game panel
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        //this.setDoubleBuffered(true);
    }

    public void startGameThread(){
        gameThread = new Thread((Runnable) this);
        gameThread.start();
    }
    @Override
    public void run() {
        //TODO: call the game loop
    }


}*/
