package com.antoiovi.mylanguage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
/**
 * 
 * @author Antoiovi
 *		22/07/2015 Singleton Class to handle the properties from every sub part of the program;
 *
 *- I have a UNIQUE instance of Properties
 *Properties prop = MyProperties.getInstance().getProperties();
 *
 * - I have e centralized set of names of properties(static final String)
 *
 * - I can use to load some properties:
 *
 *		InputStream inputProp = null;
	 			try {
	 		 		inputProp=MyProperties.getInstance().inputStreamProperties();
	 			if (inputProp != null) {
					prop.load(inputProp);
						String filename=prop.getProperty(MyProperties.FILE_MULTIQUIZ_PROP);
 *
 *
 * -	I can use to save some properties:
 *		OutputStream output = null;
		try {
	 		 output=MyProperties.getInstance().outputStreamProperties();
	 		// set the properties value
			prop.setProperty(MyProperties.FILE_QUIZ_PROP, file.getAbsolutePath());
			prop.store(output, null);
			output.close();
			...........
 *			
 */
public class MyProperties {
	static Properties properties=null;
	
	static MyProperties myprop=null;
	
	public static final String filepropname = "config.properties";
	public static final String FILE_QUIZ_PROP="filename";
	public static final String FILE_MULTIQUIZ_PROP="filename_quizmultiple";
	public static final String FILE_QUIZ_ORDERSENT="filename_quizordersentence";
	public static final String DELAY_PROP="delay";
	
	private MyProperties(){
		
	}
	
	public static final  MyProperties getInstance(){
		if(myprop==null){
			myprop=new MyProperties();
		}
		return myprop;
	}
	
	public Properties getProperties(){
		if(properties==null){
			properties=new Properties();
		}
		return properties;
	}
	
	
	public InputStream inputStreamProperties() {
	
		InputStream inputStreamProp = null;
	 	try {
	 		
			String userdir=System.getProperty("user.dir");
			String fullfilename;
			fullfilename=userdir+"\\"+filepropname;
	 		inputStreamProp = new FileInputStream(fullfilename);
			if (inputStreamProp != null) {
			return inputStreamProp;
			}else{
				return null;
			}
			}catch(Exception e)
			{
				return null;
			}
	}
	
	public OutputStream outputStreamProperties(){
		OutputStream output = null;
		try {
	 			output = new FileOutputStream(filepropname);
	 			return output;
		}catch(Exception e){
			return null;
		}
	}
	
}
