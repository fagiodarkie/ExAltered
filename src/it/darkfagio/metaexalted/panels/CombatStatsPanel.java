package it.darkfagio.metaexalted.panels;

import it.darkfagio.metaexalted.model.Abilities;
import it.darkfagio.metaexalted.saw.SolveAttackWizard;
import it.darkfagio.metaexalted.utils.Utils;
import it.darkfagio.utils.gui.DialogPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CombatStatsPanel extends JPanel {
	private static final long serialVersionUID = 380603886220260557L;
	
	public static final String[] combatAbilities = {"Mischia", "Tiro con l'Arco", "Lancio", "Arti Marziali",
		"Atletica", "Mestiere", "Espressione", "Investigazione"};
	public static final int[] abilMap = {Abilities.MELEE, Abilities.ARCHERY, Abilities.THROWN, Abilities.MARTIAL_ARTS,
		Abilities.ATHLETICS, Abilities.CRAFT, Abilities.PERFORMANCE, Abilities.INVESTIGATION};
	public static final String[] defenses = {"Schivata Fisica", "Parata Fisica", "Schivata Mentale", "Parata Mentale (CAR)", "Parata Mentale (MAN)"},
			precisions = {"Attacco in mischia (FOR)", "Attacco in mischia (DES)", "Attacco a distanza", "Attacco Mentale (CAR)", "Attacco Mentale (MAN)"};


	private class ButtonDialogListener implements ActionListener {
		
		CombatStatsPanel p;
		DialogPanel component;
		
		public ButtonDialogListener(CombatStatsPanel parent, DialogPanel component) {
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

	private MainPanel main;
	private it.darkfagio.metaexalted.model.PlayCharacter c;
	
	private String[] phLabel = {"Precisione in Mischia (Destrezza)", "Precisione in Mischia (Forza)",
			"Precisione a Distanza", "VD di Parata", "VD di Schivata"};
	private String[] mLabel = {"Precisione (Carisma)", "Precisione (Manipolazione)",
			"VD di Parata (Carisma)", "VD di Parata (Manipolazione)", "VD di Schivata"}; 
	
	public CombatStatsPanel(MainPanel mainPanel, it.darkfagio.metaexalted.model.PlayCharacter c) {
		this.c = c;
		main = mainPanel;
		remodel();
	}
	
	public void remodel() {
		setLayout(new BorderLayout());
		
		// Questo pannello è semplicemente di consultazione e dipende da altre variabili di c: salute, attributi ed equipaggiamento.
		
		JPanel dvs = new JPanel(), fourdvs = new JPanel();
		dvs.setLayout(new BorderLayout());
		fourdvs.setLayout(new GridLayout(2, 2, 10, 10));
		dvs.add(fourdvs, BorderLayout.CENTER);
		add(dvs, BorderLayout.NORTH);
		JPanel physDVs = new JPanel(), menDVs = new JPanel(),
				physPrec = new JPanel(), menPrec = new JPanel(), physDVLimbs = new JPanel();
		String ma = Utils.ability[c.getAbils().getMentalCombatAbility()],
				pa = Utils.ability[c.getAbils().getPhysicalCombatAbility()];
		
		physPrec.setLayout(new BorderLayout());
		JPanel physPrecLabels = new JPanel();
		physPrecLabels.setLayout(new GridLayout(4, 1, 10, 10));
		physPrecLabels.add(new JLabel());
		for (int i = 0; i < 3; ++i) physPrecLabels.add(new JLabel(phLabel[i]));
		physPrec.add(physPrecLabels, BorderLayout.CENTER);
		JPanel physPrecGrid = new JPanel();
		// normale e con specializzazione.
		physPrecGrid.setLayout(new GridLayout(4, 2, 10, 10));
		physPrecGrid.add(new JLabel(pa));
		physPrecGrid.add(new JLabel("(+ Spec)"));
		physPrecGrid.add(new JLabel("" + (c.calc.getPrecisionMeleeDex(false))));
		physPrecGrid.add(new JLabel("" + (c.calc.getPrecisionMeleeDex(true))));
		physPrecGrid.add(new JLabel("" + (c.calc.getPrecisionMeleeStrength(false))));
		physPrecGrid.add(new JLabel("" + (c.calc.getPrecisionMeleeStrength(true))));
		physPrecGrid.add(new JLabel("" + (c.calc.getPrecisionDistance(false))));
		physPrecGrid.add(new JLabel("" + (c.calc.getPrecisionDistance(true))));
		physPrec.add(physPrecGrid, BorderLayout.EAST);
		
		menPrec.setLayout(new BorderLayout());
		JPanel menPrecLabels = new JPanel();
		menPrecLabels.setLayout(new GridLayout(3, 1, 10, 10));
		menPrecLabels.add(new JLabel());
		for (int i = 0; i < 2; ++i) menPrecLabels.add(new JLabel(mLabel[i]));
		menPrec.add(menPrecLabels, BorderLayout.CENTER);
		JPanel menPrecGrid = new JPanel();
		// normale e con specializzazione.
		menPrecGrid.setLayout(new GridLayout(3, 2, 10, 10));
		menPrecGrid.add(new JLabel(ma));
		menPrecGrid.add(new JLabel("(+ Spec)"));
		menPrecGrid.add(new JLabel("" + (c.calc.getPrecisionChar(false))));
		menPrecGrid.add(new JLabel("" + (c.calc.getPrecisionChar(true))));
		menPrecGrid.add(new JLabel("" + (c.calc.getPrecisionMan(false))));
		menPrecGrid.add(new JLabel("" + (c.calc.getPrecisionMan(true))));
		menPrec.add(menPrecGrid, BorderLayout.EAST);

		physDVs.setLayout(new BorderLayout());
		physDVLimbs.setLayout(new BorderLayout());
		JPanel physDVLabels = new JPanel(), physDVLimbsLabels = new JPanel();
		physDVLabels.setLayout(new GridLayout(3, 1, 10, 10));
		physDVLabels.add(new JLabel());
		physDVLimbsLabels.setLayout(new GridLayout(3, 1, 10, 10));
		physDVLimbsLabels.add(new JLabel());
		for (int i = 0; i < 2; ++i) {
			physDVLabels.add(new JLabel(phLabel[i+3]));
			physDVLimbsLabels.add(new JLabel(phLabel[i+3]));
		}
		physDVs.add(physDVLabels, BorderLayout.CENTER);
		physDVLimbs.add(physDVLimbsLabels, BorderLayout.CENTER);
		JPanel physGrid = new JPanel(), physGridLimbs = new JPanel();
		// normale e con specializzazione.
		physGrid.setLayout(new GridLayout(1, 6, 10, 10));
		physGridLimbs.setLayout(new GridLayout(1, 6, 10, 10));
		JPanel phys[] = new JPanel[6];
		String[] physTitle = {"Testa", "Tronco", "Braccio Dx", "Braccio Sx", "Gamba Dx", "Gamba Sx"};
		for (int i = 0; i < 6; ++i) {
			phys[i] = new JPanel();
			phys[i].setLayout(new GridLayout(3, 2, 10, 10));
			phys[i].setBorder(BorderFactory.createTitledBorder(physTitle[i]));
			phys[i].add(new JLabel(pa));
			phys[i].add(new JLabel("(+ Spec)"));
			phys[i].add(new JLabel("" + (c.calc.getPhysicalParry(i, false))));
			phys[i].add(new JLabel("" + (c.calc.getPhysicalParry(i, true))));
			phys[i].add(new JLabel("" + (c.calc.getPhysicalDodge(i, false))));
			phys[i].add(new JLabel("" + (c.calc.getPhysicalDodge(i, true))));
		}
		physGrid.add(phys[0]);
		physGrid.add(phys[1]);
		for (int i = 2; i < 6; ++i)
			physGridLimbs.add(phys[i]);
		physDVs.add(physGrid, BorderLayout.EAST);
		physDVLimbs.add(physGridLimbs, BorderLayout.EAST);

		menDVs.setLayout(new BorderLayout());
		JPanel menDVLabels = new JPanel();
		menDVLabels.setLayout(new GridLayout(4, 1, 10, 10));
		menDVLabels.add(new JLabel());
		for (int i = 0; i < 3; ++i) menDVLabels.add(new JLabel(mLabel[i+2]));
		menDVs.add(menDVLabels, BorderLayout.CENTER);
		JPanel menDVGrid = new JPanel();
		// normale e con specializzazione.
		menDVGrid.setLayout(new GridLayout(4, 2, 10, 10));
		menDVGrid.add(new JLabel(ma));
		menDVGrid.add(new JLabel("(+ Spec)"));
		menDVGrid.add(new JLabel("" + (c.calc.getMentalParryChar(false))));
		menDVGrid.add(new JLabel("" + (c.calc.getMentalParryChar(true))));
		menDVGrid.add(new JLabel("" + (c.calc.getMentalParryMan(false))));
		menDVGrid.add(new JLabel("" + (c.calc.getMentalParryMan(true))));
		menDVGrid.add(new JLabel("" + (c.calc.getMentalDodge(false))));
		menDVGrid.add(new JLabel("" + (c.calc.getMentalDodge(true))));
		menDVs.add(menDVGrid, BorderLayout.EAST);

		
		physPrec.setBorder(BorderFactory.createTitledBorder("Precisione Fisica"));
		menPrec.setBorder(BorderFactory.createTitledBorder("Precisione Mentale"));
		physDVs.setBorder(BorderFactory.createTitledBorder("Difese Fisiche Vitali"));
		menDVs.setBorder(BorderFactory.createTitledBorder("Difese Mentali"));
		physDVLimbs.setBorder(BorderFactory.createTitledBorder("Difese Fisiche Arti"));
		
		fourdvs.add(physPrec);
		fourdvs.add(menPrec);
		fourdvs.add(physDVs);
		fourdvs.add(menDVs);
		dvs.add(physDVLimbs, BorderLayout.SOUTH);
		
		

		JPanel lowerLayer = new JPanel(new GridLayout(1, 3)),
				combatResolution = new JPanel(new FlowLayout()),
				movements = new JPanel(new BorderLayout());
		JList<String> tempEffects = new JList<String>(c.effects.getAllEffectsStringList());
		JPanel temps = new JPanel(new GridLayout(1, 1));
		temps.add(new JScrollPane(tempEffects));
		temps.setBorder(BorderFactory.createTitledBorder("Effetti attivi"));
		lowerLayer.add(combatResolution);
		lowerLayer.add(movements);
		lowerLayer.add(temps);
		combatResolution.setBorder(BorderFactory.createTitledBorder("Utilità di combattimento"));
		movements.setBorder(BorderFactory.createTitledBorder("Movimenti"));
		JButton combatStatsButton = new JButton("Valori di combattimento"), solveAttack = new JButton("Risolvi attacco");
		combatStatsButton.addActionListener(new ButtonDialogListener(this, new DetailedCombatPanel(this, c)));
		solveAttack.addActionListener(new ButtonDialogListener(this, new SolveAttackWizard(main, c)));
		combatResolution.add(combatStatsButton);
		combatResolution.add(solveAttack);
		add(lowerLayer, BorderLayout.SOUTH);
		
		// TODO da implementare la suite di movimenti che modificano DV e velocità.
				
		invalidate();
		validate();
	}

}
