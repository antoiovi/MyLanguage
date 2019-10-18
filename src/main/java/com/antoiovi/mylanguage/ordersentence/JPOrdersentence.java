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
import java.util.function.LongToDoubleFunction;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/***
 * Pannello di gioco.
 * 
 * - Dispone una lista di parole sulla parte superiore del pannello () in ordine
 * sparso : parte superiore --> Rectangle2D quizarea; parole sparese -->
 * randomsTokenList - La stessa lista di parole 'e disposta in modo ordinato
 * nella arte inferiore del pannello : parte inferiore --> Rectangle2D answarea;
 * parole ordinate --> List<RandomToken> answerTokenList;
 * 
 * INIZZIALIZZZIONE ;
 * 
 * Metodo public void configSentence(String Sentence) inizzializza il pannello
 * con le parole (che costituiscono una frase)
 * 
 * Metodo public void shake() { mescola le parole sul pannello superiore
 * 
 * OPZIONI : boolean easyOption = false; Se true : 1) e possibile disporre le
 * parole sul pannenllo superiore senza che segnali errore 2) se doppio click su
 * una parola del pannello superiore la posiziona nella giusta posizione e
 * chiama il metodo wronganswer() (perche 'e considerato un aiuto quindoi il
 * punteggio dovrebbe essere diminuito anche se larisposta e esatta )
 * 
 * 
 * EVENTI ; -- 'E possibile trascinare con il mouse una parola dalla parte
 * superiore (parole in ordine sparso) Se viene rilasciata nella giusta
 * posizione (nella parte inferiore dove sono disposte in modo ordinato) viene
 * cancellata dalla parte superiore ed inserita (Resa visibile) nella parte
 * inferiore
 * 
 * -- Si interfaccia con il mondo esterno tramite la variabile : Quizinterface
 * quiz; che deve essere inizzializzata nel pannello padre.
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
	List<SentenceWord> wordsToDragList;
	List<SentenceWord> sentenceWordsList;

	int margin = 5;
	/**
	 * easyOption 1) if easy option i can ordinate the word on the desk... 2) Se
	 * doppio click su una parola, la posiziona nel posto giusto
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

	boolean RANDOM_WORD_IS_BORDERD = false;
	boolean ANSWERED_WORD_IS_BORDERD = true;

	/**
	 * Create the panel.
	 */
	public JPOrdersentence() {
		// super.setBounds(0, 0, 500,500);
		addMouseMotionListener(this);
		addMouseListener(this);
		// super.setB
		// RandomToken :incapsula una stringa con i parametri per la visualizzazione
		wordsToDragList = new ArrayList<SentenceWord>();
		sentenceWordsList = new ArrayList<>();
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
		wordsToDragList.clear();
		sentenceWordsList.clear();
		int Min = 0 + margin;
		int Max = maxWidth - margin;
		tokens = sentence.split(" ");
		int answ_base_X = (int) answarea.getX() + margin;

		// Answeres lines : numero di linee su cui disporre il testo delle risposte
		int answ_lines = 0;
		for (int count = 0; count < tokens.length; count++) {
			SentenceWord wordToDrag = new SentenceWord();
			SentenceWord wordInPosition = new SentenceWord();
			/**
			 * Set the text and the position to question(random) and answer
			 */
			wordToDrag.text = String.format("%-10s", tokens[count]);
			wordToDrag.text2 = tokens[count];

			wordToDrag.val = count;

			wordInPosition.val = count;
			/**
			 * ANSWER : NOT VISIBLE? depends if its too short !
			 */
			if (tokens[count].length() <= minim_lgh_vis) {
				wordInPosition.visible = true;
				wordInPosition.text = tokens[count];
				wordInPosition.text2 = tokens[count];
				wordInPosition.bordered = ANSWERED_WORD_IS_BORDERD;
			} else {
				wordInPosition.text = String.format("%-10s", tokens[count]).replace(' ', '.');
				// wordInPosition.text = String.format("%-10s", tokens[count]);//tokens[count];
				wordInPosition.text2 = tokens[count];
				wordInPosition.visible = false;
				wordInPosition.bordered = true;
			}

			// get the advance of my text in this font
			// and render context
			int adv = metrics.stringWidth(wordInPosition.text);
			// calculate the size of a box to hold the
			// text with some padding.
			Dimension size = new Dimension(adv + 2, hgt + 2);
			/**
			 * answerToken is not random position
			 */
			// int baseline=(int)answarea.getY()+margin+size.height;
			/*
			 * Se la parola ha il bordo sinistro che 'e maggiore della larghezza del
			 * pannello va a capo (mette la parola su una linea inferiore.
			 * 
			 */
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
			wordInPosition.rect = rect;
			wordInPosition.loc = pa;

			wordInPosition.log();
			// Aggiunge la parola nella lista delle risposte
			sentenceWordsList.add(wordInPosition);
			answ_base_X += (size.width + margin);
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
			wordToDrag.loc = p;
			wordToDrag.bordered = RANDOM_WORD_IS_BORDERD;
			/**
			 * if too short show in the answers...
			 */
			wordToDrag.visible = (tokens[count].length() <= minim_lgh_vis) ? false : true;
			if (!wordToDrag.visible)
				wordToDrag.rect = wordInPosition.rect;
			wordsToDragList.add(wordToDrag);
		}
		repaint();
	}

	void log(String s) {
		System.out.println(s);
	}

	void inizilizeAnswerPositions(String[] tokens, Font font, FontMetrics metrics) {
		int hgt = metrics.getHeight();
		int answ_base_X = (int) answarea.getX() + margin;
		// Answeres lines : numero di linee su cui disporre il testo delle risposte
		int answ_lines = 0;

		for (int count = 0; count < tokens.length; count++) {
			SentenceWord wordToDrag = new SentenceWord();
			SentenceWord wordInRightPosition = new SentenceWord();
			/**
			 * Set the text and the position to question(random) and answer
			 */
			wordToDrag.text = String.format("%-10s", tokens[count]);
			wordToDrag.val = count;

			wordInRightPosition.val = count;
			/**
			 * ANSWER : NOT VISIBLE? depends if its too short !
			 */
			if (tokens[count].length() <= minim_lgh_vis) {
				wordInRightPosition.visible = true;
				wordInRightPosition.text = tokens[count];
				wordInRightPosition.bordered = ANSWERED_WORD_IS_BORDERD;
			} else {
				wordInRightPosition.text = String.format("%-10s", tokens[count]).replace(' ', '.');

				wordInRightPosition.visible = false;
				wordInRightPosition.bordered = true;
			}

			// get the advance of my text in this font
			// and render context
			int adv = metrics.stringWidth(wordInRightPosition.text);
			// calculate the size of a box to hold the
			// text with some padding.
			Dimension size = new Dimension(adv + 2, hgt + 2);
			/**
			 * answerToken is not random position
			 */
			// int baseline=(int)answarea.getY()+margin+size.height;
			/*
			 * Se la parola ha il bordo sinistro che 'e maggiore della larghezza del
			 * pannello va a capo (mette la parola su una linea inferiore.
			 * 
			 */
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
			wordInRightPosition.rect = rect;
			wordInRightPosition.loc = pa;
			// Aggiunge la parola nella lista delle risposte
			sentenceWordsList.add(wordInRightPosition);
			answ_base_X += (size.width + margin);
		}
	}

	/****************************
	 * COLORI PANNELLO SUPERIORE
	 */
	// Color COLOR_BACKGROUND = Color.white;
	// Color COLOR_BACKGROUND_B = Color.red;
	// colore sfondo
	Color COLOR_RANDOMWORDS_AREA = Color.green;
	// Colore testo domande sparese
	Color COLOR_RANDOM_WORDS = Color.blue;

	/****************************
	 * COLORI PANNELLO SOTTOSTANTE
	 */
	// colore sfondo
	Color COLOR_ANSWER_AREA = Color.LIGHT_GRAY;
	// Colore testo
	Color COLOR_ANSWERED_WORD = Color.BLACK;
	// colore bordo domande
	Color COLOR_BORDER_ANSWER = Color.white;

	/******************************************************************
	 * METODO PAINT COMPONENT
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(COLOR_ANSWER_AREA);
		// g2d.setBackground(COLOR_BACKGROUND_B);

		g2d.fill(answarea);

		g2d.setColor(COLOR_RANDOMWORDS_AREA);
		// g2d.setBackground(COLOR_BACKGROUND);
		g2d.fill(quizarea);
		// g2d.draw(quizarea);

		g2d.setColor(COLOR_RANDOM_WORDS);

		FontRenderContext frc = g2d.getFontRenderContext();
		// Font font=g2d.getFont();
		Font font = new Font(null, Font.PLAIN, 20);

		/**
		 * PAINT QUESTIONS. WORD TO DRAG..........
		 */
		// Imposta il COLORE delle PAROLE CASUALI
		g2d.setColor(COLOR_RANDOM_WORDS);
		int count_words = 0;
		for (SentenceWord rt_question : wordsToDragList) {
			if (rt_question.text == null || rt_question.text.isEmpty())
				continue;
			if (rt_question.visible) {
				count_words++;
				TextLayout layout = new TextLayout(rt_question.text, font, frc);
				Point2D loc = rt_question.loc;
				g2d.setColor(COLOR_RANDOM_WORDS);
				layout.draw(g2d, (float) loc.getX(), (float) loc.getY());
				Rectangle2D bounds = layout.getBounds();
				bounds.setRect(bounds.getX() + loc.getX(), bounds.getY() + loc.getY(), bounds.getWidth(),
						bounds.getHeight());
				rt_question.rect = bounds;
				if (rt_question.bordered) {
					g2d.setColor(COLOR_BORDER_ANSWER);
					g2d.draw(rt_question.rect);
				}

			}
		}

		// words finished ...
		notificationREAMAININGS_WORDS(count_words);
		/****************************************************************
		 * draw the ANSWERS...WORDS IN RIGHT POSITION
		 */
		for (SentenceWord wordInRightP : sentenceWordsList) {
			if (wordInRightP.text == null || wordInRightP.text.isEmpty())
				continue;
			// wordInRightP.log();

			TextLayout textLayout = new TextLayout(wordInRightP.text, font, frc);
			TextLayout textLayout2 = new TextLayout(wordInRightP.text2, font, frc);

			Point2D loc = wordInRightP.loc;
			if (wordInRightP.visible) {
				g2d.setColor(COLOR_ANSWERED_WORD);
				textLayout2.draw(g2d, (float) loc.getX(), (float) loc.getY());
			} else {
				g2d.setColor(COLOR_ANSWER_AREA);
				textLayout.draw(g2d, (float) loc.getX(), (float) loc.getY());
			}
			Rectangle2D bounds = textLayout.getBounds();

			bounds.setRect(bounds.getX() + loc.getX() - 5, bounds.getY() + loc.getY() - 5, bounds.getWidth() + 5,
					bounds.getHeight() + 5);
			wordInRightP.rect = bounds;
			if (wordInRightP.bordered && easyOption) {
				g2d.setColor(COLOR_BORDER_ANSWER);
				g2d.draw(wordInRightP.rect);
			}else if (wordInRightP.visible) {
				g2d.setColor(COLOR_BORDER_ANSWER);
				g2d.draw(wordInRightP.rect);
			}

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
	private class SentenceWord {
		public String text;
		public String text2;
		public Rectangle2D rect;
		public Point2D loc;
		public boolean visible = true;
		public int val = 0;
		public boolean bordered = true;

		public boolean equals(SentenceWord sw) {
			return this.val == sw.val;
		}

		public void log() {
			System.out.println(text);
			System.out.println("Visble : " + visible);
		}

	}

	/************************************************************************************
	 * Mouse events 1 PRESSED --> 2 REALISED --> 3 CLICKED Oppure PRESSED -->DRAGGED
	 * --> Realised
	 */
	boolean pressOut = false;
	int preX, preY;
	boolean isFirstTime = true;
	Rectangle area;

	@Override
	public void mouseDragged(MouseEvent e) {
		// Lavora solo con il bottone sinistro
		if (!SwingUtilities.isLeftMouseButton(e))
			return;

		if (!pressOut)
			updateLocation(e.getPoint());

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// Do nothing

	}

	SentenceWord selectedWord = null;
	int mouseButton;

	/****
	 * Mouse events 1 PRESSED --> 2 REALISED --> 3 CLICKED Oppure PRESSED -->DRAGGED
	 * --> Realised
	 */

	Point pressedPoint;

	@Override
	public void mousePressed(MouseEvent e) {

		pressedPoint = e.getPoint();
		// Controlla se il punto selezionato contiene una parola della frase
		selectedWord = containsToken(e.getX(), e.getY());
		// pressOut : serve nel metodo mouseDragged :
		// infatti se ho selezionato una parola devo trascinarla nello schermo
		if (selectedWord != null) {
			printDebug("Selected token. pressOut = " + pressOut);
			printDebug("Selected token. VISIBLE = " + selectedWord.visible);
			pressOut = false;
			// preX PreY : salvo la posizione, se rilascio nel posto sbagliato
			// la riposiziono nel posto originale
			preX = (int) selectedWord.loc.getX();
			preY = (int) selectedWord.loc.getY();
		} else {
			pressOut = true;
		}
		printDebug("Press out=" + pressOut);

	}

	/**
	 * Mouse events 1 PRESSED --> 2 REALISED --> 3 CLICKED Oppure PRESSED -->DRAGGED
	 * --> Realised
	 */
	/**
	 * Mouse rilasciato
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// 'E stato tracinato ?
		// pressedPoint inizzializzato nel metodo MoudePressed
		if (e.getPoint().equals(pressedPoint))
			return;
		// Se e stato trascinato in altro punto(dragged) allora :
		// PRESSED -->DRAGGED --> Realised
		printDebug("MouseRealised in different point ");
		execMouseDraggedRealised(e.getPoint());
	}

	private int clickCount;
	private boolean doubleClick;
	private Timer timer;

	/****
	 * Mouse events 1 PRESSED --> 2 REALISED --> 3 CLICKED Oppure 1 PRESSED --> 2
	 * DRAGGED --> 3 Realised
	 */
	@Override
	public void mouseClicked(MouseEvent mevt) {
		/**
		 * Questo ciclo serve per identificare un doppio click da un singolo click
		 */
		if (SwingUtilities.isLeftMouseButton(mevt)) {
			clickCount = 0;
			if (mevt.getClickCount() == 2)
				doubleClick = true;
			Integer timerinterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");

			timer = new Timer(timerinterval, new ActionListener() {

				public void actionPerformed(ActionEvent evt) {
					if (doubleClick) {

						System.out.println("Double click.");

						clickCount++;
						if (clickCount == 2) {
							execDoubleclick();
							clickCount = 0;
							doubleClick = false;
						}

					} else {

						System.out.println("Single click.");
					}
				}
			});
			timer.setRepeats(false);
			timer.start();
			if (mevt.getID() == MouseEvent.MOUSE_RELEASED)
				timer.stop();
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

	/**
	 * Chiamata da mousePressed : quando tengo schiacciato il mouse verifico se il
	 * punto e compreso in un rettangolo delle parole della frase return a
	 * RandomToken if there is one in (x,y)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	SentenceWord containsToken(int x, int y) {
		for (SentenceWord wordToDrag : wordsToDragList) {
			// Il punto 'e su una parola da trascinare?
			// ma la parola da trascinare deve essere ancora visibile ...
			if (wordToDrag.rect.contains(x, y) && wordToDrag.visible) {
				System.out.println("SELECTED  " + wordToDrag.text);
				return wordToDrag;
			}
		}

		return null;
	}

	/**
	 * Chiamato da mouseDragged : aggiorna la posizione della parola selezionata
	 * 
	 * @param point
	 */
	public void updateLocation(Point point) {
		if (selectedWord == null)
			return;
		selectedWord.loc = new Point(point);
		repaint();
	}

	/**
	 * Se il punto appartiene ad una risposta restituisce la risposta (come
	 * RandomToken)
	 * 
	 * @param point
	 * @return
	 */
	SentenceWord getClickeAnswer(Point point) {
		SentenceWord answTokselec = null;
		// check if mouse is realised on an answer
		for (SentenceWord answ : sentenceWordsList) {
			if (answ.rect.contains(point)) {
				answTokselec = answ;
				break;
			}
		}
		return answTokselec;
	}

	/**
	 * Chiamato dopo rilascio del mouse da mouseRealised() a seguito di un dragged
	 * 
	 * @param point Il punto dove 'e stato rilasciato il mouse
	 */
	void execMouseDraggedRealised(Point point) {
		SentenceWord fixedpositionWordSelected = getClickeAnswer(point);
		/**
		 * selected a random word and a answer box: try to see if matches:
		 */
		if (selectedWord != null && fixedpositionWordSelected != null) {
			if (selectedWord.val == fixedpositionWordSelected.val) {
				System.out.println("BRAVO MATCHING!");
				// selectedToken.rect=answTokselec.rect;
				selectedWord.visible = false;
				printDebug(String.format("answTokselec %s \t visible %s \t X= %f \t Y=%f",
						fixedpositionWordSelected.text, fixedpositionWordSelected.visible,
						fixedpositionWordSelected.loc.getX(), fixedpositionWordSelected.loc.getY()));
				fixedpositionWordSelected.visible = true;
				rightAnswer();
			} else {
				/**
				 * A word is positionated on a wrong answer box! restore position...
				 */
				Point2D prev = new Point(preX, preY);
				selectedWord.loc.setLocation(prev);
				wrongAnswer();
				// System.out.println("NOT MATCHING!");
			}
		} else if (selectedWord != null) {
			System.out.println("NOT IN BOX!");
			/**
			 * A selected word was realised outside an answer box; Restore the position
			 */
			if (easyOption) {
				if (quizarea.contains(point)) {
					selectedWord.loc.setLocation(point);
				} else {
					Point2D prev = new Point(preX, preY);
					selectedWord.loc.setLocation(prev);
				}
			} else {
				Point2D prev = new Point(preX, preY);
				selectedWord.loc.setLocation(prev);
			}
		}
		pressOut = true;
		repaint();
	}

	/**
	 * Chiamato dopo un doppio click
	 */
	void execDoubleclick() {
		printDebug("++++++ Exec double click");
		if (!pressOut && easyOption) {
			SentenceWord wordInRightPos = null;
			/***
			 * Cerca la parola slezionata nella frase , verificando il testo, e verificando
			 * se 'e gia stata resa visibile (IN CASO DI PAROLE UGUALI, deve esporre la
			 * successiva)
			 * 
			 */
			if (selectedWord != null) {
				log("selectedWord");
				selectedWord.log();
				// String selectedWordText=String.format("%-10s",selectedWord.text).replace(' ',
				// '.');
				// String selectedWordText=selectedWord.text;
				String selectedWordText = selectedWord.text2;

				for (SentenceWord sentenceWord : sentenceWordsList) {
					// se la parole e gia visibiel continua il ciclo per cercare la parola dopo
					// perche anche avendo lo stesso testo vuole dire che ci sono due parole uguali
					if (sentenceWord.text2.equals(selectedWordText) && !sentenceWord.visible) {
						wordInRightPos = sentenceWord;
						break;
					}
				}
				if (wordInRightPos != null) {
					selectedWord.visible = false;
					log("wordInRightPos");
					wordInRightPos.log();
					wordInRightPos.visible = true;
					// Chiamo wrongAnswer anche se la risposta 'e esatta perche' 'e stata
					// selezionata con il doppio click (senza il trascinamento)
					wrongAnswer();
					repaint();
				}

			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(maxWidth, bottom);
	}

	/******************************************************
	 * Mischia le parole nel rettangolo superiore :
	 */
	public void shake() {
		if (wordsToDragList == null)
			return;
		if (wordsToDragList.isEmpty())
			return;
		Font font = new Font(null, Font.PLAIN, 20);
		FontMetrics metrics = this.getFontMetrics(font);
		// get the height of a line of text in this
		// font and render context
		int hgt = metrics.getHeight();
		int Min = 0;

		for (SentenceWord wordToDrag : wordsToDragList) {
			if (!wordToDrag.visible)
				continue;
			/**
			 * Words to be Dragged hanno posizione casuale
			 */
			int MaxX = maxWidth - (int) wordToDrag.rect.getWidth() - margin;
			/**
			 * Coordinata X Numero casuale tra 0 max width;
			 */
			int MinX = 0 + margin;
			int randomX = MinX + (int) (Math.random() * ((MaxX - MinX) + 1));

			int MaxY = maxHeight - hgt - margin;
			int MinY = 0 + margin + hgt;
			int randomY = MinY + (int) (Math.random() * ((MaxY - MinY) + 1));
			Point2D p = new Point(randomX, randomY);
			wordToDrag.loc = p;

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
		repaint();
	}

}
