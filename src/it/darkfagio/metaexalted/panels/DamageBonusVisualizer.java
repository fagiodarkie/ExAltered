package it.darkfagio.metaexalted.panels;

import it.darkfagio.utils.Format;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DamageBonusVisualizer extends JPanel {

	private static final long serialVersionUID = 1L;
	private short[] bonus;
	private String title;
	private JTextField[] vals;
	private boolean enabled; 

	public DamageBonusVisualizer() {
		short[] x = {0, 0, 0, 0};
		enabled = true;
		bonus = x;
		title = "";
		remodel();
	}
	
	public DamageBonusVisualizer(short[] p) {
		enabled = true;
		bonus = p;
		title = "";
		remodel();
	}

	public DamageBonusVisualizer(String s, short[] p) {
		bonus = p;
		enabled = true;
		title = s;
		remodel();
	}
	
	public void setTitle(String title) {
		this.title = title;
		remodel();
	}
	
	public void remodel() {
		removeAll();
		setLayout(new BorderLayout());
		
		vals = new JTextField[4];
		JPanel values = new JPanel(new GridLayout(1, 4));
		for (int i = 0; i < 4; ++i) {
			vals[i] = Format.getNumericTextField("" + bonus[i]);
			vals[i].setColumns(3);
			vals[i].setEditable(enabled);
			values.add(vals[i]);
		}
		
		add(new JLabel(title), BorderLayout.CENTER);
		add(values, BorderLayout.EAST);
		invalidate();
		validate();
	}
	
	public void setBonus(short[] b) {
		bonus = b;
		remodel();
	}
	
	public short[] getPhysBonus() {
		short[] bon = new short[3],
				b = getBonus();
		for (int i = 0; i < 2; ++i)
			bon[i] = b[i];
		return bon;
	}
	public short getMenBonus() {
		return getBonus()[3];
	}

	public short[] getBonus() {
		for(int i = 0; i < 4; ++i)
			bonus[i] = Short.parseShort(vals[i].getText().trim().length() == 0 ? "0" : vals[i].getText().trim());
		return bonus;
	}

	public static JPanel getHeader() {
		JPanel c =  new JPanel(new BorderLayout());
		
		JPanel values = new JPanel(new GridLayout(1, 4));
		JTextField a = new JTextField("me");
		a.setColumns(3);
		Dimension d = a.getPreferredSize();
		String[] heads = {"U", "L", "A", "M"};
		for (int i = 0; i < 4; ++i) {
			JLabel l = new JLabel(heads[i], SwingConstants.CENTER);
			l.setPreferredSize(d);
			values.add(l);
		}
		c.add(new JLabel(), BorderLayout.CENTER);
		c.add(values, BorderLayout.EAST);
		c.invalidate();
		c.validate();
		return c;
	}

	public void setEnabled(boolean v) {
		enabled = v;
		remodel();
	}
}
