package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;


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
    public DocumentationPanel() {
        //        File file = new File("/res/UserHelp.html");
        URL helpPage = DocumentationPanel.class
                .getResource("/res/UserHelp.html");
        
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.setDocument(new HTMLDocument());
        try {
            editorPane.setPage(helpPage); //load the help page in the JEditorPanel
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        editorPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (HyperlinkEvent.EventType.ACTIVATED == e.getEventType()) {
                    String desc = e.getDescription();
                    if (desc == null) {
                        return;
                    }
                    else if (desc.startsWith("#")) {
                        editorPane.scrollToReference(desc.substring(1));
                    }
                    else {
                        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                            try {
                                desktop.browse(new URL(desc).toURI());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
                Alignment.LEADING).addComponent(editorScrollPane,
                GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
                Alignment.LEADING).addComponent(editorScrollPane,
                GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE));
        setLayout(groupLayout);
        
    }
    
}
