package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.Dimension;

public class EstimationPanel extends JPanel {
	private JTextField estimateField;

	/**
	 * Create the panel.
	 */
	public EstimationPanel() {
		setPreferredSize(new Dimension(200, 110));
		setMinimumSize(new Dimension(200, 110));
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(294, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(199, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_2 = new JLabel("Ends at:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_2.add(lblNewLabel_2);
		
		JLabel timeLabel = new JLabel("3/23 6:00 PM");
		panel_2.add(timeLabel);
		
		JLabel lblNewLabel = new JLabel("Votes:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblNewLabel);
		
		JLabel votesLabel = new JLabel("0/15");
		panel_1.add(votesLabel);
		
		JLabel lblEstimate = new JLabel("Estimate:");
		panel.add(lblEstimate);
		
		estimateField = new JTextField();
		panel.add(estimateField);
		estimateField.setColumns(10);
		setLayout(groupLayout);

	}
}