package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

public class HelpButtons extends ToolbarGroupView {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1902038690593121709L;
    
    private JButton helpButton;
    
    private final JPanel contentPanel = new JPanel();
    
    public HelpButtons() {
        super(""); // not sure if this is needed
        
        helpButton = new JButton("<html>Help</html>");
        helpButton.setIcon(ImageLoader.getIcon("help.png"));
        
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        setPreferredWidth(175);
        
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: implement help button function
            }
        });
        
        
        helpButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        contentPanel.add(helpButton);
        contentPanel.setOpaque(false);
        
        this.add(contentPanel);
    }
}
