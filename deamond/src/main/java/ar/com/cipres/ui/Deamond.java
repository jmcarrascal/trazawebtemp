package ar.com.cipres.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ar.com.cipres.ui.serverhttp.ServerHttp;



public class Deamond extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	static private final String NEW_LINE = "\n";
	JButton openButton, fromClipboardButton, signButton;
	JTextArea log;
	JFileChooser fc;
	

	private boolean fileFromClipboard = false;

	private String dataFromClipboard;

	public Deamond() throws Exception {
		super(new BorderLayout());
		//Start Server
		ServerHttp serverHttp = new ServerHttp();
		serverHttp.startServer();
				
		JLabel active = new JLabel(createImageIcon("images/cipreserver-active.png"));
		// Add the buttons and the log to this panel.
		add(active, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {

		// Handle open button action.
		if (e.getSource() == openButton) {

			int returnVal = fc.showOpenDialog(Deamond.this);

			

			log.setCaretPosition(log.getDocument().getLength());

			// Handle sign button action.
		} else if (e.getSource() == signButton) {

			

			// Handle from clipboard action
		} else if (e.getSource() == fromClipboardButton) {

			
		}

			// Handle combo box action
		
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Deamond.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 * 
	 * @throws Exception
	 */
	private static void createAndShowGUI() throws Exception {
		// Create and set up the window.
		JFrame frame = new JFrame("Deamond");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new Deamond());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				try {
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
