package WarShips.Behavior;

import WarShips.Message;
import WarShips.Point2;

import static WarShips.Common.*;

/**
 *  Vanga Decision Maker uses random and probabilities to decide
 *  it wants to destroy N ships in sequence or to make random
 *  fires in other case
 */
public class AIVangaDecisionMaker implements DecisionMaker {

    public AIVangaDecisionMaker() {
        wantToDestroy = false;
        destroyCount = 0;
        countToDestroyInSequence = 3;
        probabilitiesOfRandom = 0.59;
    }

    public void pullEvent(Message message) {

        switch (message.getActionType()) {

            case ACTION_SET_SHIP:
                ShipPositionGenerator.setShipsRandom(message.getReceiverField());
                break;

            case ACTION_FIRE:

                if (wantToDestroy) {
                    int[][] field = message.getOpponentField().getField();

                    boolean found = false;
                    for(int i = 0; i < FIELD_SIZE; i++) {
                        for(int j = 0; j < FIELD_SIZE; j++) {
                            if (field[i][j] == SLOT_SHIPPED) {
                                message.getOpponentField().fire(new Point2(i, j));
                                found = true;
                            }

                            if (found) {
                                break;
                            }
                        }

                        if (found) {
                            break;
                        }
                    }

                    destroyCount += 1;

                    if (destroyCount >= countToDestroyInSequence) {
                        wantToDestroy = false;
                        destroyCount = 0;
                    }
                }
                else {

                    double flag = Math.random();
                    if (flag >= probabilitiesOfRandom) {
                        wantToDestroy = true;
                    }

                    int x;
                    int y;

                    do {
                        x = (int)Math.round(Math.random() * (FIELD_SIZE - 1));
                        y = (int)Math.round(Math.random() * (FIELD_SIZE - 1));
                    } while (!message.getOpponentField().fire(new Point2(x,y)));

                }
        }
    }

    public boolean getStatus() { return false; }

    public boolean isArtificial() { return true; }

    public String getIdentification() {
        return  "Vanga Decision Maker";
    }

    private boolean wantToDestroy;
    private int destroyCount;
    private final int countToDestroyInSequence;
    private final double probabilitiesOfRandom;

}
