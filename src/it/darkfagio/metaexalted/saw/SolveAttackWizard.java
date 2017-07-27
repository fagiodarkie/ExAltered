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

package it.darkfagio.metaexalted.saw;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;

import it.darkfagio.metaexalted.model.Movement;
import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.panels.MainPanel;
import it.darkfagio.metaexalted.utils.Utils;
import it.darkfagio.utils.gui.DialogPanel;

public class SolveAttackWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private PlayCharacter c;
	private MainPanel panel;
		
	private int position;
	private List<Integer> queue;
	private List<SAWPanel> panes;
	private boolean[] state;
	private int[] value;
	private final int statesNum = 11;
	private final int valueNum = 15;
	private Movement mov;
	
	public static final int attacking = 0, dodge = 1, passedDefense = 2, lostBalance = 3, passedHardness = 4,
			passedSoak = 5, minimum = 6, forwardEnabled = 7, backwardEnabled = 8, perfectAction = 9, specialization = 10;
	
	public static final int pool = 0, maxAttackPenalty = 1, attackNumber = 2, currentAttack = 3, spentMotes = 4,
			spentWillpower = 5, spentPerfectDefense = 6, defenseVirtue = 7, damageType = 8, damageLevels = 9, bodyPart = 10,
			defenseVD = 11, precision = 12, weaponUsed = 13, abilityUsed = 14;
	
	public static final int START = 0, ATTACK = 1, DEFEND = 2,
			
			SIMPLE_ATTACK = 10, FLURRY = 11, CHARM = 12, INTERCEPT = 13, COUNTER = 14, COMBO = 15,
			SOAK_ATTACK = 16, BALANCE_TEST_ATTACK = 17, DICE_THROWER = 18, THROW_RESULT_DISPLAY = 19,
			HARDNESS_TEST_ATK = 21, DAMAGE = 22, PRECISION_ROLL = 23,
			
			DEF_SIMPLE = 30, DEF_FLURRY = 31, DEF_INTERCEPT = 32, DEF_CHOOSER = 33, VD_VISUALIZE = 34,
			HARDNESS_TEST_DEF = 35, DAMAGE_TAKEN = 36, SOAK_DEFENSE = 37, BALANCE_TEST_DEF = 38, VD_SHOW = 39, 
			
			POOL_DISPLAY = 40, MIN_DAMAGE = 41, END = 42;

	
	
	public SolveAttackWizard(MainPanel combatStatsPanel, PlayCharacter c) {
		panel = combatStatsPanel;
		this.c = c;
		state = new boolean[statesNum]; value = new int[valueNum];
		for (int i = 0; i < statesNum; ++i) state[i] = false;
		for (int i = 0; i < valueNum; ++i) value[i] = 0;
		state[forwardEnabled] = true;
		queue = new ArrayList<Integer>();
		panes = new ArrayList<SAWPanel>();
		queue.add(START);
		
		position = 0;
		panes.add(new StartSAW(this));
		remodel();
	}
	
	public void remodel() {
		removeAll();
		
		setLayout(new BorderLayout());
		
		String[] path = new String[position + 1];
		for (int i = 0; i <= position; ++i)
			path[i] = listName(queue.get(i));
		JList<String> l = new JList<String>(path);
		l.setEnabled(false);
		add(l, BorderLayout.WEST);
		
		// if there was no panel generation, I will generate it myself
		if (panes.size() < position + 1) generatePanel();
		int pType = queue.get(position);
		if ((pType == SIMPLE_ATTACK) && (value[attackNumber] > 0)) value[currentAttack] ++;
		
		JPanel foot = new JPanel(new BorderLayout()), flower = new JPanel(new FlowLayout());
		JButton next = new JButton(pType == END ? "Applica" : "Avanti >"), back = new JButton("< Indietro"),
				canc = new JButton("Annulla");
		canc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				superDialog.dispose();
			}
		});
		next.setEnabled(state[forwardEnabled]);
		back.setEnabled(state[backwardEnabled] && position > 0);
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (queue.get(position) == END) {
					// TODO apply effects to character
					superDialog.dispose();
				}
				// else
				Utils.log("Position" + position);
				panes.get(position).nextInvoked();
				Utils.log("nextInv returned. queue.size = " + queue.size());
				if (queue.size() > position + 1) {
					position++;
					remodel();
				}
				else superDialog.dispose();
			}
		});
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (position > 0) {
					position --;
					remodel();
				}
			}
		});
		flower.add(canc);
		foot.add(back, BorderLayout.WEST);
		foot.add(next, BorderLayout.EAST);
		foot.add(flower, BorderLayout.CENTER);
		
		add(foot, BorderLayout.SOUTH);
		add((Component) panes.get(position), BorderLayout.CENTER);
		repaint();
		revalidate();
	}
	
	private String listName(Integer integer) {
		switch (integer) {
		case START: 			return "Inizia";
		case ATTACK: 			return "Scegli come attaccare";
		case DEFEND: 			return "Scegli come difenderti";
		case FLURRY: 			return "Imposta il turbine";
		case SIMPLE_ATTACK: 	return "Attacco Semplice";
		case DEF_CHOOSER: 		return "Difesa";
		case VD_VISUALIZE: 		return "Test sul Valore di Difesa";
		case PRECISION_ROLL: 	return "Test sul VD avversario";
		
		default: 				return "Codice non conosciuto: " + integer;
		}
	}
	
	public void setState(int i, boolean b) {
		state[i] = b;
	}
	public boolean getState(int i) {
		return state[i];
	}
	public void setValue(int i, int b) {
		value[i] = b;
	}
	public int getValue(int i) {
		return value[i];
	}

	public void notifyChange() {
		panel.notifyChange();
	}
		
	public void addPane(int pane, SAWPanel nextPanel) {
		queue.add(position + 1, pane);
			/* after deciding how to attack, I show how pool is summed up and throw dices...
			// (NB attacks automatically show via setNextPane the pool used. Throw_result sets
			// the remaining dices or goes to the minimum via setLastPane
			queue.add(DICE_THROWER);
			queue.add(THROW_RESULT_DISPLAY);
			
			// test for balance. furthermore, if I hit, I throw for the raw damage...
			queue.add(BALANCE_TEST_ATTACK);
			// if balance must be determined, the panel itself will ask.
			queue.add(DICE_THROWER);
			queue.add(THROW_RESULT_DISPLAY);
			
			// which must pass hardness. If it passes again, we go against soak. etc
			*/
		if (nextPanel != null) panes.add(nextPanel);
	}
	
	private void generatePanel() {
		switch (queue.get(position)) {
		case ATTACK: {
			panes.add(new AttackSAW(this));
			break;
		}
		case DEFEND: {
			panes.add(new DefendSAW(this));
			break;
		}
		case FLURRY: {
			panes.add(new FlurrySAW(this));
			break;
		}
		case SIMPLE_ATTACK: {
			panes.add(new SimpleAttackSAW(this, state[attacking], c));
			break;
		}
		case PRECISION_ROLL: {
			panes.add(new PrecisionRollSAW(this, c));
		}
		}
	}

	public void setLastPane(int index, SAWPanel pane) {
		while (queue.size() > position) queue.remove(position + 1);
		queue.add(index);
		panes.add(pane);
		position++;
		remodel();
	}
	
	public void setDialog(JDialog d) {
		super.setDialog(d);
		superDialog.setPreferredSize(new Dimension(500, 600));
	}

	public void setMovement(Movement movement) {
		this.mov = movement;
	}

	public Movement getMovement() {
		return mov;
	}

	public int getFlurryPenalty() {
		if (value[attackNumber] > 1 && state[attacking]) return value[attackNumber] + value[currentAttack];
		else return 0;
	}

	@Override
	public String getTitle() {
		return "Risolvi Attacco";
	}

}
