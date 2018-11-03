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

package it.fagio.exaltered.panels;

import it.fagio.exaltered.model.TempEffect;
import it.darkfagio.utils.gui.DialogPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("unused")
public class EffectsModifier extends JPanel {
	private static final long serialVersionUID = 1L;

	private JList<String> display;
	private List<TempEffect> effs;
	private int src;
	private int selected;
	private boolean remove;

	private DialogPanel wiz;

	private PoolPanel pool;
		
	private class ButtonDialogListener implements ActionListener {
		
		EffectsModifier p;
		boolean mod;
		EffectViewer component;
		
		public ButtonDialogListener(EffectsModifier parent, EffectViewer component, boolean modifies) {
			p = parent;
			this.component = component;
			mod = modifies;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog dialog = new JDialog();
			dialog.add(component);
			if (src >= 0) component.setSource(src);
			if (mod && (effs.size() > 0)) {
				selected = display.getSelectedIndex();
				component.setEffect(effs.get(selected));
			}
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
	

	public EffectsModifier(TempEffect[] attunedEffects) {
		src = -1;
		remove = true;
		selected = -1;
		effs = new ArrayList<TempEffect>();
		for (int i = 0; i < attunedEffects.length; ++i)
			effs.add(attunedEffects[i]);
		wiz = null;
		
		remodel();
	}

	public EffectsModifier() {
		wiz = null;
		selected = -1;
		src = -1;
		remove = true;
		pool = null;
		effs = new ArrayList<TempEffect>();
		remodel();
	}
	
	public EffectsModifier(PoolPanel poolPanel, List<TempEffect> effs2) {
		pool = poolPanel;
		wiz = null;
		selected = -1;
		src = -1;
		remove = true;
		effs = effs2;
		remodel();
	}

	public void setWiz(DialogPanel w) {
		wiz = w;
	}
	
	public void setSource(int source) {
		src = source;
		remodel();
	}

	public void remodel() {
		
		removeAll();
		setLayout(new BorderLayout());
				
		String[] displayStrings = new String[effs.size()];
		for (int i = 0; i < effs.size(); ++i) {
			displayStrings[i] = effs.get(i).getStringPreview();
		}
		
		display = new JList<String>(displayStrings);
		
		JButton addButton = new JButton("+"),
				removeButton = new JButton("-"),
				modifyButton = new JButton("*");
		JPanel buttons = new JPanel(new GridLayout(1, 3, 5, 0));
		buttons.add(addButton);
		buttons.add(modifyButton);
		buttons.add(removeButton);
		removeButton.setEnabled(remove);
		
		addButton.addActionListener(new ButtonDialogListener(this, src < 0 ? new EffectViewer(this) : new EffectViewer(this, src), false));
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (display.getSelectedIndex() < 0) return;
				int yesNo = JOptionPane.showConfirmDialog(null, "Eliminare l'effetto (" + effs.get(display.getSelectedIndex()).getStringPreview() +  ")?",
						"Eliminazione...", JOptionPane.YES_NO_OPTION);
				if (yesNo == JOptionPane.NO_OPTION) return;
				effs.remove(display.getSelectedIndex());
				remodel();
			}
		});
		modifyButton.addActionListener(new ButtonDialogListener(this, src < 0 ? new EffectViewer(this) : new EffectViewer(this, src), true));
		
		add(new JScrollPane(display), BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);

		invalidate();
		validate();
	}

	public void setRemoveEnabled(boolean val) {
		remove = val;
		remodel();
	}
	
	public TempEffect[] getEffects() {
		TempEffect[] res = new TempEffect[effs.size()];
		for (int i = 0; i < effs.size(); ++i)
			res[i] = effs.get(i);
		return res;
	}

	public void sendTempEffect(TempEffect effect) {
		if ((selected < 0) || (selected >= effs.size())) effs.add(effect);
		else effs.get(selected).setAs(effect);
		remodel();
	}
}
