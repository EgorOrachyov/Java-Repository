package WarShips.Behavior;

import WarShips.Message;
import WarShips.Point2;

import static WarShips.Common.*;

/**
 *  Implements Random decision maker
 *  Uses random to make fire
 */
public class AIRandomDecisionMaker implements DecisionMaker {

    public void pullEvent(Message message) {

        switch (message.getActionType()) {

            case ACTION_SET_SHIP:
                ShipPositionGenerator.setShipsRandom(message.getReceiverField());
                break;

            case ACTION_FIRE:

                int x;
                int y;

                do {
                    x = (int)Math.round(Math.random() * (FIELD_SIZE - 1));
                    y = (int)Math.round(Math.random() * (FIELD_SIZE - 1));
                } while (!message.getOpponentField().fire(new Point2(x,y)));

                break;
        }
    }

    public boolean getStatus() { return false; }

    public boolean isArtificial() { return true; }

    public String getIdentification() {
        return  "Random Decision Maker";
    }

}
