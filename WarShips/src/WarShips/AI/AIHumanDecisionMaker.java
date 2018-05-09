package WarShips.AI;

import WarShips.Message;
import WarShips.Point2;

import static WarShips.Common.*;

/**
 *  Implements Human decision maker
 *  Does not have its game logic, only handles messages
 *  and pass info to game field
 */
public class AIHumanDecisionMaker implements DecisionMaker {

    public void pullEvent(Message message) {

        status = STATUS_OK;

        switch (message.getActionType()) {

            case ACTION_DO_NOTHING:
                break;

            case ACTION_FIRE: {
                int x = message.getPositionX();
                int y = message.getPositionY();

                if (!message.getOpponentField().fire(new Point2(x, y))) {
                    status = STATUS_NOT_OK;
                }
            }

            break;

            case ACTION_SET_SHIP: {
                int x = message.getPositionX();
                int y = message.getPositionY();
                int type = message.getType();
                int orientation = message.getOrientation();

                if (!message.getReceiverField().setShip(new Point2(x,y), type, orientation)) {
                    status = STATUS_NOT_OK;
                }
            }

            break;

            case ACTION_SURRENDER:
                message.getReceiverField().surrender();
                break;

            default:
        }
    }

    public boolean getStatus() {
        return status;
    }

    public boolean isArtificial() {
        return false;
    }

    public String getIdentification() {
        return  "Human Decision Maker";
    }

    private boolean status;

}