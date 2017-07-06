package it.darkfagio.metaexalted.model;

import it.darkfagio.utils.Sequences;

public class Weapon extends Item {

	private String
		notes;
	
	private String ATP, ATD;
	private Minimum[] minimums;
	
	public final static short
		speed = 0,
		phisPrecBonus = 1,
		phisDefBonus = 2,
		menPrecBonus = 3,
		menDefBonus = 4,
		drill = 5,
		drillBonus = 6,
		commotion = 7,
		cadence = 8,
		durability = 9,
		stance = 10,
		addedStats = 11;
	
	// bashing, lethal, aggravate, mental
	private short[]
			damageBonus,
			stats,
			armonizedStats;
	
	public Weapon() {
		super();
		minimums = new Minimum[0];
		notes = "";
		ATP = ATD = "";
		// U, L, A, M, aU, aL, aA, aM
		damageBonus = new short[8];
		stats = new short[addedStats];
		armonizedStats = new short[addedStats];
		for (int i = 0; i < 8; ++i) {
			damageBonus[i] = 0;
		}
		for (int i = 0; i < addedStats; ++i) {
			stats[i] = 0;
			armonizedStats[i] = 0;
		}
		
	}
	
	public static Weapon factoryFromString(String string) {
		Weapon a = new Weapon();
		a.fromString(string);
		return a;
	}
	
	@Override
	public String toString() {
		return super.toString() + ":-:" + "[" + notes + "]" + ":-:" + ATD + ":-:" + ATP
				 + ":-:" + Sequences.toString(stats, ",__,") + ":-:" + Sequences.toString(armonizedStats, ",__,")
				 + ":-:" + Sequences.toString(damageBonus, ",__,") + ":-:" + Sequences.toString(minimums, ",__,");
		}
	
	@Override 
	public void fromString(String s) {
		String[] res = s.split(":-:");
		String superString = res[0];
		for (int i = 1; i < Item.usedFields; ++i)
			superString += ":-:" + res[i];
		super.fromString(superString);
		notes = res[Item.usedFields].substring(1, res[Item.usedFields].length() - 1);
		ATD = res[Item.usedFields + 1];
		ATP = res[Item.usedFields + 2];
		stats = Sequences.shortArrayFromString(res[Item.usedFields + 3], ",__,");
		setArmoStats(Sequences.shortArrayFromString(res[Item.usedFields + 4], ",__,"));
		damageBonus = Sequences.shortArrayFromString(res[Item.usedFields + 5], ",__,");
		String[] ms = Sequences.divideStringPer(res[Item.usedFields + 6], ",__,");
		minimums = new Minimum[ms.length];
		for (int i = 0; i < ms.length; ++i)
			minimums[i] = new Minimum(ms[i]);
	}

	public Minimum[] getMinimums() {
		return minimums;
	}
	public void addMinimum(Minimum m) {
		Minimum[] tmp = new Minimum[minimums.length + 1];
		for (int i = 0; i < minimums.length; ++i)
			tmp[i] = minimums[i];
		tmp[minimums.length] = m;
		minimums = tmp;
	}
	public void removeMinimum(Minimum m) {
		for (int i = 0; i < minimums.length; ++i)
			if (minimums[i].equals(m)) {
				removeMinimum(i);
				break;
			}
	}
	private void removeMinimum(int x) {
		Minimum[] tmp = new Minimum[minimums.length - 1];
		for (int i = 0; i < minimums.length; ++i)
			if (i < x) tmp[i] = minimums[i];
			else if (i > x) tmp[i] = minimums[i - 1];
		minimums = tmp;
	}

	public short getNormalStat(int i) {
		return stats[i];
	}
	public void setNormalStat(int i, short val) {
		stats[i] = val;
	}
		
	public short[] getDamageBonus() {
		return damageBonus;
	}
	
	public void setATP(String a) {
		ATP = a;
	}
	public void setATD(String a) {
		ATD = a;
	}
	public String getATP() {
		return ATP;
	}
	public String getATD() {
		return ATD;
	}
	
	public void setDamageBonus(short[] damageBonus) {
		this.damageBonus = damageBonus;
	}
	public short getDamageBonus(short i) {
		return damageBonus[i];
	}
	public void setDamageBonus(short i, short damageBonus) {
		this.damageBonus[i] = damageBonus;
	}

	public short[] getArmoStats() {
		return armonizedStats;
	}
	public short getArmoStat(int i) {
		return armonizedStats[i];
	}	
	public void setArmoStats(short[] armonizedStats) {
		this.armonizedStats = armonizedStats;
	}
	public void setArmoStat(short i, short damageBonus) {
		this.armonizedStats[i] = damageBonus;
	}
	
	public short[] getStats() {
		if(attuned) return armonizedStats;
		else return stats;
	}
	
	public short getStat(int i) {
		return getStats()[i];
	}

	public void setNotes(String trim) {
		notes = trim;
	}
	public String getNotes() {
		return notes;
	}

	public void setStats(short[] stats2) {
		stats = stats2;
	}
	public void setAttStats(short[] stats2) {
		armonizedStats = stats2;
	}
	
	public short[] getPhysicalDamage() {
		short[] x1 = {damageBonus[0], damageBonus[1], damageBonus[2]},
				x2 = {damageBonus[4], damageBonus[5], damageBonus[6]};
		if (attuned) return x2;
		return x1;
	}
	
	public short getMentalDamage() {
		if(attuned) return damageBonus[3];
		return damageBonus[7];
	}
	
	public short[] getNormalDamage() {
		short[] x1 = {damageBonus[0], damageBonus[1], damageBonus[2], damageBonus[3]};
		return x1;
	}
	public short[] getAttunedDamage() {
		short[] x1 = {damageBonus[4], damageBonus[5], damageBonus[6], damageBonus[7]};
		return x1;
	}
	public short getNormalDamage(int i) {
		return getNormalDamage()[i];
	}
	public short getAttunedDamage(int i) {
		return getAttunedDamage()[i];
	}
	public short getDamage(int i) {
		if ((i % 4) < 3) return getPhysicalDamage()[i % 4];
		return getMentalDamage();
	}
	public short[] getDamage() {
		if (attuned) return getNormalDamage();
		return getAttunedDamage();
	}
	
	
	public boolean equalsTo(Weapon weapon) {
		if (!super.equalsTo((Item) weapon)) return false;
		if (!notes.equalsIgnoreCase(weapon.notes)) return false;
		for (int i = 0; i < 8; ++i) {
			if ((damageBonus[i] != weapon.damageBonus[i])) return false;
		}
		for (int i = 0; i < 12; ++i)
			if ((stats[i] != weapon.stats[i]) || (armonizedStats[i] != weapon.armonizedStats[i])) return false;
		if ((ATD != weapon.ATD) || (ATP != weapon.ATP))
			return false;
		return true;
	}

	public void setNormalDamage(short[] bonus) {
		damageBonus[0] = bonus[0];
		damageBonus[1] = bonus[1];
		damageBonus[2] = bonus[2];
		damageBonus[3] = bonus[3];
	}

	public void setArmoDamage(short[] bonus) {
		damageBonus[4] = bonus[0];
		damageBonus[5] = bonus[1];
		damageBonus[6] = bonus[2];
		damageBonus[7] = bonus[3];
	}

	public void setMinimums(Minimum[] mins) {
		minimums = mins;
	}

}
