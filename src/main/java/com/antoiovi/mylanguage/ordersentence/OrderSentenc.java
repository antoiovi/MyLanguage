package com.antoiovi.mylanguage.ordersentence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.antoiovi.mylanguage.Mazzocarte;
import com.antoiovi.mylanguage.MylUtility;
import com.antoiovi.mylanguage.QuestionAnswer;


/**
 * 
 * @author antoiovi Antonello Iovino
 * 
 * Questa classe ha un array di stringhe (Vector <String>) che viene
 * recuperata tramite il metodo  	
 * public List<String> getSentences() 
 * 	che le restituisce in ordine sempre diverso (vengono mescolate ogni volta che 
 * 	si chiama il metodo).
 * 
 * Le stringhe (frasi) vengono inzzializzate tramite il metodo
 * public boolean setDataFile(File file_)
 * 	che prima verifica il tipo di file
 * 
 * 
 * 
 *
 */
public class OrderSentenc {
Vector<String> sentences_list;

private Mazzocarte mazzo;
private File file;
	
	public OrderSentenc(File file_){
		this.file=file_;
		sentences_list=new Vector<String>();
		if(file==null){
			String sentence=  "This ciao ciao";
		for (int x = 0; x < 5; x++) {
			sentence=sentence.concat("word "+x);
			sentences_list.add(sentence);
		}
		}else{
			inizializeFromFile();
		}
		/**
		 * Inizialize the deck of cards
		 */
		try {
			mazzo = new Mazzocarte(sentences_list.size());
		} catch (Exception e) {
			// hsetq.size minore di 1
		}
		
	}
	public OrderSentenc(){
		sentences_list=new Vector<String>();
		String sentence=  "1 questa e una 1 frase prova a indovinare 2 frase se sei capace ";

		for (int x = 0; x < 5; x++) {
		//	sentence=sentence.concat("word "+x);
			sentences_list.add(sentence);
		}
		 
		/**
		 * Inizialize the deck of cards
		 */
		try {
			mazzo = new Mazzocarte(sentences_list.size());
		} catch (Exception e) {
			// hsetq.size minore di 1
		}
		
	}
	
	
	
	/**
	 * 
	 * @return a list of shaked (mixed) strings questions
	 */
	public List<String> getSentences() {
		mazzo.setNcarte(sentences_list.size());
		mazzo.mescola();
		//System.out.println(String.format("Mazzo sie %d \t sentences_lsit.size %d", mazzo.getNcarte(),sentences_list.size()));
		List<String> sentences = new ArrayList<String>();
		Iterator iter = mazzo.getMazzo().iterator();
		Object[] o = sentences_list.toArray();
		while (iter.hasNext()) {
			Integer in = (Integer) iter.next();
			sentences.add(sentences_list.get(in-1));
		}
	//	System.out.println(String.format(" sentences.size %d",  sentences.size()));
		return sentences;
	}
	
	/**
	 * Inizzializza il Vector<String> sentences da un file
	 * il file deve essere verificato nel metodo 
	 * public boolean setDataFile(File file_)
	 * 
	 * che se ha successo richiama questo metodo.
	 * 
	 * @return true, se l'inizzializzazione ha avuto successo
	 */
	private boolean inizializeFromFile(){
		sentences_list.clear();
		int count=0;
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null ) {
				if(line.isEmpty())
					continue;
				sentences_list.add(line);
				count++;
				}
			//System.out.println("n righe"+count);
			br.close();
		}catch(Exception e){
			return false;
		}
	return true;
}
	
	public boolean setDataFile(File file_){
		if(!testfile(file_)) return false;
		this.file=file_;
		return inizializeFromFile();
		
	}
	/**
	 * Temporarly check only if textfile is not null
	 * @return
	 */
	private boolean testfile(File file_){
		if(file_==null)
			return false;
		try {
			if(MylUtility.FileIsText(file_))
			return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
