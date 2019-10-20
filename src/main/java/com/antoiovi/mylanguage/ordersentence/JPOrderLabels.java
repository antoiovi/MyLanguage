package com.antoiovi.mylanguage.ordersentence;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.LineBorder;


import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.BorderLayout;


public class JPOrderLabels extends JLayeredPane {
	
;
public static final int WIDTH = 680;
public static final int HEIGHT = 480;
private static final int GRID_ROWS = 2;
private static final int GRID_COLS = 1;
private static final int GAP = 3;
private static final Dimension LAYERED_PANE_SIZE = new Dimension(WIDTH, HEIGHT);
private static final Dimension DRAG_PANE_SIZE = new Dimension(400, 250);
private static final Dimension SENTENCE_PANE_SIZE = new Dimension(WIDTH, HEIGHT);
private static final Dimension LABEL_SIZE = new Dimension(60, 40);

private GridLayout gridlayout = new GridLayout(GRID_ROWS, GRID_COLS, GAP, GAP);
JPanel layeredPane   = new JPanel(gridlayout);
private JLabel redLabel = new JLabel("Red", SwingConstants.CENTER);
private JLabel blueLabel = new JLabel("Blue", SwingConstants.CENTER);

	public JPOrderLabels() {
		
		layeredPane.setSize(DRAG_PANE_SIZE);
	    layeredPane.setLocation(2 * GAP, 2 * GAP);
		
	    panelWordsToDrag = new JPanel();
	    panelWordsToDrag.setBackground(Color.yellow);
	    panelWordsToDrag.setPreferredSize(LAYERED_PANE_SIZE);
	    panelWordsToDrag.setLayout(null);
		panelSentence = new JPanel();

		maxwidth=300;
		maxheight=200;
		
		
		
		layeredPane.setSize(LAYERED_PANE_SIZE);
		layeredPane.setLocation(2 * GAP, 2 * GAP);
		layeredPane.setBackground(Color.black);

		layeredPane.add(panelWordsToDrag);
		layeredPane.add(panelSentence);

		panelSentence.setLayout(new FlowLayout());
		redLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		redLabel.setOpaque(true);
		redLabel.setBackground(Color.red.brighter().brighter());
		redLabel.setPreferredSize(LABEL_SIZE);
		panelSentence.add(redLabel);

		blueLabel.setOpaque(true);
		blueLabel.setBackground(Color.blue.brighter().brighter());
		blueLabel.setPreferredSize(LABEL_SIZE);
		panelWordsToDrag.add(blueLabel);

		layeredPane.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
		setPreferredSize(LAYERED_PANE_SIZE);
		add(layeredPane, JLayeredPane.DEFAULT_LAYER);
		
		
		
		wordsToDragList = new ArrayList<SentenceWord>();
		sentenceWordsList = new ArrayList<>();
		lablesToDragList=new ArrayList<JLabel>();
		this.setSentence(sentence);
		 
		
	}

	/*
	 * RandomToken : struttura dati (inner class definita in fondo) che incapsula i
	 * seguenti dati : the text the point the Bounds pont2d etc, etc Ogni random
	 * token rappresenta UNA PAROLA
	 * 
	 */
	List<SentenceWord> wordsToDragList;
	List<SentenceWord> sentenceWordsList;
	List<JLabel> lablesToDragList;

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

	private JPanel panelSentence;

	private JPanel panelWordsToDrag;
	private int margin;
	static final int minim_lgh_vis=3;

