package it.darkfagio.metaexalted.model;

import it.darkfagio.utils.Sequences;
import java.util.ArrayList;
import java.util.List;

public class Pools {
	
	public static final boolean
		PERIPHERAL = true,
		PERSONAL = false;
	
	public static final short
		permanentEssence = 0,
		willpower = 1,
		logos = 2,
		serendipity = 3,
		limit = 4,
		usedPersonalEssence = 5,
		usedPeripheralEssence = 6,
		personalEssenceBonus = 7,
		peripheralEssenceBonus = 8,
		cachedPersonalEssence = 9,
		cachedPeripheralEssence = 10,
		usedWillpower = 11,
		perfectPercentage = 12,			// 
		imperfectPercentage = 13,		// 
		poolReserveType = 14;
	
	/*
	 * porzione celeste pu� sopportare oblio senza essere corrotta.
	 * porzione di oblio si corrompe alla morte.
	 * possanza imperitura: potenziamento della porzione celeste, dipende dalle schegge assorbite.
	 * negroplasia: l'oblio � un tumore, e si sottrae dalla porzione celeste.
	 * 
	 *  Logos: oblio / 10
	 * 
	 */
	
	private short[] stats;
	private Virtues v;
	private List<ExperienceLogEntry> experienceLog;
	private List<String> peripheralAttuned;

	@SuppressWarnings("unused")
	private PlayCharacter c;
	
	public Pools(PlayCharacter playCharacter) {
		stats = new short[15];
		for(int i = 0; i < 15; ++i)
			stats[i] = 0;
		v = new Virtues();
		c = playCharacter;
		// a minimum basis
		stats[willpower] = 1;
		stats[permanentEssence] = 1;
		experienceLog = new ArrayList<ExperienceLogEntry>();
		peripheralAttuned = new ArrayList<String>();
	}
	
	public String toString() {
		String r = Sequences.toString(stats, ",_,") + "/" + v.toString() + "/"
				+ Sequences.toString(experienceLog, ",_,") + "/" + Sequences.toString(peripheralAttuned, ",_,");
		return r;
	}
	
	public void fromString(String string) {
		String[] res = string.split("/");
		stats = Sequences.shortArrayFromString(res[0], ",_,");
		v.fromString(res[1]);
		String[] logs = Sequences.divideStringPer(res[2], ",_,");
		for (int i = 0; i < logs.length; ++i) {
			ExperienceLogEntry e = new ExperienceLogEntry();
			e.fromString(logs[i]);
			experienceLog.add(e);
		}
		String[] atts = Sequences.divideStringPer(res[3], ",_,");
		for (int i = 0; i < atts.length; ++i) {
			peripheralAttuned.add(atts[i]);
		}
	}
	
	public short getStat(int index) {
		return stats[index];
	}
	public short[] getStats() {
		return stats;
	}
	public void setStats(short[] newStats) {
		stats = newStats;
	}
	public void setStat(int index, short stat) {
		stats[index] = stat;
	}
	
	public short getPersonalEssence() {
		short res = stats[personalEssenceBonus];
		switch (stats[poolReserveType]) {
		case 0: res += (3*stats[permanentEssence] + stats[willpower]);
		
		}
		return res;
	}

	public short getPeripheralEssence() {
		short res = stats[peripheralEssenceBonus];
		switch (stats[poolReserveType]) {
		case 0: res += (7*stats[permanentEssence] + stats[willpower]
				+ v.getStat(Virtues.compassion) + v.getStat(Virtues.convinction)
				+ v.getStat(Virtues.temperance) + v.getStat(Virtues.valor)); 		
		}
		return res;
	}

	public void setVirtues(Virtues v) {
		this.v = v;
	}

	public short getCurrentPeripheralEssence() {
		return (short) (getPeripheralEssence() - stats[usedPeripheralEssence] - stats[cachedPeripheralEssence]);
	}

	public short getCurrentPersonalEssence() {
		return (short) (getPersonalEssence() - stats[usedPersonalEssence] - stats[cachedPersonalEssence]);
	}

	public Virtues getVirtues() {
		return v;
	}

	public void cacheEssence(short cachedEssence, boolean peripheral, String name) {
		if (peripheral) {
			stats[cachedPeripheralEssence] += cachedEssence;
			peripheralAttuned.add(name);
		}
		else stats[cachedPersonalEssence] += cachedEssence;
	}
	public void decacheEssence(short cachedEssence, String name) {
//		Utils.log(name + " searched in " + Sequences.toString(peripheralAttuned));
		for (int i = 0; i < peripheralAttuned.size(); ++i) {
			if (peripheralAttuned.get(i).equalsIgnoreCase(name)) {
				stats[cachedPeripheralEssence] -= cachedEssence;
				peripheralAttuned.remove(i);
				return;
			}
		}
		stats[cachedPersonalEssence] -= cachedEssence;
	}
	public void useEssence(short cachedEssence, boolean peripheral) {
		if (peripheral) stats[usedPeripheralEssence] += cachedEssence;
		else stats[usedPersonalEssence] += cachedEssence;
	}
	public void restoreEssence(short cachedEssence) {
		if (cachedEssence <= stats[usedPeripheralEssence]) stats[usedPeripheralEssence] -= cachedEssence;
		else {
			cachedEssence -= stats[usedPeripheralEssence];
			stats[usedPeripheralEssence] = 0;
			if (cachedEssence <= stats[usedPersonalEssence]) stats[usedPersonalEssence] -= cachedEssence;
			else stats[usedPersonalEssence] = 0;
		}
	}

	public void addStat(int index, short value) {
		stats[index] += value;
	}

}