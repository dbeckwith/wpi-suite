/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the AbstractUserController class
 * 
 * @author Team 9
 * @version 1.0
 */
public class AbstractUserControllerTest {
    
    /**
     * Ensures timeout setter works appropriately
     */
    @Test
    public void testSetTimeOut() {
        final EmailController ec = EmailController.getInstance();
        ec.setTimeout(1);
        Assert.assertEquals(1, ec.getTimeout());
    }
    
}
