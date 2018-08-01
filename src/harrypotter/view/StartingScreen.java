package harrypotter.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import harrypotter.controller.StartingScreenGUI;
import harrypotter.model.character.Champion;
import harrypotter.model.character.GryffindorWizard;
import harrypotter.model.character.HufflepuffWizard;
import harrypotter.model.character.RavenclawWizard;
import harrypotter.model.character.SlytherinWizard;
import harrypotter.model.character.Wizard;
import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;
import harrypotter.model.tournament.Tournament;


public class StartingScreen extends JFrame {
	
	private JLabel playerNameLabel;
	private JTextField playerNametextField;
	private JLabel wizardLabel;
	private JComboBox wizardList;
	private JButton proceed;
	private JLabel spellsLabel;
	private JCheckBox c1,c2;
	protected Tournament myTournament; // 3amelha getter..
	private ArrayList<JButton> selectSpell;
	private JComboBox chooseSpells1;
	private JComboBox chooseSpells2;
	private JComboBox chooseSpells3;
	private JButton enterGame;
	
	public StartingScreen() throws IOException
	{
		myTournament = new Tournament();
		// ----------------
		setSize(1820,1020);
		setLocation(50,5);
		setResizable(false);
		setTitle("Harry Potter: The Return of the Triwizard Tournament");
		setContentPane(new JLabel(new ImageIcon("resources\\mywallpaper.png")));
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Code starts here
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\HarryPotterFont.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		Font font = new Font("Harry P",Font.BOLD,36);
		
		
		JLabel spellsMessage = new JLabel("Please choose 3 spells: ");
		spellsMessage.setFont(font);
		selectSpell = new ArrayList<JButton>();
		
		String[] spellArray = new String[myTournament.getSpells().size()];
		
		for(int i = 0; i<myTournament.getSpells().size();i++)
			spellArray[i] = myTournament.getSpells().get(i).getName();
		
		chooseSpells1 = new JComboBox(spellArray);
		chooseSpells2 = new JComboBox(spellArray);
		chooseSpells3 = new JComboBox(spellArray);
		chooseSpells1.setBounds(220,530,160,25);
		chooseSpells2.setBounds(220,560,160,25);
		chooseSpells3.setBounds(220,590,160,25);
		chooseSpells1.setBackground(Color.WHITE);
		chooseSpells2.setBackground(Color.WHITE);
		chooseSpells3.setBackground(Color.WHITE);
		getContentPane().add(chooseSpells1);
		getContentPane().add(chooseSpells2);
		getContentPane().add(chooseSpells3);
		
		playerNameLabel = new JLabel("Player " + 1 + " name: ");
		playerNametextField= new JTextField();
		wizardLabel = new JLabel("Please choose your wizard: ");
		String[] wizardStrings = {"Gryffindor","Hufflepuff","Ravenclaw","Slytherin"};
		wizardList = new JComboBox(wizardStrings);
	
		
		enterGame = new JButton("Start Game");
		enterGame.setFont(font);
		enterGame.setBackground(Color.WHITE);
		proceed = new JButton("Next");
		proceed.setFont(font);
		proceed.setBackground(Color.WHITE);
		
		playerNameLabel.setFont(font);
		wizardLabel.setFont(font);
		
		getContentPane().setLayout(null);
		spellsMessage.setBounds(50,350,300,300);
		getContentPane().add(spellsMessage);
		//getContentPane().add(enterGame);
		playerNameLabel.setBounds(40, 250, 250, 200);
		playerNametextField.setBounds(230,340,100,25);
		getContentPane().add(playerNametextField);
		getContentPane().add(playerNameLabel);
		wizardLabel.setBounds(50,300,390,250);
		wizardList.setBounds(380,410,100,25);
		wizardList.setBackground(Color.WHITE);
		getContentPane().add(wizardLabel);
		getContentPane().add(wizardList);
		proceed.setBounds(50,740,130,35);
		enterGame.setBounds(190,740,130,35);
		getContentPane().add(proceed);
		setVisible(true);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        JOptionPane.showMessageDialog(null, "What?! I thought we were friends!");
		        	System.exit(0);		            
		        }
		});
		
		ActionListener actionListener = new StartingScreenGUI(this);
    	proceed.addActionListener(actionListener);
		
	}
	
	
	public Tournament getMyTournament() {
		return myTournament;
	}
	
	public void addSpell(JButton product)
	{
		if((int)selectSpell.size()<3)
			selectSpell.add(product);
	}
	
	
	
	public JLabel getPlayerNameLabel() {
		return playerNameLabel;
	}


	public void setPlayerNameLabel(JLabel playerNameLabel) {
		this.playerNameLabel = playerNameLabel;
	}


	public JTextField getPlayerNametextField() {
		return playerNametextField;
	}


	public void setPlayerNametextField(JTextField playerNametextField) {
		this.playerNametextField = playerNametextField;
	}


	public JLabel getWizardLabel() {
		return wizardLabel;
	}


	public void setWizardLabel(JLabel wizardLabel) {
		this.wizardLabel = wizardLabel;
	}


	public JComboBox getWizardList() {
		return wizardList;
	}


	public void setWizardList(JComboBox wizardList) {
		this.wizardList = wizardList;
	}


	public JButton getProceed() {
		return proceed;
	}


	public void setProceed(JButton proceed) {
		this.proceed = proceed;
	}


	public JLabel getSpellsLabel() {
		return spellsLabel;
	}


	public void setSpellsLabel(JLabel spellsLabel) {
		this.spellsLabel = spellsLabel;
	}


	public JCheckBox getC1() {
		return c1;
	}


	public void setC1(JCheckBox c1) {
		this.c1 = c1;
	}


	public JCheckBox getC2() {
		return c2;
	}


	public void setC2(JCheckBox c2) {
		this.c2 = c2;
	}


	public ArrayList<JButton> getSelectSpell() {
		return selectSpell;
	}


	public void setSelectSpell(ArrayList<JButton> selectSpell) {
		this.selectSpell = selectSpell;
	}


	public JComboBox getChooseSpells1() {
		return chooseSpells1;
	}


	public void setChooseSpells1(JComboBox chooseSpells1) {
		this.chooseSpells1 = chooseSpells1;
	}


	public JComboBox getChooseSpells2() {
		return chooseSpells2;
	}


	public void setChooseSpells2(JComboBox chooseSpells2) {
		this.chooseSpells2 = chooseSpells2;
	}


	public JComboBox getChooseSpells3() {
		return chooseSpells3;
	}


	public void setChooseSpells3(JComboBox chooseSpells3) {
		this.chooseSpells3 = chooseSpells3;
	}


	public void setMyTournament(Tournament myTournament) {
		this.myTournament = myTournament;
	}


	public JButton getEnterGame() {
		return enterGame;
	}


	public void setEnterGame(JButton enterGame) {
		this.enterGame = enterGame;
	}
	
}
