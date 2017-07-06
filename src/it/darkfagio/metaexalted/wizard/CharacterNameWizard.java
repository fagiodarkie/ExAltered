package it.darkfagio.metaexalted.wizard;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.panels.MainPanel;
import it.darkfagio.utils.gui.DialogPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CharacterNameWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private PlayCharacter ch;
	private MainPanel mp;
	private JTextField name;
	
	public CharacterNameWizard(MainPanel m, PlayCharacter c) {
		this.ch = c;
		this.mp = m;
		setLayout(new BorderLayout());
		name = new JTextField((c.getName() == null || c.getName().equalsIgnoreCase("")) ? "" : c.getName());
		name.setColumns(30);
		JLabel lab = new JLabel("Nuovo nome: ");
		JPanel cont = new JPanel();
		cont.setLayout(new FlowLayout());
		cont.add(lab);
		cont.add(name);
		add(cont, BorderLayout.CENTER);
		JButton apply = new JButton("Applica");
		add(apply, BorderLayout.SOUTH);
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!name.getText().trim().equalsIgnoreCase(ch.getName())) {
					mp.notifyChange();
					ch.setName(name.getText().trim());
				}
				superDialog.dispose();
			}
		});

	}
	
	@Override
	public String getTitle() {
		return "Cambia nome...";
	}

}
