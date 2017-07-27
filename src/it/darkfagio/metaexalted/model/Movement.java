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

package it.darkfagio.metaexalted.model;

import java.util.ArrayList;
import java.util.List;

import it.darkfagio.utils.Sequences;

public class Movement {
	
	private short type, status, color;
	private List<TempEffect> stats;
	private String name;
	
	// movements
	public static short
		move = 0, willingCollision = 1, unwillingCollision = 2,
		weaponAttack = 3, intercept = 4, charm = 5,
		
		shift = 6, charge = 7, attackAfterCharge = 8,
		jumpFromStill = 9, bunnyhop = 10,
		target = 11, guard = 12, luckyRoll = 13, unluckyRoll = 14,
		luckyRegioselective = 15, unluckyRegioselective = 16,
		luckyCounter = 17, unluckyCounter = 18,
		
		feint = 19, hold = 20, choke = 21, 
		talk = 22, coordinateAttack = 23, changeWeapon = 24,
		standUp = 25, blockMovement = 26, defendOther = 27,
		
		basicMovementNumber = 28;
	
	public static String[] names = {"Movimento", "Collisione (volontaria)", "Collisione (involontaria)",
		"Attacco con arma", "Intercetto", "Prodigio",
		
		"Scatto", "Carica", "Attacco dopo la carica",
		"Salto da Fermo", "Bunnyoppo", "Mira", "In Guardia", "Rototraslo (favorevole)", "Rototraslo (non favorevole)",
		"Difesa Regioselettiva (favorevole)", "Difesa Regioselettiva (non favorevole)",
		"Contrattacco (favorevole)", "Contrattacco (non favorevole)",
		
		"Finta", "Presa", "Soffocamento", "Discorso", "Attacco Coordinato", "Cambiare Arma",
		"Alzarsi", "Bloccare Movimento", "Difendere Altri"	};
	
	// status
	public static short
		noStatus = 0, inactive = 1, prone = 2;
	
	// color
	public static short
		noColor = 0, savageHit = 1, disarmMelee = 2, disarmRanged = 3,
		mutilate = 4, defile = 5, holdHit = 6;
		
		
	public static List<Movement> getBasicMovements() {
		
		List<Movement> l = new ArrayList<Movement>();
		
		for (short i = 0; i < basicMovementNumber; ++i)
			l.add(new Movement(i));
		
		return l;
	}
	
	
	public Movement() {
		stats = new ArrayList<TempEffect>();
		type = color = status = 0;
		name = "";
	}
	
	public Movement(short type) {
		this();
		this.type = type;
		this.name = names[type];
		switch(type) {
		// TODO
		}
	}

	public Movement(short type, short status) {
		this(type);
		this.status = status;
		
		switch(status) {
		// TODO
		}
	}

	public Movement(short type, short status, short color) {
		this(type, status);
		this.color = color;
		switch(color) {
		// TODO
		}
	}
	
	public static Movement factoryFromString(String string) {
		Movement m = new Movement();
		m.fromString(string);
		return m;
	}

	public void fromString(String string) {
		String[] res = string.split(":-:");
		stats = new ArrayList<TempEffect>();
		short[] r = Sequences.shortArrayFromString(res[0], "\\.");
		type = r[0]; status = r[1]; color = r[2];
		String[] te = Sequences.divideStringPer(res[1], ",_,");
		for (int i = 0; i < te.length; ++i) {
			stats.add(TempEffect.factoryFromString(te[i]));
		}
		name = res[3];
	}
	
	public String toString() {
		short[] r = {type, status, color};
		return Sequences.toString(r, ".") + ":-:" + Sequences.toString(stats, ",_,") + ":-:" + name;
	}


	public String getName() {
		return name;
	}
	public void setName(String n) {
		name = n;
	}
	
	public void addEffect(short position, TempEffect effect) {
		stats.add(position, effect);
	}
	public void removeEffect(short position) {
		stats.remove(position);
	}
	public TempEffect getEffect(short position) {
		return stats.get(position);
	}


	public static List<Movement> getAttackMovements() {
		List<Movement> l = new ArrayList<Movement>();
		
		// TODO
		return l;
	}


	public int getBonus(short tempBonus) {
		for (TempEffect t : stats)
			if (t.getPosition() == tempBonus) return t.getValue();
		return 0;
	}


	public short getColor() {
		return color;
	}


	public void setColor(short color) {
		this.color = color;
	}


	public short getStatus() {
		return status;
	}


	public void setStatus(short status) {
		this.status = status;
	}


	public short getType() {
		return type;
	}


	public void setType(short type) {
		this.type = type;
	}

}
