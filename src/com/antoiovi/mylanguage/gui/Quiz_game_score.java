package com.antoiovi.mylanguage.gui;

import java.awt.event.ActionEvent;

import com.antoiovi.mylanguage.Quizword;
import javax.swing.JLabel;

public class Quiz_game_score extends Popupquiz {
	boolean count_score=true;
	int rightAnswers=0;
	int numberOfAnswers=0;
	
	public Quiz_game_score(){
		super();
		setModal(true);
		 okButton.setText("Fine");
	 cancelButton.setVisible(false);
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if (cmd.equals("OK")) {
			jdialog.setClickButton(OK);
			endGame();
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
		//wrongLabel.setVisible(false);
	//	okLabel.setVisible(true);
		numberOfAnswers++;
		rightAnswers++;
		int x=quizword.getNextWords( answerS);
		if(x==quizword.END_GAME){
			endGame();
			return;
		}
		question=answerS[0];
		answer=answerS[1];	
		super.resetAnswersButtons();
	}
	
	

	private void wrongAnswer(){
		numberOfAnswers++;
		int x=quizword.getNextWords( answerS);
		if(x==quizword.END_GAME){
			endGame();
		}
		question=answerS[0];
		answer=answerS[1];	
		super.resetAnswersButtons();
		return;
	}

public void endGame(){
	String risultato="Numero risposte esatte "+rightAnswers+" su "+numberOfAnswers +" domande!!\n";
	
	textArea.setText(risultato);
	float percent=100* rightAnswers/numberOfAnswers;
	String strpercent=String.format("%.1f", percent);
	String str="Percentuale rispste esatte = "+strpercent+"  !!";
	textArea.append(str);
	
	btnAnswer1.setVisible(false);
	btnAnswer2.setVisible(false);
	btnAnswer3.setVisible(false);
	
	okButton.setVisible(false);
	cancelButton.setVisible(true);
	
}
	
	@Override
	public void setQuizword(Quizword quizword) {
		// TODO Auto-generated method stub
		super.setQuizword(quizword);
		quizword.setModal_score_count(count_score);
		quizword.startGame();
	}
	
}
