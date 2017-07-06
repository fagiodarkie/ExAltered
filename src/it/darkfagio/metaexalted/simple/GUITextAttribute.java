package it.darkfagio.metaexalted.simple;

import it.darkfagio.utils.Format;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUITextAttribute extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField valueText;
	private String name;
	
	public GUITextAttribute(String string, int k) {
		this(string, "" + k);
	}
	public GUITextAttribute(String string, float k) {
		this(string, Format.formatDecimal(k));
	}

	public GUITextAttribute(String string, String k) {
		name = string;
		valueText = new JTextField(k);
		valueText.setEditable(false);
		setLayout(new BorderLayout());
		valueText.setColumns(5);
		add(new JLabel(name), BorderLayout.WEST);
		add(valueText, BorderLayout.EAST);
	}
	
}
