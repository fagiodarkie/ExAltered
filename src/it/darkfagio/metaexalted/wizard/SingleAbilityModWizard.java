package it.darkfagio.metaexalted.wizard;

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

import it.darkfagio.metaexalted.model.Abilities;
import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.panels.AttributesPanel;
import it.darkfagio.metaexalted.utils.Utils;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class SingleAbilityModWizard extends DialogPanel {

	private static final long serialVersionUID = 5735383292129899153L;
	private AttributesPanel a;

	public SingleAbilityModWizard(AttributesPanel attributesPanel, final PlayCharacter c) {
		a = attributesPanel;
		setLayout(new BorderLayout());
		JPanel inner = new JPanel(), outer = new JPanel();
		outer.setLayout(new FlowLayout());
		inner.setLayout(new GridLayout(4, 2, 10, 10));
		
		inner.add(new JLabel("Modifica abilità: "));
		
		final JComboBox<String> attributes = new JComboBox<String>();
		for (int i = 0; i < 25; ++i) {
			attributes.addItem(Utils.ability[i]);
		}
		inner.add(attributes);
		
		inner.add(new JLabel("Nuovo valore: "));
		final JTextField text = Format.getNumericTextField();
		text.setColumns(3);
		text.setText("" + c.getAbils().getAsArray()[Abilities.ARCHERY]);
		inner.add(text);
		add(inner, BorderLayout.CENTER);

		inner.add(new JLabel("Specializzazione (se presente): "));		
		final JTextField spec = new JTextField(30);
		spec.setText(c.getAbils().getSpecNames()[0]);
		inner.add(spec);
		
		inner.add(new JLabel("Valore: "));
		final JTextField specVal = Format.getNumericTextField();
		specVal.setColumns(3);
		specVal.setText("" + c.getAbils().getSpecArray()[0]);
		inner.add(specVal);
		
		outer.add(inner);
		add(outer, BorderLayout.CENTER);
		
		attributes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				text.setText("" + c.getAbils().getAsArray()[attributes.getSelectedIndex()]);
				spec.setText(c.getAbils().getSpecNames()[attributes.getSelectedIndex()]);
				specVal.setText("" + c.getAbils().getSpecArray()[attributes.getSelectedIndex()]);
			}
		});

		
		JPanel butts = new JPanel();
		butts.setLayout(new FlowLayout());
		JButton button = new JButton("Applica");
		butts.add(button);
		add(butts, BorderLayout.SOUTH);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				short ab = Short.parseShort(text.getText().equalsIgnoreCase("") ? "0" : text.getText());
				short sp = Short.parseShort(specVal.getText().equalsIgnoreCase("") ? "0" : specVal.getText());
				String spn = spec.getText().trim();
				if (c.getAbils().getAsArray()[attributes.getSelectedIndex()] != ab
					|| c.getAbils().getSpecArray()[attributes.getSelectedIndex()] != sp
					|| !c.getAbils().getSpecNames()[attributes.getSelectedIndex()].equalsIgnoreCase(spn)) {
					a.notifyChange();
					c.getAbils().setAbility(attributes.getSelectedIndex(), ab);
					if (!spec.getText().trim().equalsIgnoreCase(""))
						c.getAbils().setSpecialization((short) attributes.getSelectedIndex(),
							spec.getText().trim(), (Short.parseShort(specVal.getText())));
				}
				superDialog.dispose();
			}
		});

	}
	
	@Override
	public String getTitle() {
		return "Modifica un'Abilità";
	}

}
