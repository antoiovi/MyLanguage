package com.antoiovi.mylanguage.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundsUtil implements LineListener {

	
	public void Error(){
		File soundFile=new File("NEG1.WAV");
	
     Line.Info linfo = new Line.Info(Clip.class);
    Line line;
	try {
		line = AudioSystem.getLine(linfo);
		 Clip clip = (Clip) line;
		    clip.addLineListener(this);
		    AudioInputStream ais;
		    ais = AudioSystem.getAudioInputStream(soundFile);
		    clip.open(ais);
		    clip.start();
	} catch (LineUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedAudioFileException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 
	}

	@Override
	public void update(LineEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
