package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Electron extends Entity {
    public OBJ_Electron(Game gp) {
        super(gp);

        pickUpable = true;
        type = type_weapon;
        isWeapon = true;
        name = "Electron";
        down = image = setup(13, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        projectile = new OBJ_MegaBullet(gp);
//        attackArea.width = 36;
//        attackArea.height = 36;
        setAmmoProps();
    }
    public void setAmmoProps() {
        projectile.attack = 4;
        projectile.speed = 30;
        projectile.maxLife = 10;
        projectile.life = maxLife;
        projectile.alive = false;
    }
}
