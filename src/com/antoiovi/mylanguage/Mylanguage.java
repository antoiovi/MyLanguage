package com.antoiovi.mylanguage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
/**
 * 
 * @author   Antonello Iovino
 * 
 * Known implemented :
 * 			 Language
 *
 */
public interface Mylanguage {
 public void ConnectDb();
 /**
	 * Create a thread of type TimerTask an periodically run it
	 *      exception is file is null;
	 */
 public void Start_popups() throws Exception;
 public void Start_speaking();
 /**
  * A String for each not empty line of the text file
  * @return a list of strings
  */
 public List<String> getListWords();
 /**
  * Is setted the file text. 
  * @param textfile
  * @return
  */
public boolean setTextfile(File textfile);
/**
 * Is setted the interval time bettwen popps up.
 * @param minuti
 */
 public void setTimePopsup(int minuti);
 
 
 public Vector getPairKeyWord(File infile)throws Exception;
 public Quizword getQuizword() throws Exception;
 
 /**
	 * Create a thread of type TimerTask an periodically run it
	 *  exception is file is null;
	 */
void Start_popquiz() throws Exception;
 
}
