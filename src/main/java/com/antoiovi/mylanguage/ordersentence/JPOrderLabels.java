package com.antoiovi.mylanguage.ordersentence;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class JPOrderLabels extends JLayeredPane {
	public static final int WIDTH = 680;
	public static final int HEIGHT = 480;
	Color LABLE_SENTENCE_COLOR = Color.red.brighter().brighter();
	Color LABLE_SENTENCE_COLOR_OVERLAP = Color.cyan;
	Color LABLE_COLOR_MATCH = Color.white;

	public static final int MAX_DRAG_WIDTH = WIDTH - 10;
	public static final int MAX_DRAG_HEIGHT = HEIGHT / 2 - 10;
	public static final int MARGIN = 10;

	private static final int GRID_ROWS = 2;
	private static final int GRID_COLS = 1;
	private static final int GAP = 3;
	private static final Dimension LAYERED_PANE_SIZE = new Dimension(WIDTH, HEIGHT);

	private static final int LABEL_WIDTH = 60;
	private static final int LABEL_HEIGHT = 40;

	private static final Dimension LABEL_SIZE = new Dimension(LABEL_WIDTH, LABEL_HEIGHT);
	private GridLayout gridlayout = new GridLayout(GRID_ROWS, GRID_COLS, GAP, GAP);
	private JPanel layeredPane = new JPanel(gridlayout);
	private JPanel[][] panelGrid = new JPanel[GRID_ROWS][GRID_COLS];

	private JPanel panelWordsToDrag;
	private JPanel panelSentence;

	List<JLabel> lablesToDragList = new ArrayList<JLabel>();
	List<JLabel> lablesSentence = new ArrayList<JLabel>();
	List<JLabel> lablesMatches = new ArrayList<JLabel>();

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

	public void setEasyOption(boolean easyOption) {
		this.easyOption = easyOption;
	}

	/**
	 * Riferimento al pannello principale : Creata per : potere riportare le
	 * coordinate dei punti allo stesso riferimento (quando serve ad esempio in
	 * mouse adapre realesed)
	 */
	JPOrderLabels jpordelabels;

	/**
	 * Costruttore
	 */
	public JPOrderLabels() {

		panelWordsToDrag = new JPanel();
		panelSentence = new JPanel();

		layeredPane.setSize(LAYERED_PANE_SIZE);
		layeredPane.setLocation(2 * GAP, 2 * GAP);
		layeredPane.setBackground(Color.black);

		layeredPane.add(panelWordsToDrag);
		layeredPane.add(panelSentence);

		panelSentence.setLayout(new FlowLayout());
		FlowLayout fl = (FlowLayout) panelSentence.getLayout();
		fl.setAlignment(FlowLayout.LEFT);

		panelWordsToDrag.setLayout(null);
		layeredPane.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
		setPreferredSize(LAYERED_PANE_SIZE);
		add(layeredPane, JLayeredPane.DEFAULT_LAYER);

		this.setSentence(sentence);

		myMouseAdapter = new MyMouseAdapter();
		addMouseListener(myMouseAdapter);
		addMouseMotionListener(myMouseAdapter);
		jpordelabels = this;
	}

	String sentence = "  Questa e una frase prova ad indovinarla se sei capace ";
	private MyMouseAdapter myMouseAdapter;

	public void setSentence(String sentence) {
		// Rimuovi spazi all'inizio ed alla fine
		this.sentence = sentence.trim();
		this.sentence = sentence;
		this.clearSentence();
		this.configSentence();
		this.paintSentenceLabels();
		this.paintDragLabels();
	}

	private void paintDragLabels() {
		for (JLabel lbl : lablesToDragList)
			panelWordsToDrag.add(lbl);
		for (JLabel lbl : lablesSentence)
			panelSentence.add(lbl);
	}

	private void paintSentenceLabels() {
		// TODO Auto-generated method stub

	}

	private void configSentence() {
		String tokens[];

		tokens = sentence.trim().split(" ");

		for (int count = 0; count < tokens.length; count++) {
			JLabel labelToDrag = new JLabel(tokens[count]);
			JLabel labelsentence = new JLabel(tokens[count]);
			lablesSentence.add(labelsentence);
			labelsentence.addMouseListener(new MyMouseAdapterLabel());

			labelsentence.setOpaque(true);
			labelsentence.setBackground(Color.red.brighter().brighter());
			labelsentence.setPreferredSize(LABEL_SIZE);

			labelToDrag.setOpaque(true);
			labelToDrag.setBackground(Color.blue.brighter().brighter());
			labelToDrag.setPreferredSize(LABEL_SIZE);
			lablesToDragList.add(labelToDrag);

			// get the advance of my text in this font

			/**
			 * Random token has RANDOM position
			 */
			// int MaxX = this.getPreferredSize().width -
			// wordToDrag.label.getPreferredSize().width;
			// log("PANNELLO widith : "+String.valueOf(getPreferredSize().width ));
			// log("PANNELLO height : "+String.valueOf(getPreferredSize().height));
			int MaxX = MAX_DRAG_WIDTH - labelToDrag.getPreferredSize().width;
			/**
			 * Coordinata X Numero casuale tra 0 max width;
			 */
			int MinX = 0 + MARGIN;
			int randomX = MinX + (int) (Math.random() * ((MaxX - MinX) + 1));
			int MaxY = MAX_DRAG_HEIGHT - labelToDrag.getPreferredSize().height - MARGIN;
			/*
			 * int MaxY = panelWordsToDrag.getPreferredSize().height -
			 * wordToDrag.label.getPreferredSize().height - margin;
			 */
			int MinY = 0 + MARGIN + labelToDrag.getPreferredSize().height;
			int randomY = MinY + (int) (Math.random() * ((MaxY - MinY) + 1));
			Rectangle rect = new Rectangle(randomX, randomY, labelToDrag.getPreferredSize().width,
					labelToDrag.getPreferredSize().height);
			labelToDrag.setBounds(rect);/**
										 * if too short show in the answers...
										 */
			// wordToDrag.visible = (tokens[count].length() <= minim_lgh_vis) ? false :
			// true;

		}
	}

	private void clearSentence() {
		for (JLabel lbl : lablesToDragList)
			panelWordsToDrag.remove(lbl);
		lablesToDragList.clear();
		lablesMatches.clear();

	}

	void log(String s) {
		System.out.println(s);
	}

	/**
	 * Mouse adapter per le etichette della frase (pannelo sotto
	 * 
	 * @author antoiovi
	 *
	 */
	private class MyMouseAdapterLabel extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			Component c = e.getComponent();
			if (c != null && c instanceof JLabel) {
				JLabel lbl = (JLabel) c;
				if (lablesMatches.contains(lbl))
					return;
				// log("Entered "+lbl.getText());
				// log("PARENT ="+c.getParent());
				if (c.getParent() == panelSentence) {
					if (myMouseAdapter.getDragLabel() != null) {
						lbl.setBackground(LABLE_SENTENCE_COLOR_OVERLAP);
						// log("ENTERED");
						// logLabel(lbl);
					}
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Component c = e.getComponent();
			if (c != null && c instanceof JLabel) {
				JLabel lbl = (JLabel) c;
				// log("EXITED LABLE "+lbl.getText());
				if (lablesMatches.contains(lbl))
					lbl.setBackground(LABLE_COLOR_MATCH);
				else if (lablesSentence.contains(lbl))
					lbl.setBackground(LABLE_SENTENCE_COLOR);

			}

		}

	};

	/***
	 * Mouse adapter per trascinare le lables Viene assegnato alla classe principale
	 * (che estende LayerdPane) (layeredPanel 'e il pannello normale inserito nel
	 * layerd pane con un suo gestore layout, mentre il pannello pricipale non ha
	 * gestore layout).
	 * 
	 * @author antoiovi
	 *
	 */

	private class MyMouseAdapter extends MouseAdapter {
		private JLabel dragLabel = null;

		public JLabel getDragLabel() {
			return dragLabel;
		}

		private int dragLabelWidthDiv2;
		private int dragLabelHeightDiv2;
		private JPanel clickedPanel = null;
		private Rectangle orignialBounds;

		@Override
		public void mousePressed(MouseEvent me) {
			// me.getcomponent 'e il pannello a cui 'e assegnato il mouse adapter
			// log(me.getComponent().toString());

			// Layered pane restituisce uno dei componento aggiunti a layeredpane
			// (ricorda layeredpane 'e un JPanel inserito nella classe principale (che
			// estende JLayeredPane)
			clickedPanel = (JPanel) layeredPane.getComponentAt(me.getPoint());
			if (clickedPanel == null) {
				// log("cliccato in un annello no assegnato al layered pane");
				return;
			}

			if (!(clickedPanel == panelWordsToDrag))
				return;
			Component c = panelWordsToDrag.findComponentAt(me.getX(), me.getY());

			if (c instanceof JPanel)
				return;
			// if we click on jpanel that holds a jlabel
			if (c instanceof JLabel) {
				// remove label from panel
				dragLabel = (JLabel) c;
				if (!lablesToDragList.contains(c))
					return;

				orignialBounds = dragLabel.getBounds();
				clickedPanel.remove(dragLabel);
				clickedPanel.revalidate();
				clickedPanel.repaint();

				dragLabelWidthDiv2 = dragLabel.getWidth() / 2;
				dragLabelHeightDiv2 = dragLabel.getHeight() / 2;

				int x = me.getPoint().x - dragLabelWidthDiv2;
				int y = me.getPoint().y - dragLabelHeightDiv2;
				dragLabel.setLocation(x, y);
				add(dragLabel, JLayeredPane.DRAG_LAYER);
				repaint();

			}
		}

		@Override
		public void mouseDragged(MouseEvent me) {
			if (dragLabel == null) {
				return;
			}
			int x = me.getPoint().x - dragLabelWidthDiv2;
			int y = me.getPoint().y - dragLabelHeightDiv2;
			dragLabel.setLocation(x, y);

			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			if (dragLabel == null)
				return;

			remove(dragLabel); // remove dragLabel for drag layer of JLayeredPane

			JPanel droppedPanel = (JPanel) layeredPane.getComponentAt(me.getPoint());
			boolean label_match = false;
			if (droppedPanel != null) {
				/***
				 * Controlla se il mouse e rilasciato sul pannello della frase
				 */
				if (droppedPanel == panelSentence) {
					// log(String.format("Realesd on panelsentence(%d,%d)", me.getX(), me.getY()));
					Component c = panelSentence.findComponentAt(me.getX(), me.getY());
					JLabel droppedLabel = null;
					// Controlla se una delle LABELS della frase contiene il
					// punto in cui 'e stato rilasciato il mouse
					for (JLabel lbl : lablesSentence) {
						/**
						 * Converto le coordinate delle LABELS dal panelsentence --> alle coordinate del
						 * pannello principale(JLayeredPane) perche' il punto dve rilascio il mouse 'e
						 * nel layerd pane(draggedLEVEL)
						 */
						Rectangle rect = SwingUtilities.convertRectangle(panelSentence, lbl.getBounds(), jpordelabels);
						if (rect.contains(me.getPoint())) {
							droppedLabel = lbl;
							break;
						}
					}
					if (droppedLabel != null) {
						/**
						 * Il mouse 'e stato rilasciato su una labels del pannello della frase
						 */
						if (droppedLabel.getText().equals(dragLabel.getText())
								&& !lablesMatches.contains(droppedLabel)) {
							/**
							 * La label dove 'e stata rilasciato il mouse ha il testo = alla label
							 * trascinata, e non si trova nella lista delle labels gia' indovinate
							 */
							droppedLabel.setBackground(LABLE_COLOR_MATCH);
							lablesMatches.add(droppedLabel);
							droppedLabel.setPreferredSize(null);
							log("dropplabel HEIGHT : " + String.valueOf(droppedLabel.getHeight()));

							Dimension mins = new Dimension(droppedLabel.getPreferredSize().width, LABEL_HEIGHT);
							droppedLabel.setMinimumSize(mins);
							droppedLabel.setPreferredSize(mins);

							// Per fare in modo che cambiando colore la dimensione sia quella voluta
							Rectangle rect = new Rectangle(droppedLabel.getX(), droppedLabel.getY(),
									droppedLabel.getPreferredSize().width, droppedLabel.getPreferredSize().height + 50);
							droppedLabel.setBounds(rect);

							clickedPanel.remove(dragLabel);
							dragLabel = null;

							orignialBounds = null;
							clickedPanel.revalidate();
							label_match = true;
						} else if (!lablesMatches.contains(droppedLabel)) {
							// Se la LABEL dove e stato laciato il mouse
							// NON 'e nella lista delle labels gia indovinate
							droppedLabel.setBackground(LABLE_SENTENCE_COLOR);
						}

					}
				}
			} else {
				log("dropped panel=null");
			}
			// Se non MATCH allora riposiziona la label al suo posto
			if (!label_match) {
				//log("DRAGLABEL NON MATCH:");
				//logLabel(dragLabel);
				clickedPanel.add(dragLabel);
				clickedPanel.revalidate();
				dragLabel.setBounds(orignialBounds);
				orignialBounds = null;
			}
			repaint();
			dragLabel = null;
		}
	}

	public void setQuiz(Quizinterface quiz) {
		this.quiz = quiz;
	}

	public void logLabel(JLabel lbl) {
		Rectangle r = lbl.getBounds();
		String s = String.format("Label :%s  Bounds :x=%d y=%d  width=%d  height= %d ", lbl.getText(), r.x, r.y,
				r.width, r.height);
		log(s);
	}

	public void logPoint(Point p) {
		String s = String.format("Point (%d,%d)", p.x, p.y);
		log(s);
	}

	public void logRectangle(Rectangle r) {
		String s = String.format("Rectangle (%d,%d,%d,%d)", r.x, r.y, r.width, r.height);
		log(s);
	}

	public void shake() {
		//for (int count = 0; count < tokens.length; count++) {
		for (JLabel labelToDrag:lablesToDragList) {
			/**
			 * Random token has RANDOM position
			 */
			int MaxX = MAX_DRAG_WIDTH - labelToDrag.getPreferredSize().width;
			/**
			 * Coordinata X Numero casuale tra 0 max width;
			 */
			int MinX = 0 + MARGIN;
			int randomX = MinX + (int) (Math.random() * ((MaxX - MinX) + 1));
			int MaxY = MAX_DRAG_HEIGHT - labelToDrag.getPreferredSize().height - MARGIN;
			int MinY = 0 + MARGIN + labelToDrag.getPreferredSize().height;
			int randomY = MinY + (int) (Math.random() * ((MaxY - MinY) + 1));
			Rectangle rect = new Rectangle(randomX, randomY, labelToDrag.getPreferredSize().width,
					labelToDrag.getPreferredSize().height);
			labelToDrag.setBounds(rect);
			/**
			 * if too short show in the answers...
			 */
			// wordToDrag.visible = (tokens[count].length() <= minim_lgh_vis) ? false :
			// true;

		}
	}

}
