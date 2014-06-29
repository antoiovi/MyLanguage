package com.antoiovi.mylanguage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class Quizword {
	public static final int END_GAME=1;
	public static final int NEXT_ANSWER=2;
	
	// Coppia domanda,risposta
	private HashMap<String,String> parole;
	// chiavi: set di tutte  le domande
	private Vector<String> chiavi;
	// Serve per ottenere un array di Integer contenente
	// n numeri 1....n , con ordine casuale
	Mazzocarte mazzo;
	// serve per avere int[3] per mescoloare le risposte
	Mazzocarte mazzoAnswer;
	private int score;
	// se true allora conta i punti
	private boolean modal_score_count;
	private int cursor;
	// numero di chiavi : npuò essere diverso dalle paroleppeerchè non ci sono duplicati
	private int numberofKey;
	// array contenete interi da 1 a numberOfkey, in ordine sparso,
	// per interrogare casualmente l'elenco di parole
	Object[] index;
	
	
	public Quizword(HashMap<String, String> parole) {
		this.parole = parole;
	Set keies=parole.keySet();
	
	chiavi=new Vector<String>();
	for (Iterator iterator = keies.iterator(); iterator.hasNext();) {
		String chiave = (String) iterator.next();
		chiavi.add(chiave);
	}
	numberofKey=chiavi.size();
	
	try {
		mazzoAnswer=new Mazzocarte(3);
		mazzo=new Mazzocarte(numberofKey);
		reset();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	}
	
	public Quizword() {
		// TODO Auto-generated constructor stub
	}

	// avanza il cursore e imposta la nuova domanda,risposta e due rsposte non valide
	public int getNextWords(String answers[]){
		try {
		cursor++;
		// se ho scandito tutte le chiavi rimescolo e ripunto il cursore a 0
		// SE SONO IN MODALITà e ho finito le domande score_counte return END_GAME
		if(cursor>=chiavi.size()){
			if(modal_score_count){
				return END_GAME;
			}
			reset();
			}
		// index[] =mazzo.toArray
		int x=(Integer)index[cursor];
		
		answers[0]=chiavi.get(x-1);
		answers[1]=parole.get(answers[0]);
		// devo avere due risposte casuali
		int y,z;
				Mazzocarte mazzoDue=new Mazzocarte(numberofKey);
				Iterator it=mazzoDue.getMazzo().iterator();
				y=x;
				z=x;
				while(y==x ){
					Integer i=(Integer)it.next();
					 y=i.intValue();
				}
				//trvo il successivo valore diverso da x
				while(z==x ){
					Integer i=(Integer)it.next();
					 z=i.intValue();
				}
				x--;
				y--;
				z--;
				answers[0]=chiavi.get(x);
				answers[1]=parole.get(answers[0]);
				
				
				
		
	// MESCOLO LE TRE RISPOSTE	
		mazzoAnswer.mescola();
	// mazzo di tre interi (1,2,3) così ogni giro x0,x1,x2 hanno calori casuali	
		// servono per avere le tre risposte VISULAIZZATE in modo casuale
	int x0=	mazzoAnswer.mazzo.get(0);
	int x1=	mazzoAnswer.mazzo.get(1);
	int x2=	mazzoAnswer.mazzo.get(2);
	answers[x0+1]=parole.get(answers[0]);
	
		mazzo.mazzo.get(0);
		String q1=chiavi.get(y);
		answers[x1+1]=parole.get(q1);
		q1=chiavi.get(z);
		answers[x2+1]=parole.get(q1);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return NEXT_ANSWER;
	}
	
	public void reset(){
		mazzo.mescola();
		cursor=0;
		index=mazzo.toArray();
		score=0;
		}
	
	public void startGame(){
		reset();
	}
	
	/**
	 * Riceve una risposta, restituisce true se rispota esatta
	 * @param answer
	 * @return
	 */
	public boolean setAnswer(String answer){
		int x=(Integer)index[cursor];
		String question=chiavi.get(x-1);
		boolean test=parole.get(question).equals(answer);
		if(modal_score_count){
			if(test)
			 score++;
		}
		return test;
	}


	public int getScore() {
		return score;
	}

	public boolean isModal_score_count() {
		return modal_score_count;
	}

	public void setModal_score_count(boolean modal_score_count) {
		this.modal_score_count = modal_score_count;
	}

	public int getNumberofKey() {
		return numberofKey;
	}
	

}
