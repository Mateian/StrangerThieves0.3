package PaooGame.enemy;

import PaooGame.Game;
import PaooGame.entity.Entity;
import PaooGame.objects.OBJ_Bullet;
import PaooGame.objects.OBJ_MegaBullet;

import java.util.Random;

public class MST_MegaEnemy extends Entity {
    // Base Settings
    Game gp;

    public MST_MegaEnemy(Game gp) {
        super(gp);

        this.gp = gp;

        spriteNumber = 1;
        name = "Mega_Enemy";
        speed = 3;
        maxLife = 6;
        life = maxLife;
        type = type_monster;
        projectile = new OBJ_MegaBullet(gp);
        attack = 2;
        projectile.attack = 3;

        solidArea.x = 3;
        solidArea.y = 10;
        solidArea.width = 42;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup(6, 0, "/enemy/mega_enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        up2 = setup(7, 0, "/enemy/mega_enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down1 = setup(4, 0, "/enemy/mega_enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down2 = setup(5, 0, "/enemy/mega_enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left1 = setup(2, 0, "/enemy/mega_enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left2 = setup(3, 0, "/enemy/mega_enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right1 = setup(0,0, "/enemy/mega_enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right2 = setup(1, 0, "/enemy/mega_enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
    }
    
    // De adaugat atunci cand vrem sa un inamic sa ne urmareasca doar cand ne apropiem noi

//    public void update() {
//        super.update();
//
//        int xDistance = Math.abs(worldX - gp.player.worldX);
//        int yDistance = Math.abs(worldY - gp.player.worldY);
//        int tileDistance = (xDistance + yDistance) / gp.tileSize;
//
//        if(onPath == false && tileDistance < 4) {
//            int i = new Random().nextInt(100) + 1;
//            if(i > 50) {
//                onPath = true;
//            }
//        }
//        if(onPath == true && tileDistance > 7) {
//            onPath = false;
//        }
//    }
    public void setAction() {
        if(onPath) {
            actionCounter++;
            if(actionCounter != 600) {
                int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
                int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
                searchPath(goalCol, goalRow);
            } else {
                actionCounter = 0;
                onPath = false;
            }
        } else {

            actionCounter++;

            if (actionCounter == 60) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;
                if (i <= 25) {
                    direction = "up";
                } else if (i > 25 && i <= 50) {
                    direction = "left";
                } else if (i > 50 && i <= 75) {
                    direction = "right";
                } else if (i > 75 && i <= 100) {
                    direction = "down";
                }
                actionCounter = 0;
            }
        }
        int i = new Random().nextInt(100) + 1;
        if (i > 99 && !projectile.alive && shotCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotCounter = 0;
        }
    }
    public void dmgReact() {
        actionCounter = 0;
//        switch(direction) {
//            case "down":
//                direction = "up";
//                break;
//            case "up":
//                direction = "down";
//                break;
//            case "left":
//                direction = "right";
//                break;
//            case "right":
//                direction = "left";
//                break;
//        }
        onPath = true;
    }
}
