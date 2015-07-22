package com.antoiovi.mylanguage.quizmultiplace;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.antoiovi.mylanguage.MyProperties;
import com.antoiovi.mylanguage.Mylangprogram;

public class JDQuizMultiple extends JDialog implements Mylangprogram{

	private JPanel contentPane;
	private JPQuizMultiple panelQuizMultiple;
	Properties properties = MyProperties.getInstance().getProperties();
	private File fileData=null;
	 

	/**
	 * Create the frame.
	 */
	public JDQuizMultiple() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		panelQuizMultiple = new JPQuizMultiple();
		/**
		 * load the properties : filedata name, ...
		 */
		loadproperties();
		
		if(fileData==null){
			fileData = new File("file01.txt");
		}
		try {
			panelQuizMultiple.setDataFile(fileData);
			this.setTitle(fileData.getAbsolutePath());
		} catch (Exception e) {
			this.setTitle("No file loaded");
		
		}
		contentPane.add(panelQuizMultiple, BorderLayout.CENTER);
		panelQuizMultiple.setMylangprogram(this);
	}

	@Override
	public void changedFile(File file) {
		if(file!=null){
			this.setTitle(file.getAbsolutePath());
			fileData=file;
			SAVEPROPERTIES();
		}
	}

	/**
	 * load stored properties
	 */
	private void loadproperties(){
		InputStream inputProp = null;
	 	try {
	 	inputProp=MyProperties.getInstance().inputStreamProperties();
	 	if (inputProp != null) {
			properties.load(inputProp);
			String filename=properties.getProperty(MyProperties.FILE_MULTIQUIZ_PROP);
			System.out.println("Flename= "+filename);
			if(filename!=null){
			fileData=new File(filename);
			System.out.println("Flename!=null = "+filename);
			}else{
				System.out.println("Flename=NULL "+filename);	
			}
		}
			}catch(Exception e)
			{	
				e.printStackTrace();
			}
	}
	
	/**
	 * save properties (options)
	 */
	void SAVEPROPERTIES(){
		OutputStream output = null;
		try {
			output=MyProperties.getInstance().outputStreamProperties();
			properties.setProperty(MyProperties.FILE_MULTIQUIZ_PROP, fileData.getAbsolutePath());
			properties.store(output, null);
			output.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		} 
		
	}
	
}
