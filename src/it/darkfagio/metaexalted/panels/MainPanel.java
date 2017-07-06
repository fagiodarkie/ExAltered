package it.darkfagio.metaexalted.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import it.darkfagio.metaexalted.ApplicazioneGUI;
import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.wizard.CharacterNameWizard;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private ApplicazioneGUI app;
	private PlayCharacter c;
	
	private JButton[] buttons;
	private JPanel buttonPanel;
	private JPanel panel;
	private int position;
	
	public MainPanel(ApplicazioneGUI applicazioneGUI) {
		app = applicazioneGUI;
	}
	
	public void setCharacter(PlayCharacter ch, int i) {
		c = ch;
		position = i;
		
		removeAll();
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		buttonPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		buttonPanel.setLayout(new GridLayout(7, 1, 5, 20));
		buttons = new JButton[7];
		
		addButton(0, "Attributi");
		addButton(1, "Combattimento");
		addButton(2, "Salute");
		addButton(3, "Pools");
		addButton(4, "Equipaggiamento");
//		addButton(5, "Background");
//		addButton(6, "Prodigi");
		
		final JButton nameButton = new JButton((c.getName() == null || c.getName().equalsIgnoreCase("")) ? "Imposta un nome" : c.getName());
		nameButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {
				nameButton.setText((c.getName().equalsIgnoreCase("") || c.getName() == null) ? "Imposta un nome" : c.getName());
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				nameButton.setText("Cambia nome...");
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		nameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String oldName = c.getName();
				JDialog d = new JDialog(app.getFrame());
				CharacterNameWizard center = new CharacterNameWizard(MainPanel.this, MainPanel.this.c);	
				center.setDialog(d);
				d.add(center);
				d.setMinimumSize(new Dimension(400, 200));
				d.setVisible(true);
				d.setModal(true);
				if (!oldName.equalsIgnoreCase(c.getName()))
						MainPanel.this.setCharacter(c, position);
			}
		});
		panel.add(new AttributesPanel(this, c));
		
		add(panel, BorderLayout.CENTER);
		leftPanel.add(nameButton, BorderLayout.NORTH);
		JPanel flowButtons = new JPanel();
		flowButtons.setLayout(new FlowLayout());
		flowButtons.add(buttonPanel);
		leftPanel.add(flowButtons, BorderLayout.CENTER);
		add(leftPanel, BorderLayout.WEST);
		invalidate();
		validate();
	}
	
	private void addButton(final int position, String desc) {
		
		buttons[position] = new JButton(desc);
		buttons[position].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				panel.removeAll();
				
				switch(position) {
				case 0: panel.add(new AttributesPanel(MainPanel.this, c), BorderLayout.CENTER);
				break;
				case 1: panel.add(new CombatStatsPanel(MainPanel.this, c), BorderLayout.CENTER);
				break;
				case 2: panel.add(new HealthPanel(MainPanel.this, c), BorderLayout.CENTER);
				break;
				case 6: panel.add(new CharmsPanel(MainPanel.this, c), BorderLayout.CENTER);
				break;
				case 4: panel.add(new EquipmentPanel(MainPanel.this, c), BorderLayout.CENTER);
				break;
				case 5: panel.add(new BackgroundPanel(MainPanel.this, c), BorderLayout.CENTER);
				break;
				case 3: panel.add(new PoolPanel(MainPanel.this, c), BorderLayout.CENTER);
				break;			
				}

				invalidate();
				validate();
			}
		});
		buttonPanel.add(buttons[position]);
		
	}

	public PlayCharacter getCharacter() {
		return c;
	}

	public void notifyChange() {
		app.notifyChange(position);
	}
	
}
