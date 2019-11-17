package com.antoiovi.arrangesentence;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.Button;
import java.io.File;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.antoiovi.mylanguage.gui.About;
import com.antoiovi.mylanguage.gui.MainApp;

import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * 
 * @author Antonello Iovino 23/07/2015
 * 
 *         Vengono disposte sul pannello superiore in ordine sparso delle parole
 *         di una frase Lavaora con una List<String> che contiene una certa
 *         quantita di frasi
 * 
 *         This work is licensed under the Creative Commons
 *         Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 *         license, visit http://creativecommons.org/licenses/by-sa/3.0/ or send
 *         a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042,
 *         USA.
 */
public class JDArrangeSentence extends JDialog implements Quizinterface {
	/**
	 * private OrderSentence ordersentence; questa classe si fa carico di : -
	 * inizzializzare la lista di stringhe(frasi) tramite un file di testo -
	 * verificare che il file sia corretto - creare una lista di stringhe (una
	 * stringa per ogni riga di testo del file) - restituisce una lista mescolata
	 * delle frasi - al bisogno restituisce nuovamente la lista di frasi in ordine
	 * sparso
	 */
	private ArrangeSentence ordersentence;

	int index = 0;
	File file;
	/**
	 * 
	 */
	private List<String> sentences;
	/**
	 * JCompnents
	 */
	private JPanel contentPane;
	private JScrollPane jscrollPane;
	private JPArrangeLabels panel_ordsent;

	private JLabel lblNotification;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JDArrangeSentence frame = new JDArrangeSentence();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JDArrangeSentence() {
		setMaximumSize(new Dimension(1000, 1000));
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,50+ JPArrangeLabels.WIDTH, 630);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu_1 = new JMenu("File");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Open file");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfilechooser = new JFileChooser();
				jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
				jfilechooser.setCurrentDirectory(new File(MainApp.getWorkingDir()));
				int x = jfilechooser.showOpenDialog(contentPane);
				if (x == JFileChooser.APPROVE_OPTION) {
					file = jfilechooser.getSelectedFile();
				}
				setDataFile(file);
			
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Exit");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitProgram();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help h=new Help();
				h.setVisible(true);
			}
		});
		mnNewMenu.add(mntmHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	About a=new About();
					a.setVisible(true);
	            }

	        });
		
		mnNewMenu.add(mntmAbout);
		
		JMenu mnNewMenu_2 = new JMenu("Themas");
		menuBar.add(mnNewMenu_2);
		
		JRadioButtonMenuItem rdbtnmntmNormal = new JRadioButtonMenuItem("Normal");
		rdbtnmntmNormal.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange(); 
				if(state == ItemEvent.SELECTED){ 
				//	log("SELECTED");
				// passato da NON selezionato a selezionato 
					panel_ordsent.setThema(JPArrangeLabels.THEMA_NORMAL);
				}
			}
		});
		mnNewMenu_2.add(rdbtnmntmNormal);
		
		JRadioButtonMenuItem rdbtnmntmDomino = new JRadioButtonMenuItem("Domino");
		rdbtnmntmDomino.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange(); 
				if(state == ItemEvent.SELECTED){ 
					//log("SELECTED");
				// passato da NON selezionato a selezionato 
					panel_ordsent.setThema(JPArrangeLabels.THEMA_DOMINO);
				}
			}
		});
		mnNewMenu_2.add(rdbtnmntmDomino);
		
		JRadioButtonMenuItem rdbtnmntmPoker = new JRadioButtonMenuItem("Poker");
		rdbtnmntmPoker.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange(); 
				if(state == ItemEvent.SELECTED){ 
					//log("SELECTED");
				// passato da NON selezionato a selezionato 
					panel_ordsent.setThema(JPArrangeLabels.THEMA_POKER);
				}
			}
		});
		mnNewMenu_2.add(rdbtnmntmPoker);
		
		JRadioButtonMenuItem rdbtnmntmSky = new JRadioButtonMenuItem("Sky");
		rdbtnmntmSky.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange(); 
				if(state == ItemEvent.SELECTED){ 
					//log("SELECTED");
				// passato da NON selezionato a selezionato 
					panel_ordsent.setThema(JPArrangeLabels.THEMA_SKY);
				}
			}
		});
		
		mnNewMenu_2.add(rdbtnmntmSky);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnmntmDomino);
	    group.add(rdbtnmntmPoker);
	    group.add(rdbtnmntmSky);
	    group.add(rdbtnmntmNormal);
		// setMaximumSize(new Dimension(600,600));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);

		lblScore = new JLabel("New label");
		panel_1.add(lblScore);
		btnPrev = new JButton("<<");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				index--;
				index = (index == -1 ? 0 : index);
				btnPrev.setVisible(index>=0);
				resetSentence();

			}
		});
		panel_1.add(btnPrev);
		/*********
		 * next
		 */
		JButton btnNext = new JButton(">>");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				index++;
				index = index >= sentences.size() ? 0 : index;
				// System.out.println(String.format("sentences.size= %d \t index= %d",
				// sentences.size(),index));
				resetSentence();
			}
		});
		panel_1.add(btnNext);
		
		/**
		 * SHAKE
		 */
		btnShake = new Button("SHAKE !!");
		btnShake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_ordsent.shake();
			}
		});
		panel_1.add(btnShake);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		lblNotification = new JLabel("----");
		panel.add(lblNotification);
		
		panel_ordsent=new JPArrangeLabels();
		jscrollPane = new JScrollPane((JLayeredPane) panel_ordsent);
		contentPane.add(jscrollPane, BorderLayout.CENTER);
		
		
		// contentPane.add(panel_ordsent, BorderLayout.EAST);
		panel_ordsent.setQuiz(this);
		//panel_ordsent.setBackground(Color.WHITE);
		/**
		 * Inizzializza le frasi con i valori default
		 */
		ordersentence = new ArrangeSentence();
		init();
	}

	void init() {
		// Recupera le frasi da eseguire
		sentences = ordersentence.getSentences();
		//Imposta la prima frase della lista
		index = 0;
		// disegna il pannello di gioco con la prima frase
		//panel_ordsent.configSentence(sentences.get(index));
		
		panel_ordsent.setSentence(sentences.get(index));
		
		// lblNotification.setText(sentences.get(index));
		lblNotification.setText("GO ...!");
		updatescore();
	}
	
	
