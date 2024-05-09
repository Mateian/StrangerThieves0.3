package PaooGame.main;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class CollisionChecker {

    // Base Settings
    Game gp;
    public CollisionChecker(Game gp) {
        this.gp = gp;
    }

    // Check Collision with a Tile
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileMng.mapTile[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileMng.mapTile[gp.currentMap][entityRightCol][entityTopRow];
                if(gp.tileMng.tile[tileNum1].collision || gp.tileMng.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileMng.mapTile[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileMng.mapTile[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileMng.tile[tileNum1].collision || gp.tileMng.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileMng.mapTile[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileMng.mapTile[gp.currentMap][entityLeftCol][entityBottomRow];
                if(gp.tileMng.tile[tileNum1].collision || gp.tileMng.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileMng.mapTile[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileMng.mapTile[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileMng.tile[tileNum1].collision || gp.tileMng.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    // Check Collision with an Object
    public int checkObject(Entity entity, boolean player) {
        int index = entity.invalidIndex;

        for(int i = 0; i < gp.obj[1].length; ++i) {
            if(gp.obj[gp.currentMap][i] != null) {
                // Pozitia solida a entitatilor
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Pozitia solida a obiectelor
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                switch(entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                }
                if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                    if(gp.obj[gp.currentMap][i].collision) {
                        entity.collisionOn = true;
                    }
                    if(player) {
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }

        return index;
    }

    // Check collision with an Entity
    public int checkEntity(Entity entity, Entity[][] tg) {
        int index = entity.invalidIndex;

        for(int i = 0; i < tg[1].length; ++i) {
            if(tg[gp.currentMap][i] != null) {
                // Pozitia solida a entitatilor
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Pozitia solida a obiectelor
                tg[gp.currentMap][i].solidArea.x = tg[gp.currentMap][i].worldX + tg[gp.currentMap][i].solidArea.x;
                tg[gp.currentMap][i].solidArea.y = tg[gp.currentMap][i].worldY + tg[gp.currentMap][i].solidArea.y;

                switch(entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                }
                if(entity.solidArea.intersects(tg[gp.currentMap][i].solidArea)) {
                    if(tg[gp.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                tg[gp.currentMap][i].solidArea.x = tg[gp.currentMap][i].solidAreaDefaultX;
                tg[gp.currentMap][i].solidArea.y = tg[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // Pozitia solida a entitatilor
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Pozitia solida a obiectelor
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch(entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
        }
        if(entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
}
