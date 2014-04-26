package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class HighlightedTable extends JTable implements MouseMotionListener, MouseListener {
	
	private final Color HIGHLIGHT_COLOR = new Color(220, 232, 244);

	private int highlightRow = -1;

	public HighlightedTable() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);
		if (!isRowSelected(row)) {
			c.setBackground(highlightRow == row ? HIGHLIGHT_COLOR : Color.WHITE);
		} else {
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
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		highlightRow = -1;	
		repaint();
	}
}