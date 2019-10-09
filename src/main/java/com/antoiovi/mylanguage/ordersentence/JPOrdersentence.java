package com.antoiovi.mylanguage.ordersentence;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/***
 * Pannello di gioco.
 * 
 * @author antoiovi
 *
 */
public class JPOrdersentence extends JPanel implements MouseListener, MouseMotionListener {
	int maxWidth = 600;
	int maxHeight = 300;
	int bottom = 400;
	Rectangle2D quizarea;
	Rectangle2D answarea;

	List<Rectangle2D> tokenRect;
	/*
	 * RandomToken : struttura dati (inner class definita in fondo) che incapsula i
	 * seguenti dati : the text the point the Bounds pont2d etc, etc Ogni random
	 * token rappresenta UNA PAROLA
	 * 
	 */
	List<RandomToken> randomsTokenList;
	List<RandomToken> answerTokenList;

	int margin = 5;
	/**
	 * if easy option i can ordinate the word on the desk...
	 */
	boolean easyOption = false;

	/**************
	 * Quiz interface : per comunicare cpon la classe (JDialog) che contiene il
	 * pannello : public void wrongAnswer(); public void rightAnswer(); public void
	 * endGame();
	 * 
	 * Nel pannelo padre usare setQuiz(...) per inizzializzarlo
	 */
	Quizinterface quiz;

	/**
	 * Minimun lenght of a word that wil not be hidden.
	 */
	int minim_lgh_vis = 2;

	/**
	 * Create the panel.
	 */
	public JPOrdersentence() {
		// super.setBounds(0, 0, 500,500);
		addMouseMotionListener(this);
		addMouseListener(this);
		// super.setB
		// RandomToken :incapsula una stringa con i parametri per la visualizzazione
		randomsTokenList = new ArrayList<RandomToken>();
		answerTokenList = new ArrayList<>();
		setBackground(Color.WHITE);
		// Inizzializza le aree
		quizarea = new Rectangle(0, 0, maxWidth, maxHeight);
		answarea = new Rectangle(0, maxHeight, maxWidth, bottom - maxHeight);
	}

	/**
	 * Richiamto dal pannello padre per settare la frase
	 * 
	 * @param Sentence
	 */
	public void configSentence(String Sentence) {
		String sentence;
		String tokens[];
		/**
		 * Per calcolaare dimensioni testo
		 */
		Font font = new Font(null, Font.PLAIN, 20);
		FontMetrics metrics = this.getFontMetrics(font);
		// get the height of a line of text in this
		// font and render context
		int hgt = metrics.getHeight();
		sentence = Sentence;

		// if(!randomsTokenList.isEmpty())
		/**
		 * Clear the lists
		 */
		randomsTokenList.clear();
		answerTokenList.clear();
		int Min = 0 + margin;
		int Max = maxWidth - margin;
		tokens = sentence.split(" ");
		int answ_base_X = (int) answarea.getX() + margin;

		// Answeres lines
		int answ_lines = 0;
		for (int count = 0; count < tokens.length; count++) {
			RandomToken rt = new RandomToken();
			RandomToken answert = new RandomToken();
			/**
			 * Set the text and the position to question(random) and answer
			 */
			rt.text = String.format("%-10s", tokens[count]);
			rt.val = count;

			answert.val = count;
			/**
			 * ANSWER : NOT VISIBLE? depends if its too short !
			 */
			if (tokens[count].length() <= minim_lgh_vis) {
				answert.visible = true;
				answert.text = tokens[count];
				answert.bordered = false;
			} else {
				answert.text = String.format("%-10s", tokens[count]).replace(' ', '.');
				answert.visible = false;
				answert.bordered = true;
			}

			// get the advance of my text in this font
			// and render context
			int adv = metrics.stringWidth(answert.text);
			// calculate the size of a box to hold the
			// text with some padding.
			Dimension size = new Dimension(adv + 2, hgt + 2);
			/**
			 * answerToken is not random position
			 */
			// int baseline=(int)answarea.getY()+margin+size.height;

			if ((answ_base_X + size.width + margin) > maxWidth) {
				answ_base_X = (int) answarea.getX() + margin;
				answ_lines++;
				// System.out.println("line +1-->"+answ_lines);
			}
			int answ_base_Y = (int) answarea.getY() + margin + hgt;
			// not yet checked bottom over border....
			answ_base_Y = answ_base_Y + answ_lines * (size.height + margin);
			Rectangle2D rect = new Rectangle(answ_base_X, answ_base_Y, size.width, size.height);

			Point2D pa = new Point(answ_base_X, answ_base_Y);
			answert.rect = rect;
			answert.loc = pa;
			// Aggiunge la parola nella lista delle risposte
			answerTokenList.add(answert);
			answ_base_X += size.width + margin;
			/**
			 * Random token has RANDOM position
			 */
			int MaxX = Max - size.width;
			/**
			 * Coordinata X Numero casuale tra 0 max width;
			 */
			int MinX = 0 + margin;
			int randomX = MinX + (int) (Math.random() * ((MaxX - MinX) + 1));

			int MaxY = maxHeight - hgt - margin;
			int MinY = 0 + margin + hgt;
			int randomY = MinY + (int) (Math.random() * ((MaxY - MinY) + 1));

			Point2D p = new Point(randomX, randomY);
			rt.loc = p;
			rt.bordered = false;
			/**
			 * if too short show in the answers...
			 */
			rt.visible = (tokens[count].length() <= minim_lgh_vis) ? false : true;
			if (!rt.visible)
				rt.rect = answert.rect;
			randomsTokenList.add(rt);
		}
		repaint();
	}
	
