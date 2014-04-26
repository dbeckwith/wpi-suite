/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;

/**
 * Renders tooltips for deck selection combobox
 * 
 * @author team9
 * @version 1.0
 * 
 */
public class DeckComboBoxRenderer extends BasicComboBoxRenderer {
	private static final long serialVersionUID = -6654798255103649031L;
	
	private static final DecimalFormat cardFormat = new DecimalFormat("0.0");

	@SuppressWarnings("rawtypes")
    @Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());

			if (-1 < index && (index == 0 || index > 2)) {
				String toolTip = "";
				for (Double card : ((DeckModel) value).getCards()) {
					toolTip += cardFormat.format(card)
							+ ", ";
				}
				toolTip = toolTip.replaceAll(", $", "");
				list.setToolTipText(toolTip);
			} else if (index == 1) {
				list.setToolTipText("User-entered values, no limit");
			} else if (index == 2) {
				list.setToolTipText("Create a deck with cards from 1 to specified value");
			}
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setFont(list.getFont());
		setText((value == null) ? "" : value.toString());
		return this;
	}
}