package WarShips.AI;

import WarShips.Field;
import WarShips.Message;
import WarShips.Point2;

import static WarShips.Common.*;
import static WarShips.AI.ShipPositionGenerator.*;

/**
 *  Implements Heavy decision maker
 *
 *  1) Uses random when player's score less than AI score (and it differ on 2)
 *  2) If enemy's score less than player's score (and it differ on 2) finds shipped
 *     slot and destroy it. Then it stars one-way finishing of fired ship
 *     if finishing is impossible it will make random fire on that step
 */
public class AIHeavyDecisionMaker implements DecisionMaker {

    public AIHeavyDecisionMaker() {
        shouldTotallyDestroy = false;
        positionOfFiredShip = new Point2(0,0);
        scoreDifference = 2;
        nowRandom = true;
        nowDevastate = false;
    }

    public void pullEvent(Message message) {

        switch (message.getActionType()) {

            case ACTION_SET_SHIP:
                setShipsRandom(message.getReceiverField());
                break;

            case ACTION_FIRE:

                if (message.getOpponentField().getScore() + scoreDifference <= message.getReceiverField().getScore()) {
                    nowRandom = false;
                    nowDevastate = true;
                }

                if (message.getOpponentField().getScore() >= message.getReceiverField().getScore() + scoreDifference) {
                    nowRandom = true;
                    nowDevastate = false;
                }

                if (nowRandom) {

                    int x;
                    int y;

                    do {
                        x = (int)Math.round(Math.random() * (FIELD_SIZE - 1));
                        y = (int)Math.round(Math.random() * (FIELD_SIZE - 1));
                    } while (!message.getOpponentField().fire(new Point2(x,y)));
                }
                else if (nowDevastate) {

                    if (shouldTotallyDestroy) {

                        Field f = message.getOpponentField();
                        int x = whereFire.getX();
                        int y = whereFire.getY();

                        if (f.fire(new Point2(x - 1, y))) {
                            if (f.getLastFireStatus() == SLOT_DESTROYED) {
                                whereFire = new Point2(x - 1, y);
                            }
                        }
                        else if (f.fire(new Point2(x + 1, y))) {
                            if (f.getLastFireStatus() == SLOT_DESTROYED) {
                                whereFire = new Point2(x + 1, y);
                            }
                        }
                        else if (f.fire(new Point2(x, y - 1))) {
                            if (f.getLastFireStatus() == SLOT_DESTROYED) {
                                whereFire = new Point2(x, y - 1);
                            }
                        }
                        else if (f.fire(new Point2(x, y + 1))) {
                            if (f.getLastFireStatus() == SLOT_DESTROYED) {
                                whereFire = new Point2(x, y + 1);
                            }
                        }
                        else {
                            shouldTotallyDestroy = false;

                            do {
                                x = (int)Math.round(Math.random() * (FIELD_SIZE - 1));
                                y = (int)Math.round(Math.random() * (FIELD_SIZE - 1));
                            } while (!message.getOpponentField().fire(new Point2(x,y)));
                        }

                    }
                    else {
                        int[][] field = message.getOpponentField().getField();

                        boolean found = false;
                        for(int i = 0; i < FIELD_SIZE; i++) {
                            for(int j = 0; j < FIELD_SIZE; j++) {
                                if (field[i][j] == SLOT_SHIPPED) {
                                    positionOfFiredShip = new Point2(i, j);
                                    whereFire = new Point2(i, j);
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

                        message.getOpponentField().fire(positionOfFiredShip);
                        shouldTotallyDestroy = true;
                    }
                }

                break;
        }
    }

    public boolean getStatus() { return false; }

    public boolean isArtificial() { return true; }

    public String getIdentification() {
        return  "Heavy Decision Maker";
    }

    private boolean shouldTotallyDestroy;
    private Point2 positionOfFiredShip;
    private Point2 whereFire;
    private int scoreDifference;
    private boolean nowRandom;
    private boolean nowDevastate;

}
