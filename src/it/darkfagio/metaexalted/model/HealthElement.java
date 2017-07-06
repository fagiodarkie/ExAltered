package it.darkfagio.metaexalted.model;

import it.darkfagio.utils.Sequences;

public class HealthElement {
	
	public static final short
		L0 = 0,
		L1 = -1,
		L2 = -2,
		L3 = -3,
		INC = Short.MIN_VALUE;

	// Head, Body, etc...
	private short bodyPart;
	
	// number of health levels (1 to 4?) 
	private short healthLevelNumber;
	// 3-element array: #Bashing, #Lethal, #Aggravate
	private short[] damage;
	// can be 0, -1, -2, ... inc = Short.MIN_SHORT
	private short[] penaltyLevel;
	// number of slots for each level
	private short[] totalHealthLevels;
	
	public HealthElement() {
	}
	
	public HealthElement(short bodyPart) {
		damage = new short[3];
		for (int i = 0; i < 3; ++i) {
			damage[i] = 0;
		}
		assignBodyPart(bodyPart);
	}
	
	private void assignBodyPart(short bodyPart) {
		this.bodyPart = bodyPart;
		switch (bodyPart) {
		case HealthManager.ARM_DX:
		case HealthManager.ARM_SX:
		case HealthManager.LEG_DX:
		case HealthManager.LEG_SX: {
			totalHealthLevels = new short[3];
			penaltyLevel = new short[3];
			healthLevelNumber = 3;
			totalHealthLevels[0] = totalHealthLevels[1] = totalHealthLevels[2] = 0;
			penaltyLevel[0] = L0;
			penaltyLevel[1] = L1;
			penaltyLevel[2] = INC;
			break;
		}
		case HealthManager.HEAD: {
			totalHealthLevels = new short[3];
			penaltyLevel = new short[3];
			healthLevelNumber = 3;
			totalHealthLevels[0] = totalHealthLevels[1] = totalHealthLevels[2] = 0;
			penaltyLevel[0] = L0;
			penaltyLevel[1] = L3;
			penaltyLevel[2] = INC;
			break;
		}
		case HealthManager.BODY: {
			totalHealthLevels = new short[4];
			penaltyLevel = new short[4];
			healthLevelNumber = 4;
			totalHealthLevels[0] = totalHealthLevels[1]	= totalHealthLevels[2]
					= totalHealthLevels[3] = 0;
			penaltyLevel[0] = L0;
			penaltyLevel[1] = L1;
			penaltyLevel[2] = L2;
			penaltyLevel[3] = INC;
			break;
		}
		default:
		}
		// have at least 1 INC level for each body part 
		totalHealthLevels[totalHealthLevels.length - 1] = 1;
	}
	
	public void heal(short h, short type) {
		damage[type] -= ( h < damage[type] ? h : damage[type]);
	}
	
	public void convert(short startType, short endType, short amount) {
		short realAmount = damage[startType] > amount ? amount : damage[startType];
		if (endType >= 0) {
			// -1 stands for heal
			damage[endType] += realAmount;
		} 
		damage[startType] -= realAmount;
	}
	
	public String toString() {
		return getBodyPart() + ":-:" + Sequences.toString(damage) + ":-:" + Sequences.toString(totalHealthLevels); 
	}
	
	public void fromString(String s) {
		String[] res = s.trim().split(":-:");
		assignBodyPart(Short.parseShort(res[0]));

		setTotalHealthLevels(new short[getHealthLevelNumber()]);
		setDamageLevels(new short[3]);
		damage = Sequences.shortArrayFromString(res[1], ", ");
		totalHealthLevels = Sequences.shortArrayFromString(res[2], ", ");
	}
	
	public static HealthElement factoryFromString(String s) {
		HealthElement h = new HealthElement();
		h.fromString(s);
		return h;
	}
	

	public short getTotalDamages() {
		return (short) (damage[0] + damage[1] + damage[2]);
	}

	public short getPenalty() {
		short penalty = 0;
		short damageNumber = getTotalDamages();
		int i = 0;
		while ((i < healthLevelNumber - 1) && (damageNumber > 0)) {
			penalty = penaltyLevel[i];
			damageNumber -= totalHealthLevels[i];
			++i;
		}
		return penalty;
	}
	
	public boolean isIncapacitated() {
		return getTotalDamages() == getTotalHealthLevelsNumber();
	}
	
	public short getTotalHealthLevelsNumber() {
		short healthLevels = 0;
		for (int i = 0; i < totalHealthLevels.length; ++i)
			healthLevels += totalHealthLevels[i];
		return healthLevels;
	}
	
