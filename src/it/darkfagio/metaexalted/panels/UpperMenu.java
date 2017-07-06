package it.darkfagio.metaexalted.panels;
import it.darkfagio.metaexalted.ApplicazioneGUI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class UpperMenu extends JMenuBar {

	private static final long serialVersionUID = 5801581743554175162L;
	
	private ApplicazioneGUI app;
	
	private JMenu fileMenu;
	private JMenuItem exit;
	private JMenuItem save;
	private JMenuItem openItem;
	private JMenuItem newItem;

	private JMenuItem closeChar;

	public UpperMenu(ApplicazioneGUI applicazioneGUI) {
		
		app = applicazioneGUI;
		
		newItem = new JMenuItem("Nuovo Personaggio");
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newItem.setMnemonic(KeyEvent.VK_N);
		newItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				app.createNew();
				
			}
		});
		openItem = new JMenuItem("Carica...");
		openItem.setMnemonic(KeyEvent.VK_O);
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				app.open();
				
			}
		});
		save = new JMenuItem("Salva");
		save.setMnemonic(KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				app.save();
				
			}
		});
		closeChar = new JMenuItem("Chiudi il personaggio");
		closeChar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		closeChar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				app.exit();
				
			}
		});
		exit = new JMenuItem("Esci");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				app.exitAll();
				
			}
		});
		
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(save);
		fileMenu.add(closeChar);
		fileMenu.add(exit);
		
		this.setVisible(true);
		
		this.add(fileMenu);
		
	}
		
}
