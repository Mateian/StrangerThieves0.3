package PaooGame.entity;

import PaooGame.main.Tools;
import PaooGame.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    // Base Settings
    Game gp;

    // Entity Position
    public int worldX, worldY;

    // Entity Images
    public BufferedImage spriteSheet;
    public BufferedImage up, up1, up2, left, left1, left2, down, down1, down2, right, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;

    // Entity Current State as Booleans
    public String direction = "down";
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dead = false;
    boolean toggleHpBar = false;
    public boolean pickUpable = false;
//    boolean shooting = false;

    // Index
    public int spriteNumber = 0;
    public static int invalidIndex = 999;

    // Counters
    public int spriteCounter = 0;
    public int actionCounter = 0;
    public int invincibleCounter = 0;
    public int shotCounter = 0;
    int barCounter = 0;
    int deadCounter = 0;
    public int delay = 30;

    // Solid Areas and Collisions
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // Dialogs
    public String[] dialogs = new String[25];
    int dialogIndex = 0;
    public BufferedImage image, image2, image3;
    public boolean collision = false;
    public boolean dialogFinished = true;

    // Type
    public int type; // 0 - player, 1 - npc, 2 - monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_weapon = 3;
    public final int type_consumable = 4;
    public final int type_pickup = 5;
    public final int type_obstacle = 6;

    // Variables
    public int size;

    // Character Status
    public String name;
    public int maxLife;
    public int life;
    public int speed;
    public int attack = 0;
    public int defense = 0;
    public int strength = 0;
    public Projectile projectile;
    public int useCost;
    public Entity currentWeapon;
    public boolean onPath = false;
    public int difficulty = 1;
//    public int maxMana;
//    public int mana;

    // Item Status
    public int attackValue;
    public boolean isWeapon = false;
    public int value = 0;

    public Entity(Game gp) {
        this.gp = gp;
        size = gp.tileSize;
    }
    public BufferedImage setup(int indexX, int indexY, String path, int width, int height) {
        Tools tool = new Tools();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(path + ".png")).getSubimage(indexX * gp.originalTileSize, indexY * gp.originalTileSize, width, height);
            image = tool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