	String sentence=" Questa e una frase prova ad";
	int maxwidth;
	int maxheight;
	
	
	public void setSentence(String sentence) {
		this.sentence = sentence;
		this.clearSentence();
		this.configSentence();
		this.paintSentenceLabels();
		this.paintDragLabels();
	}
	
	
	public void configSentence() {
 		String tokens[];
		/**
		 * Clear the lists
		 */
		this.clearSentence();
		tokens = sentence.split(" ");

		for (int count = 0; count < tokens.length; count++) {
			SentenceWord wordToDrag = new SentenceWord(new JLabel(tokens[count]),count);
			SentenceWord wordInPosition = new SentenceWord(new JLabel(tokens[count]),count);
			sentenceWordsList.add(wordInPosition);
			logLabe(wordToDrag.label);
			/**
			 * ANSWER : NOT VISIBLE? depends if its too short !
			 */
			if (tokens[count].length() <= minim_lgh_vis) {
				wordInPosition.visible = true;
				
			} else {
				wordInPosition.visible = false;
			}

			// get the advance of my text in this font
			
			/**
			 * Random token has RANDOM position
			 */
			//int MaxX = this.getPreferredSize().width - wordToDrag.label.getPreferredSize().width;
			//log("PANNELLO widith : "+String.valueOf(getPreferredSize().width ));
			//log("PANNELLO height : "+String.valueOf(getPreferredSize().height));
			int MaxX = maxwidth- wordToDrag.label.getPreferredSize().width;
			/**
			 * Coordinata X Numero casuale tra 0 max width;
			 */
			int MinX = 0 + margin;
			int randomX = MinX + (int) (Math.random() * ((MaxX - MinX) + 1));
			int MaxY = maxheight -wordToDrag.label.getPreferredSize().height - margin;
			/*int MaxY = panelWordsToDrag.getPreferredSize().height - 
					wordToDrag.label.getPreferredSize().height - margin;*/
			int MinY = 0 + margin + wordToDrag.label.getPreferredSize().height;
			int randomY = MinY + (int) (Math.random() * ((MaxY - MinY) + 1));

			wordToDrag.rect=new Rectangle(randomX, randomY,
					wordToDrag.label.getPreferredSize().width ,
					wordToDrag.label.getPreferredSize().height);
			/**
			 * if too short show in the answers...
			 */
			//wordToDrag.visible = (tokens[count].length() <= minim_lgh_vis) ? false : true;
			wordToDrag.visible =true;
			wordsToDragList.add(wordToDrag);
		}
	}
	
	/***
	 *  - Rimuove le Lables dai pannelli
	 *  - Libera le liste
	 */
	void clearSentence() {
		for (SentenceWord wordtodrag : wordsToDragList) {
			if (wordtodrag.label != null)
				panelWordsToDrag.remove(wordtodrag.label);
		}
		for (SentenceWord wordsentence : sentenceWordsList) {
			if (wordsentence.label != null)
				panelSentence.remove(wordsentence.label);
		}
		
		wordsToDragList.clear();
		sentenceWordsList.clear();
		lablesToDragList.clear();
	}
	
	
	
	/***
	 * Aggiunge le etichette al pannello
	 */
	void paintDragLabels() {
		for(SentenceWord wordtodrag:wordsToDragList) {
			JLabel label=wordtodrag.label;
		//wordtodrag.log();
			label.setBounds(wordtodrag.rect);
			label.setVisible(wordtodrag.visible);
			panelWordsToDrag.add(label);
			this.lablesToDragList.add(label);
		}
	}
	/***
	 * Aggiunge le etichette al pannello
	 */
	void paintSentenceLabels() {
		for(SentenceWord wordsentence:sentenceWordsList) {
			JLabel label=wordsentence.label;
			panelSentence.add(label);
			if(!wordsentence.visible) {
				label.setBorder(new LineBorder(new Color(0, 0, 0)));
				label.setText("............");
			}
			else {
				label.setBorder(null);
				label.setText(wordsentence.text);

			}
			//label.setVisible(wordsentence.visible);
		}
		//this.repaint();
	}
	/***
	 * Cambia gli attributi alle etichette
	 */
	void repaintDragLabels() {
		for(SentenceWord wordtodrag:wordsToDragList) {
			JLabel label=wordtodrag.label;
			label.setBounds(wordtodrag.rect);
			label.setVisible(wordtodrag.visible);

			//panelWordsToDrag.add(label);

		}
		this.repaint();
	}
	/***
	 * Cambia gli attributi alle etichette
	 */
	void repaintSentenceLabels() {
		for(SentenceWord wordsentence:sentenceWordsList) {
			JLabel label=wordsentence.label;
			label.setVisible(wordsentence.visible);
		}
	}
	
	
	/**
	 * 
	 * @author Antonello Iovino for each token encapsulate : the text the point the
	 *         Bounds pont2d etc, etc
	 *
	 */
	private class SentenceWord {
		
		
		public SentenceWord(JLabel label,int val) {
			super();
			this.label = label;
			this.text=label.getText();
			this.val=val;
		}

		JLabel label;
		public int val;
		public String text;
		public boolean visible = true;
		public Rectangle rect;
		
		
		public boolean equals(SentenceWord sw) {
			return this.val == sw.val;
		}

