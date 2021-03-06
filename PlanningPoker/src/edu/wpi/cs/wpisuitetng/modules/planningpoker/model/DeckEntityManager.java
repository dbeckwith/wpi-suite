/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * An Entity Manager for storing DeckModels on the server.
 * 
 * @author Team 9
 * @version 1.0
 */
public class DeckEntityManager implements EntityManager<DeckModel> {
    private final Data db;
    
    /**
     * Creates a new DeckEntityManager
     * 
     * @param db
     *        The database that decks will be stored in.
     */
    public DeckEntityManager(Data db) {
        this.db = db;
    }
    
    /**
     * Ensures that a user is of the specified role
     * 
     * @param session
     *        the current session
     * @param role
     *        the role being verified
     * 
     * @throws WPISuiteException
     *         if the user isn't authorized for the given role
     */
    private void ensureRole(Session session, Role role) throws WPISuiteException {
        final User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
        if (!user.getRole().equals(role)) {
            throw new UnauthorizedException("Unauthorized");
        }
    }
    
    @Override
    public int Count() {
        return db.retrieveAll(new DeckModel()).size();
    }
    
    @Override
    public String advancedGet(Session arg0, String[] arg1) throws NotImplementedException {
        throw new NotImplementedException();
    }
    
    @Override
    public String advancedPost(Session arg0, String arg1, String arg2)
            throws NotImplementedException {
        throw new NotImplementedException();
    }
    
    @Override
    public String advancedPut(Session arg0, String[] arg1, String arg2)
            throws NotImplementedException {
        throw new NotImplementedException();
    }
    
    @Override
    public void deleteAll(Session s) throws WPISuiteException {
        ensureRole(s, Role.ADMIN);
        db.deleteAll(new DeckModel(), s.getProject());
    }
    
    @Override
    public boolean deleteEntity(Session s, String name) throws WPISuiteException {
        ensureRole(s, Role.ADMIN);
        return db.delete(getEntity(s, name)[0]) != null;
    }
    
    @Override
    public DeckModel[] getAll(Session s) {
        return db.retrieveAll(new DeckModel(), s.getProject()).toArray(new DeckModel[0]);
    }
    
    @Override
    public DeckModel[] getEntity(Session s, String name) throws NotFoundException {
        // Despite that this function requires a UNIQUE identifier, it STILL returns an array
        // However, ours is ACTUALLY unique, hence the singular name
        DeckModel[] deck = null;
        try {
            deck = db.retrieve(DeckModel.class, "name", name, s.getProject()).toArray(
                    new DeckModel[0]);
        }
        catch (WPISuiteException e) {
            e.printStackTrace();
        }
        
        if (deck.length < 1 || deck[0] == null) {
            throw new NotFoundException("");
        }
        return deck;
    }
    
    @Override
    public DeckModel makeEntity(Session s, String json) throws WPISuiteException {
        final DeckModel newDeck = DeckModel.fromJSON(json);
        if (!db.save(newDeck, s.getProject())) {
            throw new WPISuiteException("");
        }
        
        DeckListModel.getInstance().addDeck(newDeck);
        return newDeck;
    }
    
    @Override
    public void save(Session s, DeckModel deck) {
        db.save(deck, s.getProject());
    }
    
    @Override
    public DeckModel update(Session arg0, String arg1) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
