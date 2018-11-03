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

package it.apteroscode.exaltered.swing.simple;

import it.apteroscode.exaltered.swing.panels.VirtueDisplayPanel;
import it.apteroscode.exaltered.utils.Images;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class PerfectActionVirtueLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private ImageIcon actual, alternative;
	private VirtueDisplayPanel w;
	private boolean used;

	public PerfectActionVirtueLabel(VirtueDisplayPanel virtueDisplayPanel, boolean b) {
		w = virtueDisplayPanel;
		used = b;
		String url1 = Images.LETHAL, url2 = Images.NO_DAMAGE;
		actual = Images.getIcon((b ? url1 : url2));
		alternative = Images.getIcon((b ? url2 : url1));
		setIcon(actual);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				setIcon(actual);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				setIcon(alternative);
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (used) w.restoreVirtueUse();
				else w.consumeVirtueUse();
				w.notifyChange();
			}
		});
	}

}
