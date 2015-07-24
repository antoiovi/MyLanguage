package com.antoiovi.mylanguage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class MylUtility {

	
	
	public static boolean FileIsText(File file) throws IOException  {
		FileInputStream in = null;
     //   FileOutputStream out = null;
int asci_char=0;
int formt_char=0;
int others=0;
       
	try {
		String mimeType = Files.probeContentType(file.toPath());
		System.out.println(String.format("Mimetype : %s", mimeType));
		if(mimeType!=null && mimeType.equals("text/plain"))
			return true;
            in = new FileInputStream(file.getAbsolutePath());
          //  out = new FileOutputStream("outagain.txt");
            int c;

            while ((c = in.read()) != -1) {
            	if( (c>=65 && c<=90)|| (c>=97 && c<=122))
            		asci_char++;
            	else if ( (c>=32 && c<=64)|| (c>=91 && c<=96) ||  (c>=123 && c<=127))
               formt_char++;
            	else
            		others++;
            	if((asci_char+others)>1000){
            		float ratio_oth=(float)asci_char/(float)others;
            		/*float ratio_oth=(float)asci_char/(float)others;
            		float ratio_form=(float)asci_char/(float)formt_char;*/
            		if(ratio_oth<5.0)
                	break;
            	}
            }
            
        } finally {
            if (in != null) {
                in.close();
            }
           
        }
	float ratio_oth=(float)asci_char/(float)others;
	float ratio_form=(float)asci_char/(float)formt_char;
	System.out.println(String.format("Asci char %d\t  format char %d\t others %d\t ratio asci/others %f\t ratio ascii/format %f", 
			asci_char,formt_char,others,ratio_oth,ratio_form ));
	if(ratio_oth<5.0)
		return false;
	if(ratio_form<2.0)
		return false;
	
	return true;
		 }
	    
	
	
	/**
	 * Check if in a file there are for each line at least ONE repeated separator
	 * of kind = ; | 
	 * @param file
	 * @return
	 */
	public static char FileIsCSV_Simple(File file){
		return '=';
		
	}
	
}
