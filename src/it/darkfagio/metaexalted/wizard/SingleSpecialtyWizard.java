package it.darkfagio.metaexalted.wizard;

import it.darkfagio.metaexalted.model.Abilities;
import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.panels.AttributesPanel;
import it.darkfagio.metaexalted.utils.Utils;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SingleSpecialtyWizard extends DialogPanel {

	private static final long serialVersionUID = 1L;
	private AttributesPanel a;
	public SingleSpecialtyWizard(AttributesPanel attributesPanel, final PlayCharacter c) {
		a = attributesPanel;
		setLayout(new BorderLayout());
		JPanel inner = new JPanel(), outer = new JPanel();
		outer.setLayout(new FlowLayout());
		inner.setLayout(new GridLayout(3, 2, 10, 10));
		
		inner.add(new JLabel("Nome della specializzazione: "));		
		final JTextField spec = new JTextField(30);
		inner.add(spec);
		
		inner.add(new JLabel("Abilità:"));
		final JComboBox<String> specAb = new JComboBox<String>();
		for (int i = 0; i < Utils.ability.length; ++i)
			specAb.addItem(Utils.ability[i]);
		specAb.setSelectedIndex(Abilities.CRAFT);
		inner.add(specAb);
		
		inner.add(new JLabel("Valore: "));
		final JTextField specVal = Format.getNumericTextField();
		specVal.setColumns(3);
		inner.add(specVal);
		outer.add(inner);
		add(outer, BorderLayout.CENTER);

		
		JPanel butts = new JPanel();
		butts.setLayout(new FlowLayout());
		JButton button = new JButton("Applica");
		butts.add(button);
		add(butts, BorderLayout.SOUTH);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!(spec.getText().trim().equalsIgnoreCase("") || specVal.getText().trim().equalsIgnoreCase("") )) {
					c.getAbils().addSpec(spec.getText().trim(),
							(short) specAb.getSelectedIndex(),
							(Short.parseShort(specVal.getText())));
					a.notifyChange();
				}
				superDialog.dispose();
			}
		});
	}
	
	@Override
	public String getTitle() {
		return "Aggiungi Specializzazione";
	}


}
