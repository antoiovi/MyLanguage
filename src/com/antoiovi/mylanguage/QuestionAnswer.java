package com.antoiovi.mylanguage;
/**
 * 
 * @author Antoiovi
 * 22/07/2015  This class encapsulate a pari<Question,Answers> as Strings;
 * 		
 * 	it has a mathod to check if answer is right.
 * 
 */
public class QuestionAnswer {
String question;
String answer;



public QuestionAnswer(String question, String answer) {
	super();
	this.question = question;
	this.answer = answer;
}


public String getQuestion() {
	return question;
}
public void setQuestion(String question) {
	this.question = question;
}
public String getAnswer() {
	return answer;
}
public void setAnswer(String answer) {
	this.answer = answer;
}

/**
 * Check if answer is riight
 * @param _answer
 * @return
 */
public boolean check(String _answer){
	return answer.equals(_answer);
}


@Override
public String toString() {
	return question;
}

}
