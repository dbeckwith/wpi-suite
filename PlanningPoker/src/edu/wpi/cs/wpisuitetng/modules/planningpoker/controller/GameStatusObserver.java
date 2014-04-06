package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;

public interface GameStatusObserver {
    
    public void statusChanged(GameModel game);
    
}
