package harrypotter.view;
import javax.swing.*;

import harrypotter.controller.GameViewGUI;
import harrypotter.controller.StartingScreenGUI;
import harrypotter.model.character.Champion;
import harrypotter.model.character.HufflepuffWizard;
import harrypotter.model.character.Wizard;
import harrypotter.model.tournament.Tournament;
import harrypotter.model.world.Cell;
import harrypotter.model.world.ChampionCell;
import harrypotter.model.world.EmptyCell;
import harrypotter.model.world.ObstacleCell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameView extends JFrame
{
	private JPanel map;
	private JPanel playerinfo;
	private Tournament myTournament;
	private JPanel allPlayersInfo;
		
	public GameView(Tournament myTournament) throws IOException
	{
		this.myTournament = myTournament;

		map = new JPanel();
		map.setLayout(new GridLayout(0,10));
		
		ImageIcon myTile = new ImageIcon("resources\\tile.jpg");

		playerinfo = new JPanel(new GridLayout(1,5));
		allPlayersInfo = new JPanel(new GridLayout(1,6));
		playerinfo.setBackground(Color.DARK_GRAY);
		playerinfo.setForeground(Color.WHITE);
		
		JLabel backgroundPic = new JLabel(new ImageIcon("resources\\wallpaper.jpg"));
		backgroundPic.setSize(playerinfo.getWidth(), playerinfo.getHeight());
		
		playerinfo.add(backgroundPic);
		
		playerinfo.setPreferredSize(new Dimension(getWidth(),85));
		allPlayersInfo.setPreferredSize(new Dimension(getWidth(),100));
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setResizable(false);
		setTitle("Harry Potter: The Return of the Triwizard Tournament");
		getContentPane().setBackground(Color.decode("#FFFFFF"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		/*ActionListener actionListener = new FirstTaskViewGUI(this,myTournament);
		
		Cell[][] myMap = myTournament.getFirstTask().getMap();
		
		for(int i = 0; i<myTournament.getFirstTask().getMap().length;i++)
		{
			for(int j = 0; j<myTournament.getFirstTask().getMap().length;j++)
			{
				Cell myCell = myMap[i][j];
				JButton btnCell = new JButton();
				btnCell.addActionListener(actionListener);
				
				if(myCell instanceof EmptyCell)
				{
					 ImageIcon img = new ImageIcon("C:\\Users\\Omar\\Desktop\\Harry Potter The Game\\Textures\\EmptyCell.png");
				        btnCell.setIcon(img);
				}
				
				else if(myCell instanceof ObstacleCell)
				{ImageIcon img = new ImageIcon("C:\\Users\\Omar\\Desktop\\Harry Potter The Game\\Textures\\ObstacleCell.png");
		        btnCell.setIcon(img);}
				
				else if(myCell instanceof ChampionCell)
				{
					Wizard myWizard = (Wizard)(((ChampionCell) myCell).getChamp());
					
					if(myWizard instanceof HufflepuffWizard)
					{
						CompoundIcon img = new CompoundIcon();
						img.addIcon(new ImageIcon("C:\\Users\\Omar\\Desktop\\Harry Potter The Game\\Textures\\EmptyCell.png"));
						img.addIcon(new ImageIcon("C:\\Users\\Omar\\Desktop\\Harry Potter The Game\\Textures\\Hufflepuff.gif"));
						btnCell.setIcon(img);
					}
					
				}
				
				addCell(btnCell);
				
			}
		}*/
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        JOptionPane.showMessageDialog(null, "Closing the game, Drying Harry Potter's tears, Buffing the wizards' magic marks off motherboard! Goodbye :(");
		        	System.exit(0);		            
		        }
		});
		
		add(allPlayersInfo,BorderLayout.NORTH);
		add(map, BorderLayout.CENTER);
		add(playerinfo, BorderLayout.SOUTH);
		
		
		setVisible(true);
		
	}
	
	
	public JPanel getMap() {
		return map;
	}



	public void setMap(JPanel map) {
		this.map = map;
	}




	public void addCell(JLabel btnCell) {
		map.add(btnCell);
	}

	public JPanel getPlayerinfo() {
		return playerinfo;
	}


	public void setPlayerinfo(JPanel playerinfo) {
		this.playerinfo = playerinfo;
	}


	public Tournament getMyTournament() {
		return myTournament;
	}


	public void setMyTournament(Tournament myTournament) {
		this.myTournament = myTournament;
	}


	public JPanel getAllPlayersInfo() {
		return allPlayersInfo;
	}


	public void setAllPlayersInfo(JPanel allPlayersInfo) {
		this.allPlayersInfo = allPlayersInfo;
	}	
	
		
}
