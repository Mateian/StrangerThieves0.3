package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Snaipa extends Entity {

    public OBJ_Snaipa(Game gp) {
        super(gp);

        pickUpable = true;
        type = type_weapon;
        isWeapon = true;
        name = "Snaipa";
        down = image = setup(15, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        projectile = new OBJ_MegaBullet(gp);
        setAmmoProps();
    }
    public void setAmmoProps() {
        projectile.attack = 8;
        projectile.speed = 40;
        projectile.maxLife = 15;
        projectile.life = maxLife;
        projectile.alive = false;
    }
}
