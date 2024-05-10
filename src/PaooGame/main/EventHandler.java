package PaooGame.main;

import PaooGame.Game;

public class EventHandler {
    Game gp;
    EventRect[][][] eventRect;

    public int previousEventX;
    public int previousEventY;
    public boolean canTouchEvent = true;

    public EventHandler(Game gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldColumn][gp.maxWorldRow];

        int map = 0;
        int column = 0;
        int row = 0;
        while(map < gp.maxMap && column < gp.maxWorldColumn && row < gp.maxWorldRow) {
            eventRect[map][column][row] = new EventRect();
            eventRect[map][column][row].x = 23;
            eventRect[map][column][row].y = 23;
            eventRect[map][column][row].width = 2;
            eventRect[map][column][row].height = 2;
            eventRect[map][column][row].eventRectDefaultX = eventRect[map][column][row].x;
            eventRect[map][column][row].eventRectDefaultY = eventRect[map][column][row].y;

            column++;
            if(column == gp.maxWorldColumn) {
                column = 0;
                row++;

                if(row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {

        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if(canTouchEvent) {
            // Level 1 Events
//            if(hit(0, 20, 30, "any")) {
//                giveDamage(22, 30, gp.dialogState);
//            }
            if(hit(0, 21, 30, "any")) {
                heal(25, 40, gp.dialogState);
            }
            // Level 2 Events
            // Level 3 Events
        }
    }

    public void giveDamage(int column, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.dialogText = "Ai luat damage!";
        gp.player.life--;
        canTouchEvent = false;
    }
    public void heal(int column, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.dialogText = "Ai luat heal!";
        gp.player.life = gp.player.maxLife;
        canTouchEvent = false;
    }
    public boolean hit(int map, int column, int row, String direction) {
        boolean hit = false;

        if(map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][column][row].x = column * gp.tileSize + eventRect[map][column][row].x;
            eventRect[map][column][row].y = row * gp.tileSize + eventRect[map][column][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][column][row]) && !eventRect[map][column][row].eventDone) {
                if(gp.player.direction.contentEquals(direction) || direction.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][column][row].x = eventRect[map][column][row].eventRectDefaultX;
            eventRect[map][column][row].y = eventRect[map][column][row].eventRectDefaultY;
        }

        return hit;
    }
    public void teleport(int map, int col, int row) {
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
    }
}
