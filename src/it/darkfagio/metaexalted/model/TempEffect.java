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

import it.darkfagio.utils.Sequences;
import it.darkfagio.metaexalted.utils.Utils;

public class TempEffect {
		// These are inter-cumulative
	public static final short
		T_NATURAL = 0, T_EQUIP = 1, T_CHARM = 2, T_FORM = 3, T_SITUATION = 4, T_MOVEMENT = 5, T_OTHER = 6;
	
	public static final String[] typeName = {"Naturale", "Equipaggiamento", "Potere", "Forma",
		"Situazionale", "Azione", "Altro"};
	
	public static final boolean ACTIVE = true, INACTIVE = false;
	
	public static final short
		STRENGTH = 0, DEXTERITY = 1, STAMINA = 2,
		CHARISMA = 3, MANIPULATION = 4, APPEARENCE = 5,
		INTELLIGENCE = 6, PERCEPTION = 7, WITS = 8,
		
		ARCHERY = 10, MARTIAL_ARTS = 11, MELEE = 12, THROWN = 13, WAR = 14,
		INTEGRITY = 15, PERFORMANCE = 16, PRESENCE = 17, RESISTANCE = 18, SURVIVAL = 19,
		CRAFT = 20, INVESTIGATION = 21, LORE = 22, MEDICINE = 23, OCCULT = 24,
		ATHLETICS = 25, AWARENESS = 26, DODGE = 27, LARCENY = 28, STEALTH = 29,
		BUREAUCRACY = 30, LINGUISTICS = 31, RIDE = 32, SAIL = 33, SOCIALIZE = 34,
		
		WILLPOWER = 40, PERMANENTESSENCE = 41, PERSONALESSENCE = 42, PERIPHERALESSENCE = 43,
		VALOR = 44, COMPASSION = 45, TEMPERANCE = 46, CONVINCTION = 47,
		
		MENTALPARRYDV = 60, MENTALDODGEDV = 61, MENTALPRECISION = 62,
		PHYSICALPARRYDVHEAD = 63, PHYSICALPARRYDVBODY = 64,	PHYSICALPARRYDVARMDX = 65,
		PHYSICALPARRYDVARMSX = 66, PHYSICALPARRYDVLEGDX = 67, PHYSICALPARRYDVLEGSX = 68,
		PHYSICALDODGEDVHEAD = 69, PHYSICALDODGEVBODY = 70, PHYSICALDODGEDVARMDX = 71,
		PHYSICALDODGEDVARMSX = 72, PHYSICALDODGEDVLEGDX = 73, PHYSICALDODGEDVLEGSX = 74,
		PHYSICALPRECISION = 75, MOVEMENT = 76, HARDNESS = 77, PARRYBALANCE = 78,
		DODGEBALANCE = 79, SPEED = 80, MINIMUMDAMAGE = 81,
		
		ALLDVS = 82, ALLPHYSDVS = 83, ALLMENTALDVS = 84, ALLPRECISIONS = 85,
		ALLPHYSPRECISIONS = 86, ALLMENTALPRECISIONS = 87, ALLPHYSSTATS = 88, ALLMENTALSTATS = 89,
		ALLDODGES = 90, ALLPARRIES = 91,
		
		BASHSOAK = 92, LETHALSOAK = 93, AGGRAVATEDSOAK = 94, MENTALSOAK = 95, ARMENTALSOAK = 96,
		ARBASHSOAKHEAD = 97, ARBASHSOAKBODY = 98, ARBASHSOAKRARM = 99,
		ARBASHSOAKLARM = 100, ARBASHSOAKRLEG = 101, ARBASHSOAKLLEG = 102,
		ARLETHALSOAKHEAD = 103, ARLETHALSOAKBODY = 104, ARLETHALSOAKRARM = 105,
		ARLETHALSOAKLARM = 106,	ARLETHALSOAKRLEG = 107,	ARLETHALSOAKLLEG = 108,
		ARAGGSOAKHEAD = 109, ARAGGSOAKBODY = 110, ARAGGSOAKRARM = 111,
		ARAGGSOAKLARM = 112, ARAGGSOAKRLEG = 113, ARAGGSOAKLLEG = 114;
	
	public static final String[] poolsPenaltyName = {
		"Volontà", "Ess. Permanente", "Ess. Personale", "Ess. Periferica", "Valore",
		"Compassione", "Temperanza", "Convinzione"};
	
	public static final String[] battleStatsPenaltyName = {
		"Parata Mentale", "Schivata Mentale", "Precisione Mentale", "Parata (Testa)",
		"Parata (Corpo)", "Parata (Braccio Dx)", "Parata (Braccio Sx)",
		"Parata (Gamba Dx)", "Parata (Gamba Sx)", "Schivata (Testa)",
		"Schivata (Corpo)", "Schivata (Braccio Dx)", "Schivata (Braccio Sx)",
		"Schivata (Gamba Dx)", "Schivata (Gamba Sx)", "Movimento", "Durezza",
		"Equilibrio (Parata)", "Equilibrio (Schivata)", "Velocità", "Danno Minimo" };

