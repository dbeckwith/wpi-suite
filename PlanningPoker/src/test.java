import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JSpinner;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class test extends JPanel {

	/**
	 * Create the panel.
	 */
	public test() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.gridx = 0;
		gbc_spinner.gridy = 0;
		add(spinner, gbc_spinner);

	}

}
