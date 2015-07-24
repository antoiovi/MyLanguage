package com.antoiovi.mylanguage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public interface Mylanguage {
 public void ConnectDb();
 public void Start_popups() throws Exception;
 public void Start_speaking();
 public List<String> getListWords();
public boolean setTextfile(File textfile);
 public void setTimePopsup(int minuti);
 public Vector getPairKeyWord(File infile)throws Exception;
 public Quizword getQuizword() throws Exception;
void Start_popquiz() throws Exception;
 
}
