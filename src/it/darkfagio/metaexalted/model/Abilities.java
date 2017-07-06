package it.darkfagio.metaexalted.model;

import it.darkfagio.utils.Sequences;

public class Abilities {
	
	public static final short
		ARCHERY = 0, 
		MARTIAL_ARTS = 1,
		MELEE = 2,
		THROWN = 3,
		WAR = 4,
		INTEGRITY = 5,
		PERFORMANCE = 6,
		PRESENCE = 7,
		RESISTANCE = 8,
		SURVIVAL = 9,
		CRAFT = 10,
		INVESTIGATION = 11,
		LORE = 12,
		MEDICINE = 13,
		OCCULT = 14,
		ATHLETICS = 15,
		AWARENESS = 16,
		DODGE = 17,
		LARCENY = 18,
		STEALTH = 19,
		BUREAUCRACY = 20,
		LINGUISTICS = 21,
		RIDE = 22,
		SAIL = 23,
		SOCIALIZE = 24;

	private short[] ability;
	private String[] specNames;
	private short[] specBonus;
	private PlayCharacter c;
	
	private String[] otherSpecialization;
	private short[] otherSpecBonus;
	private short[] otherSpecAbility;

	private short pCA;

	private short mCA;
	
	
	public Abilities(PlayCharacter ch) {
		c = ch;
		ability = new short[25];
		specNames = new String[25];
		specBonus = new short[25];
		for (short i = 0; i < 25; ++i) {
			ability[i] = 0;
			specNames[i] = "";
			specBonus[i] = 0;
		}
		otherSpecialization = new String[0];
		otherSpecBonus = new short[0];	
		pCA = mCA = 0;
	}
		
	public String toString() {
		String s;
		
		s = Sequences.toString(ability) + "/" + Sequences.toString(specNames) + "/"
				+ Sequences.toString(specBonus) + "/" + "[" + pCA + ", " + mCA + "]/"
				+ (Sequences.toString(otherSpecialization) + "/" + Sequences.toString(otherSpecAbility) + "/" + Sequences.toString(otherSpecBonus));
		
		return s;
	}
	
	public void fromString(String s) {
		String[] res = s.trim().split("/");
		ability = Sequences.shortArrayFromString(res[0], ", ");
		String[] tempSpecNames = Sequences.divideStringPer(res[1], ", ");
		if (tempSpecNames.length < 25) { 
			specNames = new String[25];
			for (int i = 0; i < tempSpecNames.length; ++i)
				if ((tempSpecNames[i] != null) && (!tempSpecNames[i].equalsIgnoreCase("")))
					specNames[i] = tempSpecNames[i];
				else specNames[i] = "";
		} else specNames = tempSpecNames;
		specBonus = Sequences.shortArrayFromString(res[2], ", ");
		short[] r = Sequences.shortArrayFromString(res[3], ", ");
		pCA = r[0];
		mCA = r[1];
		otherSpecialization = Sequences.divideStringPer(res[4], ", ");
		otherSpecAbility = Sequences.shortArrayFromString(res[5], ", ");
		otherSpecBonus = Sequences.shortArrayFromString(res[6], ", ");
	}
	
	
	
	public void setSpecialization(short ability, String specName, short specValue) {
		specNames[ability] = specName;
		specBonus[ability] = specValue;
	}
	
	public void addSpec(String specName, short specAb, short specValue) {
		String[] oldCS = otherSpecialization.clone();
		short[] oldCSB = otherSpecBonus.clone(),
				oldSA = otherSpecAbility.clone();
		otherSpecialization = new String[oldCS.length + 1];
		otherSpecBonus = new short[oldCSB.length + 1];
		otherSpecAbility = new short[oldSA.length + 1];
		for (int i = 0; i < oldCSB.length; ++i) {
			otherSpecBonus[i] = oldCSB[i];
			otherSpecialization[i] = oldCS[i];
			otherSpecAbility[i] = oldSA[i];
		}
		otherSpecBonus[otherSpecBonus.length - 1] = specValue;
		otherSpecAbility[otherSpecBonus.length - 1] = specAb;
		otherSpecialization[otherSpecBonus.length - 1] = specName;		
	}
	
	
	public short[] getAsArray() {
		return ability;
	}
	public short[] getSpecArray() {
		return specBonus;
	}
	public String[] getSpecNames() {
		return specNames;
	}
	
	public String[] getOtherSpecs() {
		return otherSpecialization;
	}
	
	public short[] getOtherSpecsBonus() {
		return otherSpecBonus;
	}
	public short getOtherSpecAb(int i) {
		if (otherSpecialization.length > i)
			return otherSpecAbility[i];
		return CRAFT;
	}
	public String getOtherSpecs(int i) {
		if (otherSpecialization.length > i)
			return otherSpecialization[i];
		return null;
	}
	
	public short getCraftSpecsBonus(int i) {
		if (otherSpecialization.length > i)
			return otherSpecBonus[i];
		return 0;
	}

	public void setPhysicalCombatAbility(short p) {
		pCA = p;
	}

	public short getPhysicalCombatAbility() {
		return pCA;
	}

	public void setMentalCombatAbility(short p) {
		mCA = p;
	}
	public short getMentalCombatAbility() {
		return mCA;
	}

	public void setAbility(int i, short p) {
		if (i < 25) ability[i] = p;		
	}

	public short getAbility(int i) {
		return (short) (ability[i] + c.effects.getBonus(TempEffect.ARCHERY + i));
	}

	

}
