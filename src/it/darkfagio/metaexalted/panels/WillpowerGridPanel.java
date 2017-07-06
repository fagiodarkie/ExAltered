package it.darkfagio.metaexalted.panels;

import java.awt.GridLayout;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.model.Pools;
import it.darkfagio.metaexalted.simple.WillpowerBox;
import it.darkfagio.metaexalted.simple.WillpowerLevel;

import javax.swing.JPanel;

public class WillpowerGridPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private PlayCharacter ch;
	private PoolPanel panel;
	
	public WillpowerGridPanel(PoolPanel poolPanel, PlayCharacter c) {
		panel = poolPanel;
		ch = c;
		remodel();
	}
	
	public void remodel() {
		removeAll();
		
		int cols = ch.getPools().getStat(Pools.willpower);
		int filled = ch.getPools().getStat(Pools.usedWillpower);
		
		setLayout(new GridLayout(2, cols));
		for (int i = 0; i < cols; ++i) {
			add(new WillpowerBox(this, filled > i));
		}
		for (int i = 0; i < cols; ++i) {
			add(new WillpowerLevel());
		}
		invalidate();
		validate();
	}
	
	public void consumeWillpower() {
		ch.getPools().setStat(Pools.usedWillpower, (short) (ch.getPools().getStat(Pools.usedWillpower) + 1));
	}
	public void restoreWillpower() {
		ch.getPools().setStat(Pools.usedWillpower, (short) (ch.getPools().getStat(Pools.usedWillpower) - 1));
	}
	
	public void notifyChange() {
		panel.notifyChange();
		remodel();
	}


}
