package tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.cellCol][gp.cellRow];
        getTileImage();
        loadMap("assets/maps/map01.txt");

    }

    //load tile from disk and save it into tile[]
    public void getTileImage(){
        try{
            tile[0]= new Tile();
            //put grass tile into array[0]
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("assets/tiles/Grass.png"));

            tile[1]= new Tile();
            //put wall tile into array[1]
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("assets/tiles/Wall.png"));


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //load map from map txt
    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while(col < gp.cellCol && row < gp.cellRow){
                String line = br.readLine();
                while(col < gp.cellCol){
                    String numbers[] = line.split("");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.cellCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch (Exception e){

        }
    }

    //Draw tile
    public void draw(Graphics2D g2){
        //g2.drawImage(tile[0].image,0,0,gp.tileSize,gp.tileSize, null);
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while(col < gp.cellCol && row < gp.cellRow){

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image,x,y,gp.tileSize,gp.tileSize,null);
            col++;
            x += gp.tileSize;
            if(col == gp.cellCol){
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
