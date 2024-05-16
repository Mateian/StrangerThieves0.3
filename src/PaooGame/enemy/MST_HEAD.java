package PaooGame.enemy;

import PaooGame.Game;
import PaooGame.entity.Entity;
import PaooGame.objects.OBJ_MegaBullet;

import java.util.Random;

public class MST_HEAD extends Entity {

    // Base
    Game gp;
    int scale;

    public MST_HEAD(Game gp) {
        super(gp);
        this.gp = gp;

        spriteNumber = 1;
        name = "HEAD";
        speed = 2;
        maxLife = 40;
        life = maxLife;
        type = type_monster;
        projectile = new OBJ_MegaBullet(gp);
        attack = 4;
        difficulty = 5;
        projectile.attack = 3;
        scale = 3;
        size = gp.tileSize * scale;

        solidArea.x = gp.tileSize;
        solidArea.y = gp.tileSize * 2;
        solidArea.width = 40;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setAmmoProps();
    }
    void setAmmoProps() {
        projectile.speed = 30;
        projectile.maxLife = 15;
        projectile.life = maxLife;
        projectile.attack = 4;
        projectile.useCost = 1;
        projectile.alive = false;
    }
    public void getImage() {
        up1 = setup(0, 0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
        up2 = setup(1, 0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down1 = setup(2, 0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down2 = setup(3, 0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left1 = setup(4, 0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left2 = setup(5, 0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right1 = setup(6,0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right2 = setup(7, 0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
    }
    public void setAction() {
        if (onPath) {
            actionCounter++;
            if (actionCounter != 600) {
                int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
                int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
                searchPath(goalCol, goalRow);
            } else {
                actionCounter = 0;
                onPath = false;
            }
            int i = new Random().nextInt(100) + 1;
            if (i > 99 && !projectile.alive && shotCounter == delay) {
                projectile.set(worldX + gp.tileSize, worldY + gp.tileSize * 2, direction, true, this);
                gp.projectileList.add(projectile);
                shotCounter = 0;
            }
        } else {

            actionCounter++;

            if (actionCounter == 60) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;
                if (i <= 25) {
                    direction = "up";
                } else if (i <= 50) {
                    direction = "left";
                } else if (i <= 75) {
                    direction = "right";
                } else {
                    direction = "down";
                }
                actionCounter = 0;
            }
            int i = new Random().nextInt(100) + 1;
            if (i > 99 && !projectile.alive && shotCounter == delay) {
                projectile.set(worldX + gp.tileSize, worldY + gp.tileSize * 2, direction, true, this);
                gp.projectileList.add(projectile);
                shotCounter = 0;
            }
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
