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

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.model.Virtues;
import it.darkfagio.metaexalted.panels.PoolPanel;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class VirtueModifierWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private PoolPanel a;
	private PlayCharacter ch;

	public VirtueModifierWizard(PoolPanel poolPanel, PlayCharacter c) {
		a = poolPanel;
		ch = c;
		setLayout(new BorderLayout());
		JPanel inner = new JPanel(), outer = new JPanel();
		outer.setLayout(new FlowLayout());
		inner.setLayout(new GridLayout(2, 2, 10, 10));
		
		inner.add(new JLabel("Modifica Virtù: "));
		
		final JComboBox<String> attributes = new JComboBox<String>(Virtues.name);

		inner.add(attributes);
		
		inner.add(new JLabel("Nuovo valore: "));
		final JTextField text = Format.getNumericTextField("" + c.getPools().getVirtues().getStat(Virtues.compassion));
		text.setColumns(3);
		inner.add(text);
		outer.add(inner);
		add(outer, BorderLayout.CENTER);
		attributes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				text.setText("" + ch.getPools().getVirtues().getStat((short) attributes.getSelectedIndex()));
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
				ch.getPools().getVirtues().setStat((short) attributes.getSelectedIndex(), Integer.parseInt(text.getText().trim()));
				a.notifyChange();
				superDialog.dispose();
			}
		});
		
	}
	
	@Override
	public String getTitle() {

		return "Modifica una Virtù";
	}


}
