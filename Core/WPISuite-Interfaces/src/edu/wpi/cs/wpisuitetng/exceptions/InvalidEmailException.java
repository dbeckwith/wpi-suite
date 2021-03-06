package edu.wpi.cs.wpisuitetng.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Exception thrown when invalid email address entered for a user
 * @author tcarmstrong
 *
 */
public class InvalidEmailException extends WPISuiteException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 89510899773393933L;

    public InvalidEmailException(String message) {
        super(message);
    }
    
    public InvalidEmailException() {
    }

    @Override
    public int getStatus() {
        return HttpServletResponse.SC_NOT_ACCEPTABLE; //409
    }
    
}
