package it.darkfagio.metaexalted.wizard;

import it.darkfagio.metaexalted.model.Attributes;
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

public class SingleAttributeModWizard extends DialogPanel {
	private static final long serialVersionUID = -3498436130392844309L;
	private AttributesPanel a;

	public SingleAttributeModWizard(AttributesPanel attributesPanel, final PlayCharacter c) {
		a = attributesPanel;
		setLayout(new BorderLayout());
		JPanel inner = new JPanel(), outer = new JPanel();
		outer.setLayout(new FlowLayout());
		inner.setLayout(new GridLayout(2, 2, 10, 10));
		
		inner.add(new JLabel("Modifica attributo: "));
		
		final JComboBox<String> attributes = new JComboBox<String>();
		for (int i = 0; i < 9; ++i) {
			attributes.addItem(Utils.attribute[i]);
		}
		inner.add(attributes);
		
		inner.add(new JLabel("Nuovo valore: "));
		final JTextField text = Format.getNumericTextField();
		text.setColumns(3);
		text.setText("" + c.getAtts().getAsArray()[Attributes.STRENGTH]);
		inner.add(text);
		outer.add(inner);
		add(outer, BorderLayout.CENTER);
		attributes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				text.setText("" + c.getAtts().getAsArray()[attributes.getSelectedIndex()]);
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
				if (c.getAtts().getAsArray()[attributes.getSelectedIndex()] != Short.parseShort(text.getText().trim())) {
					a.notifyChange();
					c.getAtts().setAttribute(attributes.getSelectedIndex(), Short.parseShort(text.getText()));
				}
				superDialog.dispose();
			}
		});
		
	}
	

	@Override
	public String getTitle() {
		return "Modifica Attributo";
	}


}
