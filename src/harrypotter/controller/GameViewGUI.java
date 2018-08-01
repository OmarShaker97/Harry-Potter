package harrypotter.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import harrypotter.exceptions.InCooldownException;
import harrypotter.exceptions.InvalidTargetCellException;
import harrypotter.exceptions.NotEnoughIPException;
import harrypotter.exceptions.OutOfBordersException;
import harrypotter.exceptions.OutOfRangeException;
import harrypotter.model.character.Champion;
import harrypotter.model.character.GryffindorWizard;
import harrypotter.model.character.HufflepuffWizard;
import harrypotter.model.character.RavenclawWizard;
import harrypotter.model.character.SlytherinWizard;
import harrypotter.model.character.Wizard;
import harrypotter.model.magic.Collectible;
import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.Potion;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;
import harrypotter.model.tournament.FirstTask;
import harrypotter.model.tournament.SecondTask;
import harrypotter.model.tournament.ThirdTask;
import harrypotter.model.tournament.Tournament;
import harrypotter.model.world.Cell;
import harrypotter.model.world.ChampionCell;
import harrypotter.model.world.CollectibleCell;
import harrypotter.model.world.CupCell;
import harrypotter.model.world.Direction;
import harrypotter.model.world.EmptyCell;
import harrypotter.model.world.Merperson;
import harrypotter.model.world.ObstacleCell;
import harrypotter.model.world.TreasureCell;
import harrypotter.model.world.WallCell;
import harrypotter.view.GameView;
import harrypotter.view.LosingScreen;

public class GameViewGUI implements KeyListener {

