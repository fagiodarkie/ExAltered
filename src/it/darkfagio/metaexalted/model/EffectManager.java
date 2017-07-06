package it.darkfagio.metaexalted.model;

import java.util.ArrayList;
import java.util.List;

import it.darkfagio.utils.Sequences;

public class EffectManager {

	private PlayCharacter c;
	public List<TempEffect> effs;
	
	public EffectManager(PlayCharacter playCharacter) {
		c = playCharacter;
		effs = new ArrayList<TempEffect>();
	}
	
	// meant to find out if a battleStat is physical, mental etc for general bonus.
	public static boolean equivalent(int pos1, int pos2) {
		if (pos1 == pos2) return true;
		if ((pos1 >= TempEffect.MENTALPARRYDV) && (pos1 <= TempEffect.PHYSICALPRECISION)) {
			switch (pos1) {
			case TempEffect.MENTALPARRYDV:
				return ((pos2 == TempEffect.ALLMENTALDVS)
					|| (pos2 == TempEffect.ALLMENTALSTATS)
					|| (pos2 == TempEffect.ALLDVS)
					|| (pos2 == TempEffect.ALLPARRIES));
			case TempEffect.MENTALDODGEDV:
				return ((pos2 == TempEffect.ALLMENTALDVS)
					|| (pos2 == TempEffect.ALLMENTALSTATS)
					|| (pos2 == TempEffect.ALLDVS)
					|| (pos2 == TempEffect.ALLDODGES));
			case TempEffect.MENTALPRECISION:
				return ((pos2 == TempEffect.ALLMENTALPRECISIONS)
					|| (pos2 == TempEffect.ALLMENTALSTATS)
					|| (pos2 == TempEffect.ALLPRECISIONS));
			case TempEffect.PHYSICALPRECISION:
				return ((pos2 == TempEffect.ALLPHYSPRECISIONS)
					|| (pos2 == TempEffect.ALLPHYSSTATS)
					|| (pos2 == TempEffect.ALLPRECISIONS));
			default: {
				if ((pos1 >= TempEffect.PHYSICALPARRYDVHEAD) && (pos1 <= TempEffect.PHYSICALPARRYDVLEGSX))
					return ((pos2 == TempEffect.ALLPHYSDVS)
						|| (pos2 == TempEffect.ALLPHYSSTATS)
						|| (pos2 == TempEffect.ALLPARRIES)
						|| (pos2 == TempEffect.ALLDVS));
				else if ((pos1 >= TempEffect.PHYSICALDODGEDVHEAD) && (pos1 <= TempEffect.PHYSICALDODGEDVLEGSX))
					return ((pos2 == TempEffect.ALLPHYSDVS)
						|| (pos2 == TempEffect.ALLPHYSSTATS)
						|| (pos2 == TempEffect.ALLDODGES)
						|| (pos2 == TempEffect.ALLDVS));
				else return false;
			}
			}
		}
		
		return false;
	}
	
	public int getBonus(int index) {
		
		if ((index >= TempEffect.ARBASHSOAKHEAD) && (index <= TempEffect.ARAGGSOAKLLEG)
				&& (c.equip.equippedArmor[(index - TempEffect.ARBASHSOAKHEAD) % 6] < 0))
			return 0;
		
		int[] partial = new int[TempEffect.T_OTHER];
		for (int i = 0; i < TempEffect.T_OTHER; ++i)
			partial[i] = Integer.MIN_VALUE;
		
		List<TempEffect> e = reuniteEffects();
		
		for (int i = 0; i < e.size(); ++i)
			// only consider active effects...
			if (e.get(i).isActive() && (equivalent(index, e.get(i).getPosition()))) {
				// ... and the greater of each type
				if (partial[e.get(i).getType()] < e.get(i).getValue())
					partial[e.get(i).getType()] = e.get(i).getValue();
			}
		
		int res = 0;
		for (int i = 0; i < TempEffect.T_OTHER; ++i) {
			if (partial[i] != Integer.MIN_VALUE) res += partial[i];
		}
		return res;
	}
	
	public List<TempEffect> reuniteEffects() {
		TempEffect[] others = getEffects();
		List<TempEffect> equip = c.equip.getActiveEffects();
		for (int i = 0; i < others.length; ++i)
			equip.add(others[i]);
		
		return equip;
	}
	
	public void addEffect(TempEffect e) {
		effs.add(e);
	}
	public void removeLastMovement() {
		for (int i = effs.size() - 1; i >= 0; --i)
			if (effs.get(i).getType() == TempEffect.T_MOVEMENT) {
				effs.remove(i);
				break;
			}
	}
	public void removeEffect(TempEffect e) {
		for (int i = 0; i < effs.size(); ++i)
			if (effs.get(i).equals(e)) {
				effs.remove(i);
				break;
			}
	}
	public void removeEffect(int i) {
		effs.remove(i);
	}
	public void setEffect(int i, TempEffect e) {
		if (i >= effs.size()) effs.add(e);
		else effs.get(i).setAs(e);
	}
	
	public TempEffect[] getEffects() {
		TempEffect[] res = new TempEffect[effs.size()];
		for (int i = 0; i < effs.size(); ++i) {
			res[i] = effs.get(i);
		}
		return res;
	}

	public String toString() {
		return Sequences.toString(effs, ",_,");
	}

	public void fromString(String string) {
		String[] tm = Sequences.divideStringPer(string, ",_,");
		for(int i = 0; i < tm.length; ++i) effs.add(TempEffect.factoryFromString(tm[i]));
	}

	public String[] getAllEffectsStringList() {
		List<TempEffect> l = reuniteEffects();
		String[] res = new String[l.size()];
		for (int i = 0; i < l.size(); ++i)
			res[i] = l.get(i).getStringPreview();
		return res;
	}

	public void setEffects(TempEffect[] effects) {
		effs = new ArrayList<TempEffect>();
		for (int i = 0; i < effects.length; ++i)
			effs.add(effects[i]);
	}

}
