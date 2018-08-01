package harrypotter.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
import harrypotter.view.GameView;
import harrypotter.view.StartingScreen;

public class StartingScreenGUI implements ActionListener {
	
      private StartingScreen startingScreen;
      
      public StartingScreenGUI(StartingScreen startingScreen) throws IOException
      {
    	this.startingScreen = startingScreen;
    	
    }	
      @Override
      public void actionPerformed(ActionEvent e) {
	    	
	    	
	    	if(startingScreen.getPlayerNametextField().getText().length()==0){
	    		JOptionPane.showMessageDialog(null, "You cannot proceed without entering a name.");
	    		return;
	    	}
	    	
	    	if(startingScreen.getChooseSpells1().getSelectedItem()==startingScreen.getChooseSpells2().getSelectedItem())
	    	{
	    		JOptionPane.showMessageDialog(null,"You cannot proceed with two or more of the same spell.");
	    		return;
	    	}
	    	if(startingScreen.getChooseSpells2().getSelectedItem()==startingScreen.getChooseSpells3().getSelectedItem())
	    	{
	    		JOptionPane.showMessageDialog(null,"You cannot proceed with two or more of the same spell.");
	    		return;
	    	}
	    	if(startingScreen.getChooseSpells1().getSelectedItem()==startingScreen.getChooseSpells3().getSelectedItem())
	    	{
	    		JOptionPane.showMessageDialog(null,"You cannot proceed with two or more of the same spell.");
	    		return;
	    	}
	    	
	    	Wizard currentWizard;
	    	
	    	if(startingScreen.getWizardList().getSelectedItem().toString()=="Gryffindor"){
	    		currentWizard = new GryffindorWizard(startingScreen.getPlayerNametextField().getText());
	    		startingScreen.getMyTournament().getChampions().add((Champion) currentWizard);
	    	}
	    	
	    	else if (startingScreen.getWizardList().getSelectedItem().toString()=="Hufflepuff"){
	    		currentWizard = new HufflepuffWizard(startingScreen.getPlayerNametextField().getText());
	    		startingScreen.getMyTournament().getChampions().add((Champion) currentWizard);
	    	}
	    	
	    	else if (startingScreen.getWizardList().getSelectedItem().toString() == "Ravenclaw"){
	    		currentWizard = new RavenclawWizard(startingScreen.getPlayerNametextField().getText());
	    		startingScreen.getMyTournament().getChampions().add((Champion) currentWizard);
	    	}
	    	
	    	else{
	    		currentWizard = new SlytherinWizard(startingScreen.getPlayerNametextField().getText());
	    		startingScreen.getMyTournament().getChampions().add((Champion) currentWizard);
	    		}
	    	
	    	for(Spell s : startingScreen.getMyTournament().getSpells())
	    	{
	    	
	    		// Probably I'll need to create a new instance of that spell.
	    		
	    		
	    		if(s.getName()==startingScreen.getChooseSpells1().getSelectedItem().toString()){
	    			
	    			Spell wizardSpell;
	    			
	    			if(s instanceof DamagingSpell)
	    				wizardSpell = new DamagingSpell(s.getName(),s.getCost(),s.getDefaultCooldown(),(((DamagingSpell) s).getDamageAmount()));
	    			else if(s instanceof RelocatingSpell)
	    				wizardSpell = new RelocatingSpell(s.getName(),s.getCost(),s.getDefaultCooldown(),(((RelocatingSpell) s).getRange()));
	    			else
	    				wizardSpell = new HealingSpell(s.getName(),s.getCost(),s.getDefaultCooldown(),(((HealingSpell) s).getHealingAmount()));
	    			
	    			currentWizard.getSpells().add(wizardSpell);
	    		}
	    		
	    		if(s.getName()==startingScreen.getChooseSpells2().getSelectedItem().toString()){
	    			
	    			Spell wizardSpell;
	    			
	    			if(s instanceof DamagingSpell)
	    				wizardSpell = new DamagingSpell(s.getName(),s.getCost(),s.getDefaultCooldown(),(((DamagingSpell) s).getDamageAmount()));
	    			else if(s instanceof RelocatingSpell)
	    				wizardSpell = new RelocatingSpell(s.getName(),s.getCost(),s.getDefaultCooldown(),(((RelocatingSpell) s).getRange()));
	    			else
	    				wizardSpell = new HealingSpell(s.getName(),s.getCost(),s.getDefaultCooldown(),(((HealingSpell) s).getHealingAmount()));
	    			
	    			currentWizard.getSpells().add(wizardSpell);
	    			
	    		}
	    		
	    		if(s.getName()==startingScreen.getChooseSpells3().getSelectedItem().toString()){
	    			
	    			Spell wizardSpell;
	    			
	    			if(s instanceof DamagingSpell)
	    				wizardSpell = new DamagingSpell(s.getName(),s.getCost(),s.getDefaultCooldown(),(((DamagingSpell) s).getDamageAmount()));
	    			else if(s instanceof RelocatingSpell)
	    				wizardSpell = new RelocatingSpell(s.getName(),s.getCost(),s.getDefaultCooldown(),(((RelocatingSpell) s).getRange()));
	    			else
	    				wizardSpell = new HealingSpell(s.getName(),s.getCost(),s.getDefaultCooldown(),(((HealingSpell) s).getHealingAmount()));
	    			
	    			currentWizard.getSpells().add(wizardSpell);
	    		}
	    		
	    	}
	    	
	    	startingScreen.getPlayerNametextField().setText("");
	    	int PlayerNo = startingScreen.getMyTournament().getChampions().size()+1;
	    	startingScreen.getPlayerNameLabel().setText("Player " + PlayerNo + " name: ");
	    	
	    	if(startingScreen.getMyTournament().getChampions().size()==4){
				try {
					startingScreen.getMyTournament().beginTournament();
					startingScreen.setVisible(false);
					startingScreen.dispose();
					try {
						new GameViewGUI(new GameView(startingScreen.getMyTournament()),startingScreen.getMyTournament());
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
	    	}
	    	
	    	System.out.println(startingScreen.getMyTournament().getChampions().size());
	    }

      public static void main(String[] args) throws IOException {
    	  StartingScreen startingScreen = new StartingScreen();
    		new StartingScreenGUI(startingScreen);
    	}
      
}
