package com.antoiovi.arrangesentence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.antoiovi.mylanguage.Mazzocarte;
import com.antoiovi.mylanguage.MylUtility;


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
 *This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. 
 *To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/ 
 *or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */
public class ArrangeSentence {
Vector<String> sentences_list;

private Mazzocarte mazzo;
private File file;
	
	public ArrangeSentence(File file_){
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
	public ArrangeSentence(){
		sentences_list=new Vector<String>();
		
		String sentences[]= {
				"This is a sentence. Try to guess it !",
				"You can load sentences in any language from a text file.",
				"Create your file of sentences to exercise your self.",
				"You can create a file to learn verbs or adjectives or whatever."
		};
		for (int x = 0; x < sentences.length; x++) {
		//	sentence=sentence.concat("word "+x);
			sentences_list.add(sentences[x]);
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
		//int count=0;
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null ) {
				if(line.isEmpty())
					continue;
				sentences_list.add(line);
				//count++;
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
