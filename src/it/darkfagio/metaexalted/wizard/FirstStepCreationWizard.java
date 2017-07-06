package it.darkfagio.metaexalted.wizard;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.panels.AttributesPanel;
import it.darkfagio.metaexalted.panels.CharModifyPanel;
import it.darkfagio.utils.gui.DialogPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FirstStepCreationWizard extends DialogPanel {

	private static final long serialVersionUID = 1L;
	private PlayCharacter cter;
	private JButton back, forward;
	private short status;
	private CharModifyPanel panel;
	private AttributesPanel atts;
	private final static String[] title =
		{"Attributi", "Abilità", "Specializzazioni"}; 
			
	public FirstStepCreationWizard(AttributesPanel a, PlayCharacter c) {
		atts = a;
		this.cter = c;
		setLayout(new BorderLayout());
		status = 0;
		setMinimumSize(new Dimension(400, 300));
		panel = new AttributeModifierWizard(this, c);
		add(panel, BorderLayout.CENTER);
		back = new JButton("Indietro");
		back.setEnabled(false);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				forward.setEnabled(true);
				status --;
				if (status == 0) back.setEnabled(false);
				switch (status) {
				case 0: {
					redraw(new AttributeModifierWizard(FirstStepCreationWizard.this, cter));
				}
					break;
				case 1: {
					redraw(new AbilityModifierWizard(FirstStepCreationWizard.this, cter));
					forward.setText("Avanti");
				}
				}
				FirstStepCreationWizard.this.repaint();
			}
		});
		forward = new JButton("Avanti");
		forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				back.setEnabled(true);
				panel.apply();
				status++;
				switch (status) {
				case 1: redraw(new AbilityModifierWizard(FirstStepCreationWizard.this, cter));
					break;
				case 2: {
					redraw(new SpecialtyModifierWizard(FirstStepCreationWizard.this, cter));
					forward.setText("Fine");
					break;
				}
				case 3: {
					superDialog.dispose();
				}
				}
			}
		});
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.add(back, BorderLayout.WEST);
		bottom.add(forward, BorderLayout.EAST);
		add(bottom, BorderLayout.SOUTH);
	}
			
	private void redraw(CharModifyPanel p) {
		remove(panel);
		panel = p;
		add(p, BorderLayout.CENTER);
		superDialog.setTitle(title[status]);
		superDialog.setPreferredSize(new Dimension(p.getPreferredSize().width,
				(p.getPreferredSize().height < 600 ? p.getPreferredSize().height : 600)));
		superDialog.setMaximumSize(new Dimension(p.getMaximumSize().width,
				(p.getPreferredSize().height < 600 ? p.getMaximumSize().height : 600)));
		superDialog.pack();
		superDialog.invalidate();
		superDialog.validate();
	}
		
	public void forward() {
		forward.doClick();
	}

	public void notifyChange() {
		atts.notifyChange();
	}

	@Override
	public String getTitle() {
		return title[status];
	}

}