	Color COLOR_BACKGROUND = Color.white;
	Color COLOR_FOREGROUND = Color.black;


	Color COLOR_RANDOM_WORDS = Color.blue;
	Color COLOR_ANSWERED_WORD = Color.green;
	Color COLOR_BORDER_ANSWER = Color.orange;

	/****************
	 * METODO PAINT
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(COLOR_FOREGROUND);
		g2d.setBackground(COLOR_BACKGROUND);
		Color savecolor = g2d.getColor();
		g2d.draw(answarea);
		g2d.draw(quizarea);
		FontRenderContext frc = g2d.getFontRenderContext();
		// Font font=g2d.getFont();
		Font font = new Font(null, Font.PLAIN, 20);
	
		/**
		 * PAINT QUESTIONS...........
		 */
		// Imposta il COLORE delle PAROLE CASUALI
		g2d.setColor(COLOR_RANDOM_WORDS);
		int count_words = 0;
		for (RandomToken rt_question : randomsTokenList) {
			if (rt_question.text == null || rt_question.text.isEmpty())
				continue;
			if (rt_question.visible) {
				count_words++;
				TextLayout layout = new TextLayout(rt_question.text, font, frc);
				Point2D loc = rt_question.loc;
				layout.draw(g2d, (float) loc.getX(), (float) loc.getY());
				// g2d.drawLine(0,0,(int) loc.getX(), (int)loc.getY());
				// System.out.println("X Y ="+(int) loc.getX()+" " +(int)loc.getY());
				Rectangle2D bounds = layout.getBounds();
				bounds.setRect(bounds.getX() + loc.getX(), bounds.getY() + loc.getY(), bounds.getWidth(),
						bounds.getHeight());
				rt_question.rect = bounds;
				if (rt_question.bordered)
					g2d.draw(rt_question.rect);

			}
		}
		// Reimposta il colore 
		g2d.setColor(COLOR_FOREGROUND);
		g2d.setBackground(COLOR_BACKGROUND);

		// words finished ...
		notificationREAMAININGS_WORDS(count_words);
		/****************************************************************
		 * draw the ANSWERS...
		 */
		
