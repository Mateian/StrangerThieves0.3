package PaooGame.entity;

import PaooGame.Game;

public class Projectile extends Entity {
    // Entity Owner
    Entity ent;

    public Projectile(Game gp) {
        super(gp);
    }
    public void set(int worldX, int worldY, String dir, boolean alive, Entity ent) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = dir;
        this.alive = alive;
        this.ent = ent;
        this.life = this.maxLife;
    }
    public void update() {
        if(ent == gp.player) {
            int mstIndex = gp.colChecker.checkEntity(this, gp.mst);
            if(mstIndex != invalidIndex) {
                gp.player.damageEnemy(mstIndex, attack);
                alive = false;
            }
        } else {
            boolean contactPlayer = gp.colChecker.checkPlayer(this);
            if(!gp.player.invincible && contactPlayer) {
                dmgPlayer(attack);
                alive = false;
            }
        }

        switch(direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }

        life--;
        if(life <= 0) {
            alive = false;
        }
        spriteCounter++;
        if(spriteCounter > 10) {
            if(spriteNumber == 1 || spriteNumber == 0) {
                spriteNumber = 2;
            } else if(spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }
}
