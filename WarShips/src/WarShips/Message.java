package WarShips;

/**
 *  Class message to communicate between GameManager,
 *  Players and DecisionMakers.
 *
 *  Sender must guaranty that message in the CORRECT form !!!
 */
public class Message {

    public Message() {
        this.opponentField = null;
        this.receiverField = null;
        this.actionType = 0;
        this.params= null;
    }

    public Field getOpponentField() {
        return opponentField;
    }

    public Field getReceiverField() {
        return receiverField;
    }

    public int getActionType() {
        return actionType;
    }

    public int getPositionX() {
        return params[0];
    }

    public int getPositionY() {
        return params[1];

    }

    public int getType() {
        return params[2];

    }

    public int getOrientation() {
        return params[3];
    }

    public void push(Field opponentField, Field receiverField, int actionType, int[] params) {
        this.opponentField = opponentField;
        this.receiverField = receiverField;
        this.actionType = actionType;
        this.params= params;
    }

    private Field  opponentField;
    private Field  receiverField;
    private int    actionType;
    private int[]  params;

}

