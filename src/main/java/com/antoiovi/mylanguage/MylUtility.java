package com.antoiovi.mylanguage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
/**
 * 
 * @author antoiovi  Antonello Iovino
 *
 */
public class MylUtility {


	/**
	 * Verifica se un file 'e di testo
	 *  - 1 Verifica il mimetype L sel mimetype=text/plain ok
	 *  - 2 Se non riesce con il mimetype conta 
	 *  		+ il numero di caratteri di testo
	 *  		+ il numero di caratteri stampabili non di testo
	 *  		+ gli altri caratteri
	 *  	++ se rapporto tra NUM_CAR_TESTO < del 90% ritorna false
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static boolean FileIsText(File file) throws IOException {
		FileInputStream in = null;
		// FileOutputStream out = null;
		int asci_char = 0;
		int formt_char = 0;
		int others = 0;
		int tot=0;
		try {
			String mimeType = Files.probeContentType(file.toPath());
			System.out.println(String.format("Mimetype : %s", mimeType));
			if (mimeType.equals("text/plain"))
				return true;
			in = new FileInputStream(file.getAbsolutePath());
			// out = new FileOutputStream("outagain.txt");
			int c;

			while ((c = in.read()) != -1) {
				tot++;
				if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122))
					asci_char++;
				else if ((c >= 32 && c <= 64) || (c >= 91 && c <= 96) || (c >= 123 && c <= 127))
					formt_char++;
				else
					others++;
				if (asci_char > 1000) {
					float ratio_oth = (float) asci_char / (float) others;
					/*
					 * float ratio_oth=(float)asci_char/(float)others; float
					 * ratio_form=(float)asci_char/(float)formt_char;
					 */
					if (ratio_oth < 5.0)
						break;
				}
			}

		} finally {
			if (in != null) {
				in.close();
			}

		}
		if((asci_char/tot)<0.9)
			return false;
		else
			return true;
		
	}

	/**
	 * Check if in a file there are for each line at least ONE repeated separator of
	 * kind = ; |
	 * 
	 * @param file
	 * @return
	 */
	public static char FileIsCSV_Simple(File file) {
		return '=';

	}

}
