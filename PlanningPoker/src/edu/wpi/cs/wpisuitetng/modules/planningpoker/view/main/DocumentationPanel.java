/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * 
 * @author Drew
 *
 */
public class DocumentationPanel extends JPanel {
    
    private static final long serialVersionUID = -6863310227330064388L;
    
    private static DocumentationPanel instance;
    
    /**
     * Gets the single instance of the panel.
     * 
     * @return the instance of the DocumentationPanel
     */
    public static DocumentationPanel getPanel() {
        if (instance == null) {
            instance = new DocumentationPanel();
        }
        return instance;
    }
    
    /**
     * Create the panel.
     */
    private DocumentationPanel() {
        final URL helpPage = DocumentationPanel.class
                .getResource("/res/UserHelp.html");
        
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        try {
            editorPane.setPage(helpPage); //load the help page in the JEditorPanel
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        
        final JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        final GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
                Alignment.LEADING).addComponent(editorScrollPane,
                GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
                Alignment.LEADING).addComponent(editorScrollPane,
                GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE));
        setLayout(groupLayout);
        
    }
    
}
