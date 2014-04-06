package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UpdateUserRequestObserver implements RequestObserver {
    UserPrefsController controller;
    
    public UpdateUserRequestObserver(UserPrefsController controller) {
        this.controller = controller;
    }
    
    @Override
    public void responseSuccess(IRequest iReq) {
        System.out.println("Success: " + iReq.getBody());
        
    }
    
    @Override
    public void responseError(IRequest iReq) {
        System.out.println("Error: " + iReq.getBody());        
    }
    
    @Override
    public void fail(IRequest iReq, Exception exception) {
        System.out.println("Fail: " + iReq.getBody());        
    }
    
}
