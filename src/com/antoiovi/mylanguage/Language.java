package com.antoiovi.mylanguage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

public class Language implements Mylanguage {
   File textfile;
   int minuti=5;
   
   public Language() {
	
	}  
   
   
	public Language(File textfile) {
	super();
	this.textfile = textfile;
}

	
	
	@Override
	public void setTextfile(File textfile) {
		this.textfile=textfile;
		
	}



	@Override
	public void ConnectDb() {
		// TODO Auto-generated method stub

	}
// Crea un thread di tipo TimerTask (Sends_popup) e lo richiama periodicamente
	@Override
	public void Start_popups() throws Exception {
		// Verifica se il file è idoneo; attualmente verifica solo se è null
		if(!testfile()){
			throw new Exception("textfile not inizialized");
		}
		Sends_popups sp = new Sends_popups(this); 
	    java.util.Timer timer = new java.util.Timer(); 
	    timer.schedule(sp,0,1000*60*minuti); 
	  	}
	@Override
	public void Start_popquiz() throws Exception {
		// Verifica se il file è idoneo; attualmente verifica solo se è null
		if(!testfile()){
			throw new Exception("textfile not inizialized");
		}
		TimertaskQuizword sp = new TimertaskQuizword(this); 
	    java.util.Timer timer = new java.util.Timer(); 
	    timer.schedule(sp,0,1000*60*minuti); 
	  	}

	
	@Override
	public void Start_speaking() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getListWords() {
		
		List<String> dati=new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(textfile));
			String line;
			
			while ((line = br.readLine()) != null ) {
				dati.add(line);
			}
			br.close();
			
		}catch(Exception e){
			dati.add("Errore nella lettura del file");
			dati.add(e.getMessage());
		}
				
		return dati;
	}

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
			String delims = "[=]+"; // use + to treat consecutive delims as one;
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


	@Override
	public Quizword getQuizword() throws Exception {
HashMap<String,String> map=new LinkedHashMap<String,String>();
		//Vector PairKeyWord=new Vector<Vector<Object>>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(textfile));
			String line;
			String delims = "[=]+"; // use + to treat consecutive delims as one;
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