//    De adaugat la clasele care au toate sprite-urile
//    caracterului disponibile in toate directiile.

    public void talk() {
        if (dialogs[dialogIndex] == null) {
            dialogFinished = true;
            dialogIndex = 0;
        }
        gp.ui.dialogText = dialogs[dialogIndex];
        dialogIndex++;
        dialogFinished = false;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "left":
                direction = "right";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void checkDrop() {}
    public void dropItem(Entity item)  {
        for (int i = 0; i < gp.obj[gp.currentMap].length; ++i) {
            if(gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = item;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void draw(Graphics2D graph2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            switch (direction) {
                case "up":
                    if (spriteNumber == 0) {
                        image = up;
                    }
                    if (spriteNumber == 1) {
                        image = up1;
                    }
                    if (spriteNumber == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNumber == 0) {
                        image = down;
                    }
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                    if (spriteNumber == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNumber == 0) {
                        image = left;
                    }
                    if (spriteNumber == 1) {
                        image = left1;
                    }
                    if (spriteNumber == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNumber == 0) {
                        image = right;
                    }
                    if (spriteNumber == 1) {
                        image = right1;
                    }
                    if (spriteNumber == 2) {
                        image = right2;
                    }
                    break;
            }

            // Enemy HP
            if(type == type_monster && toggleHpBar) {
                double oneScale = (double) gp.tileSize / maxLife;
                double hpBar = oneScale * life;

                // Background bar
                graph2.setColor(new Color(0, 0, 0));
                graph2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

                // Health bar
                graph2.setColor(new Color(255, 0, 0));
                graph2.fillRect(screenX, screenY - 15, (int)hpBar, 10);

                barCounter++;
                if(barCounter > 300) {
                    barCounter = 0;
                    toggleHpBar = false;
                }
            }

            if(invincible) {
                toggleHpBar = true;
                barCounter = 0;
                changeAlpha(graph2, 0.5f);
            }
            if(dead) {
                deadAnim(graph2);
            }
            graph2.drawImage(image, screenX, screenY, size, size, null);

            graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            changeAlpha(graph2, 1f);
        }
    }
    public void deadAnim(Graphics2D graph2) {
        deadCounter++;

        int i = 5;

        if(deadCounter <= i) {
            changeAlpha(graph2, 0f);
        }
        if(deadCounter > i && deadCounter <= i * 2) {
            changeAlpha(graph2,1f);
        }
        if(deadCounter > i * 2 && deadCounter <= i * 3) {
            changeAlpha(graph2,0f);
        }
        if(deadCounter > i * 3 && deadCounter <= i * 4) {
            changeAlpha(graph2,1f);
        }
        if(deadCounter > i * 4 && deadCounter <= i * 5) {
            changeAlpha(graph2,0f);
        }
        if(deadCounter > i * 5 && deadCounter <= i * 6) {
            changeAlpha(graph2,1f);
        }
        if(deadCounter > i * 6 && deadCounter <= i * 7) {
            changeAlpha(graph2,0f);
        }
        if(deadCounter > i * 7) {
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D graph2, float alpha) {
        graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }
    public void setAction() {
    }
    public void dmgReact() {}
//    Functie de adaugat in clasele in care se doreste implementarea AI-ului

    //    public void setAction() {
//        actionCounter++;
//
//        if(actionCounter == 120) {
//            Random random = new Random();
//            int i = random.nextInt(100) + 1;
//            System.out.println(i);
//            if(i <= 25) {
//                direction = "up";
//            } else if(i > 25 && i <= 50) {
//                direction = "left";
//            } else if(i > 50 && i <= 75) {
//                direction = "right";
//            } else if(i > 75 && i <= 100) {
//                direction = "down";
//            }
//            actionCounter = 0;
//        }
//    }
    public void update() {

        // AI
        setAction();

        // Collision
        checkCollision();

        if (!collisionOn) {
            switch (direction) {
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
        }

        // Sprite changer
        spriteCounter++;
        if (spriteCounter > 30) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 10 * difficulty) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotCounter < 30) {
            shotCounter++;
        }
    }
    public void dmgPlayer(int attack) {
        if (!gp.player.invincible) {
            int damage = attack - gp.player.defense;
            if(damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void use(Entity entity) {}
    public void interact() {}
    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pather.setNodes(startCol, startRow, goalCol, goalRow);
        if(gp.pather.search()) {
            int nextX = gp.pather.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pather.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if(enLeftX > nextX) {
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }
            } else if(enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }
            } else if(enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }
            } else if(enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }
            } else if(enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }
            }

            // If the goal is reached, the entity will stop following the goal
//            int nextCol = gp.pather.pathList.get(0).col;
//            int nextRow = gp.pather.pathList.get(0).row;
//            if(nextCol == goalCol && nextRow == goalRow) {
//                onPath = false;
//            }
        }
    }
    public void checkCollision() {
        collisionOn = false;
        gp.colChecker.checkTile(this);
        gp.colChecker.checkObject(this, false);
        gp.colChecker.checkEntity(this, gp.NPC);
        gp.colChecker.checkEntity(this, gp.mst);
        boolean contactPlayer = gp.colChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer) {
            dmgPlayer(attack);
        }
    }
    public int detectEntity(Entity player, Entity[][] target, String targetName) {
        int index = Entity.invalidIndex;

        int nextWorldX = player.leftX();
        int nextWorldY = player.topY();

        switch(player.direction) {
            case "up":
                nextWorldY = player.topY() - 1;
                break;
            case "down":
                nextWorldY = player.bottomY() + 1;
                break;
            case "left":
                nextWorldX = player.leftX() - 1;
                break;
            case "right":
                nextWorldX = player.rightX() + 1;
                break;
        }
        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        for(int i = 0; i < target[1].length; ++i) {
            if(target[gp.currentMap][i] != null) {
                if(target[gp.currentMap][i].getCol() == col && target[gp.currentMap][i].getRow() == row && target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
    public int leftX() {
        return worldX + solidArea.x;
    }
    public int rightX() {
        return worldX + solidArea.x + solidArea.width;
    }
    public int topY() {
        return worldY + solidArea.y;
    }
    public int bottomY() {
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol() {
        return (worldX + solidArea.x) / gp.tileSize;
    }
    public int getRow() {
        return (worldY + solidArea.y) / gp.tileSize;
    }
}
