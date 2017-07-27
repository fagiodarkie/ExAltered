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

© 2017 White Wolf Entertainment AB. All rights reserved.
Exalted® and Storytelling System™ are trademarks and/or
registered trademarks of White Wolf Entertainment AB.

All rights reserved. www.white-wolf.com
*/

package it.darkfagio.metaexalted.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import it.darkfagio.metaexalted.model.HealthManager;
import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.simple.AggravatedDamageButton;
import it.darkfagio.metaexalted.simple.BashingDamageButton;
import it.darkfagio.metaexalted.simple.LethalDamageButton;
import it.darkfagio.metaexalted.simple.NoDamageIcon;
import it.darkfagio.metaexalted.wizard.DamageWizard;
import it.darkfagio.metaexalted.wizard.HealWizard;
import it.darkfagio.metaexalted.wizard.ModifyHealthWizard;
import it.darkfagio.utils.gui.DialogPanel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HealthLimbGui extends JPanel {
	private static final long serialVersionUID = 3525032177351090713L;
	
	private class ButtonDialogListener implements ActionListener {
		
		HealthLimbGui p;
		DialogPanel component;
		
		public ButtonDialogListener(HealthLimbGui parent, DialogPanel component) {
			p = parent;
			this.component = component;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog dialog = new JDialog();
			dialog.add(component);
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


	private it.darkfagio.metaexalted.model.PlayCharacter ch;
	private HealthPanel panel;
	private int position;
	
	public HealthLimbGui(PlayCharacter c, int position, HealthPanel healthPanel) {
		ch = c;
		panel = healthPanel;
		this.position = position;
		
		remodel();
	}

	public void remodel() {
		
		removeAll();
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(HealthManager.NAME[position]));
		JPanel labels = new JPanel(), levels = new JPanel(), buttons = new JPanel();
		labels.setLayout(new GridLayout(ch.getHealth().get(position).getHealthLevelNumber(), 1, 0, 10));
		levels.setLayout(new GridLayout(ch.getHealth().get(position).getHealthLevelNumber(), 1, 0, 10));
		buttons.setLayout(new FlowLayout());
		short[] dmgs = {ch.getHealth().get(position).getDamageLevels()[0],
				ch.getHealth().get(position).getDamageLevels()[1],
				ch.getHealth().get(position).getDamageLevels()[2]};
		
		short reachedLevel = HealthManager.AGGRAVATED;
		for (int i = 0; i < ch.getHealth().get(position).getHealthLevelNumber(); ++i) {
			labels.add(new JLabel("" + (ch.getHealth().get(position).getPenaltyLevels()[i] > Short.MIN_VALUE ? 
					ch.getHealth().get(position).getPenaltyLevels()[i] : 
						"INC")));
			
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			JPanel buts = new JPanel();
			int cols = ch.getHealth().get(position).getTotalHealthLevels()[i];
			buts.setLayout(new GridLayout(1, cols));
			
			for(int j = 0; j < cols; ++j) {
				while ((reachedLevel >= 0) && (dmgs[reachedLevel] <= 0)) reachedLevel --;
				switch (reachedLevel) {
				case -1: buts.add(new NoDamageIcon());
				break;
				case HealthManager.BASHING: buts.add(new BashingDamageButton());
				break;
				case HealthManager.LETHAL: buts.add(new LethalDamageButton());
				break;
				case HealthManager.AGGRAVATED: buts.add(new AggravatedDamageButton());
				break;
				}
				if (reachedLevel >= 0) dmgs[reachedLevel]--;
			}
			p.add(buts, BorderLayout.WEST);
			levels.add(p);
		}
		
		JButton heal = new JButton("Cura"), damage = new JButton("Danneggia"), modify = new JButton("Modifica");
		heal.addActionListener(new ButtonDialogListener(this, new HealWizard(ch, position, this)));
		damage.addActionListener(new ButtonDialogListener(this, new DamageWizard(ch, position, this)));
		modify.addActionListener(new ButtonDialogListener(this, new ModifyHealthWizard(ch, position, this)));
		buttons.add(heal);
		buttons.add(damage);
		buttons.add(modify);
		
		add(labels, BorderLayout.WEST);
		add(levels, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
		invalidate();
		validate();
	}


	public void notifyChange() {
		panel.notifyChange();
	}

	public int getPosition() {
		return position;
	}
	
}
