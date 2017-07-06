package it.darkfagio.metaexalted.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import it.darkfagio.metaexalted.model.HealthManager;
import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.model.Weapon;
import it.darkfagio.metaexalted.simple.GUIAttribute;
import it.darkfagio.metaexalted.simple.GUITextAttribute;
import it.darkfagio.utils.gui.DialogPanel;

public class DetailedCombatPanel extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private PlayCharacter ch;
	@SuppressWarnings("unused")
	private CombatStatsPanel cPanel;

	public DetailedCombatPanel(CombatStatsPanel combatStatsPanel, PlayCharacter c) {
		cPanel = combatStatsPanel;
		ch = c;
		
		remodel();
	}
	
	public void remodel() {
		
		removeAll();
		
		List<Weapon> weapon = new ArrayList<Weapon>();
		//movimento, ingombro, equilibri (3), danno minimo
		int statN = 6;
			
		for (int i = 0; i < ch.equip.equippedWeapon.size(); ++i) {
			Weapon w = ch.equip.weapon.get(ch.equip.equippedWeapon.get(i));
			weapon.add(w);
		}
		
		JPanel upperPanel = new JPanel(new BorderLayout()),
				upperPartOfUpperPanel = new JPanel(new BorderLayout()),
				lowerPartOfUpperPanel = new JPanel(new BorderLayout()),
				kViewer = new JPanel(new GridLayout(2, 2)),
				physPrecKViewer = new JPanel(new GridLayout(3, 1)),
				menPrecKViewer = new JPanel(new GridLayout(2, 1)),
				physDVViewer = new JPanel(new GridLayout(2, 1)),
				menDVViewer = new JPanel(new GridLayout(3, 1)),
				
				dmgSoakBonusViewer = new JPanel(new BorderLayout()),
				dmgViewer = new JPanel(new GridLayout(weapon.size() + 1, 1)),
				// soak for each body part + natural
				soakViewer = new JPanel(new GridLayout(8, 1)),
				otherStats = new JPanel(new GridLayout(statN, 1));
		// ------ This is only for the upper part --------------------------------- //
		setLayout(new BorderLayout());
		upperPartOfUpperPanel.add(kViewer, BorderLayout.WEST);
		lowerPartOfUpperPanel.add(otherStats, BorderLayout.EAST);
		lowerPartOfUpperPanel.add(dmgSoakBonusViewer, BorderLayout.WEST);
		upperPanel.add(upperPartOfUpperPanel, BorderLayout.NORTH);
		upperPanel.add(lowerPartOfUpperPanel, BorderLayout.SOUTH);
		add(upperPanel, BorderLayout.NORTH);
		kViewer.add(physPrecKViewer);
		kViewer.add(physDVViewer);
		kViewer.add(menPrecKViewer);
		kViewer.add(menDVViewer);
		
		physPrecKViewer.setBorder(BorderFactory.createTitledBorder("K di Precisione Fisica"));
		physPrecKViewer.add(new GUIAttribute("Mischia (Forza)", ch.calc.getKPrecisionMeleeStrength()));
		physPrecKViewer.add(new GUIAttribute("Mischia (Destrezza)", ch.calc.getKPrecisionMeleeDex()));
		physPrecKViewer.add(new GUIAttribute("Distanza", ch.calc.getKPrecisionDistance()));
		physDVViewer.setBorder(BorderFactory.createTitledBorder("K di Difesa Fisica"));
		physDVViewer.add(new GUIAttribute("Parata", ch.calc.getKPhysicalParry()));
		physDVViewer.add(new GUIAttribute("Schivata", ch.calc.getKPhysicalDodge()));
		menPrecKViewer.setBorder(BorderFactory.createTitledBorder("K di Precisione Mentale"));
		menPrecKViewer.add(new GUIAttribute("Carisma", ch.calc.getKPrecisionChar()));
		menPrecKViewer.add(new GUIAttribute("Manipolazione", ch.calc.getKPrecisionMan()));
		menDVViewer.setBorder(BorderFactory.createTitledBorder("K di Difesa Mentale"));
		menDVViewer.add(new GUIAttribute("Parata (Carisma)", ch.calc.getKMentalParryChar()));
		menDVViewer.add(new GUIAttribute("Parata (Manipolazione)", ch.calc.getKMentalParryMan()));
		menDVViewer.add(new GUIAttribute("Schivata", ch.calc.getKMentalDodge()));
		
		// ----------------------- End of the K part
		dmgViewer.setBorder(BorderFactory.createTitledBorder("Bonus ai Danni"));
		dmgSoakBonusViewer.add(dmgViewer, BorderLayout.NORTH);
		dmgSoakBonusViewer.add(soakViewer, BorderLayout.SOUTH);
		dmgViewer.add(DamageBonusVisualizer.getHeader());
		for (int i = 0; i < weapon.size(); ++i) {
			DamageBonusVisualizer x = new DamageBonusVisualizer(weapon.get(i).getName(),
					weapon.get(i).getDamage());
			x.setEnabled(false);
			dmgViewer.add(x);
		}
		
		soakViewer.setBorder(BorderFactory.createTitledBorder("Assorbimento"));
		soakViewer.add(DamageBonusVisualizer.getHeader());
		DamageBonusVisualizer x = new DamageBonusVisualizer("Assorbimento naturale:", ch.calc.getSoak());
		x.setEnabled(false);
		soakViewer.add(x);
		for (int i = 0; i < 6; ++i) {
			x = new DamageBonusVisualizer("Ass.to corazzato " + HealthManager.NAME[i] + ":", ch.calc.getArmoredSoak(i));
			x.setEnabled(false);
			soakViewer.add(x);
		}
		
		//movimento, ingombro, equilibri (2 + 1), danno minimo
		otherStats.setBorder(BorderFactory.createTitledBorder("Altre statistiche"));
		otherStats.add(new GUITextAttribute("Movimento", ch.calc.getMovement()));
		otherStats.add(new GUITextAttribute("Ingombro", ch.equip.getArmorHindrance()));
		otherStats.add(new GUITextAttribute("Equilibrio di Parata", ch.calc.getParryBalance(false)));
		otherStats.add(new GUITextAttribute("Eq. Parata con Parata", ch.calc.getParryBalance(true)));
		otherStats.add(new GUITextAttribute("Equilibrio di Schivata", + ch.calc.getDodgeBalance()));
		otherStats.add(new GUITextAttribute("Danno minimo", ch.calc.getMinimumDamage()));
		
		
		invalidate();
		validate();
	}
	
	@Override
	public String getTitle() {
		return "Valori di Combattimento";
	}


}