	public short getNonIncapacitatedLevelNumber() {
		return (short) (getTotalHealthLevelsNumber() - totalHealthLevels[totalHealthLevels.length - 1]);
	}

	public void addDamage(short amount, short type) {
		// no cheating: if you want to heal, use "heal"!
		if (amount < 0) return;
		if (type == HealthManager.BASHING) {
			// special treatment: before incapaciting a limb all the previous blocks must be filled with lethal or aggravated damages.
			short bias_la = (short) (getNonIncapacitatedLevelNumber() - damage[1] - damage[2]);
			if (bias_la > 0) {
				// there are still levels before nonincapacitated.
				if (bias_la - damage[0] >= amount) {
					// must still reach the incapacitated levels, even after this damage.
					damage[type] += amount;
					return;
				} else {
					// current damages + damages to take are superior to nonincapacitated. first: convert bashing to lethal
					// until we reach nonincapacitated.
					while (amount > 0) {
						// there are still bashing to fill.
						if (getNonIncapacitatedLevelNumber() > getTotalDamages())
							damage[0]++;
						// all bashing have been filled, we convert them into lethal.
						else if ((getNonIncapacitatedLevelNumber() == getTotalDamages()) && (damage[0] > 0))
							convert(HealthManager.BASHING, HealthManager.LETHAL, (short) 1);
						// we start to fill INC levels with bashing, if we don't surpass total HL.
						else if ((getNonIncapacitatedLevelNumber() <= getTotalDamages()) && (damage[0] == 0)
								&& (getTotalHealthLevelsNumber() >= getTotalDamages() + 1))
							damage[0]++;
						// when possible, we convert bashing into lethal in INC levels also.
						else if ((getNonIncapacitatedLevelNumber() <= getTotalDamages()) && (damage[0] > 0))
							convert(HealthManager.BASHING, HealthManager.LETHAL, (short) 1);
						amount --;
					}
				}
			} else {
				// bias_la <= 0. additional bashing transforms into lethal whenever possible.
				while ((amount > 0)) {
					// add a bashing
					if ((damage[0] == 0) && (getTotalHealthLevelsNumber() >= getTotalDamages() + 1))
						damage[0] ++;
					// convert a bashing into lethal
					else if (damage[0] > 0)
						convert(HealthManager.BASHING, HealthManager.LETHAL, (short) 1);
					// reached max hl.
					else return;
					amount --;
				}
			}
		} else {
			// damage is not bashing.
			// first, normalize the amount, which may not be higher than the total damages that can be taken.
			if (amount > (getTotalHealthLevelsNumber() - getTotalDamages())) {
				short newAmount = (short) (getTotalHealthLevelsNumber() - getTotalDamages());
				// continue only if damage can be applied.
				if (newAmount > 0) amount = newAmount;
				else return;
			}
			// we can stand this, so apply the damage.
			damage[type] += amount;
		}
	}
	
	public void addHealthLevel(short level) {
		addHealthLevel(level, (short) 1);
	}
	
	public void addHealthLevel(short level, short amount) {
		totalHealthLevels[level] += amount;
	}

	
	public short getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(short bodyPart) {
		this.bodyPart = bodyPart;
	}

	public short getHealthLevelNumber() {
		return healthLevelNumber;
	}

	public void setHealthLevelNumber(short healthLevelNumber) {
		this.healthLevelNumber = healthLevelNumber;
	}

	public short[] getDamageLevels() {
		return damage;
	}

	public void setDamageLevels(short[] damageLevels) {
		this.damage = damageLevels;
	}

	public short[] getPenaltyLevels() {
		return penaltyLevel;
	}

	public void setPenaltyLevels(short[] healthLevels) {
		this.penaltyLevel = healthLevels;
	}

	public short[] getTotalHealthLevels() {
		return totalHealthLevels;
	}

	public void setTotalHealthLevels(short[] totalHealthLevels) {
		this.totalHealthLevels = totalHealthLevels;
	}

	public void setTotalHealthLevels(short levIndex, short amount) {
		totalHealthLevels[levIndex] = amount;
	}
	public void addHealthLevels(short levIndex, short amount) {
		totalHealthLevels[levIndex] += amount;
	}
	public void removeHealthLevels(short levIndex, short amount) {
		if (totalHealthLevels[levIndex] >= amount) 
			totalHealthLevels[levIndex] -= amount;
		else totalHealthLevels[levIndex] = 0;
	}

}
