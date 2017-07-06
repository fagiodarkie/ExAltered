package it.darkfagio.metaexalted.panels;

import it.darkfagio.metaexalted.model.Pools;
import it.darkfagio.metaexalted.model.Virtues;
import it.darkfagio.metaexalted.simple.GUIAttribute;
import it.darkfagio.metaexalted.simple.GUIModifiableAttribute;
import it.darkfagio.metaexalted.wizard.PeripheralEssenceWizard;
import it.darkfagio.metaexalted.wizard.PermanentEssenceWizard;
import it.darkfagio.metaexalted.wizard.PersonalEssenceWizard;
import it.darkfagio.metaexalted.wizard.RestoreEssenceWizard;
import it.darkfagio.metaexalted.wizard.VirtueModifierWizard;
import it.darkfagio.metaexalted.wizard.WillpowerWizard;
import it.darkfagio.utils.gui.DialogPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class PoolPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private class ButtonDialogListener implements ActionListener {
				
		PoolPanel p;
		DialogPanel component;
		
		public ButtonDialogListener(PoolPanel parent, DialogPanel component) {
			p = parent;
			this.component = component;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog dialog = new JDialog();
			dialog.add(component);
			component.setDialog(dialog);
			dialog.setMinimumSize(component.getMinimumSize());
			dialog.pack();
			dialog.setVisible(true);
			dialog.setModal(true);
			dialog.addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent arg0) {}
				
				@Override
				public void windowIconified(WindowEvent arg0) {}
				
				@Override
				public void windowDeiconified(WindowEvent arg0) {}
				
				@Override
				public void windowDeactivated(WindowEvent arg0) {}
				
				@Override
				public void windowClosing(WindowEvent arg0) {}
				
				@Override
				public void windowClosed(WindowEvent arg0) {
					p.remodel();
				}
				
				@Override
				public void windowActivated(WindowEvent arg0) {}
			});
		}
	}
	
	MainPanel main;
	it.darkfagio.metaexalted.model.PlayCharacter c;
	private EffectsModifier effects;

	public PoolPanel(MainPanel mainPanel, it.darkfagio.metaexalted.model.PlayCharacter c) {
		this.c = c;
		main = mainPanel;
		
		remodel();
	}

	public void remodel() {
		
		effects = new EffectsModifier(this, c.effects.effs);
		removeAll();
		setLayout(new BorderLayout());
		
		JPanel upper = new JPanel(), lower = new JPanel();		
		JPanel essence = new JPanel(), virtues = new JPanel(), experience = new JPanel(), logos = new JPanel(),
				effectPanel = new JPanel(new BorderLayout());
		essence.setBorder(BorderFactory.createTitledBorder("Essenza e Volontà"));
		virtues.setBorder(BorderFactory.createTitledBorder("Virtù"));
		experience.setBorder(BorderFactory.createTitledBorder("Esperienza"));
		effectPanel.setBorder(BorderFactory.createTitledBorder("Effetti temporanei"));
		logos.setBorder(BorderFactory.createTitledBorder("Logos"));
		upper.setLayout(new BorderLayout());
		lower.setLayout(new GridLayout(1, 3));
		
		essence.setLayout(new BorderLayout());
		JPanel essGrid = new JPanel();
		essGrid.setLayout(new GridLayout(4,1));
		
		essGrid.add(new GUIModifiableAttribute("Essenza Permanente",
				"" + c.getPools().getStat(Pools.permanentEssence),
				new ButtonDialogListener(this, new PermanentEssenceWizard(this, c))));
		essGrid.add(new GUIModifiableAttribute("Essenza Personale",
				c.getPools().getCurrentPersonalEssence() + "/" + c.getPools().getPersonalEssence(),
				new ButtonDialogListener(this, new PersonalEssenceWizard(this, c))));
		essGrid.add(new GUIModifiableAttribute("Essenza Periferica", c.getPools().getCurrentPeripheralEssence()
				+ "/" + c.getPools().getPeripheralEssence(),
				new ButtonDialogListener(this, new PeripheralEssenceWizard(this, c))));
		essGrid.add(new GUIAttribute("Essenza Impegnata:",
				(c.pools.getStat(Pools.cachedPeripheralEssence) + c.pools.getStat(Pools.cachedPersonalEssence))));
		JPanel essPanel = new JPanel(new BorderLayout());
		essPanel.add(essGrid, BorderLayout.CENTER);
		JButton restore = new JButton("Ripristina moti");
		restore.addActionListener(new ButtonDialogListener(this, new RestoreEssenceWizard(this, c)));
		essPanel.add(restore, BorderLayout.SOUTH);
		essence.add(essPanel, BorderLayout.CENTER);
		
		JPanel willpanel = new JPanel();
		willpanel.setLayout(new BorderLayout());
		willpanel.setBorder(BorderFactory.createTitledBorder("Volontà"));
		willpanel.add(new WillpowerGridPanel(this, c), BorderLayout.CENTER);
		JButton willModify = new JButton("Modifica Volontà");
		willModify.addActionListener(new ButtonDialogListener(this, new WillpowerWizard(this, c)));
		willpanel.add(willModify, BorderLayout.SOUTH);
		essence.add(willpanel, BorderLayout.SOUTH);
		upper.add(essence, BorderLayout.WEST);
		
		JPanel virtPanel = new JPanel(new BorderLayout()), modVirtPanel = new JPanel(new FlowLayout()),
				virtGridPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		JPanel[] virtInner = new JPanel[4];
		for (int i = 0; i < 4; ++i) {
			virtInner[i] = new JPanel(new BorderLayout());
			virtGridPanel.add(virtInner[i]);
		}
		JButton virtModify = new JButton("  Modifica virtù...  ");
		virtModify.addActionListener(new ButtonDialogListener(this, new VirtueModifierWizard(this, c)));
		virtPanel.add(virtGridPanel, BorderLayout.CENTER);
		virtPanel.add(modVirtPanel, BorderLayout.SOUTH);
		for (int i = 0; i < 4; ++i) {			
			virtInner[i].setBorder(BorderFactory.createTitledBorder(Virtues.name[i]));
			virtInner[i].add(new VirtueDisplayPanel(this, c, i), BorderLayout.CENTER);
		}
		modVirtPanel.add(virtModify);
		virtPanel.setPreferredSize(new Dimension(250, virtPanel.getPreferredSize().height));
		virtPanel.setBorder(BorderFactory.createTitledBorder("Virtù"));
		upper.add(virtPanel, BorderLayout.EAST);
		
		effectPanel.add(effects, BorderLayout.CENTER);
		JPanel effButtonPanel = new JPanel(new FlowLayout());
		JButton apply = new JButton("Applica");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.effects.setEffects(effects.getEffects());
				remodel();
				notifyChange();
			}
		});
		effButtonPanel.add(apply);
		effectPanel.add(effButtonPanel, BorderLayout.SOUTH);
		lower.add(effectPanel);
		
		add(upper, BorderLayout.NORTH);
		add(lower, BorderLayout.SOUTH);
		invalidate();
		validate();
	}

	public void notifyChange() {
		main.notifyChange();
	}

}
