/**
 *  * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 */
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.buttons;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * @version $Revision: 1.0 $
 * @author Chickenbellyfinn
 */
public class ImportExportButtonsPanel extends ToolbarGroupView {


	private final JPanel contentPanel = new JPanel();
	
	JButton importButton;
	JButton exportButton;
	
	/**
	 * Constructor
	 */
	public ImportExportButtonsPanel() {
		super("");
	
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

		importButton = new JButton("<html>Import Requirements</html>");
		exportButton = new JButton("<html>Export Requirements</html>");
		
		importButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().importRequirements();
			}
		});
		
		exportButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().exportRequirements();				
			}
		});
		
		try {
		    Image img = ImageIO.read(getClass().getResource("import.png"));
		    importButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("export.png"));
		    exportButton.setIcon(new ImageIcon(img));
		    
		} catch (IOException ex) {}
		
		contentPanel.add(importButton);
		contentPanel.add(exportButton);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
		this.setPreferredWidth(380);
	}
	
	/**
	 * @return the export button
	 */
	public JButton getExportButton() {
		return exportButton;
	}
	
	/**
	 * @return the import button
	 */
	public JButton getImportButton(){
		return importButton;
	}
}
