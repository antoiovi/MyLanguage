package com.antoiovi.mylanguage.ordersentence;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JScrollPane;

import com.antoiovi.mylanguage.MyProperties;

import java.awt.Dimension;
import java.awt.Button;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
//import java.awt.ScrollPane;
/**
 * 
 * @author   Antonello Iovino
 * 23/07/2015 
 *
 */
public class JDOrderSentence extends JDialog implements Quizinterface{
	
	private OrderSentence ordersentence;
	Properties properties = MyProperties.getInstance().getProperties();
	int index=0;
	File file_data;
	private List<String> sentences;
	/**
	 * JCompnents
	 */
	private JPanel contentPane;
	private JScrollPane jscrollPane;
	private JPOrdersentence panel_ordsent;
	private JLabel lblNotification;
	private JCheckBox chckbxEasyOption;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JDOrderSentence frame = new JDOrderSentence();
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
	public JDOrderSentence() {
		setMaximumSize(new Dimension(1000, 1000));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
	//	setMaximumSize(new Dimension(600,600));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		/**
		 * load the properties : filedata name, ...
		 */
		loadproperties();
		
		/*****************************
		 * OpenFile
		 */
		JButton btnOpenfile = new JButton("Open file");
		btnOpenfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfilechooser = new JFileChooser();
				jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
				int x = jfilechooser
						.showOpenDialog(contentPane);
				if (x == JFileChooser.APPROVE_OPTION) {
					file_data = jfilechooser.getSelectedFile();
				}
				/**
				 * the check of file is not made here.. 
				 */
				setDataFile(file_data);
				 
			}
		});
		panel_1.add(btnOpenfile);
		
		lblScore = new JLabel("New label");
		panel_1.add(lblScore);
		/**************************
		 * previous
		 */
		JButton btnPrev = new JButton("<<");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				index--;
				index= (index==-1?0:index);
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
				index= index>=sentences.size()?(sentences.size()-1):index;
				//System.out.println(String.format("sentences.size= %d \t index= %d", sentences.size(),index));
				resetSentence();
			}
		});
		panel_1.add(btnNext);
		/************************************
		 * EASY OPTION : TO DISPOSE HANDLY THE WORDS ON THE DESK
		 */
		chckbxEasyOption = new JCheckBox("Easy option");
		chckbxEasyOption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(chckbxEasyOption.isSelected()){
					panel_ordsent.setEasyOption(true);	
					}else{
						panel_ordsent.setEasyOption(false);
					}
			}
		});
		
		panel_1.add(chckbxEasyOption);
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
		
		lblNotification = new JLabel("New label");
		panel.add(lblNotification);
		panel_ordsent = new JPOrdersentence();
		jscrollPane = new JScrollPane((JPanel)panel_ordsent);
		contentPane.add(jscrollPane, BorderLayout.CENTER);
		//contentPane.add(panel_ordsent, BorderLayout.EAST);
		panel_ordsent.setQuiz(this);
		panel_ordsent.setBackground(Color.WHITE);
		ordersentence=new OrderSentence();
		init();
	}


	void init(){
		/**
		 * if file is null on not valid any modification occours
		 */
		ordersentence.setDataFile(file_data);
		
		sentences=ordersentence.getSentences();
		index=1;
		panel_ordsent.configSentence(sentences.get(index));
		
		//lblNotification.setText(sentences.get(index));
		lblNotification.setText("GO ...!");
		updatescore();
	}
	
	
void resetSentence(){
	
	panel_ordsent.configSentence(sentences.get(index));
	lblNotification.setText(sentences.get(index));
	score=0;
	wrongans=0;
	rightansw=0;
	updatescore();
	lblNotification.setText("GO ...!");
}

/**
 * Set the DATA FILE the check of file is made inside ordersentence
 * @param file_txt
 * @return
 */
public boolean setDataFile(File file_txt) {
	if(ordersentence==null)
		return false;
	/**
	 * if file is null on not valid any modification occours
	 */
	if(!ordersentence.setDataFile(file_data))
		return false;
	SAVEPROPERTIES();
	/**
	 * reset the sentences
	 */
	sentences=ordersentence.getSentences();
	index=1;
	panel_ordsent.configSentence(sentences.get(index));
	
	//lblNotification.setText(sentences.get(index));
	lblNotification.setText("GO ...!");
	updatescore();
	return true;
}

int score=0;
int wrongans=0;
int rightansw=0;
private JLabel lblScore;
private Button btnShake;
 


void updatescore(){
	String s=String.format("RIGHT %d  WRONGS = %d",rightansw,wrongans);
	lblScore.setText(s);
}

@Override
public void wrongAnswer() {
	// TODO Auto-generated method stub
	wrongans++;
	updatescore();
}

@Override
public void rightAnswer() {
	// TODO Auto-generated method stub
	rightansw++;
	updatescore();
}



@Override
public void endGame() {
	lblNotification.setText("END !!");
}

/**
 * load stored properties
 */
private void loadproperties(){
	InputStream inputProp = null;
 	try {
 	inputProp=MyProperties.getInstance().inputStreamProperties();
 	if (inputProp != null) {
		properties.load(inputProp);
		String filename=properties.getProperty(MyProperties.FILE_QUIZ_ORDERSENT);
		//System.out.println("Flename= "+filename);
		if(filename!=null){
		file_data=new File(filename);
		//System.out.println("Flename != null = "+filename);
		}else{
			file_data=null;
		}
	}
		}catch(Exception e)
		{	
			e.printStackTrace();
			file_data=null;
		}
}



/**
 * save properties (options)
 */
void SAVEPROPERTIES(){
	OutputStream output = null;
	if(file_data==null)
		return;
	try {
		output=MyProperties.getInstance().outputStreamProperties();
		properties.setProperty(MyProperties.FILE_QUIZ_ORDERSENT, file_data.getAbsolutePath());
		properties.store(output, null);
		output.close();
	}catch(Exception e){
		System.out.println(e.getMessage());
	} 
	
}
	
}
