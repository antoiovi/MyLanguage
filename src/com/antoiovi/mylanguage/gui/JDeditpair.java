package com.antoiovi.mylanguage.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionEvent;

import javax.swing.Action;








import com.antoiovi.mylanguage.Language;
import com.antoiovi.mylanguage.Mylanguage;
import com.antoiovi.mylanguage.Quizword;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.antoiovi.mylanguage.MyProperties;
import com.antoiovi.mylanguage.ordersentence.JDOrderSentence;
import com.antoiovi.mylanguage.quizmultiplace.JDQuizMultiple;
public class JDeditpair extends JDialog implements ActionListener,TableModelListener,
		Serializable {

	private final JPanel contentPanel = new JPanel();
	private JTable table;

	private JMenuItem mntmOpen;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveWName;
	private JMenu mnNewMenu;
	protected File file;
	Mylanguage mylanguage;
	private boolean changedData = false;

	private JButton btnDelRow;
	private JButton btnNewRow;
	private JScrollPane scrollPane;
	Vector dati;
	JDeditpair dialog;
	private JMenuItem mntmChiudi;
	private JMenu mnHel;
	private JSeparator separator;
	private JMenuItem mntmHelp;
	private JMenuItem mntmInfo;
	private JSeparator separator_1;
	private JPanel panel;
	private JLabel label;
	private JSpinner spinner;
	private JButton button;
	private JMenu mnPlay;
	private JMenuItem mntmQuiz;
	//Properties prop = new Properties();
	Properties properties = MyProperties.getInstance().getProperties();
	private JMenuItem mntmNuovo;
	private JButton btnNewButton;
	private JButton btnNuovaRigaSotto;
	private JButton btnEliminaRigheVuote;
	Quiz_game quizgame;
	private JMenuItem mntmQuizScore;
	private JMenuItem mntmQuizMultiple;
	private JMenuItem mntmQuizSortSentece;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 try {
				UIManager.setLookAndFeel(
				    UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try {
			JDeditpair dialog = new JDeditpair();
			dialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param documentModal 
	 * @param string 
	 */
	public JDeditpair() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
			}
		});
		
		setBounds(100, 100, 648, 307);
		getContentPane().setLayout(new BorderLayout());
		{
			panel = new JPanel();
			getContentPane().add(panel, BorderLayout.NORTH);
			{
				label = new JLabel("Tempo (minuti)");
				panel.add(label);
			}
			{
				spinner = new JSpinner();
				spinner.setModel(new SpinnerNumberModel(5, 1, 60, 1));
				panel.add(spinner);
			}
			{
				button = new JButton("Start popping");
				panel.add(button);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							
							Integer minuti=(Integer)spinner.getModel().getValue();
							mylanguage.setTimePopsup(minuti);
						mylanguage.Start_popups();
						closeprogram();
						}catch (Exception e){
							
						}
																			}
				});
				
	
				
				
			}
			{
				JButton btnStartPopquiz = new JButton("Start popquiz");
				btnStartPopquiz.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							Integer minuti=(Integer)spinner.getModel().getValue();
							mylanguage.setTimePopsup(minuti);
							mylanguage.Start_popquiz();
							dialog.closeprogram();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				panel.add(btnStartPopquiz);
			}
		}
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
        dialog=this;
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			mylanguage = new Language();
		}
		{
			table = new JTable();

			table.setModel(new Mymodel(4, 2));
			table.getModel().addTableModelListener(this);
			contentPanel.add(table);
		}
		{
			scrollPane = new JScrollPane(table);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnNewRow = new JButton("Aggiungi riga ");
				btnNewRow.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Mymodel model = (Mymodel) table.getModel();
						model.addRow(new Object[] { "", "" });
					}
				});
				{
					btnNuovaRigaSotto = new JButton("Inser riga sotto");
					btnNuovaRigaSotto.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Mymodel model = (Mymodel) table.getModel();
							try {
								int sr=table.getSelectedRow();
								if(sr!=-1){
									model.insertRow(sr+1, new Object[] { "", "" });
								
								}
							} catch (java.lang.ArrayIndexOutOfBoundsException ex) {

							}
						
							
						}
					});
					{
						// ELIMINa RIGHE VUOTE
						btnEliminaRigheVuote = new JButton("Elimina righe vuote");
						btnEliminaRigheVuote.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Mymodel model = (Mymodel) table.getModel();
								// Elimina le celle vuote
								model.cleanEmpty();
								// notifica alla tabella che i dati sonoc ambiati
								model.fireTableDataChanged();
															
							}
						});
						buttonPane.add(btnEliminaRigheVuote);
					}
					buttonPane.add(btnNuovaRigaSotto);
				}
				{
					btnNewButton = new JButton("Inser riga sopra");
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							Mymodel model = (Mymodel) table.getModel();
							try {
								int sr=table.getSelectedRow();
								if(sr!=-1){
									model.insertRow(sr, new Object[] { "", "" });
								
								}
							} catch (java.lang.ArrayIndexOutOfBoundsException ex) {

							}
						}
					});
					buttonPane.add(btnNewButton);
				}
				{
					separator_1 = new JSeparator();
					buttonPane.add(separator_1);
				}
				buttonPane.add(btnNewRow);
			}
			{		//ELIMINA RIGA
				btnDelRow = new JButton("Elimina riga");
				btnDelRow.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Mymodel model = (Mymodel) table.getModel();
						try {
							model.removeRow(table.getSelectedRow());
						} catch (java.lang.ArrayIndexOutOfBoundsException ex) {

						}
					}
				});
				buttonPane.add(btnDelRow);
			}
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				mnNewMenu = new JMenu("File");
				menuBar.add(mnNewMenu);
				{
					mntmOpen = new JMenuItem("Apri");
					mntmOpen.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// APRI UN FILE
							JFileChooser jfilechooser = new JFileChooser();
							jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
							int x = jfilechooser
									.showOpenDialog(getContentPane());
							if (x == JFileChooser.APPROVE_OPTION) {
								file = jfilechooser.getSelectedFile();
								/***
								 * TEST IF IS GOOD File
								 */
								if(mylanguage.setTextfile(file)){	
									// File is a text file...other check is possible
								 loadFile();
								}
							}
						}
					});
					{
						mntmNuovo = new JMenuItem("Nuovo");
						mntmNuovo.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// Prima di creare una nuova tabella verifica se il file corrente è stato salvato
								if(changedData){
									int n= JOptionPane.showConfirmDialog(null,
											"Modifiche non salvate. Chiudere comunque?",
									 "Tiitolo...",
								    JOptionPane.YES_NO_OPTION);
									if(n==1){
										return;
									}
							
								}
								//file salvato... creo nuovo
								dialog.newFile();
							}
						});
						mnNewMenu.add(mntmNuovo);
					}
					mnNewMenu.add(mntmOpen);
				}
				{
					mntmSaveWName = new JMenuItem("Salva con nome");
					mntmSaveWName.setActionCommand("SaveWithName");
					mntmSaveWName.addActionListener(this);
					mnNewMenu.add(mntmSaveWName);
				}
				{
					mntmSave =new JMenuItem("Salva");
					mntmSave.setEnabled(false);
					mntmSave.setActionCommand("Save");
					mntmSave.addActionListener(this);
					mnNewMenu.add(mntmSave);
					mntmSave.setEnabled(false);
					}
				{
					mntmChiudi = new JMenuItem("Chiudi");
					mntmChiudi.setActionCommand("Chiudi");
					mntmChiudi.addActionListener(this);
					
					mnNewMenu.add(mntmChiudi);
				}
			}
			{
				mnPlay = new JMenu("Play");
				menuBar.add(mnPlay);
				{
					mntmQuiz = new JMenuItem("Quiz");
					mntmQuiz.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// CREA E LANCIA IL QUIZ GAME
						try {
							Quizword	quizword=mylanguage.getQuizword();
							quizgame=new Quiz_game();
						 	quizgame.setQuizword(quizword);
						 	
						 	quizgame.setVisible(true);
						 	
						 	int exitbutton=quizgame.getClickButton();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						 
						}
					});
					mnPlay.add(mntmQuiz);
				}
				{
					mntmQuizScore = new JMenuItem("Quiz score");
					mntmQuizScore.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							// LANCIA QUIZ_SCORE...............
							try {
								Quizword	quizword=mylanguage.getQuizword();
							 Quiz_game_score	quizgamescore=new Quiz_game_score();
							 	quizgamescore.setQuizword(quizword);
							 	if(quizgamescore.getClickButton()==quizgamescore.NOT_VALID){
							 		quizgamescore.dispose();
							 		return;
							 	}
							 	quizgamescore.setVisible(true);
							 	
							 	int exitbutton=quizgamescore.getClickButton();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							 
							
						}
					});
					mnPlay.add(mntmQuizScore);
				}
				{
					/***************************************************************************************
					 * OPEN QUIZ MULTIPLE FRAME
					 **************************************************************************************/
					mntmQuizMultiple = new JMenuItem("Quiz multiple");
					mntmQuizMultiple.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							JDQuizMultiple quizMultipleFrame=new JDQuizMultiple();
							quizMultipleFrame.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
							quizMultipleFrame.setVisible(true);
							
						}
					});
					mnPlay.add(mntmQuizMultiple);
				}
				/***********************************************
				 * ORDER SENTENCE
				 ***************************************************/
				{
					mntmQuizSortSentece = new JMenuItem("Quiz sort sentece");
					mntmQuizSortSentece.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							JDOrderSentence quizordersentence=new JDOrderSentence();
							quizordersentence.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
							quizordersentence.setVisible(true);
						}
					});
					mnPlay.add(mntmQuizSortSentece);
				}
			}
			/*******************************
			 * HELP menu
			 **************************/
			{
				mnHel = new JMenu("Help");
				menuBar.add(mnHel);
				{
					mntmHelp = new JMenuItem("Help");
					mnHel.add(mntmHelp);
				}
				/**************************
				 * INFO menu
				 *************************/
				{
					mntmInfo = new JMenuItem("Info");
					mntmInfo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							About a=new About();
							a.setVisible(true);
						}
					});
					mnHel.add(mntmInfo);
				}
			}
			{
				separator = new JSeparator();
				menuBar.add(separator);
			}
		}
		loadproperties();
		dialog=this;
	}


	//  MY MODEL
	private class Mymodel extends DefaultTableModel {

		public Mymodel(int rowCount, int columnCount) {
			super(rowCount, columnCount);
			// TODO Auto-generated constructor stub

		
		}
		public void cleanEmpty(){
			Iterator i=dataVector.iterator();
			while(i.hasNext()){
				Vector<String> riga=(Vector<String>) i.next();
				String s=riga.get(0);
				String s0=s.trim();
				if(s0.isEmpty()){
						i.remove();
						
						}
			}
			
		}
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		this.setChangedData(true);
		
	}

	public boolean isChangedData() {
		return changedData;
	}

	public void setChangedData(boolean changedData) {
		this.changedData = changedData;
		if(changedData){
			this.mntmSave.setEnabled(true);
		}else{
			this.mntmSave.setEnabled(false);	
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Save")){
			/**
			 * SAVE THE FILE
			 */
				if (file == null) {
						JFileChooser jfilechooser = new JFileChooser();
						//jfilechooser.setDialogType(JFileChooser.SAVE_DIALOG);
						int x = jfilechooser
								.showOpenDialog(getContentPane());
						if (x == JFileChooser.APPROVE_OPTION) {
							file = jfilechooser.getSelectedFile();
						} else {
							return;
						}
					}
					try {
						BufferedWriter output = new BufferedWriter(
								new FileWriter(file));

						Mymodel model = (Mymodel) table.getModel();
						Vector righe = model.getDataVector();
						
						Iterator it = righe.iterator();
						while (it.hasNext()) {
							Vector riga=(Vector) it.next();
							String k = (String) riga.get(0);
							String w = (String) riga.get(1);
							if(k!=null){
								if(w==null)
									w="";
								k=k.trim();
								w=w.trim();
							String r = k + " = " + w;
							output.write(r);
							output.newLine();
							}
						}
						output.close();
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					mntmSave.setEnabled(false);
					this.setChangedData(false);
					dialog.setTitle(file.getAbsolutePath());
		
		}else if(e.getActionCommand().equals("SaveWithName")){
			JFileChooser jfilechooser = new JFileChooser();
			jfilechooser.setDialogType(JFileChooser.SAVE_DIALOG);
			int x = jfilechooser
					.showOpenDialog(getContentPane());
			if (x == JFileChooser.APPROVE_OPTION) {
				file = jfilechooser.getSelectedFile();
			} else {
				return;
			}
		
		try {
			BufferedWriter output = new BufferedWriter(
					new FileWriter(file));

			Mymodel model = (Mymodel) table.getModel();
			Vector righe = model.getDataVector();
			
			Iterator it = righe.iterator();
			while (it.hasNext()) {
				Vector riga=(Vector) it.next();
				String k = (String) riga.get(0);
				String w = (String) riga.get(1);
				if(k!=null){
					if(w==null)
						w="";
					k=k.trim();
					w=w.trim();
				String r = k + " = " + w;
				output.write(r);
				output.newLine();
				}
			}
			output.close();
			dialog.setTitle(file.getAbsolutePath());
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mntmSave.setEnabled(false);
		this.setChangedData(false);
		}else if(e.getActionCommand().equals("Chiudi")){
			if(changedData){
				int n= JOptionPane.showConfirmDialog(null,
						"Modifiche non salvate. Chiudere comunque?",
				 "Tiitolo...",
			    JOptionPane.YES_NO_OPTION);
				if(n==1){
					return;
				}
			}
			this.closeprogram();
			System.exit(1);
		//	this.setVisible(false);
			
		}
	}
	
	
	/**
	 * LOAD PROPERTIES
	 */
	private void loadproperties() {
		InputStream inputProp = null;
		try {
		
			inputProp = MyProperties.getInstance().inputStreamProperties();
			if (inputProp != null) {
				properties.load(inputProp);
				String delay = properties.getProperty(MyProperties.DELAY_PROP);
				if (delay != null)
					spinner.getModel().setValue(Integer.parseInt(delay));
				String filename = properties.getProperty(MyProperties.FILE_QUIZ_PROP);
				if (filename != null) {
					file = new File(filename);
					if(mylanguage.setTextfile(file)){
					 loadFile();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private boolean loadFile(){
		try {
			dati = mylanguage.getPairKeyWord(file);
		} catch (Exception e) {
			return false;
		}
		Vector<String> header =new Vector<String>();
		header.add("Parola");
		header.add("Significto");
		Mymodel model = (Mymodel) table.getModel();
		model.setDataVector(dati, header);
		dialog.setTitle(file.getAbsolutePath());
		this.setChangedData(false);
		return true;
	}
	
	
	public void newFile(){
		Vector PairKeyWord=new Vector<Vector<Object>>();
		for(int x=0;x<10;x++){
		Vector v	=new Vector<String>();
		v.add("");
		v.add("");
		PairKeyWord.addElement(v);
			}
		Vector<String> header =new Vector<String>();
		header.add("Parola");
		header.add("Significto");
		Mymodel model = (Mymodel) table.getModel();
		model.getDataVector().clear();
		model.setDataVector(PairKeyWord, header);
		mylanguage.setTextfile(null);
		file=null;
		this.setChangedData(false);
	
	}
	
	private void closeprogram(){
		OutputStream output = null;
		try {
	 			//output = new FileOutputStream("config.properties");
			output=MyProperties.getInstance().outputStreamProperties();
	 		// set the properties value
			properties.setProperty(MyProperties.FILE_QUIZ_PROP, file.getAbsolutePath());
			Integer delay=(Integer) spinner.getModel().getValue();
			properties.setProperty(MyProperties.DELAY_PROP,delay.toString());
			// save properties to project root folder
			properties.store(output, null);
			output.close();
	}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
		this.setVisible(false);
		this.dispose();
		}
	}
}
