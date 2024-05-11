package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Skargun extends Entity {
    public OBJ_Skargun(Game gp) {
        super(gp);

        pickUpable = true;
        type = type_weapon;
        isWeapon = true;
        name = "Skargun";
        down = image = setup(8, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        projectile = new OBJ_Bullet(gp);
        setAmmoProps();
    }
    public void setAmmoProps() {
        projectile.attack = 2;
        projectile.speed = 20;
        projectile.maxLife = 15;
        projectile.life = maxLife;
        projectile.alive = false;
    }
}
