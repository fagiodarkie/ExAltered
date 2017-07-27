/*
Author: Jacopo Freddi
Project: ExAltered
Description: Interactive Character Sheet for a heavily-altered version of
  the  role-playing game Exalted, 2nd Edition, by White Wolf Publishing.

Copyright (C) 2014 - 2017 Jacopo Freddi

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

© 2017 White Wolf Entertainment AB. All rights reserved.
Exalted® and Storytelling System™ are trademarks and/or
registered trademarks of White Wolf Entertainment AB.

All rights reserved. www.white-wolf.com
*/

package it.darkfagio.metaexalted;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.panels.MainPanel;
import it.darkfagio.metaexalted.panels.UpperMenu;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ApplicazioneGUI implements Applicazione {

	private JFrame mainWindow;
	private List<MainPanel> panel;
	private UpperMenu menu;
	private List<String> url;
	private List<Boolean> modified;
	private JTabbedPane tabs;

	private String version = "1.1.6";
	
	public JFrame getFrame() {
		return mainWindow;
	}
	
	private String title() {
		return "MetaExalted " + version;
	}
	
	public ApplicazioneGUI() {
		url = new ArrayList<String>();
		panel = new ArrayList<MainPanel>();
		modified = new ArrayList<Boolean>();
		tabs = new JTabbedPane();
	}
	
	@Override
	public void show() {
		
		menu = new UpperMenu(this);		
		mainWindow = new JFrame();
		mainWindow.setMinimumSize(new Dimension(1024, 700));
		mainWindow.setTitle(title());
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setJMenuBar(menu);
		mainWindow.add(tabs);
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainWindow.addWindowListener(new WindowListener() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				exitAll();
			}

			@Override
			public void windowActivated(WindowEvent e) {				
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
		mainWindow.pack();
	}
	
	public void exitAll() {
		if (panel.size() > 0)
			for(int i = 0; i < panel.size(); ++i)
				exit();
		else exit();
	}

	@Override
	public void save() {
		int sel = tabs.getSelectedIndex();
		String c = panel.get(sel).getCharacter().toString();
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Exalted PlayCharacter File", "exa");
		chooser.setFileFilter(filter);
		if (url.get(sel).compareTo("") == 0) {
			int ret = chooser.showOpenDialog(mainWindow);
			if (ret == JFileChooser.APPROVE_OPTION) {
				url.set(sel, chooser.getSelectedFile().getPath());
				if (!url.get(sel).substring(url.get(sel).length()-4).equalsIgnoreCase(".exa")) {
					url.set(sel, url.get(sel) +".exa");
				}
			}
			else return;
		}
		
		try {
			FileWriter w = new FileWriter(url.get(sel), false);
			w.write(c);
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		modified.set(sel, false);
		resetTitle();
		
	}

	@Override
	public void open() {
		PlayCharacter c = new PlayCharacter();
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Exalted PlayCharacter File", "exa");
		chooser.setFileFilter(filter);
		int ret = chooser.showOpenDialog(mainWindow);
		if (ret == JFileChooser.APPROVE_OPTION) {
			Scanner f;
			try {
				File file = chooser.getSelectedFile();
				String furl = file.getPath();
				String[] fparts = furl.split("\\.");
				if((fparts.length < 2) || (!fparts[1].equalsIgnoreCase("exa")))
					furl = fparts[0] + ".exa";
				f = new Scanner(new File(furl));
				if ((f != null)) {
					try {
						String s = f.nextLine();
						c.fromString(s);
					} catch(Exception e) {
						e.printStackTrace();
					}
					MainPanel mp = new MainPanel(this);
					panel.add(mp);
					tabs.addTab(c.getName(), mp);
					mp.setCharacter(c, modified.size());
					modified.add(false);
					url.add(furl);
				}
			} catch (FileNotFoundException e) {
				JDialog d = new JDialog(mainWindow, "File not found (or other error)");
				d.setVisible(true);
				e.printStackTrace();
			} catch (Exception e) {
				
			}
			
		}
		resetTitle();
		tabs.invalidate();
		tabs.validate();
		
	}
	
	public void resetTitle() {
		int selected = tabs.getSelectedIndex();
		PlayCharacter c = panel.get(selected).getCharacter();
		tabs.setTitleAt(selected, (c.getName() == null ? "" : c.getName()));
	}

	@Override
	public void exit() {
		if (tabs.getTabCount() == 0)
			System.exit(0);
		int selected = tabs.getSelectedIndex();
		
		if (modified.get(selected)) {
			String name = panel.get(selected).getCharacter().getName();
			JOptionPane save = new JOptionPane("Salvare le modifiche al personaggio '" + name + "' prima di uscire?");
			save.setOptions(new String[] {"Salva", "No", "Annulla"});
			JDialog d = save.createDialog(mainWindow, "Uscita");
			d.setVisible(true);
			if ("Salva".equals(save.getValue()))
				save();
			else if ("Annulla".equals(save.getValue()))
				return;
		}
		tabs.remove(selected);
		panel.remove(selected);
		modified.remove(selected);
		if (tabs.getComponentCount() == 0)
			System.exit(0);
		if (selected > 0) {
			selected--;
			tabs.setSelectedIndex(selected);
		}
		tabs.invalidate();
		tabs.validate();
		
	}

	@Override
	public void createNew() {
		modified.add(false);
		MainPanel np = new MainPanel(this);
		panel.add(np);
		tabs.add(np);		
		np.setCharacter(new PlayCharacter(), url.size());
		url.add("");
	}

	public void notifyChange(int index) {
		if (!modified.get(index)) {
				modified.set(index, true);
				tabs.setTitleAt(index, (tabs.getTitleAt(index) == null ? "" : tabs.getTitleAt(index)) + "*");
		}
	}
	
}
