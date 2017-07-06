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
import it.darkfagio.metaexalted.panels.PoolPanel;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class RestoreEssenceWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	
	public RestoreEssenceWizard(final PoolPanel poolPanel, final PlayCharacter c) {
		
		setLayout(new BorderLayout());
		JPanel center = new JPanel(new FlowLayout()),
				buttons = new JPanel(new FlowLayout()),
				item = new JPanel(new GridLayout(1, 3));
		final JTextField value = Format.getNumericTextField("0");
		value.setColumns(3);
		JButton apply = new JButton("Applica");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.pools.restoreEssence(Short.parseShort(value.getText()));
				poolPanel.notifyChange();
				superDialog.dispose();
				
			}
		});
		item.add(new JLabel("Ripristina "));
		item.add(value);
		item.add(new JLabel(" moti"));
		add(center, BorderLayout.CENTER);
		center.add(item);
		buttons.add(apply);
		add(buttons, BorderLayout.SOUTH);
	}

	@Override
	public String getTitle() {
		return "Ripristina Moti";
	}
}
