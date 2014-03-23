/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.toolbar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

import java.awt.Color;

/**
 * @author Dan
 *
 */
@SuppressWarnings("serial")
public class AdminButtons extends ToolbarGroupView {
	private JButton newGameButton = new JButton("<html>End<br/>Estimation</html>");
	private JButton btnDeleteGame = new JButton("<html>Delete<br/>Game</html>");
	
	private final JPanel contentPanel = new JPanel();
	
	public AdminButtons() {
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(350);
		
		this.newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.btnDeleteGame.setHorizontalAlignment(SwingConstants.CENTER);
		this.btnDeleteGame.setForeground(Color.RED);
		
		contentPanel.add(newGameButton);
		contentPanel.add(btnDeleteGame);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
		
	}
}
