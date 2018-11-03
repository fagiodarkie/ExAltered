/*
Author: Jacopo Freddi
Project: ExAltered
Description: Interactive Character Sheet for a heavily-altered version of
  the  role-playing game Exalted, 2nd Edition, by White Wolf Publishing.

Copyright (C) 2014 - 2017 Jacopo Freddi

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

� 2017 White Wolf Entertainment AB. All rights reserved.
Exalted� and Storytelling System� are trademarks and/or
registered trademarks of White Wolf Entertainment AB.

All rights reserved. www.white-wolf.com
*/

package it.fagio.exaltered.wizard;

import it.fagio.exaltered.model.PlayCharacter;
import it.fagio.exaltered.panels.AttributesPanel;
import it.fagio.exaltered.panels.CharModifyPanel;
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
		{"Attributi", "Abilit�", "Specializzazioni"}; 
			
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
