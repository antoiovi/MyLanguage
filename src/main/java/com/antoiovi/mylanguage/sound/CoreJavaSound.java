package com.antoiovi.mylanguage.sound;


	import java.io.File;
import java.io.InputStream;

	import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

	public class CoreJavaSound extends Object implements LineListener {
	  File soundFile;

	  JDialog playingDialog;

	  Clip clip;

	  public static void main(String[] args) throws Exception {
	    CoreJavaSound s = new CoreJavaSound();
	  }

	  public CoreJavaSound() throws Exception {
	  //  JFileChooser chooser = new JFileChooser();
	 //   chooser.showOpenDialog(null);
	  //  soundFile = chooser.getSelectedFile();
		 InputStream in=this.getClass().getResourceAsStream("/com/antoiovi/mylanguage/sound/NEG1.WAV");
		// InputStream in=this.getClass().getResourceAsStream("/NEG1.WAV");
		 AudioInputStream ais = AudioSystem.getAudioInputStream(in);
		// soundFile=new File( CoreJavaSound.class.getResource());
	   // System.out.println("Playing " + soundFile.getName());
		 Clip clip = AudioSystem.getClip();
         clip.open(ais);
         clip.start();
		 
		 
	/*    Line.Info linfo = new Line.Info(Clip.class);
	    Line line = AudioSystem.getLine(linfo);
	    clip = (Clip) line;
	    clip.addLineListener(this);
	    //AudioInputStream aiss = AudioSystem.getAudioInputStream(soundFile);
	    clip.open(ais);
	    clip.start();*/
	  }

	  public void update(LineEvent le) {
	    LineEvent.Type type = le.getType();
	    if (type == LineEvent.Type.OPEN) {
	      System.out.println("OPEN");
	    } else if (type == LineEvent.Type.CLOSE) {
	      System.out.println("CLOSE");
	      System.exit(0);
	    } else if (type == LineEvent.Type.START) {
	      System.out.println("START");
	      playingDialog.setVisible(true);
	    } else if (type == LineEvent.Type.STOP) {
	      System.out.println("STOP");
	      playingDialog.setVisible(false);
	      clip.close();
	    }
	  }
	}

