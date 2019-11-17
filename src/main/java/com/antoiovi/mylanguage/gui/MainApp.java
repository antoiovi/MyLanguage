package com.antoiovi.mylanguage.gui;

import java.io.File;
import java.net.URISyntaxException;

import com.antoiovi.arrangesentence.JDArrangeSentence;

/**
 * Applicazione principale :
 * Versione 1 : Lancia l'applicazione JDOrdersentence
 * @author antoiovi
 *This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. 
 *To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/ 
 *or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */
public class MainApp {

	public static void main(String[] args) {
		JDArrangeSentence jdordersentece=new JDArrangeSentence();
		jdordersentece.setVisible(true);

	}
public static final String getWorkingDir() {
	try {
		return new File(MainApp.class.getProtectionDomain().getCodeSource().getLocation()
			    .toURI()).getPath();
	} catch (URISyntaxException e) {
		return null;
	}
	}
}
