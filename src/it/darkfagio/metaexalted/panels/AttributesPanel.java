package it.darkfagio.metaexalted.panels;

import it.darkfagio.metaexalted.model.Abilities;
import it.darkfagio.metaexalted.simple.GUIAttribute;
import it.darkfagio.metaexalted.utils.Utils;
import it.darkfagio.metaexalted.wizard.FirstStepCreationWizard;
import it.darkfagio.metaexalted.wizard.SingleAbilityModWizard;
import it.darkfagio.metaexalted.wizard.SingleAttributeModWizard;
import it.darkfagio.metaexalted.wizard.SingleSpecialtyWizard;
import it.darkfagio.utils.gui.DialogPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AttributesPanel extends JPanel {
	private static final long serialVersionUID = 5966189533190973957L;
	
	private class ButtonDialogListener implements ActionListener {
		
		AttributesPanel p;
		DialogPanel component;
		
		public ButtonDialogListener(AttributesPanel parent, DialogPanel component) {
			p = parent;
			this.component = component;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog dialog = new JDialog();
			dialog.add(component);
			component.setDialog(dialog);
			dialog.setMinimumSize(component.getMinimumSize());
			dialog.pack();
			dialog.setVisible(true);
			dialog.setModal(true);
			dialog.addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent arg0) {}
				
				@Override
				public void windowIconified(WindowEvent arg0) {}
				
				@Override
				public void windowDeiconified(WindowEvent arg0) {}
				
				@Override
				public void windowDeactivated(WindowEvent arg0) {}
				
				@Override
				public void windowClosing(WindowEvent arg0) {}
				
				@Override
				public void windowClosed(WindowEvent arg0) {
					p.remodel();
				}
				
				@Override
				public void windowActivated(WindowEvent arg0) {}
			});
		}
	}
		
	
	MainPanel main;
	it.darkfagio.metaexalted.model.PlayCharacter c;
	GUIAttribute[] attribute, ability;
	
	public AttributesPanel(MainPanel mainPanel, it.darkfagio.metaexalted.model.PlayCharacter c) {
		this.c = c;
		main = mainPanel;

		remodel();
	}
	
	public void notifyChange() {
		main.notifyChange();
	}

	public void remodel() {
		
		removeAll();

		setLayout(new BorderLayout());
		
		attribute = new GUIAttribute[9];
		ability = new GUIAttribute[25];
				
		JPanel attributesPanel = new JPanel();
		attributesPanel.setBorder(BorderFactory.createTitledBorder("Attributi"));
		attributesPanel.setLayout(new GridLayout(3, 3, 40 , 10));
		
		for (int i = 0; i < 9; ++i) {
			attribute[i] = new GUIAttribute(Utils.attribute[i], c.atts.getAttribute(i));
		}

		for (int i = 0; i < 25; ++i) {
			ability[i] = new GUIAttribute(Utils.ability[i], c.abils.getAbility(i),
					c.getAbils().getSpecNames()[i],
					c.getAbils().getSpecArray()[i]);
		}

		for (int i = 0; i < 3; ++i) { 
			attributesPanel.add(attribute[i]);
			attributesPanel.add(attribute[i + 3]);
			attributesPanel.add(attribute[i + 6]);
		}
		
		add(attributesPanel, BorderLayout.NORTH);
		

	
		JPanel abilities = new JPanel();
		abilities.setBorder(BorderFactory.createTitledBorder("Abilità"));
		abilities.setLayout(new GridLayout(2, 1, 0, 30));
		
		JPanel abilities1 = new JPanel();
		abilities1.setLayout(new GridLayout(5, 3, 40, 10));

		JPanel abilities2 = new JPanel();
		abilities2.setLayout(new GridLayout(5, 3, 40, 10));
		
		
		for (int i = 0; i < 5; ++i) { 
			abilities1.add(ability[i]);
			abilities1.add(ability[i + 5]);
			abilities1.add(ability[i + 10]);
			
			abilities2.add(ability[i + 15]);
			abilities2.add(ability[i + 20]);
			abilities2.add(c.getAbils().getOtherSpecs().length > i ?
					new GUIAttribute(Utils.ability[c.getAbils().getOtherSpecAb(i)] + " - " + c.getAbils().getOtherSpecs(i), c.getAbils().getCraftSpecsBonus(i))
							: new JPanel());
		}		
		abilities.add(abilities1);
		abilities.add(abilities2);
		
		add(abilities, BorderLayout.CENTER);

		
		JPanel options = new JPanel();
		options.setBorder(BorderFactory.createTitledBorder("Opzioni aggiuntive"));
		options.setLayout(new GridLayout(2, 4, 10, 30));
		
		JButton modAttribute = new JButton("Modifica un attributo...");
		JButton modAbility = new JButton("Modifica un'abilità...");
		JButton addCraftSpecialty = new JButton(" Aggiungi Specialità...");
		JButton wizard = new JButton("Wizard di setup");
		
		modAbility.addActionListener(new ButtonDialogListener(this, new SingleAbilityModWizard(this, c)));
		modAttribute.addActionListener(new ButtonDialogListener(this, new SingleAttributeModWizard(this, c)));
		addCraftSpecialty.addActionListener(new ButtonDialogListener(this, new SingleSpecialtyWizard(this, c)));
		wizard.addActionListener(new ButtonDialogListener(this, new FirstStepCreationWizard(this, c)));
		
		options.add(modAttribute);
		options.add(modAbility);
		options.add(addCraftSpecialty);
		options.add(wizard);
		
		options.add(new JLabel("Abilità Combattimento Fisico"));
		JComboBox<String> physCombat = new JComboBox<String>();
		physCombat.addItem(Utils.ability[Abilities.MELEE]);
		physCombat.addItem(Utils.ability[Abilities.ARCHERY]);
		physCombat.addItem(Utils.ability[Abilities.THROWN]);
		physCombat.addItem(Utils.ability[Abilities.MARTIAL_ARTS]);
		physCombat.addItem(Utils.ability[Abilities.ATHLETICS]);
		physCombat.addItem(Utils.ability[Abilities.CRAFT]);
		physCombat.addItem(Utils.ability[Abilities.PERFORMANCE]);
		physCombat.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ab = (String) ((JComboBox<String>) arg0.getSource()).getSelectedItem();
				if (!ab.equalsIgnoreCase(Utils.ability[c.getAbils().getPhysicalCombatAbility()]))
					main.notifyChange();
				if (ab.equalsIgnoreCase(Utils.ability[Abilities.MELEE]))
					c.getAbils().setPhysicalCombatAbility(Abilities.MELEE);
				if (ab.equalsIgnoreCase(Utils.ability[Abilities.ARCHERY]))
					c.getAbils().setPhysicalCombatAbility(Abilities.ARCHERY);
				if (ab.equalsIgnoreCase(Utils.ability[Abilities.THROWN]))
					c.getAbils().setPhysicalCombatAbility(Abilities.THROWN);
				if (ab.equalsIgnoreCase(Utils.ability[Abilities.MARTIAL_ARTS]))
					c.getAbils().setPhysicalCombatAbility(Abilities.MARTIAL_ARTS);
				if (ab.equalsIgnoreCase(Utils.ability[Abilities.ATHLETICS]))
					c.getAbils().setPhysicalCombatAbility(Abilities.ATHLETICS);
				if (ab.equalsIgnoreCase(Utils.ability[Abilities.CRAFT]))
					c.getAbils().setPhysicalCombatAbility(Abilities.CRAFT);
				if (ab.equalsIgnoreCase(Utils.ability[Abilities.PERFORMANCE]))
					c.getAbils().setPhysicalCombatAbility(Abilities.PERFORMANCE);
			}
		});
		short s = c.getAbils().getPhysicalCombatAbility();
		physCombat.setSelectedIndex( s == Abilities.MELEE ? 0 :
									s == Abilities.THROWN ? 2 :
									s == Abilities.ARCHERY? 1 : 
									s == Abilities.ATHLETICS ? 4 :
									s == Abilities.CRAFT? 5 :
									s == Abilities.PERFORMANCE? 6 : 3);
		options.add(physCombat);
		
		
		options.add(new JLabel("Abilità Combattimento Sociale"));
		JComboBox<String> socCombat = new JComboBox<String>();
		socCombat.addItem(Utils.ability[Abilities.PERFORMANCE]);
		socCombat.addItem(Utils.ability[Abilities.PRESENCE]);
		socCombat.addItem(Utils.ability[Abilities.INVESTIGATION]);
		socCombat.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ab = (String) ((JComboBox<String>) arg0.getSource()).getSelectedItem();
				if (!ab.equalsIgnoreCase(Utils.ability[c.getAbils().getMentalCombatAbility()]))
					main.notifyChange();
				if (ab.equalsIgnoreCase(Utils.ability[Abilities.PERFORMANCE]))
					c.getAbils().setMentalCombatAbility(Abilities.PERFORMANCE);
				if (ab.equalsIgnoreCase(Utils.ability[Abilities.PRESENCE]))
					c.getAbils().setMentalCombatAbility(Abilities.PRESENCE);
				if (ab.equalsIgnoreCase(Utils.ability[Abilities.INVESTIGATION]))
					c.getAbils().setMentalCombatAbility(Abilities.INVESTIGATION);
			}
		});
		s = c.getAbils().getMentalCombatAbility();
		socCombat.setSelectedIndex( s == Abilities.PERFORMANCE ? 0 :
									s == Abilities.PRESENCE ? 1 : 2);
		options.add(socCombat);
		add(options, BorderLayout.SOUTH);
		
		invalidate();
		validate();
	}
	
}
