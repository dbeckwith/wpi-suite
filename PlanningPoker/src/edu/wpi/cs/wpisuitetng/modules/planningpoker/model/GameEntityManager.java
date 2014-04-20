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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameTimeoutObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications.NotificationServer;


public class GameEntityManager implements EntityManager<GameModel> {
    
    private final Data db;
    
    private static GameEntityManager instance;
    
    /**
     * Creates a new GameEntityManager attatched to the given database.
     * 
     * @param db
     */
    public GameEntityManager(Data db) {
        this.db = db;
        if(NotificationServer.getInstance().getState() == Thread.State.NEW){
                NotificationServer.getInstance().start();
        }
        instance = this;
    }
    
    public static GameEntityManager getInstance() {
        return instance;
    }
    
    /**
     * Ensures that a user is of the specified role
     * 
     * @param session
     *        the session
     * @param role
     *        the role being verified
     * 
     * @throws WPISuiteException
     *         if the user isn't authorized for the given role
     */
    private void ensureRole(Session session, Role role) throws WPISuiteException {
        final User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
        if (!user.getRole().equals(role)) {
            throw new UnauthorizedException("");
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int Count() {
        return db.retrieveAll(new GameModel()).size();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String advancedGet(Session arg0, String[] arg1) throws NotImplementedException {
        throw new NotImplementedException();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String advancedPost(Session arg0, String arg1, String arg2)
            throws NotImplementedException {
        throw new NotImplementedException();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String advancedPut(Session arg0, String[] arg1, String arg2)
            throws NotImplementedException {
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
    public GameModel[] getAll(Session s) {
        return db.retrieveAll(new GameModel(), s.getProject()).toArray(new GameModel[0]);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public GameModel[] getEntity(Session s, String id) throws NotFoundException {
        final int intId = Integer.parseInt(id);
        if (intId < 0) {
            throw new NotFoundException("");
        }
        GameModel[] GameModels = null;
        try {
            GameModels = db.retrieve(GameModel.class, "id", intId, s.getProject()).toArray(
                    new GameModel[0]);
        }
        catch (WPISuiteException e) {
            e.printStackTrace();
        }
        if (GameModels.length < 1 || GameModels[0] == null) {
            throw new NotFoundException("");
        }
        return GameModels;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public GameModel makeEntity(Session s, String content) throws WPISuiteException {
        final GameModel newGameModel = GameModel.fromJSON(content);
        newGameModel.setID(getNextID(s));
        if (!db.save(newGameModel, s.getProject())) {
            throw new WPISuiteException("");
        }
        new GameTimeoutObserver(s, newGameModel);
        System.out.println("GEM makeEntity()");
        NotificationServer.getInstance().sendUpdateNotification();
        return newGameModel;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Session s, GameModel GameModel){
        db.save(GameModel, s.getProject());
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public GameModel update(Session s, String content) throws WPISuiteException {
        final GameModel updatedGameModel = GameModel.fromJSON(content);
        System.out.println("Updating game " + updatedGameModel);
        /*
         * Because of the disconnected objects problem in db4o, we can't just
         * save GameModels. We have to get the original GameModel from db4o,
         * copy properties from updatedGameModel, then save the original
         * GameModel again.
         */
        final List<Model> oldGameModels = db.retrieve(GameModel.class, "id",
                updatedGameModel.getID(), s.getProject());
        if (oldGameModels.size() < 1 || oldGameModels.get(0) == null) { throw new BadRequestException(
                "GameModel with ID does not exist."); }
        
        final GameModel existingGameModel = (GameModel) oldGameModels.get(0);
        
        // copy values to old GameModel
        existingGameModel.copyFrom(updatedGameModel);
        
        if (updatedGameModel.getStatus().equals(GameModel.GameStatus.PENDING)) {
            // start observer only when the game is live
            System.out.println("Getting observer for game");
            GameTimeoutObserver obs = GameTimeoutObserver
                    .getObserver(updatedGameModel);
            if (obs == null) {
                System.out.println("Could not find observer for game");
            }
            else if (!obs.isAlive()) {
                System.out.println("Starting observer");
                obs.start();
            }
        }
        
        if (!db.save(existingGameModel, s.getProject())) { throw new WPISuiteException(); }
        System.out.println("GEM update()");
        NotificationServer.getInstance().sendUpdateNotification();
        return existingGameModel;
    }
    
    /**
     * Gets the next available unique ID for a GameModel
     */
    private int getNextID(Session s) throws WPISuiteException{
        int max = 0;
        for (GameModel g : getAll(s)) {
            if (g.getID() > max) {
                max = g.getID();
            }
        }
        return max + 1;
    }
    
}