	public static final String[] generalPenaltyName = {
		"Valori di Difesa", "VD Fisiche", "VD Mentali", "Precisioni", "Precisioni Fisiche", "Precisioni Mentali",
		"Statistiche Fisiche", "Statistiche Mentali", "Schivate", "Parate" };
	
	public static final String[] soakPenaltyName = {
		"Ass.to da Urto", "Ass.to Letale", "Ass.to Aggravato", "Ass.to Mentale", "Ass.to Coraz. Mentale",
		"Ass.to Coraz. U (Testa)", "Ass.to Coraz. U (Tronco)", "Ass.to Coraz. U (Braccio Dx)", "Ass.to Coraz. U (Braccio Sx)",
		"Ass.to Coraz. U (Gamba Dx)", "Ass.to Coraz. U (Gamba Sx)", "Ass.to Coraz. L (Testa)", "Ass.to Coraz. L (Tronco)",
		"Ass.to Coraz. L (Braccio Dx)", "Ass.to Coraz. L (Braccio Sx)", "Ass.to Coraz. L (Gamba Dx)", "Ass.to Coraz. L (Gamba Sx)",
		"Ass.to Coraz. A (Testa)", "Ass.to Coraz. A (Tronco)", "Ass.to Coraz. A (Braccio Dx)", "Ass.to Coraz. A (Braccio Sx)",
		"Ass.to Coraz. A (Gamba Dx)", "Ass.to Coraz. A (Gamba Sx)" };
	
	public static int poolPenaltyNumber() {
		return poolsPenaltyName.length;
	}

	public static int battleStatsPenaltyNumber() {
		return battleStatsPenaltyName.length;
	}
	
	public static int generalPenaltyNumber() {
		return generalPenaltyName.length;
	}
	
	public static int soakPenaltyNumber() {
		return soakPenaltyName.length;
	}
		
	public static String getBonusName(int index) {
		if (index < 10) return Utils.attribute[index];
		index -= 10;
		if (index < 30) return Utils.ability[index];
		index -= 30;
		if (index < poolPenaltyNumber()) return poolsPenaltyName[index];
		index -= (poolPenaltyNumber() + 13);
		if (index < battleStatsPenaltyNumber()) return battleStatsPenaltyName[index];
		index -= battleStatsPenaltyNumber();
		if (index < generalPenaltyNumber()) return generalPenaltyName[index];
		index -= generalPenaltyNumber();
		return soakPenaltyName[index];
	}
	
	public static String[] getBonusNameList() {
		String[] res = Sequences.concatenate(Utils.attribute, Utils.ability);
		String[] res2 = Sequences.concatenate(poolsPenaltyName, battleStatsPenaltyName);
		String[] res3 = Sequences.concatenate(generalPenaltyName, soakPenaltyName);
		return Sequences.concatenate(Sequences.concatenate(res, res2), res3);
	}
	
	public static int additionalEffectNumber() {
		return poolPenaltyNumber() + generalPenaltyNumber() + battleStatsPenaltyNumber() + soakPenaltyNumber();
	}
	
	private String description;
	private short position;
	private short value;
	private boolean status;
	private short type;
	
	public TempEffect() {
		setDescription("");
		setPosition((short) 0);
		setValue((short) 0);
		setType((short) 0); setStatus(INACTIVE);
	}
	
	public TempEffect(String trim, short trim2, short parseShort, boolean active, short typ) {
		description = trim;
		position = trim2;
		value = parseShort;
		type = typ;
		status = active;
	}

	public String getStringPreview() {
		return description + " (" + typeName[type] + "): " + getBonusName(position) + " " + (value > 0 ? "+" : "") + value
				+ (status ? "" : "(Inattivo)");
	}
	
	public String toString() {
		return getDescription() + ",.," + getPosition() + ",.," + getValue() + ",.," + type + ",.," + status;
	}
	
	public void fromString(String s) {
		String[] r = s.trim().split(",.,");
		setDescription(r[0]);
		setPosition(Short.parseShort(r[1]));
		setValue(Short.parseShort(r[2]));
		type = Short.parseShort(r[3]);
		status = Boolean.parseBoolean(r[4]);
	}

	public static TempEffect factoryFromString(String string) {
		TempEffect n = new TempEffect();
		n.fromString(string);
		return n;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getPosition() {
		return position;
	}

	public void setPosition(short position) {
		this.position = position;
	}

	public short getValue() {
		return value;
	}

	public short setValue(short value) {
		this.value = value;
		return value;
	}
	
	public boolean equals(TempEffect t) {
		return (description.equalsIgnoreCase(t.getDescription()) && (value == t.getValue())
				&& (position == t.getPosition()) && (type == t.getType()));
	}

	public TempEffect getReverseEffect() {
		return new TempEffect(description, position, (short) (0 - value), status, type);
	}

	public boolean isActive() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public void setAs(TempEffect effect) {
		type = effect.type;
		status = effect.status;
		description = effect.description;
		position = effect.position;
		value = effect.value;
	}

}
