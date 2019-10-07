package com.antoiovi.mylanguage;

import java.io.File;
/**
 * 
 * @author Anto
 * 22/07/2015 This interface is used to send notification from inner levels to outer level
 * (for example form a panel to the master JDialog which implements this interface)
 *
 */
public interface Mylangprogram {

	public void changedFile(File file);
	
	
}