/*****
 * Pulisce il pannello e disegna una nuova frase 
 * in base all indice
 */
	void resetSentence() {
		
		//panel_ordsent.configSentence();
		panel_ordsent.setSentence(sentences.get(index));
		lblNotification.setText(sentences.get(index));
		score = 0;
		wrongans = 0;
		rightansw = 0;
		updatescore();
		lblNotification.setText("GO ...!");
	}
	/**
	 * Set the DATA FILE
	 *  Prima re-inizzializzare la lista di stringhe (Frasi) tramite la classe
	 *  ordersence con il metodo setDatatFile: questa ckasse si fa carico di
	 *  	- verificare che il file sia corretto
	 *  	- creare una lista di stringhe (una stringa per ogni riga do testo del file)
	 *  	- restituisce una lista mescolata delle frasi
	 * @param file_txt
	 * @return
	 */
	public boolean setDataFile(File file_txt) {
		if (ordersentence.setDataFile(file_txt)) {
			init();
			return true;
		} else {
			return false;
		}

	}

	int score = 0;
	int wrongans = 0;
	int rightansw = 0;
	private JLabel lblScore;
	private Button btnShake;


	private JButton btnPrev;

	/**
	 * Aggiorna il testo di informazione delle domande giuste e sbagliate
	 */
	void updatescore() {
		String s = String.format("RIGHT %d  WRONGS = %d POINTS %d", rightansw, wrongans,(rightansw-wrongans));
		lblScore.setText(s);
	}
	
/******************
 * Exit program
 */
	 void exitProgram() {
		this.dispose();
	}
	
/*****************
 * Quizinterface methods :
 * 
 *  Vengono usati dal pannello gioco per comunicare con pannello padre
 */
	@Override
	public void wrongAnswer() {
		wrongans++;
		updatescore();
	}

	@Override
	public void rightAnswer() {
		rightansw++;
		updatescore();
	}

	@Override
	public void endGame() {
		lblNotification.setText("END !!");

	}
	void log(String s) {
		System.out.println(s);
	}
}
