package com.antoiovi.mylanguage.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class About extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			About dialog = new About();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public About() {
		setSize(new Dimension(257, 167));
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTextArea txtrAutoreAntoioviantoiovicomVersione = new JTextArea();
			txtrAutoreAntoioviantoiovicomVersione.setEditable(false);
			txtrAutoreAntoioviantoiovicomVersione.setEnabled(false);
			txtrAutoreAntoioviantoiovicomVersione.setDisabledTextColor(Color.BLUE);
			txtrAutoreAntoioviantoiovicomVersione.setCaretColor(Color.BLUE);
			txtrAutoreAntoioviantoiovicomVersione.setBackground(Color.LIGHT_GRAY);
			txtrAutoreAntoioviantoiovicomVersione.setText("Autore\r\nantoiovi@antoiovi.com\r\n\r\nVersione 2.1");
			contentPanel.add(txtrAutoreAntoioviantoiovicomVersione);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
