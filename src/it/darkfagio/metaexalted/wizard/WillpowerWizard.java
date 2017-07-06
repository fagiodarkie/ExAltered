package it.darkfagio.metaexalted.wizard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.model.Pools;
import it.darkfagio.metaexalted.panels.PoolPanel;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class WillpowerWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	JTextField t;

	public WillpowerWizard(final PoolPanel poolPanel, final PlayCharacter c) {
		setLayout(new BorderLayout());
		JPanel grid = new JPanel(new FlowLayout());
		grid.add(new JLabel("Modifica livelli di volontà:    "));
		t = Format.getNumericTextField("" + c.getPools().getStat(Pools.willpower));
		t.setColumns(3);
		grid.add(t);
		
		JPanel applyPanel = new JPanel(new FlowLayout());
		JButton apply = new JButton("Applica");
		applyPanel.add(apply);
		add(grid, BorderLayout.CENTER);
		add(applyPanel, BorderLayout.SOUTH);
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				short newVal = Short.parseShort(t.getText());
				if (newVal != c.getPools().getStat(Pools.willpower)) {
					c.getPools().setStat(Pools.willpower, newVal);
					poolPanel.notifyChange();
					poolPanel.remodel();
				}
				superDialog.dispose();
			}
		});
	}
	
	@Override
	public void setDialog(JDialog d) {
		super.setDialog(d);
		superDialog.setPreferredSize(new Dimension(400, 150));
	}

	@Override
	public String getTitle() {
		return "Modifica Volontà";
	}

}
