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

package it.apteroscode.exaltered.swing.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import it.apteroscode.exaltered.core.model.Minimum;
import it.apteroscode.exaltered.swing.wizard.AddWeaponWizard;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MinimumManager extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private class ButtonDialogListener implements ActionListener {
		
		MinimumManager p;
		boolean mod;
		MinimumViewer component;
		
		public ButtonDialogListener(MinimumManager parent, MinimumViewer component, boolean modifies) {
			p = parent;
			this.component = component;
			mod = modifies;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog dialog = new JDialog();
			dialog.add(component);
			if (mod && (mins.size() > 0))
				component.setMin(mins.get(display.getSelectedIndex()), display.getSelectedIndex());
			component.setDialog(dialog);
			dialog.setMinimumSize(component.getMinimumSize());
			dialog.pack();
			dialog.setVisible(true);
			dialog.setModal(true);
			dialog.addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent arg0) {}
				
				@Override
				public void windowIconified(WindowEvent arg0) {}
				
				@Override
				public void windowDeiconified(WindowEvent arg0) {}
				
				@Override
				public void windowDeactivated(WindowEvent arg0) {}
				
				@Override
				public void windowClosing(WindowEvent arg0) {}
				
				@Override
				public void windowClosed(WindowEvent arg0) {
					p.remodel();
				}
				
				@Override
				public void windowActivated(WindowEvent arg0) {}
			});
		}
	}

	private JList<String> display;
	private List<Minimum> mins;
	private AddWeaponWizard wiz;

	public MinimumManager(AddWeaponWizard w, Minimum[] minimums) {
		wiz = w;
		mins = new ArrayList<Minimum>();
		for (int i = 0; i < minimums.length; ++i) {
			mins.add(minimums[i]);
		}
		remodel();
	}

	public void remodel() {
		removeAll();
		setLayout(new BorderLayout());
		
		String[] diString = new String[mins.size()];
		for (int i = 0; i < mins.size(); ++i)
			diString[i] = mins.get(i).getStringPreview();
		display = new JList<String>(diString);
		add(display, BorderLayout.CENTER);
		
		JButton addButton = new JButton("+"),
				removeButton = new JButton("-"),
				modifyButton = new JButton("*");
		JPanel buttons = new JPanel(new GridLayout(1, 3, 5, 0));
		buttons.add(addButton);
		buttons.add(modifyButton);
		buttons.add(removeButton);
		add(buttons, BorderLayout.SOUTH);
		
		addButton.addActionListener(new ButtonDialogListener(this, new MinimumViewer(this), false));
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (display.getSelectedIndex() < 0) return;
				int yesNo = JOptionPane.showConfirmDialog(null, "Eliminare il minimo (" + mins.get(display.getSelectedIndex()).getStringPreview() +  ")?",
						"Eliminazione...", JOptionPane.YES_NO_OPTION);
				if (yesNo == JOptionPane.NO_OPTION) return;
				mins.remove(display.getSelectedIndex());
				remodel();
			}
		});
		modifyButton.addActionListener(new ButtonDialogListener(this, new MinimumViewer(this), true));

		invalidate();
		validate();
	}

	public MinimumManager(AddWeaponWizard w) {
		wiz = w;
		mins = new ArrayList<Minimum>();
		remodel();
	}

	public Minimum[] getMins() {
		Minimum[] res = new Minimum[mins.size()];
		for (int i = 0; i < mins.size(); ++i)
			res[i] = mins.get(i);
		return res;
	}

	public void setMinimum(int oldIndex, Minimum min) {
		mins.set(oldIndex, min);
		wiz.remodel();
	}

	public void addMinimum(Minimum min) {
		mins.add(min);
		wiz.remodel();
	}


}
