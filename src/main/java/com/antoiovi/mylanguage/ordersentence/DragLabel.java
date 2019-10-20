package com.antoiovi.mylanguage.ordersentence;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class DragLabel extends JLayeredPane {
	public static final int WIDTH = 680;
	public static final int HEIGHT = 480;
Color LABLE_SENTENCE_COLOR=Color.red.brighter().brighter();
Color LABLE_SENTENCE_COLOR_OVERLAP=Color.cyan;




	public static final int MAX_DRAG_WIDTH = WIDTH - 10;
	public static final int MAX_DRAG_HEIGHT = HEIGHT / 2 - 10;
	public static final int MARGIN = 10;

	private static final int GRID_ROWS = 2;
	private static final int GRID_COLS = 1;
	private static final int GAP = 3;
	private static final Dimension LAYERED_PANE_SIZE = new Dimension(WIDTH, HEIGHT);
	private static final Dimension LABEL_SIZE = new Dimension(60, 40);
	private GridLayout gridlayout = new GridLayout(GRID_ROWS, GRID_COLS, GAP, GAP);
	private JPanel layeredPane = new JPanel(gridlayout);
	private JPanel[][] panelGrid = new JPanel[GRID_ROWS][GRID_COLS];
	private JLabel redLabel = new JLabel("Red", SwingConstants.CENTER);
	private JLabel blueLabel = new JLabel("Blue", SwingConstants.CENTER);
	private JPanel panelWordsToDrag;
	private JPanel panelSentence;

	List<JLabel> lablesToDragList = new ArrayList<JLabel>();
	List<JLabel> lablesSentence = new ArrayList<JLabel>();

	public DragLabel() {

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
		redLabel.setOpaque(true);
		redLabel.setBackground(Color.red.brighter().brighter());
		redLabel.setPreferredSize(LABEL_SIZE);
		panelSentence.add(redLabel);

		blueLabel.setOpaque(true);
		blueLabel.setBackground(Color.blue.brighter().brighter());
		blueLabel.setPreferredSize(LABEL_SIZE);
		panelWordsToDrag.add(blueLabel);
		panelWordsToDrag.setLayout(null);
		layeredPane.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
		setPreferredSize(LAYERED_PANE_SIZE);
		add(layeredPane, JLayeredPane.DEFAULT_LAYER);

		this.setSentence(sentence);

		myMouseAdapter = new MyMouseAdapter();
		addMouseListener(myMouseAdapter);
		addMouseMotionListener(myMouseAdapter);
	}

	String sentence = " Questa e una frase prova ad";
	private MyMouseAdapter myMouseAdapter;

	public void setSentence(String sentence) {
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

		tokens = sentence.split(" ");

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
			labelToDrag.setBounds(rect);
			/**
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

	}

	void log(String s) {
		System.out.println(s);
	}
	private class MyMouseAdapterLabel extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			Component c=e.getComponent();
			if(c!=null && c instanceof JLabel) {
				JLabel lbl=(JLabel)c;
				log("Entered "+lbl.getText());
				log("PARENT ="+c.getParent());
				if(c.getParent()==panelSentence) {
					if(myMouseAdapter.getDragLabel()!=null)
					lbl.setBackground(LABLE_SENTENCE_COLOR_OVERLAP);
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
				Component c=e.getComponent();
			if(c!=null && c instanceof JLabel) {
				JLabel lbl=(JLabel)c;
				log("EXITED LABLE "+lbl.getText());
				log("PARENT ="+c.getParent());
				if(c.getParent()==panelSentence) {
					if(myMouseAdapter.getDragLabel()!=null)
					lbl.setBackground(LABLE_SENTENCE_COLOR);
				}
			}
			
			
		}
		
		 
	};
	
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
			clickedPanel = (JPanel) layeredPane.getComponentAt(me.getPoint());
			log("CLICKED :" + clickedPanel.toString());
			/*
			 * Component[] components = clickedPanel.getComponents(); if (components.length
			 * == 0) { return; }
			 */
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
			if (dragLabel == null) {
				
				return;
			}
			remove(dragLabel); // remove dragLabel for drag layer of JLayeredPane

			JPanel droppedPanel = (JPanel) layeredPane.getComponentAt(me.getPoint());
			if (droppedPanel == null) {
				log("MOUSE RELEASED : droppedPanel == null");

				// if off the grid, return label to home
				clickedPanel.add(dragLabel);
				dragLabel.setBounds(orignialBounds);
				orignialBounds = null;
				clickedPanel.revalidate();
			} else {

				if (droppedPanel == panelSentence) {
					 log(String.format("Realesd on panelsentence(%d,%d)",me.getX(),me.getY()));
					Component c = panelSentence.findComponentAt(me.getX(), me.getY());
					Point point2 = new Point(me.getX(), me.getY() - panelSentence.getBounds().y);
					log("Point2=" + point2.toString());
					JLabel droppedLabel = null;
					for (JLabel lbl : lablesSentence) {
						if (lbl.getBounds().contains(point2)) {
							droppedLabel = lbl;
							log("MATCH lbl.getBounds().contains(me.getX(),me.getY()+MAX_DRAG_HEIHT : " + lbl.getText());
							break;
						}
					}

					if (droppedLabel != null) {
						if (droppedLabel.getText().equals(dragLabel.getText())) {
							droppedLabel.setText("MATCH");
							clickedPanel.remove(dragLabel);
							dragLabel=null;
							droppedPanel=null;
							droppedLabel=null;
							orignialBounds = null;
							clickedPanel.revalidate();

							
						} else {
							clickedPanel.add(dragLabel);
							clickedPanel.revalidate();
							dragLabel.setBounds(orignialBounds);
							orignialBounds = null;
						}
					} else {

						clickedPanel.add(dragLabel);
						clickedPanel.revalidate();
						dragLabel.setBounds(orignialBounds);
						orignialBounds = null;
					}
				}else {
					clickedPanel.add(dragLabel);
					clickedPanel.revalidate();
					dragLabel.setBounds(orignialBounds);
					orignialBounds = null;
				}

			}

			repaint();
			dragLabel = null;
		}
	}

	private static void createAndShowUI() {
		JFrame frame = new JFrame("DragLabelOnLayeredPane");
		frame.getContentPane().add(new DragLabel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowUI();
			}
		});
	}
}
