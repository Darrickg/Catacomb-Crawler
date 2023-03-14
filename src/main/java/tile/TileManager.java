package tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

public class TileManager {
    private JPanel panel;
    private BufferedImage[] tileImages;
    private int[][] mapTileNum;
    int cellCol;
    int cellRow;
    int tileSize;

    public TileManager(JPanel panel, int cellCol, int cellRow, int tileSize){
        this.panel = panel;
        this.cellCol = cellCol;
        this.cellRow = cellRow;
        this.tileSize = tileSize;
        tileImages = new BufferedImage[10];
        mapTileNum = new int[cellCol][cellRow];
        getTileImage();
        loadMap("assets/maps/map03.txt");
    }

    //load tile from disk and save it into tile[]
    public void getTileImage(){
        try{
            //put grass tile into array[0]
            tileImages[0] = ImageIO.read(new File("assets/tiles/Grass.png"));

            //put wall tile into array[1]
            tileImages[1] = ImageIO.read(new File("assets/tiles/Wall.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //load map from map txt
    public void loadMap(String filePath){
        try{
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            int col = 0;
            int row = 0;
            String line;
            while((line = br.readLine()) != null){
                String[] numbers = line.split("");
                for(col = 0; col < cellCol; col++){
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
                row++;
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    //Draw tile
    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while(col < cellCol && row < cellRow){

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tileImages[tileNum],x,y,tileSize,tileSize,null);
            col++;
            x += tileSize;
            if(col == cellCol){
                col = 0;
                x = 0;
                row++;
                y += tileSize;
            }
        }
    }
}
