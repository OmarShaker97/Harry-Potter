package harrypotter.view;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import harrypotter.controller.StartingScreenGUI;

public class LosingScreen extends JFrame {
	
	public LosingScreen()
	{
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setResizable(false);
		setTitle("Harry Potter: The Return of the Triwizard Tournament");
		setContentPane(new JLabel(new ImageIcon("resources\\DeathScreen.jpg")));
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}	
	
}
