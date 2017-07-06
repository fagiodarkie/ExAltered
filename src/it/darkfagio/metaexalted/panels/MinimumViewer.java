package it.darkfagio.metaexalted.panels;

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

import it.darkfagio.metaexalted.model.Minimum;
import it.darkfagio.utils.gui.DialogPanel;

public class MinimumViewer extends DialogPanel {
	private static final long serialVersionUID = 1L;
	
	private MinimumManager manag;
	private JComboBox<String> trait;
	private JTextField value;

	private int oldIndex;

	public MinimumViewer(MinimumManager minimumManager) {
		manag = minimumManager;
		oldIndex = -1;
		trait = new JComboBox<String>(Minimum.getStatNames());
		value = new JTextField(3);
		
		remodel();
	}

	public void remodel() {
		
		removeAll();
		
		setLayout(new BorderLayout());
		JPanel center = new JPanel(new FlowLayout()),
				button = new JPanel(new FlowLayout()),
				centerGrid = new JPanel(new GridLayout(2, 2));
		centerGrid.add(new JLabel("Attributo: "));
		centerGrid.add(trait);
		centerGrid.add(new JLabel("Valore minimo: "));
		centerGrid.add(value);
		center.add(centerGrid);
		add(center, BorderLayout.CENTER);
		
		JButton apply = new JButton("Applica");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				backup();
				superDialog.dispose();
			}
		});
		button.add(apply);
		add(button, BorderLayout.SOUTH);
		
		invalidate();
		validate();
	}
	
	public void backup() {
		Minimum m = getMin();
		if (m == null) return;
		if (oldIndex > 0) manag.setMinimum(oldIndex, m);
		else manag.addMinimum(m);
	}	
	
	private Minimum getMin() {
		short val = Short.parseShort(value.getText());
		if (val <= 0) return null;
		return new Minimum((short) Minimum.convertListIndexToStat(trait.getSelectedIndex()), val);
	}

	public void setMin(Minimum minimum, int i) {
		oldIndex = i;
		trait.setSelectedIndex(Minimum.convertStatToListIndex(minimum.getStat()));
		value.setText("" + minimum.getValue());
		remodel();
	}

	@Override
	public String getTitle() {
		return "Crea / Modifica Minimo";
	}
}
