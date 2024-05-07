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

    // Solid Areas and Collisions
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // Dialogs
    String[] dialogs = new String[25];
    int dialogIndex = 0;
    public BufferedImage image, image2, image3;
    public boolean collision = false;
    public int type; // 0 - player, 1 - npc, 2 - monster
//    public boolean onPath = false;

    // Character Status
    public String name;
    public int maxLife;
    public int life;
    public int speed;
    public int attack = 0;
    public int defense = 0;
    public Projectile projectile;
    public int useCost;
//    public int maxMana;
//    public int mana;

    public Entity(Game gp) {
        this.gp = gp;
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
            dialogIndex = 0;
        }
        gp.ui.dialogText = dialogs[dialogIndex];
        dialogIndex++;

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
            if(type == 2 && toggleHpBar) {
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
            if(dead == true) {
                deadAnim(graph2);
            }
            graph2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

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
        collisionOn = false;
        gp.colChecker.checkTile(this);
        gp.colChecker.checkObject(this, false);
        gp.colChecker.checkEntity(this, gp.NPC);
        gp.colChecker.checkEntity(this, gp.mst);
        boolean contactPlayer = gp.colChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer) {
            dmgPlayer(attack);
        }

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
            if (invincibleCounter > 40) {
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
            gp.player.life--;
            gp.player.invincible = true;
        }
    }
}
