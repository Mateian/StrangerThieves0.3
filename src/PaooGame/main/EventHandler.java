package PaooGame.main;

import PaooGame.Game;

public class EventHandler {
    Game gp;
    EventRect[][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(Game gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldColumn][gp.maxWorldRow];

        int column = 0;
        int row = 0;
        while(column < gp.maxWorldColumn && row < gp.maxWorldRow) {
            eventRect[column][row] = new EventRect();
            eventRect[column][row].x = 23;
            eventRect[column][row].y = 23;
            eventRect[column][row].width = 2;
            eventRect[column][row].height = 2;
            eventRect[column][row].eventRectDefaultX = eventRect[column][row].x;
            eventRect[column][row].eventRectDefaultY = eventRect[column][row].y;

            column++;
            if(column == gp.maxWorldColumn) {
                column = 0;
                row++;
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
            if(hit(22, 30, "any")) {
                giveDamage(22, 30, gp.dialogState);
            }
            if(hit(21, 30, "any")) {
                heal(25, 40, gp.dialogState);
            }
        }
    }

    public void giveDamage(int column, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.dialogText = "Ai luat damage!";
        gp.player.life--;
//        eventRect[column][row].eventDone = true;
        canTouchEvent = false;
    }

    public void heal(int column, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.dialogText = "Ai luat heal!";
        gp.player.life = gp.player.maxLife;
        canTouchEvent = false;
    }

    public boolean hit(int column, int row, String direction) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[column][row].x = column * gp.tileSize + eventRect[column][row].x;
        eventRect[column][row].y = row * gp.tileSize + eventRect[column][row].y;

        if(gp.player.solidArea.intersects(eventRect[column][row]) && !eventRect[column][row].eventDone) {
            if(gp.player.direction.contentEquals(direction) || direction.contentEquals("any")) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[column][row].x = eventRect[column][row].eventRectDefaultX;
        eventRect[column][row].y = eventRect[column][row].eventRectDefaultY;

        return hit;
    }
}
