package PaooGame.objects;

import PaooGame.entity.Projectile;
import PaooGame.Game;

public class OBJ_Bullet extends Projectile {
    Game gp;
    public OBJ_Bullet(Game gp) {
        super(gp);
        this.gp = gp;
        name = "Bullet";
        speed = 20;
        maxLife = 15;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage() {
        up = up1 = up2 = setup(4, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down = down1 = down2 = setup(1, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left = left1 = left2 = setup(2, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right = right1 = right2 = setup(3, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
    }
}
