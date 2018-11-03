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

import it.apteroscode.exaltered.model.pool.Pools;
import it.fagio.exaltered.model.PlayCharacter;
import it.apteroscode.exaltered.simple.WillpowerBox;
import it.apteroscode.exaltered.simple.WillpowerLevel;

import javax.swing.JPanel;

public class WillpowerGridPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private PlayCharacter ch;
	private PoolPanel panel;
	
	public WillpowerGridPanel(PoolPanel poolPanel, PlayCharacter c) {
		panel = poolPanel;
		ch = c;
		remodel();
	}
	
	public void remodel() {
		removeAll();
		
		int cols = ch.getPools().getStat(Pools.willpower);
		int filled = ch.getPools().getStat(Pools.usedWillpower);
		
		setLayout(new GridLayout(2, cols));
		for (int i = 0; i < cols; ++i) {
			add(new WillpowerBox(this, filled > i));
		}
		for (int i = 0; i < cols; ++i) {
			add(new WillpowerLevel());
		}
		invalidate();
		validate();
	}
	
	public void consumeWillpower() {
		ch.getPools().setStat(Pools.usedWillpower, (short) (ch.getPools().getStat(Pools.usedWillpower) + 1));
	}
	public void restoreWillpower() {
		ch.getPools().setStat(Pools.usedWillpower, (short) (ch.getPools().getStat(Pools.usedWillpower) - 1));
	}
	
	public void notifyChange() {
		panel.notifyChange();
		remodel();
	}


}
