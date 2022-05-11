import java.util.ArrayList;

public class Deck {
	
	private String name;
	private ArrayList<Word> words;
	
	public Deck(String name) {
		
		this.name=name;
		this.words = new ArrayList<Word>();
		
	}
	
	public ArrayList<Word> getWords() {
		return words;
	}
	
	public String getName() {
		return name;
	}

}