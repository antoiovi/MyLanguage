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

public class Ppoupword extends JDialog {
private String myword;
private String mytext;
public final int OK=0;
public final int ESC=1;
Ppoupword jdialog;
	
	private final JPanel contentPanel = new JPanel();
int clickButton;
	

	/**
	 * Create the dialog.
	 */
	public Ppoupword(String mywor,String mytex) {
		super(null, "", ModalityType.DOCUMENT_MODAL);
		setResizable(false);
		jdialog=this;
		myword=mywor;
		mytext=mytex;
		setBounds(100, 100, 301, 229);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setForeground(Color.BLUE);
			textArea.setFont(new Font("Arial", Font.BOLD, 14));
			contentPanel.add(textArea);
			textArea.setText(mytext);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new GridLayout(0, 2, 0, 0));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Chiudi e continua");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					@SuppressWarnings("unused")
					Object o=arg0.getSource();
					jdialog.setClickButton(OK);
					jdialog.dispose();
					
					
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Arresta");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Object o=arg0.getSource();
						jdialog.setClickButton(ESC);
						jdialog.dispose();
						
					}
				});
				cancelButton.setActionCommand("Cancel");
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

}