	private Tournament myTournament;
	private GameView GameView;
	private JPanel buttonsPanel;
	private JPanel cooldownPanel;
	private JProgressBar healthBar;
	private JProgressBar intellBar;
	private JPanel currentplayer;
	private JButton exitButton;
	private JPanel exitButtonPanel;
	private JComboBox winners;
	
	
	public GameViewGUI(GameView firstTask, Tournament Tournament) throws IOException, InterruptedException
	{
		
		
		this.myTournament = Tournament;
		
		this.GameView = firstTask;
		
		firstTask.getMap().setFocusable(true);
		this.GameView.setFocusable(true);
		this.GameView.addKeyListener(this);
		//firstTask.addKeyListener(this);
		
		//this.firstTask.getMap().addKeyListener(this);
		//firstTask.getMap().addKeyListener(this);
		
	
		
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\HarryPotterFont.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		Font font = new Font("Harry P",Font.PLAIN,21);
		

		exitButton = new JButton("Exit Game");
		exitButton.setBackground(Color.RED);
		exitButton.setFont(font);
		exitButton.setForeground(Color.WHITE);
		exitButtonPanel = new JPanel();
		buttonsPanel = new JPanel();

		exitButtonPanel.add(exitButton);
		exitButtonPanel.setPreferredSize(new Dimension(300,300));
		exitButtonPanel.setOpaque(false);
		buttonsPanel.add(exitButtonPanel, BorderLayout.NORTH);
		
		cooldownPanel = new JPanel();
		
		firstTask.getMap().removeAll();
		this.GameView.getMap().removeAll();
		
		firstTask.getPlayerinfo().removeAll();
		this.GameView.getPlayerinfo().removeAll();
		
		updateAllPlayersInfo();
		
		updatePlayerInfo();
		
		updateView();
		
		firstTask.setVisible(true);
		
		exitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				JOptionPane.showMessageDialog(null, "Closing the game, Drying Harry Potter's tears, Buffing the wizards' magic marks off motherboard! Goodbye :(");
				System.exit(0);
			}
		});
		
	}
	
	public void updateAllPlayersInfo()
	{
		if(myTournament.getMyTask() instanceof FirstTask)
			updateAllPlayersInfoFirstTask();
		
		else if(myTournament.getMyTask() instanceof SecondTask)
			updateAllPlayersInfoSecondTask();
		else
			updateAllPlayersInfoThirdTask();
		
	}
 	
	public void updateAllPlayersInfoFirstTask()
	{
		JPanel eachPlayer = new JPanel();
		
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\HarryPotterFont.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		Font font = new Font("Harry P",Font.PLAIN,46);
		
		GameView.getAllPlayersInfo().removeAll();
		
		for(Champion champ : myTournament.getMyTask().getChampions())
		{
			Wizard myWizard = (Wizard) champ;
			
			UIManager.put("ProgressBar.foreground", Color.GREEN);
			UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
			UIManager.put("ProgressBar.selectionForeground", Color.WHITE);
			healthBar = new JProgressBar(0,myWizard.getDefaultHp());
			healthBar.setStringPainted(true);
			healthBar.setValue(myWizard.getHp());
			healthBar.setString("HP: " + myWizard.getHp());
			
			JTextArea textArea = new JTextArea(myWizard.getClass().getSimpleName() + "\n" + myWizard.getName() + "\n");
			textArea.setForeground(Color.WHITE);
			textArea.setOpaque(false);
			textArea.setFont(font);
			eachPlayer.add(textArea);
			eachPlayer.add(healthBar);
			eachPlayer.setBackground(Color.DARK_GRAY);
			
			GameView.getAllPlayersInfo().add(eachPlayer);
		}
	}

	public void updateAllPlayersInfoSecondTask()
	{
		JPanel eachPlayer = new JPanel();
		
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\HarryPotterFont.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		Font font = new Font("Harry P",Font.PLAIN,46);
		
		GameView.getAllPlayersInfo().removeAll();
		
		for(Champion champ : myTournament.getSecondTask().getChampions())
		{
			Wizard myWizard = (Wizard) champ;
			
			UIManager.put("ProgressBar.foreground", Color.GREEN);
			UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
			UIManager.put("ProgressBar.selectionForeground", Color.WHITE);
			healthBar = new JProgressBar(0,myWizard.getDefaultHp());
			healthBar.setStringPainted(true);
			healthBar.setValue(myWizard.getHp());
			healthBar.setString("HP: " + myWizard.getHp());
			
			JTextArea textArea = new JTextArea(myWizard.getClass().getSimpleName() + "\n" + myWizard.getName() + "\n");
			textArea.setForeground(Color.WHITE);
			textArea.setOpaque(false);
			textArea.setFont(font);
			eachPlayer.add(textArea);
			eachPlayer.add(healthBar);
			eachPlayer.setBackground(Color.DARK_GRAY);
			
			GameView.getAllPlayersInfo().add(eachPlayer);
		}
	}
	
	public void updateAllPlayersInfoThirdTask()
	{
		JPanel eachPlayer = new JPanel();
		
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\HarryPotterFont.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		Font font = new Font("Harry P",Font.PLAIN,46);
		
		GameView.getAllPlayersInfo().removeAll();
		
		for(Champion champ : myTournament.getThirdTask().getChampions())
		{
			Wizard myWizard = (Wizard) champ;
			
			UIManager.put("ProgressBar.foreground", Color.GREEN);
			UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
			UIManager.put("ProgressBar.selectionForeground", Color.WHITE);
			healthBar = new JProgressBar(0,myWizard.getDefaultHp());
			healthBar.setStringPainted(true);
			healthBar.setValue(myWizard.getHp());
			healthBar.setString("HP: " + myWizard.getHp());
			
			JTextArea textArea = new JTextArea(myWizard.getClass().getSimpleName() + "\n" + myWizard.getName() + "\n");
			textArea.setForeground(Color.WHITE);
			textArea.setOpaque(false);
			textArea.setFont(font);
			eachPlayer.add(textArea);
			eachPlayer.add(healthBar);
			eachPlayer.setBackground(Color.DARK_GRAY);
			
			GameView.getAllPlayersInfo().add(eachPlayer);
		}
	}
	
	public void updatePlayerInfoFirstTask()
	{
		GameView.getPlayerinfo().removeAll();
		cooldownPanel.removeAll();
		/*buttonsPanel.removeAll();
		
		buttonsPanel.add(useSpellButton,BorderLayout.EAST);
		buttonsPanel.add(useTraitButton,BorderLayout.CENTER);
		buttonsPanel.add(usePotionButton, BorderLayout.WEST);*/
	
		
		Wizard currentWizard = (Wizard)(myTournament.getMyTask().getCurrentChamp());
		
		
		
		currentplayer = new JPanel();
		JPanel BarPanel = new JPanel(new BorderLayout());
		
		
		UIManager.put("ProgressBar.foreground", Color.GREEN);
		UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
		UIManager.put("ProgressBar.selectionForeground", Color.WHITE);
		healthBar = new JProgressBar(0,currentWizard.getDefaultHp());
		healthBar.setStringPainted(true);
		healthBar.setValue(currentWizard.getHp());
		healthBar.setString("HP: " + currentWizard.getHp());
		
		BarPanel.add(healthBar,BorderLayout.NORTH);
		
		UIManager.put("intellBar.foreground", Color.BLUE);
		UIManager.put("intellBar.selectionBackground", Color.BLACK);
		UIManager.put("intellBar.selectionForeground", Color.WHITE);
		intellBar = new JProgressBar(0,currentWizard.getDefaultIp());;
		intellBar.setStringPainted(true);
		intellBar.setValue(currentWizard.getIp());
		intellBar.setString("IP: " + currentWizard.getIp());
		
		BarPanel.add(intellBar,BorderLayout.SOUTH);
		BarPanel.setOpaque(false);
		BarPanel.setPreferredSize(new Dimension(400,50));
		
		
		
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\HarryPotterFont.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		Font font = new Font("Harry P",Font.PLAIN,26);
		
		winners = new JComboBox();
		winners.setBackground(Color.WHITE);
		winners.addItem("Winners:");
		
		for(int i = 0;i<myTournament.getFirstTask().getWinners().size();i++)
		{
			Wizard myWizard = (Wizard) myTournament.getFirstTask().getWinners().get(i);
			winners.addItem(myWizard.getName());
		}
		
		buttonsPanel.add(winners, BorderLayout.NORTH);
		
		JTextArea firstTaskText = new JTextArea("First Task");
		firstTaskText.setFont(font);
		firstTaskText.setEditable(false);
		firstTaskText.setBackground(Color.DARK_GRAY);
		firstTaskText.setForeground(Color.WHITE);
		
		buttonsPanel.add(firstTaskText,BorderLayout.AFTER_LINE_ENDS);
		
		JTextArea currentChampName = new JTextArea(currentWizard.getClass().getSimpleName() + "\n" + "Player Name: " + currentWizard.getName()+"\n");
		currentChampName.setEditable(false);
		currentChampName.setForeground(Color.WHITE);
		
		currentChampName.setFont(font);
		currentChampName.setOpaque(false);
		currentplayer.add(currentChampName, BorderLayout.NORTH);
		currentplayer.add(BarPanel, BorderLayout.SOUTH);
		currentplayer.setBackground(Color.DARK_GRAY);
		JLabel traitActivated = new JLabel("Trait Activated: " + myTournament.getMyTask().isTraitActivated());
		traitActivated.setFont(font);
		traitActivated.setForeground(Color.WHITE);
		traitActivated.setBackground(Color.DARK_GRAY);
		
		JLabel traitCooldown = new JLabel("Trait Cooldown: " + currentWizard.getTraitCooldown());
		traitCooldown.setFont(font);
		traitCooldown.setForeground(Color.WHITE);
		traitCooldown.setBackground(Color.DARK_GRAY);
		
		GameView.getPlayerinfo().add(currentplayer);
		
		String briefDescription = "";
		
		if(currentWizard instanceof GryffindorWizard)
			briefDescription = "Trait Description: This turn, the champion can \n make 2 moves instead of 1" + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		else if(currentWizard instanceof SlytherinWizard)
			briefDescription = "Trait Description: This turn, the champion can \n choose between: 1. Jumping over a cell containing \n an obstacle"
					+ " without destroying or moving \n the obstacle. "
					+ "2. Traversing two cells instead of \n one" + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		else if(currentWizard instanceof HufflepuffWizard )
			briefDescription = "Trait Description: This turn, the dragon doesn’t attack" + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		else if(currentWizard instanceof RavenclawWizard)
			briefDescription = "Trait Description: This turn, the champion is shown where \n the dragon is going to attack" + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		JTextArea currentChampTrait = new JTextArea(briefDescription + " \n Allowed Moves: " + myTournament.getFirstTask().getAllowedMoves()+"\n");
		currentChampTrait.setForeground(Color.WHITE);
		currentChampTrait.setOpaque(false);
		currentChampTrait.setEditable(false);
		
		currentChampTrait.setFont(font);
			
		GameView.getPlayerinfo().add(currentChampTrait);
		
		GameView.getPlayerinfo().add(cooldownPanel);
		
		JTextArea spellInfoTextArea = new JTextArea();
		spellInfoTextArea.setFont(font);
		String spellInfo = "";
		
		for(Spell s : currentWizard.getSpells())
		{
			spellInfo = spellInfo + s.getName() + " || Cost: " + s.getCost() + " || Cooldown: " + s.getCoolDown() + "\n";  
		}
		
		
		spellInfoTextArea.setText(spellInfo + "\n" + "Potions Amount: " + currentWizard.getInventory().size());
		
		spellInfoTextArea.setOpaque(false);
		
		spellInfoTextArea.setForeground(Color.WHITE);
		spellInfoTextArea.setBackground(Color.DARK_GRAY);
		
		cooldownPanel.add(traitActivated,BorderLayout.CENTER);
		cooldownPanel.add(traitCooldown, BorderLayout.EAST);
		cooldownPanel.add(spellInfoTextArea, BorderLayout.WEST);
		cooldownPanel.setBackground(Color.DARK_GRAY);
		
		GameView.getPlayerinfo().setPreferredSize(new Dimension(GameView.getWidth(),200));
	
		buttonsPanel.setBackground(Color.DARK_GRAY);
		GameView.getPlayerinfo().add(buttonsPanel);

		//firstTask.getPlayerinfo().add(useTraitButton, BorderLayout.WEST);
		//firstTask.getPlayerinfo().add(useSpellButton, BorderLayout.WEST);
	}

	public void updatePlayerInfoSecondTask()
	{
		GameView.getPlayerinfo().removeAll();
		cooldownPanel.removeAll();
		/*buttonsPanel.removeAll();
		
		buttonsPanel.add(useSpellButton,BorderLayout.EAST);
		buttonsPanel.add(useTraitButton,BorderLayout.CENTER);
		buttonsPanel.add(usePotionButton, BorderLayout.WEST);*/
		
		Wizard currentWizard = (Wizard)(myTournament.getMyTask().getCurrentChamp());
		
		
		currentplayer = new JPanel();
		JPanel BarPanel = new JPanel(new BorderLayout());
		
		
		UIManager.put("ProgressBar.foreground", Color.GREEN);
		UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
		UIManager.put("ProgressBar.selectionForeground", Color.WHITE);
		healthBar = new JProgressBar(0,currentWizard.getDefaultHp());
		healthBar.setStringPainted(true);
		healthBar.setValue(currentWizard.getHp());
		healthBar.setString("HP: " + currentWizard.getHp());
		
		BarPanel.add(healthBar,BorderLayout.NORTH);
		
		UIManager.put("intellBar.foreground", Color.BLUE);
		UIManager.put("intellBar.selectionBackground", Color.BLACK);
		UIManager.put("intellBar.selectionForeground", Color.WHITE);
		intellBar = new JProgressBar(0,currentWizard.getDefaultIp());;
		intellBar.setStringPainted(true);
		intellBar.setValue(currentWizard.getIp());
		intellBar.setString("IP: " + currentWizard.getIp());
		
		BarPanel.add(intellBar,BorderLayout.SOUTH);
		BarPanel.setOpaque(false);
		BarPanel.setPreferredSize(new Dimension(400,50));
		
		
		
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\HarryPotterFont.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		Font font = new Font("Harry P",Font.PLAIN,26);
		
		JTextArea firstTaskText = new JTextArea("Second Task");
		firstTaskText.setFont(font);
		firstTaskText.setEditable(false);
		firstTaskText.setBackground(Color.DARK_GRAY);
		firstTaskText.setForeground(Color.WHITE);
		
		buttonsPanel.add(firstTaskText,BorderLayout.AFTER_LINE_ENDS);
		
		JTextArea currentChampName = new JTextArea(currentWizard.getClass().getSimpleName() + "\n" + "Player Name: " + currentWizard.getName()+"\n");
		currentChampName.setEditable(false);
		currentChampName.setForeground(Color.WHITE);
		
		currentChampName.setFont(font);
		currentChampName.setOpaque(false);
		currentplayer.add(currentChampName, BorderLayout.NORTH);
		currentplayer.add(BarPanel, BorderLayout.SOUTH);
		currentplayer.setBackground(Color.DARK_GRAY);
		JLabel traitActivated = new JLabel("Trait Activated: " + myTournament.getMyTask().isTraitActivated());
		traitActivated.setFont(font);
		traitActivated.setForeground(Color.WHITE);
		traitActivated.setBackground(Color.DARK_GRAY);
		
		JLabel traitCooldown = new JLabel("Trait Cooldown: " + currentWizard.getTraitCooldown());
		traitCooldown.setFont(font);
		traitCooldown.setForeground(Color.WHITE);
		traitCooldown.setBackground(Color.DARK_GRAY);
		
		GameView.getPlayerinfo().add(currentplayer);
		
		String briefDescription = "";
		
		if(currentWizard instanceof GryffindorWizard)
			briefDescription = "Trait Description: This turn, the champion can \n make 2 moves instead of 1" + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		else if(currentWizard instanceof SlytherinWizard)
			briefDescription = "Trait Description: This turn, the champion's \n movement traverses two cells instead \n of"
					+ " one that he ends up in \n an empty cell. "
					+ "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		else if(currentWizard instanceof HufflepuffWizard )
			briefDescription = "Trait Description: This turn, the merperson won't do any damage." + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		else if(currentWizard instanceof RavenclawWizard)
			briefDescription = "Trait Description: This turn, shows a hint on where target is hidden" + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		JTextArea currentChampTrait = new JTextArea(briefDescription + " \n Allowed Moves: " + myTournament.getSecondTask().getAllowedMoves()+"\n");
		currentChampTrait.setForeground(Color.WHITE);
		currentChampTrait.setOpaque(false);
		currentChampTrait.setEditable(false);
		
		currentChampTrait.setFont(font);
			
		GameView.getPlayerinfo().add(currentChampTrait);
		
		GameView.getPlayerinfo().add(cooldownPanel);
		
		JTextArea spellInfoTextArea = new JTextArea();
		spellInfoTextArea.setFont(font);
		String spellInfo = "";
		
		for(Spell s : currentWizard.getSpells())
		{
			spellInfo = spellInfo + s.getName() + " || Cost: " + s.getCost() + " || Cooldown: " + s.getCoolDown() + "\n";  
		}
		
		spellInfoTextArea.setText(spellInfo + "\n" + "Potions Amount: " + currentWizard.getInventory().size());
		
		spellInfoTextArea.setOpaque(false);
		
		spellInfoTextArea.setForeground(Color.WHITE);
		spellInfoTextArea.setBackground(Color.DARK_GRAY);
		
		cooldownPanel.add(traitActivated,BorderLayout.CENTER);
		cooldownPanel.add(traitCooldown, BorderLayout.EAST);
		cooldownPanel.add(spellInfoTextArea, BorderLayout.WEST);
		cooldownPanel.setBackground(Color.DARK_GRAY);
		
		GameView.getPlayerinfo().setPreferredSize(new Dimension(GameView.getWidth(),200));
	
		buttonsPanel.setBackground(Color.DARK_GRAY);
		GameView.getPlayerinfo().add(buttonsPanel);

		//firstTask.getPlayerinfo().add(useTraitButton, BorderLayout.WEST);
		//firstTask.getPlayerinfo().add(useSpellButton, BorderLayout.WEST);
	}

	public void updatePlayerInfoThirdTask()
	{
		GameView.getPlayerinfo().removeAll();
		cooldownPanel.removeAll();
		/*buttonsPanel.removeAll();
		
		buttonsPanel.add(useSpellButton,BorderLayout.EAST);
		buttonsPanel.add(useTraitButton,BorderLayout.CENTER);
		buttonsPanel.add(usePotionButton, BorderLayout.WEST);*/
		
		Wizard currentWizard = (Wizard)(myTournament.getMyTask().getCurrentChamp());
		
		
		currentplayer = new JPanel();
		JPanel BarPanel = new JPanel(new BorderLayout());
		
		
		UIManager.put("ProgressBar.foreground", Color.GREEN);
		UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
		UIManager.put("ProgressBar.selectionForeground", Color.WHITE);
		healthBar = new JProgressBar(0,currentWizard.getDefaultHp());
		healthBar.setStringPainted(true);
		healthBar.setValue(currentWizard.getHp());
		healthBar.setString("HP: " + currentWizard.getHp());
		
		BarPanel.add(healthBar,BorderLayout.NORTH);
		
		UIManager.put("intellBar.foreground", Color.BLUE);
		UIManager.put("intellBar.selectionBackground", Color.BLACK);
		UIManager.put("intellBar.selectionForeground", Color.WHITE);
		intellBar = new JProgressBar(0,currentWizard.getDefaultIp());;
		intellBar.setStringPainted(true);
		intellBar.setValue(currentWizard.getIp());
		intellBar.setString("IP: " + currentWizard.getIp());
		
		BarPanel.add(intellBar,BorderLayout.SOUTH);
		BarPanel.setOpaque(false);
		BarPanel.setPreferredSize(new Dimension(400,50));
		
		
		
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\HarryPotterFont.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		Font font = new Font("Harry P",Font.PLAIN,26);
		
		JTextArea firstTaskText = new JTextArea("Third Task");
		firstTaskText.setFont(font);
		firstTaskText.setEditable(false);
		firstTaskText.setBackground(Color.DARK_GRAY);
		firstTaskText.setForeground(Color.WHITE);
		
		buttonsPanel.add(firstTaskText,BorderLayout.AFTER_LINE_ENDS);
		
		JTextArea currentChampName = new JTextArea(currentWizard.getClass().getSimpleName() + "\n" + "Player Name: " + currentWizard.getName()+"\n");
		currentChampName.setEditable(false);
		currentChampName.setForeground(Color.WHITE);
		
		currentChampName.setFont(font);
		currentChampName.setOpaque(false);
		currentplayer.add(currentChampName, BorderLayout.NORTH);
		currentplayer.add(BarPanel, BorderLayout.SOUTH);
		currentplayer.setBackground(Color.DARK_GRAY);
		JLabel traitActivated = new JLabel("Trait Activated: " + myTournament.getMyTask().isTraitActivated());
		traitActivated.setFont(font);
		traitActivated.setForeground(Color.WHITE);
		traitActivated.setBackground(Color.DARK_GRAY);
		
		JLabel traitCooldown = new JLabel("Trait Cooldown: " + currentWizard.getTraitCooldown());
		traitCooldown.setFont(font);
		traitCooldown.setForeground(Color.WHITE);
		traitCooldown.setBackground(Color.DARK_GRAY);
		
		GameView.getPlayerinfo().add(currentplayer);
		
		String briefDescription = "";
		
		if(currentWizard instanceof GryffindorWizard)
			briefDescription = "Trait Description: This turn, the champion can \n make 2 moves instead of 1" + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		else if(currentWizard instanceof SlytherinWizard)
			briefDescription = "Trait Description: This turn, the champion can \n choose between: 1. Jumping over a wall \n cell containing an obstacle"
					+ " without destroying or moving \n the obstacle. "
					+ "2. Traversing two cells instead of \n one" + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		else if(currentWizard instanceof HufflepuffWizard )
			briefDescription = "Trait Description: Attacks from other champions deal half damage." + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		else if(currentWizard instanceof RavenclawWizard)
			briefDescription = "Trait Description: This turn, shows a hint where target is hidden." + "\n" + "Cooldown: " + currentWizard.getTraitCooldown();
		
		JTextArea currentChampTrait = new JTextArea(briefDescription  + " \n Allowed Moves: " + myTournament.getThirdTask().getAllowedMoves()+"\n");
		currentChampTrait.setForeground(Color.WHITE);
		currentChampTrait.setOpaque(false);
		currentChampTrait.setEditable(false);
		
		currentChampTrait.setFont(font);
			
		GameView.getPlayerinfo().add(currentChampTrait);
		
		GameView.getPlayerinfo().add(cooldownPanel);
		
		JTextArea spellInfoTextArea = new JTextArea();
		spellInfoTextArea.setFont(font);
		String spellInfo = "";
		
		for(Spell s : currentWizard.getSpells())
		{
			spellInfo = spellInfo + s.getName() + " || Cost: " + s.getCost() + " || Cooldown: " + s.getCoolDown() + "\n";  
		}
		
		spellInfoTextArea.setText(spellInfo + "\n" + "Potions Amount: " + currentWizard.getInventory().size());
		
		spellInfoTextArea.setOpaque(false);
		
		spellInfoTextArea.setForeground(Color.WHITE);
		spellInfoTextArea.setBackground(Color.DARK_GRAY);
		
		cooldownPanel.add(traitActivated,BorderLayout.CENTER);
		cooldownPanel.add(traitCooldown, BorderLayout.EAST);
		cooldownPanel.add(spellInfoTextArea, BorderLayout.WEST);
		cooldownPanel.setBackground(Color.DARK_GRAY);
		
		GameView.getPlayerinfo().setPreferredSize(new Dimension(GameView.getWidth(),200));
	
		buttonsPanel.setBackground(Color.DARK_GRAY);
		GameView.getPlayerinfo().add(buttonsPanel);

		//firstTask.getPlayerinfo().add(useTraitButton, BorderLayout.WEST);
		//firstTask.getPlayerinfo().add(useSpellButton, BorderLayout.WEST);
	}

	public void updatePlayerInfo()
	{
		
		if(myTournament.getMyTask() instanceof FirstTask)
			updatePlayerInfoFirstTask();
		
		else if(myTournament.getMyTask() instanceof SecondTask)
			updatePlayerInfoSecondTask();
		
		else
			updatePlayerInfoThirdTask();
		
		GameView.validate();
		GameView.repaint();
		
		
	}
	
	public void updateView(Point firstMarked, Point secondMarked)
	{
		if(myTournament.getMyTask() instanceof FirstTask){
			
		Cell[][] map = myTournament.getMyTask().getMap();
		
		GameView.getMap().removeAll();
		
		if(myTournament.getMyTask().getChampions().size()==0 && myTournament.getFirstTask().getWinners().size()==0)
		{
			GameView.addKeyListener(null);
			GameView.setVisible(false);
			//firstTask.dispose();
			
			new LosingScreenGUI(new LosingScreen());
		}
		

		for(int i = 0; i<myTournament.getMyTask().getMap().length;i++)
		{
			for(int j = 0; j<myTournament.getMyTask().getMap().length;j++)
			{
				Cell myCell = map[i][j];
				JLabel labelCell = new JLabel();
				labelCell.setSize(new Dimension(100,100));
				
				if(i==firstMarked.x && j==firstMarked.y)
				{
					ImageIcon img = new ImageIcon("resources\\Fire.gif");
					labelCell.setIcon(img);
				}
				
				else if(i==secondMarked.x && j==secondMarked.y)
				{
					ImageIcon img = new ImageIcon("resources\\Fire.gif");
					labelCell.setIcon(img);
				}
				
				else if(myCell instanceof EmptyCell && i==4 && j==4)
				{
					ImageIcon img = new ImageIcon("resources\\EggCell2.png");
			        labelCell.setIcon(img);
				}
				
				else if(myCell instanceof EmptyCell || myCell instanceof CollectibleCell)
				{
					 ImageIcon img = new ImageIcon("resources\\EmptyCell.png");
				        labelCell.setIcon(img);
				}
				
				else if(myCell instanceof ObstacleCell)
				{
				ImageIcon img = new ImageIcon("resources\\ObstacleCell.png");
		        labelCell.setIcon(img);
		        }
						
				else if(myCell instanceof ChampionCell)
				{
					Wizard myWizard = (Wizard)(((ChampionCell) myCell).getChamp());
					
					if(myWizard instanceof GryffindorWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Gryffindor.png");
						labelCell.setIcon(img);
					}
					else if(myWizard instanceof SlytherinWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Gryffindor.png");
						labelCell.setIcon(img);
					}
					
					else if(myWizard instanceof HufflepuffWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Hufflepuff.png");
						labelCell.setIcon(img);
					}
					
					else
					{
						ImageIcon img = new ImageIcon("resources\\Ravenclaw.png");
						labelCell.setIcon(img);
					}
					
					
				}
				
				GameView.addCell(labelCell);
				GameView.validate();
				GameView.revalidate();
				GameView.repaint();
				
			}
		}
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		
		System.out.println("Hello");
		
		if(event.getKeyCode()== KeyEvent.VK_RIGHT)
		{
			try {
				//Thread.sleep(500);
				Wizard myWizard = (Wizard) myTournament.getMyTask().getCurrentChamp();
				
				boolean previousTraitActivated = false;
				
				int previousAllowedMoves = myTournament.getMyTask().getAllowedMoves();
				
				if(myTournament.getMyTask().isTraitActivated())
					previousTraitActivated = true;
				
				
				Point newLocation = new Point(myWizard.getLocation().x, myWizard.getLocation().y + 1);
				
				ArrayList<Point> markedCells = myTournament.getFirstTask().getMarkedCells();
				
				if(newLocation.y<=9){
				if(myTournament.getMyTask().getMap()[newLocation.x][newLocation.y] instanceof CollectibleCell)
				{
					CollectibleCell collectibleCell = (CollectibleCell) (myTournament.getMyTask().getMap()[newLocation.x][newLocation.y]);
					Potion myPotion = (Potion) collectibleCell.getCollectible();
					JOptionPane.showMessageDialog(null, "You just picked up a " + myPotion.getName() + " potion with IP: " + myPotion.getAmount());
				}
			}
				
				myTournament.getMyTask().moveRight();
				
				updateAllPlayersInfo();
				
				if(myWizard instanceof HufflepuffWizard && previousTraitActivated || 
						(myWizard instanceof GryffindorWizard && previousTraitActivated && previousAllowedMoves>1))
					updateView();
				else if(myTournament.getMyTask() instanceof FirstTask)
					updateView(markedCells.get(0),markedCells.get(1));
				else
					updateView();
				
				if(!((myWizard instanceof HufflepuffWizard && previousTraitActivated) || 
						(myWizard instanceof GryffindorWizard && previousTraitActivated && previousAllowedMoves>1))){
				if(myTournament.getMyTask() instanceof SecondTask)
				{
					if(newLocation.x+1<=9)
					if(myTournament.getSecondTask().getMap()[newLocation.x+1][newLocation.y] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.x-1>=0)
					if(myTournament.getSecondTask().getMap()[newLocation.x-1][newLocation.y] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.y+1<=9)
					if(myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y+1] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.y-1>=0)
					if(myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y-1] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
				}
				}
				
				
			/*	Timer myTimer = new Timer(1250,new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						updateView();
					}
				});
				myTimer.setInitialDelay(1250);
				myTimer.restart();*/

				updatePlayerInfo();
			} catch (OutOfBordersException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (InvalidTargetCellException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		
		else if(event.getKeyCode()== KeyEvent.VK_DOWN)
		{
			try {

				//Thread.sleep(500);
				Wizard myWizard = (Wizard) myTournament.getMyTask().getCurrentChamp();
				
				boolean previousTraitActivated = false;
				
				int previousAllowedMoves = myTournament.getMyTask().getAllowedMoves();
				
				if(myTournament.getMyTask().isTraitActivated())
					previousTraitActivated = true;
				
				Point newLocation = new Point(myWizard.getLocation().x + 1, myWizard.getLocation().y);
				
				ArrayList<Point> markedCells = myTournament.getFirstTask().getMarkedCells();
				
				if(newLocation.x<=9){
				if(myTournament.getMyTask().getMap()[newLocation.x][newLocation.y] instanceof CollectibleCell)
				{
					CollectibleCell collectibleCell = (CollectibleCell) (myTournament.getMyTask().getMap()[newLocation.x][newLocation.y]);
					Potion myPotion = (Potion) collectibleCell.getCollectible();
					JOptionPane.showMessageDialog(null, "You just picked up a " + myPotion.getName() + " potion with IP: " + myPotion.getAmount());
				}
				}
				
				myTournament.getMyTask().moveBackward();		
				
				updateAllPlayersInfo();
				
				if(myWizard instanceof HufflepuffWizard && previousTraitActivated || 
						(myWizard instanceof GryffindorWizard && previousTraitActivated && previousAllowedMoves>1))
					updateView();
				else if(myTournament.getMyTask() instanceof FirstTask)
					updateView(markedCells.get(0),markedCells.get(1));
				else
					updateView();
				
				if(!((myWizard instanceof HufflepuffWizard && previousTraitActivated) || 
						(myWizard instanceof GryffindorWizard && previousTraitActivated && previousAllowedMoves>1))){
				if(myTournament.getMyTask() instanceof SecondTask)
				{
					if(newLocation.x+1<=9)
					if(myTournament.getSecondTask().getMap()[newLocation.x+1][newLocation.y] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.x-1>=0)
					if(myTournament.getSecondTask().getMap()[newLocation.x-1][newLocation.y] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.y+1<=9)
					if(myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y+1] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.y-1>=0)
					if(myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y-1] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
				}
				}
				
				/*Timer myTimer = new Timer(1250,new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						updateView();
					}
				});
				myTimer.setInitialDelay(1250);
				myTimer.restart();*/

				updatePlayerInfo();
			} catch (OutOfBordersException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (InvalidTargetCellException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		
		else if(event.getKeyCode()== KeyEvent.VK_LEFT)
		{
			try {
				Wizard myWizard = (Wizard) myTournament.getMyTask().getCurrentChamp();
				
				boolean previousTraitActivated = false;
				
				int previousAllowedMoves = myTournament.getMyTask().getAllowedMoves();
				
				if(myTournament.getMyTask().isTraitActivated())
					previousTraitActivated = true;
				
				Point newLocation = new Point(myWizard.getLocation().x, myWizard.getLocation().y - 1);
				
				ArrayList<Point> markedCells = myTournament.getFirstTask().getMarkedCells();
				
				if(newLocation.y>=0){
				if(myTournament.getMyTask().getMap()[newLocation.x][newLocation.y] instanceof CollectibleCell)
				{
					CollectibleCell collectibleCell = (CollectibleCell) (myTournament.getMyTask().getMap()[newLocation.x][newLocation.y]);
					Potion myPotion = (Potion) collectibleCell.getCollectible();
					JOptionPane.showMessageDialog(null, "You just picked up a " + myPotion.getName() + " potion with IP: " + myPotion.getAmount());
				}
				}
				
				myTournament.getMyTask().moveLeft();
						
				updateAllPlayersInfo();
				
				if(myWizard instanceof HufflepuffWizard && previousTraitActivated || 
						(myWizard instanceof GryffindorWizard && previousTraitActivated && previousAllowedMoves>1))
					updateView();
				else if(myTournament.getMyTask() instanceof FirstTask || !(myTournament.getMyTask() instanceof FirstTask))
					updateView(markedCells.get(0),markedCells.get(1));
				else
					updateView();
				
				if(!(myTournament.getMyTask() instanceof FirstTask))
					updateView();
				
				if(!((myWizard instanceof HufflepuffWizard && previousTraitActivated) || 
						(myWizard instanceof GryffindorWizard && previousTraitActivated && previousAllowedMoves>1))){
				if(myTournament.getMyTask() instanceof SecondTask)
				{
					if(newLocation.x+1<=9)
					if(myTournament.getSecondTask().getMap()[newLocation.x+1][newLocation.y] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x+1][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.x-1>=0)
					if(myTournament.getSecondTask().getMap()[newLocation.x-1][newLocation.y] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x-1][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.y+1<=9)
					if(myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y+1] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y+1]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.y-1>=0)
					if(myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y-1] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y-1]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
				}
				}
				
				
				/*Timer myTimer = new Timer(1250,new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						updateView();
					}
				});
				myTimer.setInitialDelay(1250);
				myTimer.restart();*/

				updatePlayerInfo();
			} catch (OutOfBordersException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (InvalidTargetCellException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		
		else if(event.getKeyCode()== KeyEvent.VK_UP)
		{
			try {
				//dragonFire();
				Wizard myWizard = (Wizard) myTournament.getMyTask().getCurrentChamp();
				
				int previousAllowedMoves = myTournament.getMyTask().getAllowedMoves();
				
				boolean previousTraitActivated = false;
				if(myTournament.getMyTask().isTraitActivated())
					previousTraitActivated = true;
					
				Point newLocation = new Point(myWizard.getLocation().x - 1, myWizard.getLocation().y);
				
				ArrayList<Point> markedCells = myTournament.getFirstTask().getMarkedCells();
				
				if(newLocation.x>=0){
				if(myTournament.getMyTask().getMap()[newLocation.x][newLocation.y] instanceof CollectibleCell)
				{
					CollectibleCell collectibleCell = (CollectibleCell) (myTournament.getMyTask().getMap()[newLocation.x][newLocation.y]);
					Potion myPotion = (Potion) collectibleCell.getCollectible();
					JOptionPane.showMessageDialog(null, "You just picked up a " + myPotion.getName() + " potion with IP: " + myPotion.getAmount());
				}
				}
				
				myTournament.getMyTask().moveForward();
				
				updateAllPlayersInfo();
				
				if(myWizard instanceof HufflepuffWizard && previousTraitActivated || 
						(myWizard instanceof GryffindorWizard && previousTraitActivated && previousAllowedMoves>1))
					updateView();
				else if(myTournament.getMyTask() instanceof FirstTask)
					updateView(markedCells.get(0),markedCells.get(1));
				else
					updateView();
				
				if(!((myWizard instanceof HufflepuffWizard && previousTraitActivated) || 
						(myWizard instanceof GryffindorWizard && previousTraitActivated && previousAllowedMoves>1))){
				if(myTournament.getMyTask() instanceof SecondTask)
				{
					if(newLocation.x+1<=9)
					if(myTournament.getSecondTask().getMap()[newLocation.x+1][newLocation.y] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.x-1>=0)
					if(myTournament.getSecondTask().getMap()[newLocation.x-1][newLocation.y] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.y+1<=9)
					if(myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y+1] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
					if(newLocation.y-1>=0)
					if(myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y-1] instanceof ObstacleCell)
					{
						ObstacleCell obstacleCell = (ObstacleCell) (myTournament.getSecondTask().getMap()[newLocation.x][newLocation.y]);
						Merperson myMerperson = (Merperson) ((obstacleCell).getObstacle());
						JOptionPane.showMessageDialog(null, "You have recieved " + myMerperson.getDamage() + " damage from a merperson.");
					}
				}
				}
				
				updatePlayerInfo();
			} catch (OutOfBordersException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (InvalidTargetCellException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
		}
		else if(event.getKeyCode()==KeyEvent.VK_T)
		{
			if(myTournament.getMyTask() instanceof FirstTask)
				useTraitFirstTask();
			else if(myTournament.getMyTask() instanceof SecondTask)
				useTraitSecondTask();
			else
				useTraitThirdTask();
		}
		
		else if(event.getKeyCode()== KeyEvent.VK_S)
		{
			Wizard myWizard = (Wizard) myTournament.getMyTask().getCurrentChamp();
			String[] myArray = new String[myWizard.getSpells().size()];
			
			ArrayList<Point> markedCells = myTournament.getFirstTask().getMarkedCells();
			
			 for(int i = 0; i<myWizard.getSpells().size();i++)
         		myArray[i] = myWizard.getSpells().get(i).getName();
			 
			 String input = (String) JOptionPane.showInputDialog(null, "Choose Spell",
	                		"Select a spell to use", JOptionPane.QUESTION_MESSAGE, null, myArray, myArray[0]);
			 
			 for(int i = 0; i<myWizard.getSpells().size();i++)
			 {
				 //Spell wizardSpell = (Spell) s;
				 
				 if(myWizard.getSpells().get(i).getName().equals(input.toString()))
				 {
					 if(myWizard.getSpells().get(i) instanceof HealingSpell)
					 {
						 HealingSpell mySpell = (HealingSpell) myWizard.getSpells().get(i);
						 try {
							JOptionPane.showMessageDialog(null, mySpell.getHealingAmount() + " was added to your HP.");
							myTournament.getMyTask().castHealingSpell((HealingSpell) mySpell);
							if(myTournament.getMyTask() instanceof FirstTask)
								updateView(markedCells.get(0),markedCells.get(1));
							else
								updateView();
							updatePlayerInfo();
						} catch (InCooldownException e) {
							updateView();
							JOptionPane.showMessageDialog(null, e.getMessage());
						} catch (NotEnoughIPException e) {
							updateView();
							JOptionPane.showMessageDialog(null, e.getMessage());
						} catch (IOException e) {
							updateView();
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					 }
					 
					 if(myWizard.getSpells().get(i) instanceof DamagingSpell)
					 {
						 DamagingSpell mySpell = (DamagingSpell) myWizard.getSpells().get(i);
						 
						 String[] directionArray = {"Forward","Backward","Right","Left"};
						 
						 input = (String) JOptionPane.showInputDialog(null, "Select your target direction",
			                		"Choose Direction", JOptionPane.QUESTION_MESSAGE, null, directionArray, directionArray[0]);
						 
						 if(input.equals("Forward"))
							try {
								myTournament.getMyTask().castDamagingSpell(mySpell, Direction.FORWARD);
								if(myTournament.getMyTask() instanceof FirstTask)
									updateView(markedCells.get(0),markedCells.get(1));
								else
									updateView();
								updatePlayerInfo();
							} catch (InCooldownException | NotEnoughIPException | OutOfBordersException
									| InvalidTargetCellException | IOException e) {
								updateView();
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						else if(input.equals("Backward"))
							try {
								myTournament.getMyTask().castDamagingSpell(mySpell, Direction.BACKWARD);
								if(myTournament.getMyTask() instanceof FirstTask)
									updateView(markedCells.get(0),markedCells.get(1));
								else
									updateView();
								updatePlayerInfo();
							} catch (InCooldownException | NotEnoughIPException | OutOfBordersException
									| InvalidTargetCellException | IOException e) {
								updateView();
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						else if(input.equals("Right"))
							try {
								myTournament.getMyTask().castDamagingSpell(mySpell, Direction.RIGHT);
								if(myTournament.getMyTask() instanceof FirstTask)
									updateView(markedCells.get(0),markedCells.get(1));
								else
									updateView();
								updatePlayerInfo();
							} catch (InCooldownException | NotEnoughIPException | OutOfBordersException
									| InvalidTargetCellException | IOException e) {
								updateView();
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						else
							try {
								myTournament.getMyTask().castDamagingSpell(mySpell, Direction.LEFT);
								if(myTournament.getMyTask() instanceof FirstTask)
									updateView(markedCells.get(0),markedCells.get(1));
								else
									updateView();
								updatePlayerInfo();
							} catch (InCooldownException | NotEnoughIPException | OutOfBordersException
									| InvalidTargetCellException | IOException e) {
								updateView();
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						
					 }
				
					 
					 if(myWizard.getSpells().get(i) instanceof RelocatingSpell)
					 {
						 RelocatingSpell mySpell = (RelocatingSpell) myWizard.getSpells().get(i);
						 
						 String[] directionArray = {"Forward","Backward","Right","Left"};
						 
						 String targetDirection = (String) JOptionPane.showInputDialog(null, "Select your target direction",
			                		"Choose Direction", JOptionPane.QUESTION_MESSAGE, null, directionArray, directionArray[0]);
						 
						 String relocateDirection = (String) JOptionPane.showInputDialog(null, "Select the direction to relocate your target",
			                		"Choose Direction", JOptionPane.QUESTION_MESSAGE, null, directionArray, directionArray[0]);
						 
						 String[] rangeArray = {"2","3","4","5","6","7","8","9","10"};
						 
						 String range = (String) JOptionPane.showInputDialog(null, "Select the range to relocate your target",
			                		"Choose Range", JOptionPane.QUESTION_MESSAGE, null, rangeArray, rangeArray[0]);
						 
						 if(targetDirection.equals("Forward")){
							 if(relocateDirection.equals("Forward"))
								try {
									myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.FORWARD,Direction.FORWARD,Integer.parseInt(range));
									if(myTournament.getMyTask() instanceof FirstTask)
										updateView(markedCells.get(0),markedCells.get(1));
									else
										updateView();
									updatePlayerInfo();
								} catch (InCooldownException | NotEnoughIPException | NumberFormatException
										| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
										| IOException e) {
									updateView();
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
							else if(relocateDirection.equals("Backward"))
								try {
									myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.FORWARD,Direction.BACKWARD,Integer.parseInt(range));
									if(myTournament.getMyTask() instanceof FirstTask)
										updateView(markedCells.get(0),markedCells.get(1));
									else
										updateView();
									updatePlayerInfo();
								} catch (InCooldownException | NotEnoughIPException | NumberFormatException
										| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
										| IOException e) {
									updateView();
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
							else if(relocateDirection.equals("Right"))
								try {
									myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.FORWARD,Direction.RIGHT,Integer.parseInt(range));
									if(myTournament.getMyTask() instanceof FirstTask)
										updateView(markedCells.get(0),markedCells.get(1));
									else
										updateView();
									updatePlayerInfo();
								} catch (InCooldownException | NotEnoughIPException | NumberFormatException
										| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
										| IOException e) {
									updateView();
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
								
							else
								try {
									myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.FORWARD,Direction.LEFT,Integer.parseInt(range));
									if(myTournament.getMyTask() instanceof FirstTask)
										updateView(markedCells.get(0),markedCells.get(1));
									else
										updateView();
									updatePlayerInfo();
								} catch (InCooldownException | NotEnoughIPException | NumberFormatException
										| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
										| IOException e) {
									updateView();
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
						 }
						else if(targetDirection.equals("Backward"))
						{
							if(relocateDirection.equals("Forward"))
								try {
									myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.BACKWARD,Direction.FORWARD,Integer.parseInt(range));
									if(myTournament.getMyTask() instanceof FirstTask)
										updateView(markedCells.get(0),markedCells.get(1));
									else
										updateView();
									updatePlayerInfo();
								} catch (InCooldownException | NotEnoughIPException | NumberFormatException
										| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
										| IOException e) {
									updateView();
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
							else if(relocateDirection.equals("Backward"))
								try {
									myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.BACKWARD,Direction.BACKWARD,Integer.parseInt(range));
									if(myTournament.getMyTask() instanceof FirstTask)
										updateView(markedCells.get(0),markedCells.get(1));
									else
										updateView();
									updatePlayerInfo();
								} catch (InCooldownException | NotEnoughIPException | NumberFormatException
										| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
										| IOException e) {
									updateView();
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
								
							else if(relocateDirection.equals("Right"))
								try {
									myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.BACKWARD,Direction.RIGHT,Integer.parseInt(range));
									if(myTournament.getMyTask() instanceof FirstTask)
										updateView(markedCells.get(0),markedCells.get(1));
									else
										updateView();
									updatePlayerInfo();
								} catch (InCooldownException | NotEnoughIPException | NumberFormatException
										| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
										| IOException e) {
									updateView();
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
								
							else
								try {
									myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.BACKWARD,Direction.LEFT,Integer.parseInt(range));
									if(myTournament.getMyTask() instanceof FirstTask)
										updateView(markedCells.get(0),markedCells.get(1));
									else
										updateView();
									updatePlayerInfo();
								} catch (InCooldownException | NotEnoughIPException | NumberFormatException
										| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
										| IOException e) {
									updateView();
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
						}
							
						else if(targetDirection.equals("Right"))
						{
							if(relocateDirection.equals("Forward"))
							try {
								myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.RIGHT,Direction.FORWARD,Integer.parseInt(range));
								if(myTournament.getMyTask() instanceof FirstTask)
									updateView(markedCells.get(0),markedCells.get(1));
								else
									updateView();
								updatePlayerInfo();
							} catch (InCooldownException | NotEnoughIPException | NumberFormatException
									| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
									| IOException e) {
								updateView();
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						else if(relocateDirection.equals("Backward"))
							try {
								myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.RIGHT,Direction.BACKWARD,Integer.parseInt(range));
								if(myTournament.getMyTask() instanceof FirstTask)
									updateView(markedCells.get(0),markedCells.get(1));
								else
									updateView();
								updatePlayerInfo();
							} catch (InCooldownException | NotEnoughIPException | NumberFormatException
									| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
									| IOException e) {
								updateView();
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
							
						else if(relocateDirection.equals("Right"))
							try {
								myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.RIGHT,Direction.RIGHT,Integer.parseInt(range));
								if(myTournament.getMyTask() instanceof FirstTask)
									updateView(markedCells.get(0),markedCells.get(1));
								else
									updateView();
								updatePlayerInfo();
							} catch (InCooldownException | NotEnoughIPException | NumberFormatException
									| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
									| IOException e) {
								updateView();
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
							
						else
							try {
							myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.RIGHT,Direction.LEFT,Integer.parseInt(range));
							if(myTournament.getMyTask() instanceof FirstTask)
								updateView(markedCells.get(0),markedCells.get(1));
							else
								updateView();
							updatePlayerInfo();
						} catch (InCooldownException | NotEnoughIPException | NumberFormatException
								| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
								| IOException e) {
							updateView();
							JOptionPane.showMessageDialog(null, e.getMessage());
						}}
							
						else{
							if(relocateDirection.equals("Forward"))
								try {
									myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.LEFT,Direction.FORWARD,Integer.parseInt(range));
									if(myTournament.getMyTask() instanceof FirstTask)
										updateView(markedCells.get(0),markedCells.get(1));
									else
										updateView();
									updatePlayerInfo();
								} catch (InCooldownException | NotEnoughIPException | NumberFormatException
										| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
										| IOException e) {
									updateView();
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
						else if(relocateDirection.equals("Backward"))
							try {
								myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.LEFT,Direction.BACKWARD,Integer.parseInt(range));
								if(myTournament.getMyTask() instanceof FirstTask)
									updateView(markedCells.get(0),markedCells.get(1));
								else
									updateView();
								updatePlayerInfo();
							} catch (InCooldownException | NotEnoughIPException | NumberFormatException
									| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
									| IOException e) {
								updateView();
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
							
						else if(relocateDirection.equals("Right"))
							try {
								myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.LEFT,Direction.RIGHT,Integer.parseInt(range));
								if(myTournament.getMyTask() instanceof FirstTask)
									updateView(markedCells.get(0),markedCells.get(1));
								else
									updateView();
								updatePlayerInfo();
							} catch (InCooldownException | NotEnoughIPException | NumberFormatException
									| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
									| IOException e) {
								updateView();
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
							
						else
							try {
							myTournament.getMyTask().castRelocatingSpell(mySpell, Direction.LEFT,Direction.LEFT,Integer.parseInt(range));
							if(myTournament.getMyTask() instanceof FirstTask)
								updateView(markedCells.get(0),markedCells.get(1));
							else
								updateView();
							updatePlayerInfo();
						} catch (InCooldownException | NotEnoughIPException | NumberFormatException
								| OutOfBordersException | InvalidTargetCellException | OutOfRangeException
								| IOException e) {
							updateView();
							JOptionPane.showMessageDialog(null, e.getMessage());
						}}
							
					 }
				 }
			 }
			 	
				updatePlayerInfo();
		}
		
		else if(event.getKeyCode()== KeyEvent.VK_P)
		{
			Wizard myWizard = (Wizard) myTournament.getMyTask().getCurrentChamp();
            String[] myArray = new String[myWizard.getInventory().size()];
            
            for(int i = 0; i<myWizard.getInventory().size();i++)
            		myArray[i] = (String) myWizard.getInventory().get(i).getName();

            if(myArray.length>0){
            String input = (String) JOptionPane.showInputDialog(null, "Choose Potion",
            		"Select a potion to use", JOptionPane.QUESTION_MESSAGE, null, myArray, myArray[0]);
            
            for(Collectible p : myWizard.getInventory())
            {
            	Potion selectedPotion = (Potion) p;
            	if(input.equals(selectedPotion.getName())){
            		myTournament.getMyTask().usePotion(selectedPotion);
            		updatePlayerInfo();
            		updateAllPlayersInfo();
            		return;
            	}
            }
            
            }
            else
            {
            	JOptionPane.showMessageDialog(null,myWizard.getName() + "'s potions inventory is currently empty.");
            }
            
            updatePlayerInfo();
		}
		
		else if(event.getKeyCode()==KeyEvent.VK_X)
		{
			Wizard myWizard = (Wizard) myTournament.getMyTask().getCurrentChamp();
			
			String[] directionArray = {"Forward","Backward","Right","Left"};
			 
			String input = (String) JOptionPane.showInputDialog(null, "Select your target direction",
                		"Choose Direction", JOptionPane.QUESTION_MESSAGE, null, directionArray, directionArray[0]);
			 
			 if(input.equals("Forward"))
			 {
				 if(myWizard.getLocation().x-1>=0)
				 {
					 if(myTournament.getMyTask().getMap()[myWizard.getLocation().x-1][myWizard.getLocation().y]
							 instanceof ObstacleCell)
					 {
						 ObstacleCell myObstacleCell = (ObstacleCell) myTournament.getMyTask().getMap()[myWizard.getLocation().x-1][myWizard.getLocation().y];
						 if(myTournament.getMyTask() instanceof FirstTask || myTournament.getMyTask() instanceof ThirdTask)
							 JOptionPane.showMessageDialog(null, "HP: " +  myObstacleCell.getObstacle().getHp());
						 else if(myTournament.getMyTask() instanceof SecondTask)
							 JOptionPane.showMessageDialog(null, "HP: " +  myObstacleCell.getObstacle().getHp() + " DMG: " + ((Merperson)myObstacleCell.getObstacle()).getDamage());
					 }
					 
					 else
						 JOptionPane.showMessageDialog(null, "You were trying to select an non-obstacle cell.");
				 }
				 
				 else
					 JOptionPane.showMessageDialog(null, "You were trying to access out of borders.");
			 }
			 
			 else  if(input.equals("Backward"))
			 {
				 if(myWizard.getLocation().x+1<=9)
				 {
					 if(myTournament.getMyTask().getMap()[myWizard.getLocation().x+1][myWizard.getLocation().y]
							 instanceof ObstacleCell)
					 {
						 ObstacleCell myObstacleCell = (ObstacleCell) myTournament.getMyTask().getMap()[myWizard.getLocation().x-1][myWizard.getLocation().y];
						 if(myTournament.getMyTask() instanceof FirstTask || myTournament.getMyTask() instanceof ThirdTask)
							 JOptionPane.showMessageDialog(null, "HP: " +  myObstacleCell.getObstacle().getHp());
						 else if(myTournament.getMyTask() instanceof SecondTask)
							 JOptionPane.showMessageDialog(null, "HP: " +  myObstacleCell.getObstacle().getHp() + " DMG: " + ((Merperson)myObstacleCell.getObstacle()).getDamage());
					 }
					 
					 else
						 JOptionPane.showMessageDialog(null, "You were trying to select an non-obstacle cell.");
				 }
				 
				 else
					 JOptionPane.showMessageDialog(null, "You were trying to access out of borders.");
			 }
			 else  if(input.equals("Right"))
			 {
				 if(myWizard.getLocation().y+1<=9)
				 {
					 if(myTournament.getMyTask().getMap()[myWizard.getLocation().x][myWizard.getLocation().y+1]
							 instanceof ObstacleCell)
					 {
						 ObstacleCell myObstacleCell = (ObstacleCell) myTournament.getMyTask().getMap()[myWizard.getLocation().x-1][myWizard.getLocation().y];
						 if(myTournament.getMyTask() instanceof FirstTask || myTournament.getMyTask() instanceof ThirdTask)
							 JOptionPane.showMessageDialog(null, "HP: " +  myObstacleCell.getObstacle().getHp());
						 else if(myTournament.getMyTask() instanceof SecondTask)
							 JOptionPane.showMessageDialog(null, "HP: " +  myObstacleCell.getObstacle().getHp() + " DMG: " + ((Merperson)myObstacleCell.getObstacle()).getDamage());
					 }
					 
					 else
						 JOptionPane.showMessageDialog(null, "You were trying to select an non-obstacle cell.");
				 }
				 
				 else
					 JOptionPane.showMessageDialog(null, "You were trying to access out of borders.");
			 }
			 
			 else
			 {

				 if(myWizard.getLocation().y-1>=0)
				 {
					 if(myTournament.getMyTask().getMap()[myWizard.getLocation().x][myWizard.getLocation().y-1]
							 instanceof ObstacleCell)
					 {
						 ObstacleCell myObstacleCell = (ObstacleCell) myTournament.getMyTask().getMap()[myWizard.getLocation().x-1][myWizard.getLocation().y];
						 if(myTournament.getMyTask() instanceof FirstTask || myTournament.getMyTask() instanceof ThirdTask)
							 JOptionPane.showMessageDialog(null, "HP: " +  myObstacleCell.getObstacle().getHp());
						 else if(myTournament.getMyTask() instanceof SecondTask)
							 JOptionPane.showMessageDialog(null, "HP: " +  myObstacleCell.getObstacle().getHp() + " DMG: " + ((Merperson)myObstacleCell.getObstacle()).getDamage());
					 }
					 
					 else
						 JOptionPane.showMessageDialog(null, "You were trying to select an non-obstacle cell.");
				 }
				 
				 else
					 JOptionPane.showMessageDialog(null, "You were trying to access out of borders.");
			 }
		}
		
		
		
		GameView.validate();
		GameView.repaint();
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateFirstTaskView()
	{
		Cell[][] map = myTournament.getMyTask().getMap();
		
		if(myTournament.getMyTask().getChampions().size()==0 && myTournament.getFirstTask().getWinners().size()==0)
		{
			GameView.addKeyListener(null);
			GameView.setVisible(false);
			//firstTask.dispose();
			
			new LosingScreenGUI(new LosingScreen());
		}
		
		for(int i = 0; i<myTournament.getMyTask().getMap().length;i++)
		{
			for(int j = 0; j<myTournament.getMyTask().getMap().length;j++)
			{
				Cell myCell = map[i][j];
				JLabel labelCell = new JLabel();
				
				if(myCell instanceof EmptyCell && i==4 && j==4)
				{
					ImageIcon img = new ImageIcon("resources\\EggCell2.png");
			        labelCell.setIcon(img);
				}
				
				else if(myCell instanceof EmptyCell || myCell instanceof CollectibleCell)
				{
					 ImageIcon img = new ImageIcon("resources\\EmptyCell.png");
				        labelCell.setIcon(img);
				}
				
				else if(myCell instanceof ObstacleCell)
				{
				ImageIcon img = new ImageIcon("resources\\ObstacleCell.png");
				ObstacleCell myObstacleCell = (ObstacleCell) myCell;
				labelCell.setText("HP: " + myObstacleCell.getObstacle().getHp());
		        labelCell.setIcon(img);
		        }
						
				else if(myCell instanceof ChampionCell)
				{
					Wizard myWizard = (Wizard)(((ChampionCell) myCell).getChamp());
					
					if(myWizard instanceof GryffindorWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Gryffindor.png");
						labelCell.setIcon(img);
					}
					else if(myWizard instanceof SlytherinWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Slytherin.png");
						labelCell.setIcon(img);
					}
					
					else if(myWizard instanceof HufflepuffWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Hufflepuff.png");
						labelCell.setIcon(img);
					}
					
					else
					{
						ImageIcon img = new ImageIcon("resources\\Ravenclaw.png");
						labelCell.setIcon(img);
					}
					
					
				}
				
				GameView.addCell(labelCell);
				GameView.validate();
				GameView.revalidate();
				GameView.repaint();
				
			}
		}
	}
	
	public void updateSecondTaskView()
	{
		Cell[][] map = myTournament.getMyTask().getMap();
		
		if(myTournament.getMyTask().getChampions().size()==0 && myTournament.getSecondTask().getWinners().size()==0)
		{
			GameView.addKeyListener(null);
			GameView.setVisible(false);
			//firstTask.dispose();
			
			new LosingScreenGUI(new LosingScreen());
		}
		
		for(int i = 0; i<myTournament.getMyTask().getMap().length;i++)
		{
			for(int j = 0; j<myTournament.getMyTask().getMap().length;j++)
			{
				Cell myCell = map[i][j];
				JLabel labelCell = new JLabel();
				
				if(myCell instanceof EmptyCell || myCell instanceof CollectibleCell || myCell instanceof TreasureCell)
				{
					 ImageIcon img = new ImageIcon("resources\\EmptyCell.png");
				        labelCell.setIcon(img);
				}
				
				else if(myCell instanceof ObstacleCell)
				{
				ImageIcon img = new ImageIcon("resources\\Merperson.png");
		        labelCell.setIcon(img);
		        }
						
				else if(myCell instanceof ChampionCell)
				{
					Wizard myWizard = (Wizard)(((ChampionCell) myCell).getChamp());
					
					if(myWizard instanceof GryffindorWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Gryffindor.png");
						labelCell.setIcon(img);
					}
					else if(myWizard instanceof SlytherinWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Slytherin.png");
						labelCell.setIcon(img);
					}
					
					else if(myWizard instanceof HufflepuffWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Hufflepuff.png");
						labelCell.setIcon(img);
					}
					
					else
					{
						ImageIcon img = new ImageIcon("resources\\Ravenclaw.png");
						labelCell.setIcon(img);
					}	
					
				}
				
				GameView.addCell(labelCell);
				GameView.validate();
				GameView.revalidate();
				GameView.repaint();
				
			}
		}
	}
	
	public void updateThirdTaskView()
	{

		Cell[][] map = myTournament.getMyTask().getMap();
		
		for(int i = 0; i<myTournament.getMyTask().getMap().length;i++)
		{
			for(int j = 0; j<myTournament.getMyTask().getMap().length;j++)
			{
				Cell myCell = map[i][j];
				JLabel labelCell = new JLabel();
				
				if(myCell instanceof EmptyCell || myCell instanceof CollectibleCell || myCell instanceof CupCell)
				{
					 ImageIcon img = new ImageIcon("resources\\EmptyCell.png");
				        labelCell.setIcon(img);
				}
				
				else if(myCell instanceof ObstacleCell)
				{
				ImageIcon img = new ImageIcon("resources\\ObstacleCell.png");
		        labelCell.setIcon(img);
		        }
						
				else if(myCell instanceof ChampionCell)
				{
					Wizard myWizard = (Wizard)(((ChampionCell) myCell).getChamp());
					
					if(myWizard instanceof GryffindorWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Gryffindor.png");
		
						labelCell.setIcon(img);
					}
					else if(myWizard instanceof SlytherinWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Slytherin.png");
						labelCell.setIcon(img);
					}
					
					else if(myWizard instanceof HufflepuffWizard)
					{
						ImageIcon img = new ImageIcon("resources\\Hufflepuff.png");
						labelCell.setIcon(img);
					}
					
					else
					{
						ImageIcon img = new ImageIcon("resources\\Ravenclaw.png");
						labelCell.setIcon(img);
					}
					
					
				}
				
				else if(myCell instanceof WallCell)
				{
					ImageIcon img = new ImageIcon("resources\\WallCell.jpg");
					labelCell.setIcon(img);
				}
				
				GameView.addCell(labelCell);
				GameView.validate();
				GameView.revalidate();
				GameView.repaint();
				
			}
		}
	}
	
	public void updateView()
	{	
		
		GameView.getMap().removeAll();
		
		
		if(myTournament.getMyTask() instanceof FirstTask)
			updateFirstTaskView();
			
		else if(myTournament.getMyTask() instanceof SecondTask)
			updateSecondTaskView();
		
		else
			updateThirdTaskView();
	}

	public void useTraitFirstTask()
	{
		Wizard myWizard = (Wizard) myTournament.getMyTask().getCurrentChamp();
		
		ArrayList<Point> markedCells = myTournament.getFirstTask().getMarkedCells();
		
		if(!(myWizard instanceof SlytherinWizard))
		{
			if(myWizard instanceof GryffindorWizard)
				try {
					((GryffindorWizard) (myWizard)).useTrait();
					updatePlayerInfo();
				} catch (InCooldownException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			
			else if(myWizard instanceof HufflepuffWizard)
				try {
					((HufflepuffWizard) (myWizard)).useTrait();
				} catch (InCooldownException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			else
			{
				try {
					((RavenclawWizard) (myWizard)).useTrait();
					updateView(markedCells.get(0),markedCells.get(1));
				} catch (InCooldownException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
		
		else
		{
			String[] directionArray = {"Forward","Backward","Right","Left"};
			 
			String input = (String) JOptionPane.showInputDialog(null, "Choose Direction",
                		"Select your target direction", JOptionPane.QUESTION_MESSAGE, null, directionArray, directionArray[0]);
			
			if(input.equals("Forward"))
			{
				try {
					((SlytherinWizard) (myWizard)).setTraitDirection(Direction.FORWARD);
					((SlytherinWizard) (myWizard)).useTrait();
					if(myTournament.getMyTask() instanceof FirstTask)
						updateView(markedCells.get(0),markedCells.get(1));
					else
						updateView();
						
				} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
						| IOException e) {
					updateView();
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
			
			else if(input.equals("Backward"))
			{
					try {
						((SlytherinWizard) (myWizard)).setTraitDirection(Direction.BACKWARD);
						((SlytherinWizard) (myWizard)).useTrait();
						if(myTournament.getMyTask() instanceof FirstTask)
							updateView(markedCells.get(0),markedCells.get(1));
						else
							updateView();
					} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
							| IOException e) {
						updateView();
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
			}
			
			else if(input.equals("Right"))
			{
					try {
						((SlytherinWizard) (myWizard)).setTraitDirection(Direction.RIGHT);
						((SlytherinWizard) (myWizard)).useTrait();
						if(myTournament.getMyTask() instanceof FirstTask)
							updateView(markedCells.get(0),markedCells.get(1));
						else
							updateView();
					} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
							| IOException e) {
						updateView();
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
			}
			
			else
			{
					try {
						((SlytherinWizard) (myWizard)).setTraitDirection(Direction.LEFT);
						((SlytherinWizard) (myWizard)).useTrait();
						if(myTournament.getMyTask() instanceof FirstTask)
							updateView(markedCells.get(0),markedCells.get(1));
						else
							updateView();
					} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
							| IOException e) {
						updateView();
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
			}
			
			
		}
	}

	public void useTraitSecondTask()
	{
		Wizard myWizard = (Wizard) myTournament.getMyTask().getCurrentChamp();
		
		if(!(myWizard instanceof SlytherinWizard))
		{
			if(myWizard instanceof GryffindorWizard)
				try {
					((GryffindorWizard) (myWizard)).useTrait();
					updatePlayerInfo();
				} catch (InCooldownException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			
			else if(myWizard instanceof HufflepuffWizard)
				try {
					((HufflepuffWizard) (myWizard)).useTrait();
				} catch (InCooldownException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			else
			{
				try {
					ArrayList<Direction> target = (ArrayList<Direction>) myTournament.getSecondTask().onRavenclawTrait();
					String myString = "";
					for(int i = 0; i<target.size();i++)
					{
						if(target.get(i)==Direction.FORWARD)
							myString+="Up ";
						if(target.get(i)==Direction.BACKWARD)
							myString+="Down ";
						if(target.get(i)==Direction.RIGHT)
							myString+="Right ";
						if(target.get(i)==Direction.LEFT)
							myString+="Left ";
					}
					
					JOptionPane.showMessageDialog(null, "Target is at: " + myString);
				} catch (InCooldownException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
		
		else
		{
			String[] directionArray = {"Forward","Backward","Right","Left"};
			 
			String input = (String) JOptionPane.showInputDialog(null, "Choose Direction",
                		"Select your target direction", JOptionPane.QUESTION_MESSAGE, null, directionArray, directionArray[0]);
			
			if(input.equals("Forward"))
			{
				try {
					((SlytherinWizard) (myWizard)).setTraitDirection(Direction.FORWARD);
					((SlytherinWizard) (myWizard)).useTrait();
				} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
						| IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
			
			else if(input.equals("Backward"))
			{
					try {
						((SlytherinWizard) (myWizard)).setTraitDirection(Direction.BACKWARD);
						((SlytherinWizard) (myWizard)).useTrait();
					} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
							| IOException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
			}
			
			else if(input.equals("Right"))
			{
					try {
						((SlytherinWizard) (myWizard)).setTraitDirection(Direction.RIGHT);
						((SlytherinWizard) (myWizard)).useTrait();
					} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
							| IOException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
			}
			
			else
			{
					try {
						((SlytherinWizard) (myWizard)).setTraitDirection(Direction.LEFT);
						((SlytherinWizard) (myWizard)).useTrait();
					} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
							| IOException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
			}
			
			updateView();
			
		}
	}
	
	public void useTraitThirdTask()
	{
		Wizard myWizard = (Wizard) myTournament.getMyTask().getCurrentChamp();
		
		ArrayList<Point> markedCells = myTournament.getFirstTask().getMarkedCells();
		
		if(!(myWizard instanceof SlytherinWizard))
		{
			if(myWizard instanceof GryffindorWizard)
				try {
					((GryffindorWizard) (myWizard)).useTrait();
					updatePlayerInfo();
				} catch (InCooldownException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			
			else if(myWizard instanceof HufflepuffWizard)
				try {
					((HufflepuffWizard) (myWizard)).useTrait();
				} catch (InCooldownException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			else
			{
				try {
					ArrayList<Direction> target = (ArrayList<Direction>) myTournament.getThirdTask().onRavenclawTrait();
					String myString = "";
					for(int i = 0; i<target.size();i++)
					{
						if(target.get(i)==Direction.FORWARD)
							myString+="Up ";
						if(target.get(i)==Direction.BACKWARD)
							myString+="Down ";
						if(target.get(i)==Direction.RIGHT)
							myString+="Right ";
						if(target.get(i)==Direction.LEFT)
							myString+="Left ";
					}
				} catch (InCooldownException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
		
		else
		{
			String[] directionArray = {"Forward","Backward","Right","Left"};
			 
			String input = (String) JOptionPane.showInputDialog(null, "Choose Direction",
                		"Select your target direction", JOptionPane.QUESTION_MESSAGE, null, directionArray, directionArray[0]);
			
			if(input.equals("Forward"))
			{
				try {
					((SlytherinWizard) (myWizard)).setTraitDirection(Direction.FORWARD);
					((SlytherinWizard) (myWizard)).useTrait();
				} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
						| IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
			
			else if(input.equals("Backward"))
			{
					try {
						((SlytherinWizard) (myWizard)).setTraitDirection(Direction.BACKWARD);
						((SlytherinWizard) (myWizard)).useTrait();
					} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
							| IOException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
			}
			
			else if(input.equals("Right"))
			{
					try {
						((SlytherinWizard) (myWizard)).setTraitDirection(Direction.RIGHT);
						((SlytherinWizard) (myWizard)).useTrait();
					} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
							| IOException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
			}
			
			else
			{
					try {
						((SlytherinWizard) (myWizard)).setTraitDirection(Direction.LEFT);
						((SlytherinWizard) (myWizard)).useTrait();
					} catch (InCooldownException | OutOfBordersException | InvalidTargetCellException
							| IOException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
			}
			
			updateView();
			
			
		}
	}

}
