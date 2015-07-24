package com.antoiovi.mylanguage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import com.sun.org.apache.regexp.internal.recompile;

public class Language implements Mylanguage {
   /**
    * The file in use by the editor
    */
	File textfile;
   /**
    * The time to be used for pop up periodically by
    * 	Start_pops and Start_quizzing
    */
   int minuti=5;
   
   char DELIMITER='=';
   
   public Language() {
	
	}  
   
   
	public Language(File textfile) {
	super();
	this.textfile = textfile;
}

	
	/**
	 * 24/07/2015 Added File verification
	 *  converted to booelan to test validity of file.
	 *  	Only text file are valid!!!
	 *  	Override the method or cretae a new prptotype
	 *  	to accept other files.
	 */
	@Override
	public boolean setTextfile(File textfile) {
		try {
			if(MylUtility.FileIsText(textfile)){
			this.textfile=textfile;
			return true;
			}
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}



	@Override
	public void ConnectDb() {
		try {
			throw new Exception("Unumplemetd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create a thread of type TimerTask an periodically run it
	 */
	@Override
	public void Start_popups() throws Exception {
		/**
		 * Verifica se il file è idoneo; attualmente verifica solo se è null
		 * 24/07/2015  The file verification is made in setFile(File );
		 * 				so if is not null, its verified as text File.
		 * 			others verification for more specific formats
		 * 			can be made here. 
		 */
		if(textfile==null){
			throw new Exception("textfile not inizialized");
		}
		Sends_popups sp = new Sends_popups(this); 
	    java.util.Timer timer = new java.util.Timer(); 
	    timer.schedule(sp,0,1000*60*minuti); 
	  	}
	/**
	 * Create a thread of type TimerTask an periodically run it
	 */
	@Override
	public void Start_popquiz() throws Exception {
		/**
		 * Verifica se il file è idoneo; attualmente verifica solo se è null
		 * 24/07/2015  The file verification is made in setFile(File );
		 * 				so if is not null, its verified as text File.
		 * 			others verification for more specific formats
		 * 			can be made here. 
		 */
		if(textfile==null){
			throw new Exception("textfile not inizialized");
		}
		TimertaskQuizword sp = new TimertaskQuizword(this); 
	    java.util.Timer timer = new java.util.Timer(); 
	    timer.schedule(sp,0,1000*60*minuti); 
	  	}

	
	@Override
	public void Start_speaking()  {
		try {
			throw new Exception("Unimplemetd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
/**
 * A list of strings: one string each not empty row
 */
	@Override
	public List<String> getListWords() {
		if(textfile==null)
			return null;
		List<String> dati=new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(textfile));
			String line;
			
			while ((line = br.readLine()) != null ) {
				if(line.isEmpty())
					continue;
				dati.add(line);
			}
			br.close();
			
		}catch(Exception e){
			dati.add("Errore nella lettura del file");
			dati.add(e.getMessage());
		}
				
		return dati;
	}
 /**
  * not yet used cause the validaation is made in setTextFile(File)
  * 	can be implemented some specific verification.
  * @return
  */
	private boolean testfile(){
		if(textfile==null)
			return false;
		return true;
	}

	@Override
	public void setTimePopsup(int minuti) {
		// TODO Auto-generated method stub
		this.minuti=minuti;
		
	}


	@Override
	public Vector getPairKeyWord(File infile) throws Exception {
	textfile=infile;
		Vector PairKeyWord=new Vector<Vector<Object>>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(infile));
			String line;
			String delims = "["+DELIMITER+"]+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
			// Per ogni riga
			while ((line = br.readLine()) != null ) {
				String[] tokens = line.split(delims);	
				if(tokens.length<2){
					if(tokens.length==1){
					Vector v	=new Vector<String>();
					v.add(tokens[0]);
					v.add("");
						PairKeyWord.addElement(v);
					}						
					else
					continue;
					
				}else{
					Vector v	=new Vector<String>();
					v.add(tokens[0]);
					v.add(tokens[1]);
						PairKeyWord.addElement(v);
					
				}
					
		}
			br.close();
			
		}catch(Exception e){
			throw e;
		}
			
		return PairKeyWord;
	}

/**
 * Create a map<key,value> wher key value are extracted from
 * each line of the file, in wich they are separated by the DELIMITER
 * 	the default delimiter is =; 
 * then a new object Quizword is returned
 */
	@Override
	public Quizword getQuizword() throws Exception {
		
		HashMap<String,String> map=new LinkedHashMap<String,String>();
		//Vector PairKeyWord=new Vector<Vector<Object>>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(textfile));
			String line;
			String delims = "[" + DELIMITER + "]+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
			// Per ogni riga
			while ((line = br.readLine()) != null ) {
				String[] tokens = line.split(delims);	
				if(tokens.length<2){
					if(tokens.length==1){
						map.put(tokens[0], "xxx");
					}						
					else
					continue;
					
				}else{
					map.put(tokens[0], tokens[1]);
						}
			}
			br.close();
			return new Quizword(map);
		}catch(Exception e){
			throw e;
		}
		
	}
	
}
