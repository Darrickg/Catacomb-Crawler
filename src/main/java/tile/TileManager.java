package tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * The tile manager class represent a manager that manage all tiles
 */
public class TileManager {
    private BufferedImage[] tileImages;
    private int[][] mapTileNum;
    int cellCol;
    int cellRow;
    int tileSize;

    /**
     * tile manager constructor
     * @param panel game panel
     * @param cellCol game col
     * @param cellRow game row
     * @param tileSize game tile size
     */
    public TileManager(JPanel panel, int cellCol, int cellRow, int tileSize){
        this.cellCol = cellCol;
        this.cellRow = cellRow;
        this.tileSize = tileSize;
        tileImages = new BufferedImage[20];
        mapTileNum = new int[cellRow][cellCol];
        getTileImage();
        loadMap("assets/maps/map02.txt");
    }

    /**
     * load tile from disk and save it into tile[]
     */
    public void getTileImage(){
        try{
            //put grass tile into array[0]
            tileImages[0] = ImageIO.read(new File("assets/tiles/Grass.png"));

            //put wall tile into array[1]
            tileImages[1] = ImageIO.read(new File("assets/tiles/Wall.png"));
            // door image
            tileImages[2] = ImageIO.read(new File("assets/tiles/door.png"));
            // open door
            tileImages[3] = ImageIO.read(new File("assets/tiles/door2.png"));
            // top wall and bottom wall
            tileImages[4] = ImageIO.read(new File("assets/tiles/topwall.png"));
            // left wall
            tileImages[5] = ImageIO.read(new File("assets/tiles/leftwall.png"));
            // right wall
            tileImages[6] = ImageIO.read(new File("assets/tiles/rightwall.png"));
            //barrel
            tileImages[7] = ImageIO.read(new File("assets/tiles/barrel.png"));
            //rock
            tileImages[8] = ImageIO.read(new File("assets/tiles/rock.png"));
            //box
            tileImages[9] = ImageIO.read(new File("assets/tiles/box.png"));
            //signright
            tileImages[10] = ImageIO.read(new File("assets/tiles/signright.png"));
            // path
            tileImages[11] = ImageIO.read(new File("assets/tiles/path.png"));
            //writing
            tileImages[12] = ImageIO.read(new File("assets/tiles/writing.png"));
            // grave
            tileImages[13] = ImageIO.read(new File("assets/tiles/grave.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * load map from map txt
     * @param filePath file in
     */
    public void loadMap(String filePath){
        try{
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            int col = 0;
            int row = 0;
            String line;

            while((line = br.readLine()) != null){
                if(line.equals(""))
                    break;
                String[] numbers = line.split(",");
                for(col = 0; col < cellCol; col++){
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;
                }
                row++;
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Draw tile
     * @param g2 game
     */
    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;      //8             //6
        while(col < cellCol && row < cellRow){

            int tileNum = mapTileNum[row][col];

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
        drawLines(g2);
    }
    /**
     * Draw lines between tiles
     * @param g2 game
     */
    public void drawLines(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        for (int i = 0; i <= cellCol; i++) {
            int x = i * tileSize;
            g2.drawLine(x, 0, x, cellRow * tileSize);
        }
        for (int i = 0; i <= cellRow; i++) {
            int y = i * tileSize;
            g2.drawLine(0, y, cellCol * tileSize, y);
        }
    }


    /**
     * map tile number getter
     * @return tile number
     */
    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    /**
     * map door tile number
     * @return door
     */
    public int[] getDoorTileNum(){
        int[] door = new int[2];
        for (int i =0; i < cellRow;i++){
            for (int j =0; j < cellCol; j++){
                if(mapTileNum[i][j] == 2){
                    door[0] = i;
                    door[1] = j;
                }
            }
        }
        return door;
    }

    /**
     * door getter
     * @param map game map
     * @return door
     */
    public int getDoorX(int[] map){
        return map[1];
    }

    /**
     * door getter
     * @param map game map
     * @return door
     */
    public int getDoorY(int[] map){
        return map[0];
    }

    /**
     * map tile number setter
     * @param mapTileNum num to num
     */
    public void setMapTileNum(int[][] mapTileNum) {
        this.mapTileNum = mapTileNum;
    }

    /**
     * map tile solid checker
     * @param x dimension x
     * @param y dimension y
     * @param width tile width
     * @param height tile height
     * @return yes or no
     */
    public boolean isSolid(double x, double y, int width, int height) {
        int playerLeft = (int) x;
        int playerTop = (int) y;
        int playerRight = (int) (x + width-1);
        int playerBottom = (int) (y + height-1);

        // Check if any of the player's corners are solid
        return isTileSolid(playerLeft, playerTop)
                || isTileSolid(playerRight, playerTop)
                || isTileSolid(playerLeft, playerBottom)
                || isTileSolid(playerRight, playerBottom);
}

    /**
     * check if tile is door
     * @param x dimension x
     * @param y dimension y
     * @param width tile width
     * @param height tile height
     * @return yes or no
     */
    public boolean isDoor(double x, double y, int width, int height){
    int playerLeft = (int) x;
    int playerTop = (int) y;
    int playerRight = (int) (x + width);
    int playerBottom = (int) (y + height);
    return isTiledoor(playerLeft, playerTop)
            || isTiledoor(playerRight, playerTop)
            || isTiledoor(playerLeft, playerBottom)
            || isTiledoor(playerRight, playerBottom);
}

    /**
     * check if tile is door
     * @param x dimension x
     * @param y dimension y
     * @return yes or no
     */
    private boolean isTiledoor(int x, int y) {
        int tileX = (int) (x / tileSize);
        int tileY = (int) (y / tileSize);
        if (mapTileNum[tileY][tileX] == 3) {
            return true;
        } else { return false;}
    }

    /**
     * check if tile is solid
     * @param x dimension x
     * @param y dimension y
     * @return yes or no
     */
    private boolean isTileSolid(int x, int y) {
        int tileX = (int) (x / tileSize);
        int tileY = (int) (y / tileSize);

        if (tileX < 0 || tileX >= cellCol || tileY < 0 || tileY >= cellRow) {
            // Out of bounds
            return true;
        }

        // Check if the tile at the given position is a solid wall
        if (mapTileNum[tileY][tileX] == 1 || mapTileNum[tileY][tileX] == 4
            || mapTileNum[tileY][tileX] == 5 || mapTileNum[tileY][tileX] == 6
            || mapTileNum[tileY][tileX] == 7 || mapTileNum[tileY][tileX] == 8
            || mapTileNum[tileY][tileX] == 9 || mapTileNum[tileY][tileX] == 10
            || mapTileNum[tileY][tileX] == 13) {
            return true;
        }

        // Tile is not a solid wall
        return false;
    }

    /**
     * tile size getter
     * @return size
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * tile size setter
     * @param tileSize size to size
     */
    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    /**
     * abandon class
     * @return col
     */
    public int getCellCol() {
        return cellCol;
    }

    /**
     * abandon class
     * @return row
     */
    public int getCellRow() {
        return cellRow;
    }
}
