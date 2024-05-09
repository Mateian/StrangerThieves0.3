package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;
import PaooGame.entity.Projectile;

public class OBJ_MegaBullet extends Projectile {

    // Base
    Game gp;

    public OBJ_MegaBullet(Game gp) {
        super(gp);
        this.gp = gp;
        name = "Bullet";
        speed = 30;
        maxLife = 15;
        life = maxLife;
        attack = 4;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up = up1 = up2 = setup(9, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down = down1 = down2 = setup(12, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left = left1 = left2 = setup(11, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right = right1 = right2 = setup(10, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
    }
}
