package it.darkfagio.metaexalted.simple;

import it.darkfagio.utils.Format;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIAttribute extends JPanel {

	private static final long serialVersionUID = 1L;

	protected String name;
	private JLabel value;

	
	public GUIAttribute(String n, int v, String specName, short specVal) {
		name = n + (specVal > 0 ? (" (" + specName + ": +" + specVal + ")") : "");
		value = new JLabel();
		String s = "";
		for (int i = 0; i < v; ++i) {
			s += "\u25CF";
		}
		value.setText("" + s);
		setLayout(new BorderLayout());
		add(new JLabel(name), BorderLayout.WEST);
		add(value, BorderLayout.EAST);
	}

	public GUIAttribute(String n, String s) {
		name = n;
		value = new JLabel(s);
		setLayout(new BorderLayout());
		add(new JLabel(name), BorderLayout.WEST);
		add(value, BorderLayout.EAST);
	}

	public GUIAttribute(String string, int v) {
		this(string, "");
		String s = "";
		for (int i = 0; i < v; ++i) {
			s += "\u25CF";
		}
		value.setText("" + s);
	}

	public GUIAttribute(String string, double k) {
		this(string, Format.formatDecimal((float) k));
	}

	public GUIAttribute(String n, String showAbility, String specName,
			short specVal) {
		this(n + (specVal > 0 ? (" (" + specName + ": +" + specVal + ")") : ""), showAbility);
	}

	public void setValue(int v) {
		value.setText("" + v);
	}
	
	public int getValue() {
		return Integer.parseInt(value.getText());
	}
	
}
