package it.darkfagio.metaexalted.panels;

import java.awt.GridLayout;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.simple.PerfectActionVirtueLabel;
import it.darkfagio.metaexalted.simple.WillpowerLevel;

import javax.swing.JPanel;

public class VirtueDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private PoolPanel p;
	private PlayCharacter ch;
	private int virt;

	public VirtueDisplayPanel(PoolPanel poolPanel, PlayCharacter c, int virtue) {
		p = poolPanel;
		ch = c;
		setVirt(virtue);
		remodel();
	}		

	public void remodel() {
		removeAll();
		
		int cols = 0, used = 0;
		cols = ch.getPools().getVirtues().getStat((short) virt);
		used = ch.getPools().getVirtues().getStat((short) (virt + 4));
		
		setLayout(new GridLayout(2, cols));
		
		for (int i = 0; i < cols; ++i) {
			add(new PerfectActionVirtueLabel(this, i < used));
		}
		for (int i = 0; i < cols; ++i) {
			add(new WillpowerLevel());
		}
		invalidate();
		validate();
		
	}
	
	public void notifyChange() {
		p.notifyChange();
		remodel();
	}

	public int getVirt() {
		return virt;
	}

	public void setVirt(int virt) {
		this.virt = virt;
	}

	public void restoreVirtueUse() {
		ch.getPools().getVirtues().restoreVirtue(virt);
		notifyChange();
	}

	public void consumeVirtueUse() {
		ch.getPools().getVirtues().consumeVirtue(virt);
		notifyChange();
	}
	
	public void resetVirtue(short val) {
		ch.getPools().getVirtues().setStat((short) (virt), val);
		notifyChange();
	}

}
