/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications.NotificationServer;


/**
 * This class is an Entity Manager for storing GameModels on the server.
 * 
 * @author Team 9
 * @version 1.0
 */
public class GameEntityManager implements EntityManager<GameModel> {
    
    private Data db;
    
    /**
     * Creates a new GameEntityManager attatched to the given database.
     * @param db
     */
    public GameEntityManager(Data db) {
        this.db = db;
        NotificationServer.getInstance().start();
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
     *            if the user isn't authorized for the given role
     */
    private void ensureRole(Session session, Role role)
            throws WPISuiteException {
        final User user = (User) db.retrieve(User.class, "username",
                session.getUsername()).get(0);
        if (!user.getRole().equals(role)) {
            throw new UnauthorizedException();
        }
    }
    
    @Override
    public int Count() throws WPISuiteException {
        return db.retrieveAll(new GameModel()).size();
    }
    
    @Override
    public String advancedGet(Session arg0, String[] arg1)
            throws WPISuiteException {
        throw new NotImplementedException();
    }
    
    @Override
    public String advancedPost(Session arg0, String arg1, String arg2)
            throws WPISuiteException {
        throw new NotImplementedException();
    }
    
    @Override
    public String advancedPut(Session arg0, String[] arg1, String arg2)
            throws WPISuiteException {
        throw new NotImplementedException();
    }
    
    @Override
    public void deleteAll(Session s) throws WPISuiteException {
        ensureRole(s, Role.ADMIN);
        db.deleteAll(new GameModel(), s.getProject());
    }
    
    @Override
    public boolean deleteEntity(Session s, String id) throws WPISuiteException {
        ensureRole(s, Role.ADMIN);
        return db.delete(getEntity(s, id)[0]) != null;
    }
    
    @Override
    public GameModel[] getAll(Session s) throws WPISuiteException {
        return db.retrieveAll(new GameModel(), s.getProject()).toArray(
                new GameModel[0]);
    }
    
    @Override
    public GameModel[] getEntity(Session s, String id) throws NotFoundException {
        final int intId = Integer.parseInt(id);
        if (intId < 0) {
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
    
    @Override
    public GameModel makeEntity(Session s, String content)
            throws WPISuiteException {
        final GameModel newGameModel = GameModel.fromJSON(content);
        newGameModel.setID(getNextID(s));
        if (!db.save(newGameModel, s.getProject())) {
            throw new WPISuiteException();
        }
        System.out.println("GEM makeEntity()");
        NotificationServer.getInstance().sendUpdateNotification();
        return newGameModel;
    }
    
    @Override
    public void save(Session s, GameModel GameModel) throws WPISuiteException {
        db.save(GameModel, s.getProject());
        
    }
    
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
        System.out.println("GEM update()");
        NotificationServer.getInstance().sendUpdateNotification();
        return existingGameModel;
    }
    
    /**
     * Gets the next available unique ID for a GameModel
     */
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
