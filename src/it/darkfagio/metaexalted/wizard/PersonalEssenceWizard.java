package it.darkfagio.metaexalted.wizard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.model.Pools;
import it.darkfagio.metaexalted.panels.PoolPanel;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class PersonalEssenceWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private JTextField t, u;

	public PersonalEssenceWizard(final PoolPanel poolPanel, final PlayCharacter c) {
		setLayout(new BorderLayout());
		JPanel inner = new JPanel(new FlowLayout()), grid = new JPanel(new GridLayout(2, 2, 0, 10)),
				applyPanel = new JPanel(new FlowLayout());
		add(inner, BorderLayout.CENTER);
		grid.add(new JLabel("Modifica bonus all'essenza Personale:"));
		t = Format.getNumericTextField("" + c.getPools().getStat(Pools.personalEssenceBonus));
		grid.add(t);
		grid.add(new JLabel("Spendi essenza..."));
		u = Format.getNumericTextField();
		u.setColumns(3);
		grid.add(u);
		
		JButton apply = new JButton("Applica");
		applyPanel.add(apply);
		add(grid, BorderLayout.CENTER);
		add(applyPanel, BorderLayout.SOUTH);
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				short newVal = Short.parseShort(t.getText());
				boolean mod = false;
				if (newVal != c.getPools().getStat(Pools.personalEssenceBonus)) {
					mod = true;
					c.getPools().setStat(Pools.personalEssenceBonus, newVal);
				}
				if (!u.getText().trim().equalsIgnoreCase("") || !u.getText().trim().equalsIgnoreCase("0")) {
					mod = true;
					c.getPools().setStat(Pools.usedPersonalEssence, (short) (c.getPools().getStat(Pools.usedPersonalEssence)
							+ Short.parseShort(u.getText().trim())));
				}
				if (mod) {
					poolPanel.notifyChange();
					poolPanel.remodel();
				}
				superDialog.dispose();
			}
		});
		
	}
	
	@Override
	public String getTitle() {
		return "Modifica l'essenza Personale";
	}

}
