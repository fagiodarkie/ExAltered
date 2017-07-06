package it.darkfagio.metaexalted.model;

import it.darkfagio.utils.Sequences;
import it.darkfagio.metaexalted.utils.Utils;

public class Minimum {
	
	// minimum stats are chosen from: * attributes * abilities * pool stats.
	private short stat;
	private short value;
	
	public Minimum(short s, short v) {
		setStat(s);
		setValue(v);
	}
	
	public Minimum(String s) {
		fromString(s);
	}
	
	public Minimum() {
		value = stat = 0;
	}

	public short getPenalty(short statVal) {
		if (statVal >= value) return 0;
		return (short) (statVal - value);
	}
	
	public static String[] getStatNames() {
		return Sequences.concatenate(Utils.attribute, Sequences.concatenate(Utils.ability, TempEffect.poolsPenaltyName));
	}
	public static int convertListIndexToStat(int x) {
		if (x > 8) x++;
		if (x > 34) x += 5;
		return x;
	}
	public static int convertStatToListIndex(int x) {
		if (x > 8) x--;
		if (x > 33) x -= 5;
		return x;
	}
	
	public String getStringPreview() {
		String s = TempEffect.getBonusName(stat);
				
		return s + ": " + value;
	}
	
	public boolean equals(Minimum m) {
		return (stat == m.stat) && (value == m.value);
	}
	
	public void fromString(String s) {
		String[] r = s.split("\\.");
		setStat(Short.parseShort(r[0]));
		setValue(Short.parseShort(r[1]));
	}
	
	public String toString() {
		return getStat() + "." + getValue();
	}
	
	public static Minimum factoryFromString(String s) {
		Minimum m = new Minimum(s);
		return m;
	}

	public short getValue() {
		return value;
	}

	public void setValue(short value) {
		this.value = value;
	}

	public short getStat() {
		return stat;
	}

	public void setStat(short stat) {
		this.stat = stat;
	}

}
