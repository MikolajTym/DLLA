import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AppFrame extends JFrame implements KeyListener {
	
	private static JPanel tools = new JPanel();
	private static JPanel first = new JPanel();
	private static JPanel second = new JPanel();
	
	private static JButton learn = new JButton("Learn");
	private static JButton newList = new JButton("New List");
	private static JButton newWord = new JButton("New Word");
	private static JButton speak = new JButton("Speak");
	private static JButton stop = new JButton("Stop");
	
	public static JComboBox Lists;
	
	private static JLabel w1 = new JLabel("",SwingConstants.CENTER);
	private static JLabel w2 = new JLabel("",SwingConstants.CENTER);
	
	private static JLabel deck = new JLabel("Deck of card: ",SwingConstants.CENTER);
	
	private static int xSize;
	private static int ySize;
	private static int toolsHeigth = 35;
	private static int sizeOfFont=180;
	
	private static int los;
	private static boolean isAnswer = false;
	private static boolean isLearning = false;
	private static int learningDeck;
	private static String word; // PRESENT ENGLISH WORD
	
	private static TextToSpeech tts = new TextToSpeech(); // TODO AUDIO
	public static Reader reader;
	public static Writer writer;

	public AppFrame() {

		reader = new Reader();
		
		this.setTitle("DLLA - Delightful Languages Learning Application");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.addKeyListener(this);
		this.setFocusable(true);
		
		createGUI();
	}
	
	public void createGUI() {
		
		Lists = new JComboBox(Reader.namesOfDecks);
		
		this.setVisible(true);
		xSize= (int)this.getSize().getWidth();
		ySize= (int)this.getSize().getHeight();
		
		this.setLayout(new BorderLayout());
		this.add(tools,BorderLayout.NORTH);
		tools.setBackground(Color.BLACK);
		tools.setPreferredSize(new Dimension(xSize,toolsHeigth));
		this.add(first,BorderLayout.CENTER);
		first.setBackground(Color.DARK_GRAY);
		first.setPreferredSize(new Dimension(xSize,(ySize-toolsHeigth)/2));
		this.add(second,BorderLayout.SOUTH);
		second.setBackground(Color.DARK_GRAY);
		second.setPreferredSize(new Dimension(xSize,(ySize-toolsHeigth)/2));
		
		tools.add(deck);
		deck.setFocusable(false);
		deck.setForeground(Color.WHITE);
		
		tools.add(Lists);
		Lists.setFocusable(false);
		Lists.setPreferredSize(new Dimension(120,25));
		
		tools.add(learn);
		learn.addActionListener(AL);
		learn.setFocusable(false);
		
		tools.add(newList);
		newList.addActionListener(AL);
		newList.setFocusable(false);
		
		tools.add(newWord);
		newWord.setFocusable(false);
		
		tools.add(speak);
		speak.addActionListener(AL);
		speak.setFocusable(false);
		
		tools.add(stop);
		stop.addActionListener(AL);
		stop.setFocusable(false);
		
		first.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		first.add(w1,gbc);
		w1.setForeground(Color.WHITE);
		w1.setFont(new Font("Calibri",Font.BOLD, 180));
		
		second.setLayout(new GridBagLayout());
		second.add(w2,gbc);
		w2.setForeground(Color.WHITE);
		w2.setFont(new Font("Calibri",Font.BOLD, 180));
		
	}
	
	private static void changeSizeOfLabel(JLabel label) {
		while((int) label.getPreferredSize().getWidth()>(xSize-190)) {
			sizeOfFont-=10;
			label.setFont(new Font("Calibri",Font.BOLD, sizeOfFont));
		}
		sizeOfFont=180;
	}
	
	private static void readWord() {
		
		w1.setText("");
		w2.setText("");
		
		los = (int)(Math.random()*Reader.Decks.get(learningDeck).getWords().size());
		
		if(Reader.Decks.get(learningDeck).getWords().size()==0) {
			stopLearning();
			JOptionPane.showMessageDialog(null, "Congratulations! You know all the words!");
			return;
		}

		word = (String) Reader.Decks.get(learningDeck).getWords().get(los).getWord();
		w1.setText(word);
		
		changeSizeOfLabel(w1);
		
		speakWord(word);
		
		isAnswer=false;
	
	}
	
	private static void speakWord(String word) {
		tts.speak(word, 2.0f, false, false);
	}
	
	private static void showAnswer() {
		w2.setFont(new Font("Calibri",Font.BOLD, sizeOfFont));
		w2.setText((String) Reader.Decks.get(learningDeck).getWords().get(los).getDef());
		
		changeSizeOfLabel(w2);
		
		isAnswer=true;
	}
	
	private static void delete() {
		Reader.Decks.get(learningDeck).getWords().remove(los);
	}
	
	private static void stopLearning() {
		isLearning=false;
		w1.setText("");
		w2.setText("");
	}
	
	private static ActionListener AL = (e) -> {
		Object s = e.getSource();
		
		if (s==learn) {
			isLearning=true;
			learningDeck=Lists.getSelectedIndex();
			reader.readFile(reader.Decks.get(learningDeck).getName());
			readWord();
		} else if (s==speak) {
			speakWord(word);
		} else if (s==newList) {
			writer = new Writer();
			String name;
			name=JOptionPane.showInputDialog(null,"Enter new deck name:");
			writer.createFile(name);
		} else if (s==stop) {
			stopLearning();
		}
		
	};
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(isLearning==true) {
			switch(e.getKeyCode()) {
			case 32: // SPACE
				if(isAnswer==false && isLearning==true) {
					showAnswer();
				} else if (isLearning==true) {
					delete();
					readWord();
				}
				
				break;
			case 50: // 2
				if(isAnswer==false && isLearning==true) {
					showAnswer();
				} else {
					delete();
					readWord();
				}
				break;
			case 49: // 1
				if(isAnswer==false && isLearning==true) {
					showAnswer();
				} else {
					readWord();
				}
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}