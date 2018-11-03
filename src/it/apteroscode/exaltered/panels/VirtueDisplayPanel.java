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

package it.apteroscode.exaltered.panels;

import java.awt.GridLayout;

import it.fagio.exaltered.model.PlayCharacter;
import it.apteroscode.exaltered.simple.PerfectActionVirtueLabel;
import it.apteroscode.exaltered.simple.WillpowerLevel;

import javax.swing.JPanel;

public class VirtueDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private PoolPanel p;
	private PlayCharacter ch;
	private int virt;

	public VirtueDisplayPanel(PoolPanel poolPanel, PlayCharacter c, int virtue) {
		p = poolPanel;
		ch = c;
		setVirt(virtue);
		remodel();
	}		

	public void remodel() {
		removeAll();
		
		int cols = 0, used = 0;
		cols = ch.getPools().getVirtues().getStat((short) virt);
		used = ch.getPools().getVirtues().getStat((short) (virt + 4));
		
		setLayout(new GridLayout(2, cols));
		
		for (int i = 0; i < cols; ++i) {
			add(new PerfectActionVirtueLabel(this, i < used));
		}
		for (int i = 0; i < cols; ++i) {
			add(new WillpowerLevel());
		}
		invalidate();
		validate();
		
	}
	
	public void notifyChange() {
		p.notifyChange();
		remodel();
	}

	public int getVirt() {
		return virt;
	}

	public void setVirt(int virt) {
		this.virt = virt;
	}

	public void restoreVirtueUse() {
		ch.getPools().getVirtues().restoreVirtue(virt);
		notifyChange();
	}

	public void consumeVirtueUse() {
		ch.getPools().getVirtues().consumeVirtue(virt);
		notifyChange();
	}
	
	public void resetVirtue(short val) {
		ch.getPools().getVirtues().setStat((short) (virt), val);
		notifyChange();
	}

}
