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
    public BufferedImage spriteSheetMap1, spriteSheetMap2, spriteSheetMap3;
    // Map - a matrix with all tiles ID placed all over the width X height map
    public int[][][] mapTile;
    public boolean drawPath = false;

    public TileManager(Game gp) {
        this.gp = gp;
        tile = new Tile[50];
        try {
            spriteSheetMap1 = ImageIO.read(getClass().getResourceAsStream("/tiles/level01/map01Tiles.png"));
            spriteSheetMap2 = ImageIO.read(getClass().getResourceAsStream("/tiles/level02/map02Tiles.png"));
            spriteSheetMap3 = ImageIO.read(getClass().getResourceAsStream("/tiles/level03/map03Tiles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mapTile = new int[gp.maxMap][gp.maxWorldColumn][gp.maxWorldRow];

        getImage();
        loadMap("/maps/level01.txt", 0);
        loadMap("/maps/level02.txt", 1);
        loadMap("/maps/level03.txt", 2);
    }

    public void getImage() {
        setup(13, 0, 0, spriteSheetMap1, false);    // flower_bush - 0
        setup(0, 1, 0, spriteSheetMap1, false);     // grass - 1
        setup(11, 2, 0, spriteSheetMap1, true);     // grass_fence - 2
        setup(1, 3, 0, spriteSheetMap1, false);     // gravel - 3
        setup(12, 4, 0, spriteSheetMap1, false);    // hidden chest - 4
        setup(3, 5, 0, spriteSheetMap1, true);      // house_door_reversed - 5
        setup(4, 6, 0, spriteSheetMap1, true);      // house_wall - 6
        setup(10, 7, 0, spriteSheetMap1, true);     // plank_fence - 7
        setup(5, 8, 0, spriteSheetMap1, false);     // planks - 8
        setup(6, 9, 0, spriteSheetMap1, true);      // roof_left - 9
        setup(7, 10, 0, spriteSheetMap1, true);     // roof_middle - 10
        setup(8, 11, 0, spriteSheetMap1, true);     // roof_right - 11
        setup(9, 12, 0, spriteSheetMap1, false);    // stairs - 12
        setup(2, 13, 0, spriteSheetMap1, true);     // tree - 13
        setup(14, 0, 0, spriteSheetMap2, false);    // ship floor - 14
        setup(15, 1, 0, spriteSheetMap2, true);    // ship wall - 15
        setup(16, 2, 0, spriteSheetMap2, true);    // space - 16
        setup(17, 3, 0, spriteSheetMap2, true);    // ship-part - 17
        setup(18, 0, 0, spriteSheetMap3, true);    // cactus - 18
        setup(19, 1, 0, spriteSheetMap3, true);    // pyramid-door - 19
        setup(20, 2, 0, spriteSheetMap3, true);    // fence - 20
        setup(21, 3, 0, spriteSheetMap3, true);    // pyramid - 21
        setup(22, 4, 0, spriteSheetMap3, false);    // sand - 22
        setup(23, 5, 0, spriteSheetMap3, true);    // ship-light - 23
        setup(24, 6, 0, spriteSheetMap3, true);    // ship-part - 24
        setup(25, 7, 0, spriteSheetMap3, false);    // black-tile - 25
        setup(26, 8, 0, spriteSheetMap3, false);    // pyramid-floor - 26
    }

    public void setup(int index, int indexX, int indexY, BufferedImage image, boolean collision) {
        Tools tool = new Tools();
        try {
            tile[index] = new Tile();
            tile[index].image = tool.cropImage(image, gp.originalTileSize * indexX, gp.originalTileSize * indexY, gp.originalTileSize, gp.originalTileSize);
            tile[index].image = tool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String path, int map) {
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
                    mapTile[map][column][row] = number;
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
            int tileNumber = mapTile[gp.currentMap][worldColumn][worldRow];

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
        if(drawPath) {
            graph2.setColor(new Color(0,0,255, 50));
            for(int i = 0; i < gp.pather.pathList.size(); ++i) {
                int worldX = gp.pather.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pather.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                graph2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
    }
}
