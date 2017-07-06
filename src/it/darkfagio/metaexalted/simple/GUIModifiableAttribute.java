package it.darkfagio.metaexalted.simple;

import it.darkfagio.utils.Format;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIModifiableAttribute extends JPanel {
	private static final long serialVersionUID = 1L;

	public GUIModifiableAttribute(String string, String string2,
			ActionListener buttonDialogListener) {
		JButton b = new JButton(string);
		b.setPreferredSize(new Dimension(200, b.getSize().height));
		JTextField t = Format.getNumericTextField();
		t.setText(string2);
		t.setEnabled(false);
		t.setColumns(5);
		b.addActionListener(buttonDialogListener);
		setLayout(new BorderLayout());
		add(b, BorderLayout.WEST);
		add(t, BorderLayout.EAST);
	}

}
