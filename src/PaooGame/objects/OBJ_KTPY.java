package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_KTPY extends Entity {

    public OBJ_KTPY(Game gp) {
        super(gp);

        pickUpable = true;
        type = type_weapon;
        isWeapon = true;
        name = "KTPY";
        image = setup(14, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        projectile = new OBJ_MegaBullet(gp);
        setAmmoProps();
    }
    public void setAmmoProps() {
        projectile.attack = 1;
        projectile.speed = 30;
        projectile.maxLife = 7;
        projectile.life = maxLife;
        projectile.delay = 15;
        projectile.alive = false;
    }
}
