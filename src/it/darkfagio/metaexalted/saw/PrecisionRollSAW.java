package it.darkfagio.metaexalted.saw;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.utils.Utils;
import it.darkfagio.utils.Format;

public class PrecisionRollSAW extends SAWPanel {
	private static final long serialVersionUID = 1L;
	private PlayCharacter c;
	private SolveAttackWizard saw;
	private int rows;
	private JRadioButton[] button;
	private JTextField[] textFields;
	private ButtonGroup b;
	private String[] val;
	
	private static int diceToAdd = 0, successToAdd = 1, enemyVD = 2, throwResult = 3;

	public PrecisionRollSAW(SolveAttackWizard solveAttackWizard, PlayCharacter ch) {
		saw 		= solveAttackWizard;
		c 			= ch;
		rows 		= 11;
		button 		= new JRadioButton[2];
		button[0] 	= new JRadioButton("Tira automaticamente o ");
		button[1] 	= new JRadioButton("inserisci il risultato: ");
		b = new ButtonGroup();
		b.add(button[0]); b.add(button[1]);
		button[0].setSelected(true);
		textFields 	= new JTextField[4];
		for (int i = 0; i < 4; ++i) {
			textFields[i] = Format.getNumericTextField("0");
			textFields[i].setColumns(4);
		}
		
		val 		= c.calc.getPartials(saw.getValue(SolveAttackWizard.precision),
						saw.getState(SolveAttackWizard.specialization),
						saw.getValue(SolveAttackWizard.weaponUsed),
						saw.getMovement(),
						saw.getValue(SolveAttackWizard.abilityUsed),
						saw.getFlurryPenalty());

		remodel();
	}

	public void remodel() {
		removeAll();
		
		setLayout(new FlowLayout());
		JPanel labels = new JPanel(new GridLayout(rows, 2)),
				throwPanel = new JPanel(new BorderLayout());
		throwPanel.add(button[1], BorderLayout.CENTER); 
		throwPanel.add(textFields[3], BorderLayout.EAST); 
		
		labels.add(new JLabel("Pool attuale:"));
		labels.add(new JLabel(val[0]));
		labels.add(new JLabel("Effetti temporanei:"));
		labels.add(new JLabel(val[1]));
		labels.add(new JLabel("Precisione dell'arma:"));
		labels.add(new JLabel(val[2]));
		labels.add(new JLabel("Modificatore di movimento:"));
		labels.add(new JLabel(val[3]));
		labels.add(new JLabel("K base:"));
		labels.add(new JLabel(val[4]));
		labels.add(new JLabel("Abilit�:"));
		labels.add(new JLabel(val[5]));
		labels.add(new JLabel("Penalit� da turbine:"));
		labels.add(new JLabel(val[6]));
		labels.add(new JLabel("Dadi da aggiungere:"));
		labels.add(textFields[0]);
		labels.add(new JLabel("Successi da aggiungere:"));
		labels.add(textFields[1]);
		labels.add(new JLabel("VD avversario"));
		labels.add(textFields[2]);
		labels.add(button[0]);
		labels.add(throwPanel);
		
		add(labels);
		
		revalidate();
	}

	@Override
	public void nextInvoked() {
		
		int finalPrecision = (button[0].isSelected()
				? Utils.rollDie(Integer.parseInt(val[0]) + Integer.parseInt(textFields[diceToAdd].getText().trim()))
				: Integer.parseInt(textFields[throwResult].getText().trim())) + Integer.parseInt(textFields[successToAdd].getText().trim());
		int opponentDV = Integer.parseInt(textFields[enemyVD].getText().trim());
		
		int pane = finalPrecision > opponentDV ? SolveAttackWizard.BALANCE_TEST_ATTACK : SolveAttackWizard.MIN_DAMAGE;

		// TODO decide what pane to show after, set variables
		
		saw.addPane(pane, null);
	}

}
