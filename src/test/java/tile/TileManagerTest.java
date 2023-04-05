package tile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;

class TileManagerTest{

    @Test
    void testLoadMap() {
        TileManager tileManager = new TileManager(null, 5, 5, 16);
        tileManager.loadMap("src/test/assets/test_map.txt");
        int[][] mapTileNum = tileManager.getMapTileNum();
        assertArrayEquals(new int[][]{{1,1,1,1,1},{1,3,0,0,1},{1,0,1,0,1},{1,0,0,2,1},{1,1,1,1,1}}, mapTileNum);
    }

    @Test
    void testIsSolid() {
        TileManager tileManager = new TileManager(null, 5, 5, 16);
        tileManager.loadMap("src/test/assets/test_map.txt");
        boolean result1 = tileManager.isSolid(0, 0, 16, 16);
        boolean result2 = tileManager.isSolid(32, 32, 16, 16);
        boolean result3 = tileManager.isSolid(32, 16, 16, 16);
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
    }

    @Test
    void testIsDoor() {
        TileManager tileManager = new TileManager(null, 5, 5, 16);
        tileManager.loadMap("src/test/assets/test_map.txt");
        boolean result1 = tileManager.isDoor(32, 0, 16, 16);
        boolean result2 = tileManager.isDoor(16, 16, 16, 16);
        boolean result3 = tileManager.isDoor(32, 32, 16, 16);
        assertFalse(result1);
        assertTrue(result2);
        assertFalse(result3);
    }

    @Test
    void testGetTileImage() {
        TileManager tileManager = new TileManager(null, 5, 5, 16);
        tileManager.getTileImage();
        BufferedImage[] tileImages = tileManager.tileImages;
        assertNotNull(tileImages);
        assertEquals(20, tileImages.length);
    }

    @Test
    void testDraw() {
        TileManager tileManager = new TileManager(null, 5, 5, 16);
        tileManager.loadMap("src/test/assets/test_map.txt");
        BufferedImage image = new BufferedImage(160, 160, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        tileManager.draw(g2);

        // Check if the top-left tile was drawn correctly
        int expectedColor = -16777216; // the color of that tile
        int actualColor = image.getRGB(0, 0);
        assertEquals(expectedColor, actualColor, "Top-left tile was not drawn correctly");
    }
    @Test
    void doorTest(){
        TileManager tileManager = new TileManager(null, 5, 5, 16);
        tileManager.loadMap("src/test/assets/test_map.txt");
        assertEquals(3,tileManager.getDoorY(tileManager.getDoorTileNum()));
        assertEquals(3,tileManager.getDoorX(tileManager.getDoorTileNum()));
    }

    @Test
    void mapTile(){
        TileManager tileManager = new TileManager(null, 5, 5, 16);
        tileManager.loadMap("src/test/assets/test_map.txt");
        int[][] tileNum = {{1,1},{1,1}};
        tileManager.setMapTileNum(tileNum);

        assertEquals(tileNum, tileManager.getMapTileNum());
        assertEquals(16, tileManager.getTileSize());

    }

}
