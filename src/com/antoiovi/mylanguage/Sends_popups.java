package com.antoiovi.mylanguage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JDialog;

import com.antoiovi.mylanguage.gui.Ppoupword;

public class Sends_popups extends java.util.TimerTask {
Mylanguage mylanguage;
List<String> texts;
List<String> texts_2;

	
	
	public Sends_popups(Mylanguage mylanguag) {
		super();
		mylanguage=mylanguag;
		texts=mylanguage.getListWords();
		texts_2=new ArrayList<String>();
		Iterator i=texts.iterator();
		while(i.hasNext()){
			texts_2.add((String)i.next());
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		int Min=0;
		int Max=texts_2.size();
		if(Max==0){
			Iterator i=texts.iterator();
			while(i.hasNext()){
				texts_2.add((String)i.next());
			}
			Max=texts_2.size();
		}
		Max--;
		int x=Min + (int)(Math.random() * ((Max - Min) + 1));
		String text=texts_2.get(x);
		
		try {
			Ppoupword dialog = new Ppoupword("",text);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			if(dialog.getClickButton()==dialog.ESC){
				this.cancel();
				System.exit(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		texts_2.remove(x);
		
	}

	
}
