package harrypotter.view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import harrypotter.controller.StartingScreenGUI;
import harrypotter.model.character.Champion;
import harrypotter.model.character.Wizard;

public class Winner extends JFrame {
	
	public Winner(Champion winner)
	{
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setResizable(false);
		setTitle("Harry Potter: The Return of the Triwizard Tournament");
		setContentPane(new JLabel(new ImageIcon("resources\\Winner.jpg")));
		
	
		
		
		Wizard myWizard = (Wizard) winner;
		JButton exit = new JButton("Exit Game");
		
		exit.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		
		JTextArea text = new JTextArea();
		text.setOpaque(false);
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\HarryPotterFont.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		Font font = new Font("Harry P",Font.PLAIN,26);
		
		
		
		add(text);
		
		add(exit);
		
		text.setFont(font);
		
		exit.setFont(font);
		
		text.setText("Congratulations " + myWizard.getName() + " has won the tournament!" );
		
		text.setBounds(300,300,400,400);
		
		exit.setBounds(600,600,100,100);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}	
	
}
