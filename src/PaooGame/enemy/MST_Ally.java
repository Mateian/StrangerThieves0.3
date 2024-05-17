package PaooGame.enemy;

import PaooGame.Game;
import PaooGame.entity.Entity;
import PaooGame.objects.*;

import java.util.Random;

public class MST_Ally extends Entity {
    // Base Settings
    Game gp;

    public MST_Ally(Game gp, String enemyName, int worldX, int worldY) {
        super(gp);

        this.gp = gp;

        this.name = enemyName;
        this.worldX = worldX;
        this.worldY = worldY;
        spriteNumber = 1;
        speed = 2;
        maxLife = 21;
        life = maxLife;
        type = type_monster;
        projectile = new OBJ_AllyBullet(gp);
        attack = 2;
        difficulty = 2;
        projectile.attack = 3;
        size = gp.tileSize + gp.tileSize;
        delay = 20;

        solidArea.x = gp.tileSize / 2;
        solidArea.y = gp.tileSize;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setAmmoProps();
    }
    void setAmmoProps() {
        projectile.speed = 20;
        projectile.maxLife = 15;
        projectile.life = maxLife;
        projectile.attack = 1;
        projectile.alive = false;
    }

    public void getImage() {
        up1 = setup(3, 0, "/enemy/ally_spritesheet", gp.originalTileSize, gp.originalTileSize);
        up2 = setup(3, 0, "/enemy/ally_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down1 = setup(0, 0, "/enemy/ally_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down2 = setup(0, 0, "/enemy/ally_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left1 = setup(1, 0, "/enemy/ally_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left2 = setup(1, 0, "/enemy/ally_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right1 = setup(2,0, "/enemy/ally_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right2 = setup(2, 0, "/enemy/ally_spritesheet", gp.originalTileSize, gp.originalTileSize);
    }

    // De adaugat atunci cand vrem sa un inamic sa ne urmareasca doar cand ne apropiem noi

        public void update() {
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if(onPath == false && tileDistance < 4) {
            int i = new Random().nextInt(100) + 1;
            if(i > 50) {
                onPath = true;
            }
        }
        if(onPath == true && tileDistance > 7) {
            onPath = false;
        }
    }
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
            if (shotCounter == delay) {
                projectile.set(worldX + gp.tileSize / 2, worldY + gp.tileSize, direction, true, this);
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
        if (shotCounter == delay) {
            projectile.set(worldX + gp.tileSize / 2, worldY + gp.tileSize, direction, true, this);
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
    public void checkDrop() {
        int n = new Random().nextInt(100) + 1;
        if(n < 5) {
            dropItem(new OBJ_Snaipa(gp));
        }
        if(n >= 5 && n < 10) {
            dropItem(new OBJ_KTPY(gp));
        }
        if(n >= 10 && n < 70) {
            dropItem(new OBJ_HealPotion(gp));
        }
    }
}
