package com.antoiovi.mylanguage.quizmultiplace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.antoiovi.mylanguage.Mazzocarte;
import com.antoiovi.mylanguage.MylUtility;
import com.antoiovi.mylanguage.QuestionAnswer;

public class QuizMultiplePlace {
File textfile;
/**
 * Hashset to ensure unicity
 */
	HashSet<QuestionAnswer> questionanswer_set;
	/**
	 * HasSet to ensure unicity
	 */
	HashSet<String> answer_set;
	// Serve per ottenere un array di Integer contenente
	// n numeri 1....n , con ordine casuale
	Mazzocarte mazzo;
	/**
	 * list of <question,answer> redy to be distribuited or shaked
	 */
	private List<QuestionAnswer> questionanswers_list;
	/**
	 * list of (unique) answers ready to be distribuited or randomly mixed
	 */
	private List<String> answers_list;

	 

	QuizMultiplePlace() {
		/**
		 * default questionanswer_set
		 */
		questionanswer_set = new HashSet<QuestionAnswer>();
		for (int x = 0; x < 5; x++) {
			QuestionAnswer qa = new QuestionAnswer(("Random question n " + x), "answer ="+ x);
		questionanswer_set.add(qa);
		}
		
		/**
		 * Inizialize the deck of cards
		 */
		try {
			mazzo = new Mazzocarte(questionanswer_set.size());
		} catch (Exception e) {
			// hsetq.size minore di 1
		}
		/**
		 * inizialize the quesstionaanswers_list
		 */
		questionanswers_list = new ArrayList<QuestionAnswer>();
		
		/**
		 * inizialize the set of answers
		 */
		answer_set = new HashSet<String>();
	}

	/**
	 * SET THE DATA FILE  and load the data from the file
	 * 	CREATE the questionanswer_set
	 * @param file_txt
	 * @return
	 */
	public boolean setDataFile(File file_txt){
		this.textfile=file_txt;
		if(!testfile()) return false;
		if(questionanswer_set==null)
		questionanswer_set = new HashSet<QuestionAnswer>();
		else if(!questionanswer_set.isEmpty())
			questionanswer_set.clear();
		try{
			BufferedReader br = new BufferedReader(new FileReader(textfile));
			String line;
			String delims = "[=]+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
			// Per ogni riga
			while ((line = br.readLine()) != null ) {
			//	System.out.println( "line :"+line);
				String[] tokens = line.split(delims);	
				if(tokens.length<2){
					if(tokens.length==1){
						// row only withoute delimiter; assume is a question
					QuestionAnswer qa = new QuestionAnswer(tokens[0], "Risposta mancante");
					questionanswer_set.add(qa);
					}						
					else
					continue;
					
				}else{
					QuestionAnswer qa = new QuestionAnswer(tokens[0], tokens[1]);
					questionanswer_set.add(qa);
					// System.out.println( qa.getQuestion()+"  "+qa.getAnswer());
									
				}
					
		}
			br.close();
			
		}catch(Exception e){
			return false;
		}
	
		return true;
	}
	
	/**
	 * Temporarly check only if textfile is not null
	 * 24/07/2015 Added a test to MylUtility.
	 * @return
	 */
	private boolean testfile(){
		if(textfile==null)
			return false;
		try {
			if(MylUtility.FileIsText(textfile))
			return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
/**
	 * 
	 * @return a list of shaked (mixed) strings questions
	 */
	public List<String> getQuestions() {
		mazzo.mescola();
		List<String> questions = new ArrayList<String>();
		Iterator iter = mazzo.getMazzo().iterator();
		Object[] o = questionanswer_set.toArray();
		while (iter.hasNext()) {
			Integer in = (Integer) iter.next();
			QuestionAnswer qa = (QuestionAnswer) o[in - 1];
			questions.add(qa.getQuestion());
		}

		return questions;
	}

	/**
	 * 
	 * @return a shacked list of QusetionAnswerss
	 */
	public List<QuestionAnswer> getQuestionAnswer() {
		if (questionanswers_list != null)
			questionanswers_list.clear();
		if (answer_set != null)
			answer_set.clear();
		/**
		 * shacke the desck of cards
		 */
		mazzo.mescola();
		// questionanswers_list = new ArrayList<QuestionAnswer>();
		Iterator iter = mazzo.getMazzo().iterator();
		Object[] o = questionanswer_set.toArray();
		while (iter.hasNext()) {
			Integer in = (Integer) iter.next();
			QuestionAnswer qa = (QuestionAnswer) o[in - 1];
			questionanswers_list.add(qa);
			
			boolean test=answer_set.add(qa.getAnswer());
		/**
		 * 	if(!test)
				System.out.println( qa.getAnswer()+" NOT added to answerset..");
			else
				System.out.println( qa.getAnswer()+" ADDED to answerset..");
				*/
		}
		
		// answers viene richiamato d getAnswers, in quanto vien rimescolato
		return questionanswers_list;
	}
	
/**
 * 
 * @return the SET of answers as a LIST , the answers are randomly mixed
 */
	public List<String> getAnswers() {
		if (answers_list == null) {
			answers_list = new ArrayList<String>();
		}
		if (answers_list != null && !answers_list.isEmpty())
			answers_list.clear();
		Mazzocarte mazzo_answers;
		try {
			mazzo_answers = new Mazzocarte(answer_set.size());
			mazzo_answers.mescola();
			Iterator iter = mazzo_answers.getMazzo().iterator();
			Object[] o = answer_set.toArray();
			while (iter.hasNext()) {
				Integer in = (Integer) iter.next();
				String qa = (String) o[in - 1];
				answers_list.add(qa);
			}
			return answers_list;
		} catch (Exception e) {
			return null;
		}
	}

}
