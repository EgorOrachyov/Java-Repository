package WarShips;

import WarShips.Behavior.DecisionMaker;

/**
 *  Stores player's info, provides functions to work
 *  with that and implements mechanism to handle
 *  messages and pass it into decision maker
 *
 *  One Player equivalent to One Real Gamer
 *  Player could be driven by an AI
 */
public class Player {

    public Player(DecisionMaker decisionMaker) {
        score = 0;
        fires = 0;
        wins = 0;
        this.decisionMaker = decisionMaker;
        name = decisionMaker.getIdentification();
    }

    public Player(String name, DecisionMaker decisionMaker) {
        this(decisionMaker);
        this.name = name;
    }

    public void handleMessage(Message message) {
        decisionMaker.pullEvent(message);
        if (message.getOpponentField() != null) {
            score = message.getOpponentField().getScore();
            fires = message.getOpponentField().getFires();
        }
    }

    public void addWins() {
        wins += 1;
    }

    public boolean getStatus() {
        return decisionMaker.getStatus();
    }

    public int getScore() {
        return score;
    }

    public int getFires() {
        return fires;
    }

    public int getWinsToFalls() {
        return wins;
    }

    public String getName() {
        return name;
    }

    public boolean isArtificial() {
        return decisionMaker.isArtificial();
    }

    public String toString() {
        return "Info: " + name +
                "; " + score +
                "; " + wins +
                "; " + decisionMaker.getIdentification();
    }

    private int score;
    private int fires;
    private int wins;
    private String name;
    private DecisionMaker decisionMaker;

}
