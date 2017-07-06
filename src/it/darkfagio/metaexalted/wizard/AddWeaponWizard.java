package it.darkfagio.metaexalted.wizard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.model.TempEffect;
import it.darkfagio.metaexalted.model.Weapon;
import it.darkfagio.metaexalted.panels.DamageBonusVisualizer;
import it.darkfagio.metaexalted.panels.EffectsModifier;
import it.darkfagio.metaexalted.panels.EquipmentPanel;
import it.darkfagio.metaexalted.panels.MinimumManager;
import it.darkfagio.metaexalted.utils.Utils;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class AddWeaponWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private EquipmentPanel eq;
	private PlayCharacter c;
	private Weapon w;
	private JTextArea description;
	private JTextField nameField, attuneCost, atp, atd;
	private JTextField[] fieldGrid;
	private EffectsModifier effs, attEffs;
	private MinimumManager min;
	private int oldWeaponPosition;
	private JTextField notes;
	private DamageBonusVisualizer normalDamage, attunedDamage;
	private boolean mod;
	
	private static int rowNum = Weapon.addedStats + 1;
		
	public AddWeaponWizard(EquipmentPanel ePanel, PlayCharacter ch, boolean modify) {
		c = ch;
		eq = ePanel;
		mod = modify;
		if (!mod) {
			oldWeaponPosition = -1;
			w = new Weapon();
			effs = new EffectsModifier();
			attEffs = new EffectsModifier();
			min = new MinimumManager(this);
		} else {
			oldWeaponPosition = ePanel.weaponList.getSelectedIndex();
			w = c.equip.weapon.get(oldWeaponPosition);
			effs = new EffectsModifier(w.getEffects());
			attEffs = new EffectsModifier(w.getAttunedEffects());
			min = new MinimumManager(this, w.getMinimums());
		}
		effs.setSource(TempEffect.T_EQUIP);
		attEffs.setSource(TempEffect.T_EQUIP);
		remodel();
	}
	
	public void remodel() {
		removeAll();
		setLayout(new BorderLayout());
				
		JPanel head = new JPanel(new GridLayout(5, 2, 5, 5));
		JPanel header = new JPanel(new BorderLayout());
		head.add(new JLabel("Nome:"));
		nameField = new JTextField(w.getName());
		head.add(nameField);
		head.add(new JLabel("ATP"));
		atp = new JTextField(w.getATP());
		head.add(atp);
		head.add(new JLabel("ATD"));
		atd = new JTextField(w.getATD());
		head.add(atd);
		head.add(new JLabel("Costo Armonizzazione:"));
		attuneCost = Format.getNumericTextField("" + w.getEssence());
		head.add(attuneCost);
		head.add(new JLabel("Note"));
		notes = new JTextField(w.getNotes());
		head.add(notes);
		min.setBorder(BorderFactory.createTitledBorder("Minimi"));
		header.add(head, BorderLayout.NORTH);
		header.add(min, BorderLayout.CENTER);
		add(header, BorderLayout.NORTH);
		
		int rows = Weapon.addedStats + 1;
		JPanel statNameGrid = new JPanel(new GridLayout(rows + 1, 1, 8, 5));
		JPanel statFieldsGrid = new JPanel(new GridLayout(rows + 1, 2, 8, 5));
		statNameGrid.add(new JLabel());
		statFieldsGrid.add(new JLabel("Normale"));
		statFieldsGrid.add(new JLabel("Armonizzato"));
		fieldGrid = new JTextField[2 * rows];
		for(int i = 0; i < rows; ++i) {
			statNameGrid.add(new JLabel(Utils.weaponStat[i]));
			fieldGrid[i] = Format.getNumericTextField((i == 0 ? "" + w.getWeightArray()[0] : "" + w.getNormalStat(i - 1)));
			fieldGrid[i + rows] = Format.getNumericTextField((i == 0 ? "" + w.getWeightArray()[1] : "" + w.getArmoStat(i - 1)));
			fieldGrid[i].setColumns(3);
			fieldGrid[i + rows].setColumns(3);
			statFieldsGrid.add(fieldGrid[i]);
			statFieldsGrid.add(fieldGrid[i + rows]);
		}
		
		JPanel statPanel = new JPanel(new BorderLayout());
		statPanel.add(statNameGrid, BorderLayout.WEST);
		statPanel.add(statFieldsGrid, BorderLayout.EAST);
		
		effs.setBorder(BorderFactory.createTitledBorder("Effetti"));
		attEffs.setBorder(BorderFactory.createTitledBorder("Effetti da Armonizzato"));
		JPanel effects = new JPanel(new BorderLayout());
		normalDamage = new DamageBonusVisualizer(w.getNormalDamage());
		attunedDamage = new DamageBonusVisualizer(w.getAttunedDamage());
		normalDamage.setTitle("Normale");
		attunedDamage.setTitle("Armonizzato");
		JPanel damages = new JPanel(new GridLayout(3,1));
		damages.add(DamageBonusVisualizer.getHeader());
		damages.add(normalDamage);
		damages.add(attunedDamage);
		damages.setBorder(BorderFactory.createTitledBorder("Bonus ai Danni"));
		effects.add(effs, BorderLayout.NORTH);
		effects.add(attEffs, BorderLayout.SOUTH);

		JPanel footStats = new JPanel(new BorderLayout());
		footStats.add(effects, BorderLayout.CENTER);
		footStats.add(damages, BorderLayout.NORTH);
		JPanel center = new JPanel(new BorderLayout());
		center.add(statPanel, BorderLayout.CENTER);
		center.add(footStats, BorderLayout.SOUTH);
		JScrollPane centerPane = new JScrollPane(center);
		centerPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		centerPane.setBorder(BorderFactory.createTitledBorder("Valori"));
		add(centerPane, BorderLayout.CENTER);	
		
		description = new JTextArea(w.getDescription());
		description.setRows(3);
		JPanel descPanel = new JPanel(new GridLayout(1, 1));
		descPanel.add(description);
		descPanel.setBorder(BorderFactory.createTitledBorder("Descrizione"));
		JPanel footer = new JPanel(new BorderLayout());
		footer.add(descPanel, BorderLayout.CENTER);
		JPanel appRefr = new JPanel(new GridLayout(1, 2, 5, 0));
		JButton apply = new JButton("Applica"), refresh = new JButton("Ricarica");
		appRefr.add(apply);
		appRefr.add(refresh);
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				backupInfo();
				remodel();
			}
		});
		footer.add(appRefr, BorderLayout.SOUTH);
		add(footer, BorderLayout.SOUTH);
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backupInfo();
				if (oldWeaponPosition >= 0) {
					c.equip.weapon.remove(oldWeaponPosition);
				}
				c.equip.addWeapon(w);
				eq.notifyChange();
				superDialog.dispose();
			}
		});
		
		invalidate();
		validate();
	}
	
	@Override
	public void setDialog(JDialog d) {
		super.setDialog(d);
		d.setPreferredSize(new Dimension(500, 600));
	}
	
	private void backupInfo() {
		w = new Weapon();
		float weight1 = Float.parseFloat(fieldGrid[0].getText().trim());
		float weight2 = Float.parseFloat(fieldGrid[rowNum].getText().trim());
		float[] weight = {weight1, weight2};
		w.setWeight(weight);
		
		w.setName(nameField.getText().trim());
		w.setDescription(description.getText().trim());
		w.setATP(atp.getText());
		w.setATD(atd.getText());
		w.setEssenceCost(Short.parseShort(attuneCost.getText().trim()));
		w.setNotes(notes.getText().trim());
		w.setEffects(effs.getEffects());
		w.setAttunedEffects(attEffs.getEffects());
		short[] stats = new short[Weapon.addedStats], attStats = new short[Weapon.addedStats];
		
		for (int i = 1; i <= Weapon.addedStats;++i) {
			stats[i - 1] = Short.parseShort(fieldGrid[i].getText().trim());
			attStats[i - 1] = Short.parseShort(fieldGrid[i + rowNum].getText().trim());
		}
		
		w.setStats(stats);
		w.setArmoStats(attStats);
		w.setNormalDamage(normalDamage.getBonus());
		w.setArmoDamage(attunedDamage.getBonus());
		w.setMinimums(min.getMins());
	}

	@Override
	public String getTitle() {
		if (!mod) return "Nuova Arma";
		else return "Modifica Arma";

	}

}