		for (RandomToken rt_answ : answerTokenList) {
			if (rt_answ.text == null || rt_answ.text.isEmpty())
				continue;
			TextLayout textLayout = new TextLayout(rt_answ.text, font, frc);
			Point2D loc = rt_answ.loc;
			if (rt_answ.visible) {
				// printDebug("Answer VISIBLE");
				g2d.setColor(COLOR_ANSWERED_WORD);
				textLayout.draw(g2d, (float) loc.getX(), (float) loc.getY());
				// layout.draw(g2d, (float)(rt.rect.getMaxX()-rt.rect.getWidth()), (float)
				// rt.rect.getMinY());
			} else {
				// printDebug("Answer NOT VISIBLE");
				g2d.setColor(COLOR_BACKGROUND);
				g2d.setBackground(COLOR_BACKGROUND);
				// printDebug("RED. Answer= "+rt_answ.text);
				// printDebug("Visible = "+rt_answ.visible);
				textLayout.draw(g2d, (float) loc.getX(), (float) loc.getY());
				g2d.setColor(COLOR_FOREGROUND);
				g2d.setBackground(COLOR_BACKGROUND);
			}
			Rectangle2D bounds = textLayout.getBounds();
			bounds.setRect(bounds.getX() + loc.getX(), bounds.getY() + loc.getY(), bounds.getWidth(),
					bounds.getHeight());
			rt_answ.rect = bounds;
			g2d.setColor(COLOR_BORDER_ANSWER);
			if (rt_answ.bordered)
				g2d.draw(rt_answ.rect);
		}

	}

	/***********************************************************************************************************
	 * END PAINT
	 */

	void notificationREAMAININGS_WORDS(int n) {
		if (n == 0) {
			if (quiz != null)
				quiz.endGame();
		}
	}

	void wrongAnswer() {
		if (quiz != null)
			quiz.wrongAnswer();
	}

	void rightAnswer() {
		if (quiz != null)
			quiz.rightAnswer();
	}

	/**
	 * 
	 * @author Antonello Iovino for each token encapsulate : the text the point the
	 *         Bounds pont2d etc, etc
	 *
	 */
	private class RandomToken {
		public String text;

		public Rectangle2D rect;
		public Point2D loc;
		public boolean visible = true;
		public int val = 0;
		public boolean bordered = true;

	}

	/***
	 * Mouse events
	 */
	boolean pressOut = false;
	int preX, preY;
	boolean isFirstTime = true;
	Rectangle area;

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!pressOut)
			updateLocation(e);

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// Do nothing

	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// Do nothing

		  if (event.getClickCount() == 2) {
		    System.out.println("double clicked");
		  }
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Do nothing
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Do nothing
	}

	RandomToken selectedToken = null;

	@Override
	public void mousePressed(MouseEvent e) {
		// Controlla se il punto selezionato contiene una parola della frase
		selectedToken = containsToken(e.getX(), e.getY());
		// pressOut : serve nel metodo mouseDragged :
		// infatti se ho selezionato una parola devo trascinarla nello schermo
		if (selectedToken != null) {
			printDebug("Selected token. pressOut = " + pressOut);
			printDebug("Selected token. VISIBLE = " + selectedToken.visible);
			pressOut = false;
			// preX PreY : salvo la posizione, se rilascio nel posto sbagliato
			// la riposiziono nel posto originale
			preX = (int) selectedToken.loc.getX();
			preY = (int) selectedToken.loc.getY();
		} else {
			pressOut = true;
		}

		printDebug("Press out=" + pressOut);

	}

	/**
	 * Chiamata da mousePressed : quando tengo schiacciato il mouse verifico se il
	 * punto e compreso in un rettangolo delle parole della frase return a
	 * RandomToken if there is one in (x,y)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	RandomToken containsToken(int x, int y) {
		for (RandomToken rt : randomsTokenList) {
			if (rt.rect.contains(x, y)) {
				System.out.println("SELECTED  " + rt.text);
				return rt;
			}
		}

		return null;
	}

	/**
	 * Chiamat da mouseDragged : aggiorna la posizione della parola selezionata
	 * 
	 * @param e
	 */
	public void updateLocation(MouseEvent e) {
		if (selectedToken == null)
			return;
		selectedToken.loc = new Point(e.getX(), e.getY());
		// rect.setLocation(preX + e.getX(), preY + e.getY());

		/**
		 * if (checkRect()) { ShapeMover.label.setText(rect.getX() + ", " +
		 * rect.getY()); } else { ShapeMover.label.setText("drag inside the area."); }
		 */
		repaint();
	}

	/**
	 * Mouse rilasciato
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// int relX=e.getX();
		// int relY=e.getY();

		RandomToken answTokselec = null;
		// check if mouse is realised on an answer
		for (RandomToken answ : answerTokenList) {
			if (answ.rect.contains(e.getPoint())) {
				answTokselec = answ;
				break;
			}
		}
		/**
		 * selected a random word and a answer box: try to see if matches:
		 */
		if (selectedToken != null && answTokselec != null) {
			if (selectedToken.val == answTokselec.val) {
				System.out.println("BRAVO MATCHING!");
				// selectedToken.rect=answTokselec.rect;
				selectedToken.visible = false;
				printDebug(String.format("answTokselec %s \t visible %s \t X= %f \t Y=%f", answTokselec.text,
						answTokselec.visible, answTokselec.loc.getX(), answTokselec.loc.getY()));
				answTokselec.visible = true;
				rightAnswer();
			} else {
				/**
				 * A word is positionated on a wrong answer box! restore position...
				 */
				Point2D prev = new Point(preX, preY);
				selectedToken.loc.setLocation(prev);
				wrongAnswer();
				// System.out.println("NOT MATCHING!");
			}
		} else if (selectedToken != null) {
			System.out.println("NOT IN BOX!");
			/**
			 * A selected word was realised outside an answer box; Restore the position
			 */
			if (easyOption) {
				if (quizarea.contains(e.getPoint())) {
					selectedToken.loc.setLocation(e.getPoint());
				} else {
					Point2D prev = new Point(preX, preY);
					selectedToken.loc.setLocation(prev);
				}
			} else {
				Point2D prev = new Point(preX, preY);
				selectedToken.loc.setLocation(prev);
			}
		}
		pressOut = true;
		repaint();
	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(maxWidth, bottom);
	}

	/*****
	 * Mischia le parole nel rettangolo superiore :
	 */
	public void shake() {
		if (randomsTokenList == null)
			return;
		if (randomsTokenList.isEmpty())
			return;
		Font font = new Font(null, Font.PLAIN, 20);
		FontMetrics metrics = this.getFontMetrics(font);
		// get the height of a line of text in this
		// font and render context
		int hgt = metrics.getHeight();
		int Min = 0;

		for (RandomToken rt : randomsTokenList) {
			if (!rt.visible)
				continue;

			/**
			 * Random token has RANDOM position
			 */
			int MaxX = maxWidth - (int) rt.rect.getWidth() - margin;
			/**
			 * Coordinata X Numero casuale tra 0 max width;
			 */
			int MinX = 0 + margin;
			int randomX = MinX + (int) (Math.random() * ((MaxX - MinX) + 1));

			int MaxY = maxHeight - hgt - margin;
			int MinY = 0 + margin + hgt;
			int randomY = MinY + (int) (Math.random() * ((MaxY - MinY) + 1));

			Point2D p = new Point(randomX, randomY);

			rt.loc = p;

		}
		repaint();
	}

	boolean DEBUG = true;

	public void printDebug(String s) {
		if (DEBUG) {
			System.out.println(s);
		}

	}

	/**********************************
	 * SETTER GETTERS 
	 */
	/********
	 * @return quiz la quizinteafec
	 */

	public Quizinterface getQuiz() {
		return quiz;
	}

	/***************
	 * 
	 * @param quiz , in JPanel superiore che contiene questo pannello deve
	 *             implementare interface Quizinterface, e deve chiamare questo
	 *             metodo per fare comunicare i due pannelli
	 */

	public void setQuiz(Quizinterface quiz) {
		this.quiz = quiz;
	}

	
	public boolean isEasyOption() {
		return easyOption;
	}

	public void setEasyOption(boolean easyOption) {
		this.easyOption = easyOption;
	}

}
