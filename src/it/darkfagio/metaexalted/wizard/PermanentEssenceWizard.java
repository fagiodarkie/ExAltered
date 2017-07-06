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

public class PermanentEssenceWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private JTextField t;

	public PermanentEssenceWizard(final PoolPanel poolPanel, final PlayCharacter c) {
		setLayout(new BorderLayout());
		JPanel inner = new JPanel(new FlowLayout()), grid = new JPanel(new GridLayout(2, 1, 0, 10)),
				applyPanel = new JPanel(new FlowLayout());
		add(inner, BorderLayout.CENTER);
		grid.add(new JLabel("Nuova essenza Permanente:"));
		t = Format.getNumericTextField("" + c.getPools().getStat(Pools.permanentEssence));
		grid.add(t);
		
		JButton apply = new JButton("Applica");
		applyPanel.add(apply);
		add(grid, BorderLayout.CENTER);
		add(applyPanel, BorderLayout.SOUTH);
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				short newVal = Short.parseShort(t.getText());
				if (newVal != c.getPools().getStat(Pools.permanentEssence)) {
					c.getPools().setStat(Pools.permanentEssence, newVal);
					poolPanel.notifyChange();
					poolPanel.remodel();
				}
				superDialog.dispose();
			}
		});
		
	}
	

	@Override
	public String getTitle() {
		return "Modifica l'essenza Permanente";
	}

}
