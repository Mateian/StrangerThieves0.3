package PaooGame.main;

import PaooGame.Game;

public class StateChanger {

    // Game
    Game gp;

    public StateChanger(Game gp) {
        this.gp = gp;
    }
    public void changeState(int state) {
        gp.gameState = state;
    }
    public boolean stateEqualTo(int state) {
        if(gp.gameState == state) {
            return true;
        }
        return false;
    }
}
