package it.darkfagio.metaexalted.model;

import it.darkfagio.utils.Sequences;

public class Attributes {
	
	public static final int
		STRENGTH = 0, DEXTERITY = 1, STAMINA = 2,
		CHARISMA = 3, MANIPULATION = 4, APPEARENCE = 5,
		INTELLIGENCE = 6, PERCEPTION = 7, WITS = 8;

	private PlayCharacter c;
	private short[] attributes;
	
	
	public Attributes(PlayCharacter ch) {
		attributes = new short[9];
		for (short i = 0; i < 9; ++i) {
			attributes[i] = 0;
		}
		c = ch;
	}
	
	public Attributes(PlayCharacter ch, short[] attrs) {
		if (attrs.length == 9)
			attributes = attrs;
		c = ch;
	}
	
	public String toString() {
		return Sequences.toString(attributes);
	}
	
	public void fromString(String s) {
		attributes = Sequences.shortArrayFromString(s, ", ");
	}
				
	public short[] getAsArray() {
		return attributes;
	}
	public short getAttribute(int index) {
		return (short) (attributes[index] + c.effects.getBonus(index));
	}

	public void setAttribute(int i, short p) {
		if (i < 9) attributes[i] = p;
	}
	
}
