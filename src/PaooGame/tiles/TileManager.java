package PaooGame.tiles;

import PaooGame.Game;
import PaooGame.main.Tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    // Base Settings
    Game gp;

    // Tile Array - all tiles in one array
    public Tile[] tile;
    // Tile SpriteSheet
    public BufferedImage spriteSheet;
    // Map - a matrix with all tiles ID placed all over the width X height map
    public int[][] mapTile;

    public TileManager(Game gp) {
        this.gp = gp;
        tile = new Tile[20];
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/tiles/level01/map01Tiles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mapTile = new int[gp.maxWorldColumn][gp.maxWorldRow];

        getImage();
        loadMap("/maps/level01.txt");
    }

    public void getImage() {
        setup(13, 0, 0, "flower_bush", false);
        setup(0, 1, 0, "grass", false);
        setup(11, 2, 0, "grass_fence", true);
        setup(1, 3, 0, "gravel", false);
        setup(12, 4, 0, "hidden_chest", false);
        setup(3, 5, 0, "house_door_reversed", true);
        setup(4, 6, 0, "house_wall", true);
        setup(10, 7, 0, "plank_fence", true);
        setup(5, 8, 0, "planks", false);
        setup(6, 9, 0, "roof_left", true);
        setup(7, 10, 0, "roof_middle", true);
        setup(8, 11, 0, "roof_right", true);
        setup(9, 12, 0, "stairs", false);
        setup(2, 13, 0, "tree", true);
    }

    public void setup(int index, int indexX, int indexY, String imagePath, boolean collision) {
        Tools tool = new Tools();
        try {
            tile[index] = new Tile();
            tile[index].image = tool.cropImage(spriteSheet, gp.originalTileSize * indexX, gp.originalTileSize * indexY, gp.originalTileSize, gp.originalTileSize);
            tile[index].image = tool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String path) {
        try{
            InputStream in = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            int column = 0;
            int row = 0;

            while(column < gp.maxWorldColumn && row < gp.maxWorldRow) {
                String line = br.readLine();
                while(column < gp.maxWorldColumn) {
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[column]);
                    mapTile[column][row] = number;
                    column += 1;
                }
                if(column == gp.maxWorldColumn) {
                    column = 0;
                    row += 1;
                }
            }
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graph2) {
        int worldColumn = 0;
        int worldRow = 0;

        while(worldColumn < gp.maxWorldColumn && worldRow < gp.maxWorldRow) {
            int tileNumber = mapTile[worldColumn][worldRow];

            int worldX = worldColumn * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX -gp.tileSize < gp.player.worldX + gp.player.screenX && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                graph2.drawImage(tile[tileNumber].image, screenX, screenY, null);
            }
            worldColumn += 1;
            if(worldColumn == gp.maxWorldColumn) {
                worldColumn = 0;
                worldRow += 1;
            }
        }
    }
}
