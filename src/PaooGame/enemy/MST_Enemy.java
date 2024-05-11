package PaooGame.enemy;

import PaooGame.objects.OBJ_Bullet;
import PaooGame.entity.Entity;
import PaooGame.Game;
import PaooGame.objects.OBJ_Electron;
import PaooGame.objects.OBJ_HealPotion;

import java.util.Random;

public class MST_Enemy extends Entity {

    // Base Settings
    Game gp;

    public MST_Enemy(Game gp) {
        super(gp);

        this.gp = gp;

        spriteNumber = 1;
        name = "Enemy";
        speed = 2;
        maxLife = 4;
        life = maxLife;
        type = type_monster;
        projectile = new OBJ_Bullet(gp);
        projectile.attack = 2;
        attack = 1;
        difficulty = 1;

        solidArea.x = 3;
        solidArea.y = 10;
        solidArea.width = 42;
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
        projectile.attack = 2;
        projectile.useCost = 1;
        projectile.alive = false;
    }
    public void getImage() {
        up1 = setup(0, 0, "/enemy/enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        up2 = setup(1, 0, "/enemy/enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down1 = setup(2, 0, "/enemy/enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down2 = setup(3, 0, "/enemy/enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left1 = setup(4, 0, "/enemy/enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left2 = setup(5, 0, "/enemy/enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right1 = setup(6,0, "/enemy/enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right2 = setup(7, 0, "/enemy/enemy_spritesheet", gp.originalTileSize, gp.originalTileSize);
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
        direction = gp.player.direction;
    }
    public void checkDrop() {
        int n = new Random().nextInt(100) + 1;

        if(n < 30) {
            dropItem(new OBJ_HealPotion(gp));
        }
        if(n >= 30 && n < 40) {
            dropItem(new OBJ_Electron(gp));
        }
    }
}
