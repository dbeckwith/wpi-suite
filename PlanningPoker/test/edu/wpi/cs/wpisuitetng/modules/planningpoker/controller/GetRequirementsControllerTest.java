package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;

public class GetRequirementsControllerTest {
    
    @Test
    public void testReceivedRequirements() {
        GetRequirementsController grc = GetRequirementsController.getInstance();
        GameRequirementModel req1 = new GameRequirementModel(1, "Requirement 1", "Requirement 1 Description", "User Story");
        GameRequirementModel req2 = new GameRequirementModel(2, "Requirement 2", "Requirement 2 Description", "User Story");
        GameRequirementModel req3 = new GameRequirementModel(3, "Requirement 3", "Requirement 3 Description", "User Story");
        GameRequirementModel req4 = new GameRequirementModel(4, "Requirement 4", "Requirement 4 Description", "User Story");
        GameRequirementModel[] list = new GameRequirementModel[] {req1, req2, req3, req4};
        grc.receivedRequirements(list);
        assertTrue(RequirementsListModel.getInstance().getAll().contains(req1));
        assertTrue(RequirementsListModel.getInstance().getAll().contains(req2));
        assertTrue(RequirementsListModel.getInstance().getAll().contains(req3));
        assertTrue(RequirementsListModel.getInstance().getAll().contains(req4));
    }
    
}
