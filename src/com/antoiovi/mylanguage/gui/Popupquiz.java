package com.antoiovi.mylanguage.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

import javax.swing.SwingConstants;

import java.awt.Component;

import javax.swing.ImageIcon;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.antoiovi.mylanguage.Quizword;

public class Popupquiz extends JDialog implements ActionListener {
	private String myword;
	private String mytext;
	public final int OK = 0;
	public final int ESC = 1;
	public final int NOT_VALID=2;
	Popupquiz jdialog;

	private final JPanel contentPanel = new JPanel();
	int clickButton;
	protected JButton btnAnswer1;
	protected JButton btnAnswer2;
	protected JButton btnAnswer3;
	protected Quizword quizword;
	String question="";
	String answer="";
	String answerS[]={"","","","",""};
	protected JLabel wrongLabel;
	protected JLabel okLabel;
	protected JTextArea textArea;
	protected JPanel panel_3;
	protected JPanel buttonPane;
	private JPanel panel_2;
	private JPanel panel_4;
	private JPanel panel_1;
	protected JButton okButton;
	protected JButton cancelButton;
	
	
	/**
	 * Create the dialog.
	 */
	public Popupquiz() {
	
		super(null, "", ModalityType.DOCUMENT_MODAL);
		setResizable(false);
		jdialog = this;
		
		setBounds(100, 100, 377, 242);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{361, 0};
			gbl_panel.rowHeights = new int[]{67, 96, 0};
			gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				panel_1 = new JPanel();
				GridBagConstraints gbc_panel_1 = new GridBagConstraints();
				gbc_panel_1.fill = GridBagConstraints.BOTH;
				gbc_panel_1.insets = new Insets(0, 0, 5, 0);
				gbc_panel_1.gridx = 0;
				gbc_panel_1.gridy = 0;
				panel.add(panel_1, gbc_panel_1);
				panel_1.setLayout(new BorderLayout(0, 0));
				{
					textArea = new JTextArea();
					textArea.setEditable(false);
					textArea.setForeground(Color.BLUE);
					textArea.setFont(new Font("Arial", Font.BOLD, 14));
					panel_1.add(textArea);
					textArea.setText(question);;
				}
			}
			{
				panel_4 = new JPanel();
				GridBagConstraints gbc_panel_4 = new GridBagConstraints();
				gbc_panel_4.fill = GridBagConstraints.BOTH;
				gbc_panel_4.gridx = 0;
				gbc_panel_4.gridy = 1;
				panel.add(panel_4, gbc_panel_4);
				panel_4.setLayout(new GridLayout(0, 1, 0, 0));
				{
					panel_2 = new JPanel();
					panel_4.add(panel_2);
					panel_2.setLayout(new GridLayout(0, 1, 0, 0));
				
				}
				{
					
					btnAnswer1 = new JButton(answerS[4]);
					btnAnswer1.setFont(new Font("Tahoma", Font.BOLD, 14));
					btnAnswer1.setActionCommand("Button1");
					btnAnswer1.addActionListener(this);
					btnAnswer1.setHorizontalAlignment(SwingConstants.LEFT);
					panel_2.add(btnAnswer1);
					
				}
				{
					btnAnswer2 = new JButton(answerS[2]);
					btnAnswer2.setFont(new Font("Tahoma", Font.BOLD, 14));
					btnAnswer2.setActionCommand("Button2");
					btnAnswer2.addActionListener(this);
					btnAnswer2.setHorizontalAlignment(SwingConstants.LEFT);
					panel_2.add(btnAnswer2);
				}
				{
					btnAnswer3 = new JButton(answerS[3]);
					btnAnswer3.setFont(new Font("Tahoma", Font.BOLD, 14));
					btnAnswer3.setActionCommand("Button3");
					btnAnswer3.addActionListener(this);
					btnAnswer3.setHorizontalAlignment(SwingConstants.LEFT);
					panel_2.add(btnAnswer3);
				}
				{
					panel_3 = new JPanel();
					panel_4.add(panel_3);
					{
						okLabel = new JLabel("Ok!");
						okLabel.setVerticalAlignment(SwingConstants.TOP);
						okLabel.setVisible(false);
						okLabel.setIcon(new ImageIcon(Popupquiz.class.getResource("/com/antoiovi/mylanguage/icons/ok.png")));
						panel_3.add(okLabel);
					}
					{
						wrongLabel = new JLabel("Errato !");
						wrongLabel.setVerticalAlignment(SwingConstants.TOP);
						wrongLabel.setVisible(false);
						wrongLabel.setIcon(new ImageIcon(Popupquiz.class.getResource("/com/antoiovi/mylanguage/icons/wrong.png")));
						panel_3.add(wrongLabel);
					}
				}
			}
		}
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Chiudi e continua");
				okButton.addActionListener(this);

				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Arresta");
				cancelButton.addActionListener(this);
				cancelButton.setActionCommand("ESC");
				buttonPane.add(cancelButton);
			}
		}
	}

	// per recuperare dal form chiamante l'azione di uscita..
	public int getClickButton() {
		return clickButton;
	}

	public void setClickButton(int clickButton) {
		this.clickButton = clickButton;
	}
	
	protected void  resetAnswersButtons(){
		btnAnswer1.setText(answerS[2]);
		btnAnswer2.setText(answerS[3]);
		btnAnswer3.setText(answerS[4]);
		textArea.setText(question);
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if (cmd.equals("OK")) {
			jdialog.setClickButton(OK);
			jdialog.dispose();
		} else if (cmd.equals("ESC")) {
			jdialog.setClickButton(ESC);
			jdialog.dispose();
		}else if (cmd.equals("Button1")) {
			 if((btnAnswer1.getText().equals(answer)))
				 rightAnswer();
			 else
				 wrongAnswer();
		}else if (cmd.equals("Button2")) {
			if((btnAnswer2.getText().equals(answer)))
				 rightAnswer();
			 else
				 wrongAnswer();
		}else if (cmd.equals("Button3")) {
			if((btnAnswer3.getText().equals(answer)))
				 rightAnswer();
			 else
				 wrongAnswer();
		}
	}

	private void rightAnswer(){
		wrongLabel.setVisible(false);
		jdialog.setClickButton(OK);
		jdialog.dispose();
	}

	private void wrongAnswer(){
		wrongLabel.setVisible(true);
		return;
	}

	
	/**
	 * Da chiamare dopo il costruttore per lanciare il game
	 */
	public void setQuizword(Quizword quizword) {
		this.quizword = quizword;
	/*	if(quizword.getNumberofKey()<6)
		{
			this.setClickButton(clickButton);
			return;
		}*/
		quizword.getNextWords(answerS);
		question=answerS[0];
		answer=answerS[1];
		resetAnswersButtons();
		
	}
	
	
	
}
