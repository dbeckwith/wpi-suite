/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @version 1.0
 * @author Team 9
 * 
 */
public class HighlightedTable extends JTable implements MouseMotionListener, MouseListener {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7039413546375398892L;
    
    private final Color HIGHLIGHT_COLOR = new Color(220, 232, 244);
    
    private int highlightRow = -1;
    
    public HighlightedTable() {
        
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        final Component c = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(row)) {
            c.setBackground(highlightRow == row ? HIGHLIGHT_COLOR : Color.WHITE);
        }
        else {
            c.setBackground(getSelectionBackground());
        }
        return c;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        highlightRow = rowAtPoint(e.getPoint());
        repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        highlightRow = -1;
        repaint();
    }
}
