package PaooGame.enemy;

import PaooGame.Game;
import PaooGame.entity.Entity;
import PaooGame.objects.OBJ_MegaBullet;

public class MST_HEAD extends Entity {

    // Base
    Game gp;

    public MST_HEAD(Game gp) {
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
        difficulty = 2;
        projectile.attack = 3;

        solidArea.x = 3;
        solidArea.y = 10;
        solidArea.width = 42;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setAmmoProps();
        setDialog();
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
    public void update() {
        // Sprite changer
        spriteCounter++;
        if (spriteCounter > 30) {
            if (spriteNumber == 0) {
                spriteNumber = 1;
            }
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }
    public void setDialog() {
        dialogs[0] = "M-ai gasit, eh?";
        dialogs[1] = "Lasa ca vezi tu...";
        dialogs[2] = "Sa vad cum ma vei mai gasi de acum!";
    }
    public void talk() {
        super.talk();
    }
}
