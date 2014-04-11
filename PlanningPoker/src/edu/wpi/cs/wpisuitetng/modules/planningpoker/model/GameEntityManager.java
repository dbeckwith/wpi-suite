/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * TODO: Contributors' names
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


public class GameEntityManager implements EntityManager<GameModel> {
    
    Data db;
    
    /**
     * @param db
     */
    public GameEntityManager(Data db) {
        this.db = db;
    }
    
    /**
     * Ensures that a user is of the specified role
     * 
     * @param session
     *            the session
     * @param role
     *            the role being verified
     * 
     * @throws WPISuiteException
     *             user isn't authorized for the given role
     */
    private void ensureRole(Session session, Role role)
            throws WPISuiteException {
        final User user = (User) db.retrieve(User.class, "username",
                session.getUsername()).get(0);
        if (!user.getRole().equals(role)) {
            throw new UnauthorizedException();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int Count() throws WPISuiteException {
        return db.retrieveAll(new GameModel()).size();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String advancedGet(Session arg0, String[] arg1)
            throws WPISuiteException {
        throw new NotImplementedException();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String advancedPost(Session arg0, String arg1, String arg2)
            throws WPISuiteException {
        throw new NotImplementedException();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String advancedPut(Session arg0, String[] arg1, String arg2)
            throws WPISuiteException {
        throw new NotImplementedException();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll(Session s) throws WPISuiteException {
        ensureRole(s, Role.ADMIN);
        db.deleteAll(new GameModel(), s.getProject());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEntity(Session s, String id) throws WPISuiteException {
        ensureRole(s, Role.ADMIN);
        return db.delete(getEntity(s, id)[0]) != null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public GameModel[] getAll(Session s) throws WPISuiteException {
        return db.retrieveAll(new GameModel(), s.getProject()).toArray(
                new GameModel[0]);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public GameModel[] getEntity(Session s, String id) throws NotFoundException {
        final int intId = Integer.parseInt(id);
        if (intId < 1) {
            throw new NotFoundException();
        }
        GameModel[] GameModels = null;
        try {
            GameModels = db.retrieve(GameModel.class, "id", intId,
                    s.getProject()).toArray(new GameModel[0]);
        }
        catch (WPISuiteException e) {
            e.printStackTrace();
        }
        if (GameModels.length < 1 || GameModels[0] == null) {
            throw new NotFoundException();
        }
        return GameModels;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public GameModel makeEntity(Session s, String content)
            throws WPISuiteException {
        final GameModel newGameModel = GameModel.fromJSON(content);
        newGameModel.setID(getNextID(s));
        if (!db.save(newGameModel, s.getProject())) {
            throw new WPISuiteException();
        }
        return newGameModel;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Session s, GameModel GameModel) throws WPISuiteException {
        db.save(GameModel, s.getProject());
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public GameModel update(Session s, String content) throws WPISuiteException {
        final GameModel updatedGameModel = GameModel.fromJSON(content);
        /*
         * Because of the disconnected objects problem in db4o, we can't just
         * save GameModels. We have to get the original GameModel from db4o,
         * copy properties from updatedGameModel, then save the original
         * GameModel again.
         */
        final List<Model> oldGameModels = db.retrieve(GameModel.class, "id",
                updatedGameModel.getID(), s.getProject());
        if (oldGameModels.size() < 1 || oldGameModels.get(0) == null) {
            throw new BadRequestException("GameModel with ID does not exist.");
        }
        
        final GameModel existingGameModel = (GameModel) oldGameModels.get(0);
        
        // copy values to old GameModel
        existingGameModel.copyFrom(updatedGameModel);
        
        if (!db.save(existingGameModel, s.getProject())) {
            throw new WPISuiteException();
        }
        
        return existingGameModel;
    }
    
    private int getNextID(Session s) throws WPISuiteException {
        int max = 0;
        for (GameModel g : getAll(s)) {
            if (g.getID() > max) {
                max = g.getID();
            }
        }
        return max + 1;
    }
    
}
