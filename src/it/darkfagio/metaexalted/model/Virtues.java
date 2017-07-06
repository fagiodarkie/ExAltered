package it.darkfagio.metaexalted.model;

import it.darkfagio.utils.Sequences;

public class Virtues {

	public static final String[] name = {"Convinzione", "Compassione", "Valore", "Temperanza"};
	public static short convinction = 0,
		compassion = 1,
		valor = 2,
		temperance = 3,
		usedConv = 4,
		usedComp = 5,
		usedVal = 6,
		usedTemp = 7,
		statNum = 8;
	
	private short[] stats;
	
	public Virtues() {
		stats = new short[statNum];
		for (int i = 0; i < statNum; ++i) stats[i] = 0;
	}
	
	public String toString() {
		return Sequences.toString(stats, ":-:");
	}
	
	public void fromString(String s) {
		String[] x = s.split(":-:");
		if (x.length == 4) {
			stats = new short[8];
			for (int i = 0; i < 4; ++i) {
				stats[i] = Short.parseShort(x[i].split("\\.")[0]);
				stats[i + 4] = Short.parseShort(x[i].split("\\.")[1]);
			}
		} else stats = Sequences.shortArrayFromString(s, ":-:");
	}
	
	public short getStat(short pos) {
		return stats[pos];
	}
	
	public void setStat(short pos, int val) {
		stats[pos] = (short) val;
	}
	
	public void consumeVirtue(int virt) {
		stats[virt + 4] ++;
	}
	
	public void restoreVirtue(int virt) {
		stats[virt + 4] --;
	}
	
}
