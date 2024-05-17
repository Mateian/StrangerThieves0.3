package PaooGame.main;

import PaooGame.Game;
import PaooGame.entity.Entity;
import PaooGame.entity.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {

    // Base Settings
    Game gp;

    // keyPressed - is the key pressed?
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean ePressed, enterPressed;
    public boolean shotPressed;

    // Debug
    public boolean toggleDebugInfo = false;

    public KeyHandler(Game gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Apasare tasta - control
        int code = e.getKeyCode();

        // Menu State
        if(gp.sc.stateEqualTo(gp.menuState)) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                if(gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 2;
                }
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                if(gp.ui.commandNumber > 2) {
                    gp.ui.commandNumber = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNumber == 0) {
                    gp.currentMap = 0;
                    gp.sc.changeState(gp.playState);
                    gp.playMusic(0);
                }
                if(gp.ui.commandNumber == 1) {
                    gp.loadData();
                }
                if(gp.ui.commandNumber == 2) {
                    System.exit(0);
                }
            }
        }
        // Options state
        else if(gp.sc.stateEqualTo(gp.optionsState)) {
            optionsState(code);
        }
        // Play State
        else if(gp.sc.stateEqualTo(gp.playState)) {
            if(code == KeyEvent.VK_UP) {
                gp.restart();
            }
            if(code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if(code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if(code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if(code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if(code == KeyEvent.VK_ESCAPE) {
                gp.sc.changeState(gp.optionsState);
            }
            if(code == KeyEvent.VK_E) {
                ePressed = true;
            }
            if(code == KeyEvent.VK_G) {
                gp.player.checkCollision();
                if(gp.player.hasWeapon) {
                    gp.player.hasWeapon = false;
                    gp.player.canPickup = false;
                    gp.player.dropItem(gp.player.currentWeapon);
                    gp.player.inventory.remove(gp.player.currentWeapon);
                }
            }
            if(code == KeyEvent.VK_SPACE) {
                shotPressed = true;
            }
            if(code == KeyEvent.VK_P) {
                gp.sc.changeState(gp.pauseState);
            }
            if(code == KeyEvent.VK_I) {
                gp.sc.changeState(gp.inventoryState);
            }

            // Debug
            if(code == KeyEvent.VK_T) {
                if(!toggleDebugInfo) {
                    toggleDebugInfo = true;
                    gp.tileMng.drawPath = true;
                } else {
                    toggleDebugInfo = false;
                    gp.tileMng.drawPath = false;
                }
            }
            if(code == KeyEvent.VK_R) {
                gp.tileMng.loadMap("/maps/level0" + (gp.currentMap + 1) + ".txt", gp.currentMap);
            }
        }
        else if(gp.sc.stateEqualTo(gp.paperState)) {
            if(code == KeyEvent.VK_E) {
                gp.sc.changeState(gp.playState);
            }
        }
        // Chest State
        else if(gp.sc.stateEqualTo(gp.chestState)) {
            if(code == KeyEvent.VK_ESCAPE) {
                gp.sc.changeState(gp.playState);
            }
        }
        // Inventory State
        else if(gp.sc.stateEqualTo(gp.inventoryState)) {
            if(code == KeyEvent.VK_I) {
                gp.sc.changeState(gp.playState);
            }
            if(code == KeyEvent.VK_ENTER) {
                gp.player.selectItem();
            }
            if(code == KeyEvent.VK_G) {
                for(int i = 0; i < gp.player.inventory.size(); ++i) {
                    if (gp.ui.getItemIndexOnSlot() < gp.player.inventory.size() && gp.player.currentWeapon != null && gp.player.inventory.get(gp.ui.getItemIndexOnSlot()) == gp.player.currentWeapon) {
                        gp.player.hasWeapon = false;
                        gp.player.currentWeapon = null;
                        break;
                    }
                }
                if(gp.ui.getItemIndexOnSlot() < gp.player.inventory.size() && !gp.player.inventory.isEmpty()) {
                    gp.player.canPickup = false;
                    gp.player.dropItem(gp.player.inventory.get(gp.ui.getItemIndexOnSlot()));
                    gp.player.inventory.remove(gp.ui.getItemIndexOnSlot());
                }
            }
            if(code == KeyEvent.VK_W) {
                if(gp.ui.slotRow != 0) {
                    gp.ui.slotRow--;
                }
            }
            if(code == KeyEvent.VK_A) {
                if(gp.ui.slotCol != 0) {
                    gp.ui.slotCol--;
                }
            }
            if(code == KeyEvent.VK_S) {
                if(gp.ui.slotRow != 0) {
                    gp.ui.slotRow++;
                }
            }
            if(code == KeyEvent.VK_D) {
                if(gp.ui.getItemIndexOnSlot() < gp.player.inventory.size()) {
                    if(gp.ui.slotCol != gp.player.inventory.size() - 1) {
                        gp.ui.slotCol++;
                    }
                }

            }
        }

        // Pause State
        else if(gp.sc.stateEqualTo(gp.pauseState)) {
            if(code == KeyEvent.VK_P) {
                gp.sc.changeState(gp.playState);
            }
        }

        // Dialog State
        else if(gp.sc.stateEqualTo(gp.dialogState)) {
            if(code == KeyEvent.VK_E) {
                gp.sc.changeState(gp.playState);
                int index = gp.colChecker.checkEntity(gp.player, gp.NPC);
                if(index != Entity.invalidIndex) {
                    if(gp.NPC[gp.currentMap][index] != null) {
                        if(gp.NPC[gp.currentMap][index].name.equals("HEAD NPC")) {
                            gp.level2HeadInteraction = true;
                        }
                    }
                }
            }
        }
        else if(gp.sc.stateEqualTo(gp.gameFinished)) {
            if(code == KeyEvent.VK_ENTER) {
                gp.sc.changeState(gp.menuState);
            }
        }
        // Dead State
        else if(gp.sc.stateEqualTo(gp.deadState)) {
            if(code == KeyEvent.VK_ENTER) {
                gp.sc.changeState(gp.playState);
                gp.restart();
            }
        }

        // Level Complete State
        else if(gp.sc.stateEqualTo(gp.levelCompleteState)) {
            if(code == KeyEvent.VK_ENTER) {
                gp.sc.changeState(gp.playState);
                gp.levelCounter = 0;
                gp.currentMap++;
                switch(gp.currentMap) {
                    case 0:
                        gp.levelScore = gp.level1Score;
                        break;
                    case 1:
                        gp.levelScore = gp.level2Score;
                        break;
                    case 2:
                        gp.levelScore = gp.level3Score;
                        break;
                }
                switch(gp.currentMap) {
                    case 1:
                        gp.player.teleport(gp.currentMap, 40, 42);
                        break;
                    case 2:
                        gp.player.teleport(2, 15, 42);
                        break;
                }
                gp.player.keyNumber = 0;
            }
        }
    }
    public void optionsState(int code) {
        if(code == KeyEvent.VK_ESCAPE) {
            gp.sc.changeState(gp.playState);
            gp.ui.subState = 0;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        int maxCommandNum = 0;
        switch(gp.ui.subState) {
            case 0:
                maxCommandNum = 4;
            break;
        }
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNumber--;
            if(gp.ui.commandNumber < 0) {
                gp.ui.commandNumber = 0;
            }
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNumber++;
            if(gp.ui.commandNumber > 4) {
                gp.ui.commandNumber = 4;
            }
        }
        if(code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNumber == 0 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }
                if(gp.ui.commandNumber == 1 && gp.fx.volumeScale > 0) {
                    gp.fx.volumeScale--;
                }
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNumber == 0 && gp.music.volumeScale < 4) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }
                if(gp.ui.commandNumber == 1 && gp.fx.volumeScale < 4) {
                    gp.fx.volumeScale++;
                }
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SPACE) {
            shotPressed = false;
        }
    }
}
