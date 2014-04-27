package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


public class DocumentationPanel extends JPanel {
    
    private static final long serialVersionUID = -6863310227330064388L;
    
    /**
     * Create the panel.
     */
    public DocumentationPanel() {
        //        File file = new File("/res/UserHelp.html");
        URL helpPage = DocumentationPanel.class
                .getResource("/res/UserHelp.html");
        
        JEditorPane editorPane = new JEditorPane();
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

        
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(editorScrollPane, GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(editorScrollPane, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
        );
        setLayout(groupLayout);
        
    }
    
}
