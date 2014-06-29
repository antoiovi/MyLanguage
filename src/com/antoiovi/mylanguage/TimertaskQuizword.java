package com.antoiovi.mylanguage;

import java.util.Iterator;
import java.util.TimerTask;

import javax.swing.JDialog;

import com.antoiovi.mylanguage.gui.Popupquiz;
import com.antoiovi.mylanguage.gui.Ppoupword;

public class TimertaskQuizword extends TimerTask {
	Mylanguage mylanguage;
	Quizword quizword;
	
	
	
	
	public TimertaskQuizword(Mylanguage mylanguage) {
		super();
		this.mylanguage = mylanguage;
		try {
			quizword=mylanguage.getQuizword();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		
		
		try {
			
			
			Popupquiz dialog = new Popupquiz();
			dialog.setQuizword(quizword);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			if(dialog.getClickButton()==dialog.ESC){
				this.cancel();
				System.exit(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}


	

}
