package PaooGame.entity;

import PaooGame.Game;
import PaooGame.enemy.MST_Ally;
import PaooGame.enemy.MST_Enemy;
import PaooGame.enemy.MST_HEAD;
import PaooGame.enemy.MST_MegaEnemy;
import PaooGame.exceptions.InvalidEntityNameException;

public class EnemyManager {

    // Base
    Game gp;

    public EnemyManager(Game gp) {
        this.gp = gp;
    }

    public Entity createEnemy(String enemyName, int worldX, int worldY) {
        try {
            switch(enemyName) {
                case "Ally":
                    return new MST_Ally(gp, enemyName, worldX * gp.tileSize, worldY * gp.tileSize);
                case "Enemy":
                    return new MST_Enemy(gp, enemyName, worldX * gp.tileSize, worldY * gp.tileSize);
                case "HEAD":
                    return new MST_HEAD(gp, enemyName, worldX * gp.tileSize, worldY * gp.tileSize);
                case "Mega Enemy":
                    return new MST_MegaEnemy(gp, enemyName, worldX * gp.tileSize, worldY * gp.tileSize);
                default:
                    throw new InvalidEntityNameException();
            }
        } catch (InvalidEntityNameException e) {
            e.printStackTrace();
        }

        return null;
    }
}
