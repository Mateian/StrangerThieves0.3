package PaooGame.entity;

import PaooGame.Game;
import PaooGame.objects.OBJ_MegaBullet;

public class NPC_HEAD extends Entity {

    // Base
    Game gp;

    public NPC_HEAD(Game gp) {
        super(gp);
        this.gp = gp;

        spriteNumber = 1;
        name = "HEAD NPC";

        size = gp.tileSize * 2;
        solidArea.x = gp.tileSize;
        solidArea.y = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = 2 * gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialog();
    }
    public void getImage() {
        up1 = setup(0, 0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
        up2 = setup(1, 0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down = down1 = setup(2, 0, "/enemy/head_spritesheet", gp.originalTileSize, gp.originalTileSize);
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
        dialogs[0] = "M-ai gasit, eh?\nLasa ca vezi tu...\nSa vad cum ma vei mai gasi de\nacum!";
    }
    public void talk() {
        super.talk();
    }
}
