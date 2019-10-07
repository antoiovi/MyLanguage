package com.antoiovi.mylanguage.gui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.antoiovi.mylanguage.Quizword;

import java.awt.event.ActionListener;

public class Quiz_game extends Popupquiz {
	boolean count_score=false;
	
	public Quiz_game() {
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
		okLabel.setVisible(true);
		jdialog.setClickButton(OK);
		quizword.getNextWords( answerS);
		question=answerS[0];
		answer=answerS[1];	
		super.resetAnswersButtons();
	}

	private void wrongAnswer(){
		wrongLabel.setVisible(true);
		okLabel.setVisible(false);;
		return;
	}



	@Override
	public void setQuizword(Quizword quizword) {
		// TODO Auto-generated method stub
		super.setQuizword(quizword);
		quizword.setModal_score_count(count_score);
		quizword.startGame();
	}
	
	
}
