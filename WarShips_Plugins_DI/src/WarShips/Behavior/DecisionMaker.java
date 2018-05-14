package WarShips.Behavior;

import WarShips.Message;

public interface DecisionMaker {

    void pullEvent(Message message);

    boolean getStatus();

    boolean isArtificial();

    String getIdentification();

}