		public void log() {
			System.out.print("Text: "+text);
			System.out.print("label width : "+String.valueOf(label.getWidth()));

			System.out.print(String.format("rect  :(%f, %f, %f, %f", rect.getX(),rect.getY(),
															rect.getWidth(),rect.getHeight()));
			System.out.println("Visble : " + visible);
		}

	}
	public Quizinterface getQuiz() {
		return quiz;
	}

	public void setQuiz(Quizinterface quiz) {
		this.quiz = quiz;
	}

	public boolean isEasyOption() {
		return easyOption;
	}

	public void setEasyOption(boolean easyOption) {
		this.easyOption = easyOption;
	}

	public void shake() {
		if (wordsToDragList == null)
			return;
		if (wordsToDragList.isEmpty())
			return;
		for (SentenceWord wordToDrag : wordsToDragList) {
			if (!wordToDrag.visible)
				continue;
			int MaxX = 600- wordToDrag.label.getPreferredSize().width;
			/**
			 * Coordinata X Numero casuale tra 0 max width;
			 */
			int MinX = 0 + margin;
			int randomX = MinX + (int) (Math.random() * ((MaxX - MinX) + 1));
			int MaxY = 300 -wordToDrag.label.getPreferredSize().height - margin;
			/*int MaxY = panelWordsToDrag.getPreferredSize().height - 
					wordToDrag.label.getPreferredSize().height - margin;*/
			int MinY = 0 + margin + wordToDrag.label.getPreferredSize().height;
			int randomY = MinY + (int) (Math.random() * ((MaxY - MinY) + 1));

			wordToDrag.rect=new Rectangle(randomX, randomY,
					100 , 20);
	
		}	
		this.repaintDragLabels();
	}
	
	public void logLabe(JLabel lbl) {
		System.out.print("------LABLE : Text: "+lbl.getText());
		System.out.print("  width: "+String.valueOf(lbl.getWidth()));
		System.out.print("  Preferred size width : "+String.valueOf(lbl.getPreferredSize().width));
		System.out.print(" Bounds: "+String.valueOf(lbl.getBounds().toString()));

		System.out.println("----");
	
	}
	
	

	public String getSentence() {
		return sentence;
	}

	
	 private class MyMouseAdapter extends MouseAdapter {
	        private JLabel dragLabel = null;
	        Rectangle draggedBound;
	        private int dragLabelWidthDiv2;
	        private int dragLabelHeightDiv2;
	        private JPanel clickedPanel = null;

	        @Override
	        public void mousePressed(MouseEvent me) {
	        	log("mouse pressed");

	        	clickedPanel = (JPanel) layeredPane.getComponentAt(me.getPoint());
				Component[] components = clickedPanel.getComponents();
				if (components.length == 0) {
					return;
				}
				// if we click on jpanel that holds a jlabel
				if (components[0] instanceof JLabel) {

					// remove label from panel
					dragLabel = (JLabel) components[0];
					 
		        		            
					
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
	          
	            if (dragLabel==null) {
	                return;
	            }else {
		            clickedPanel = panelWordsToDrag;
		            draggedBound=dragLabel.getBounds();
	            	 // remove label from panel
	                clickedPanel.remove(dragLabel);
	                clickedPanel.revalidate();
	                clickedPanel.repaint();

	                dragLabelWidthDiv2 = dragLabel.getWidth() / 2;
	                dragLabelHeightDiv2 = dragLabel.getHeight() / 2;

	                int x = me.getPoint().x - dragLabelWidthDiv2;
	                int y = me.getPoint().y - dragLabelHeightDiv2;
	                dragLabel.setLocation(x, y);
	                panelWordsToDrag.add(dragLabel, JLayeredPane.DRAG_LAYER);
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
	            if (dragLabel == null) {
	                return;
	            }
	            remove(dragLabel); // remove dragLabel for drag layer of JLayeredPane
	            JPanel droppedPanel = (JPanel) panelSentence.getComponentAt(me.getPoint());
	            // Verificare se la lable ' stata lascita su un parola esatta
	            
	            
	            if (droppedPanel == null) {
	                // if off the grid, return label to home
	            	dragLabel.setBounds(draggedBound);
	                clickedPanel.add(dragLabel);
	                clickedPanel.revalidate();
	            } else {
	            	 // if off the grid, return label to home
	            	dragLabel.setBounds(draggedBound);
	                clickedPanel.add(dragLabel);
	                clickedPanel.revalidate();
	            }

	            repaint();
	            dragLabel = null;
	        }
	 }
	 
	 
	void log(String string) {
		System.out.println(string);				
					}
}
