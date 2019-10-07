package com.antoiovi.mylanguage.quizmultiplace;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.ListModel;

import com.antoiovi.mylanguage.Mylangprogram;
import com.antoiovi.mylanguage.QuestionAnswer;

import javax.swing.BoxLayout;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
/***
 * 
 * @author Antoiovi	Antonello Iovino
 * 22/07/2015
 * 	
 *
 */
public class JPQuizMultiple extends JPanel {

	QuizMultiplePlace quizmultp=new QuizMultiplePlace();
	/**
	 * QUESTIONS
	 */
	private JList jlistQuestions;
	private ListModel listmodel;
	/**
	 * ANSWERS
	 */
	private List<String> answers;
	private JPanel panel_answ;
	private final Action action = new SwingAction();
	private File textfile;
	private JLabel lblError;
	private JLabel lblOk;
	int score=0;
	private JLabel lblScore;
	private JLabel lblWarning;
	private JPanel contentPane;
	private File file;
	List<JButton> jbanswers;
	
	Mylangprogram mylangprogram;
	private JLabel lblMaxScore;
	private JButton btnRELOAD;
	private JLabel lblGAMEOVER;
	
	public void setMylangprogram(Mylangprogram mylangprogram) {
		this.mylangprogram = mylangprogram;
	}


	/**
	 * Create the panel.
	 */
	public JPQuizMultiple() {
		setLayout(new BorderLayout(0, 0));
		contentPane=this;
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		jlistQuestions = new JList();
		jlistQuestions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblError.setVisible(false);
				lblOk.setVisible(false);
				lblWarning.setVisible(false);
			}
		});
		
		jbanswers=new ArrayList<JButton>();
		panel.add(jlistQuestions, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		
		lblGAMEOVER = new JLabel("GAME OVER! Select new file or RELOAD!");
		lblGAMEOVER.setIcon(new ImageIcon(JPQuizMultiple.class.getResource("/com/antoiovi/mylanguage/icons/warning.png")));
		panel_2.add(lblGAMEOVER);
		
		lblWarning = new JLabel("WARNING! Select an item!");
		lblWarning.setIcon(new ImageIcon(JPQuizMultiple.class.getResource("/com/antoiovi/mylanguage/icons/warning.png")));
		panel_2.add(lblWarning);
		
		lblOk = new JLabel("OK !");
		lblOk.setIcon(new ImageIcon(JPQuizMultiple.class.getResource("/com/antoiovi/mylanguage/icons/ok.png")));
		panel_2.add(lblOk);
		
		lblError = new JLabel("ERROR !");
		lblError.setIcon(new ImageIcon(JPQuizMultiple.class.getResource("/com/antoiovi/mylanguage/icons/wrong.png")));
		panel_2.add(lblError);
		
		panel_answ = new JPanel();
		 
		add(panel_answ, BorderLayout.EAST);
		panel_answ.setLayout(new BoxLayout(panel_answ, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("Risposte");
		panel_answ.add(lblNewLabel_1);
				
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		/*******************************
		 * OPEN FILE
		****************************** */
		JButton btnOpenFile = new JButton("Open file");
		btnOpenFile.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfilechooser = new JFileChooser();
				jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
				int x = jfilechooser
						.showOpenDialog(contentPane);
				if (x == JFileChooser.APPROVE_OPTION) {
					file = jfilechooser.getSelectedFile();
				}
				setDataFile(file);
			}
		});
		
		panel_1.add(btnOpenFile);
		
		lblScore = new JLabel("Score : 0");
		panel_1.add(lblScore);
		lblMaxScore = new JLabel(" Max score :");
		panel_1.add(lblMaxScore);
		/*****************
		 * reload game
		***************** */
		btnRELOAD = new JButton("RELOAD!");
		btnRELOAD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeanswersbuttons();
				init();
			}
		});
		panel_1.add(btnRELOAD);
		/***************
		 * INITIALIZE
		 **************/
		init();
	}
	
	/**
	 * Set the DATA FILE
	 * @param file_txt
	 * @return
	 */
public boolean setDataFile(File file_txt) {
	if( quizmultp.setDataFile(file_txt)){
		removeanswersbuttons();
		init();
		/**
		 * NOTIFICATION TO PARENT
		 */
		if(mylangprogram!=null)
			mylangprogram.changedFile(file_txt);
			return true;
	}else{
		return false;
	}
	
	}

/*************************************************
 * INITIALIZE
 *************************************************/
void init(){
  
	/**
	 * Load the questions
	 */
	DefaultListModel deflistmodel=new DefaultListModel();
	List<QuestionAnswer> listQestAnsw=quizmultp.getQuestionAnswer();
	for(QuestionAnswer qas:listQestAnsw){
		deflistmodel.addElement(qas);
		//answers.add(qas.getAnswer());
	}
	 jlistQuestions.setModel(deflistmodel);
	 lblMaxScore.setText("Highest Score : "+listQestAnsw.size());
	 
	 /**
	  * Create the answers
	  */
	 answers=quizmultp.getAnswers();
	 for(String s:answers){
	 	 JButton btnNewButton = new JButton(s);
	 	 btnNewButton.setAction(new SwingAction(s));
	 	 jbanswers.add(btnNewButton);
		 }
	addanswersbuttons();
	lblError.setVisible(false);
	lblOk.setVisible(false);
	lblWarning.setVisible(false);
	if(deflistmodel.isEmpty())
	lblGAMEOVER.setVisible(true);
	else
		lblGAMEOVER.setVisible(false);

	resetScore();
}
/**
 * ADD THE ANSWERS BUTTONS
 */
void addanswersbuttons(){
	for(JButton jb:jbanswers){
	panel_answ.add(jb);	}
}
/**
 * REMOVE THE ANSWERS BUTTONS
 */
void removeanswersbuttons(){
	for(JButton jb:jbanswers){
		panel_answ.remove(jb);}
	jbanswers.clear();
}


	
void resetScore(){
	score=0;
	lblScore.setText("Score = "+score);

}
void scoreAdd(int x){
	score+=x;
	lblScore.setText(" Score = "+score);
}


	private class SwingAction extends AbstractAction {
		String answer;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public SwingAction(String answer) {
			putValue(NAME, answer);
			putValue(SHORT_DESCRIPTION, "Some short description");
			this.answer=answer;
		}
		
		public void actionPerformed(ActionEvent e) {
		int x=	jlistQuestions.getSelectedIndex();
		if(x==-1){
			lblWarning.setVisible(true);
			lblError.setVisible(false);
			lblOk.setVisible(false);
			return;
		}
		DefaultListModel deflistmode= (DefaultListModel) jlistQuestions.getModel();
			QuestionAnswer qa=(QuestionAnswer) jlistQuestions.getModel().getElementAt(x);
			if(qa.check(answer)){
				lblError.setVisible(false);
				lblOk.setVisible(true);
				lblWarning.setVisible(false);
				deflistmode.remove(x);
				scoreAdd(1);
			}else{
				lblError.setVisible(true);
				lblOk.setVisible(false);
				lblWarning.setVisible(false);
				scoreAdd(-1);
				
			}
			if(deflistmode.isEmpty()){
				lblGAMEOVER.setVisible(true);
			}else{
				lblGAMEOVER.setVisible(false);
			}
		}
	}


	 
	
	 
}
