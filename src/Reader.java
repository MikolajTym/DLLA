import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class Reader {
	
	public static ArrayList<Deck> Decks = new ArrayList<Deck>(); // List of Decks
	
	private static ArrayList<String> readingWords;
	
	File folder = new File("Deck"); // PATH WITHOUT /
	private static String nameFile;
	public static String namesOfDecks[];
	
	public Reader() {
		
		Decks.clear();
		readFolder(folder);

	}
	
	public static void createNames() {
		String names[] = new String[Decks.size()];
		
		for(int i=0;i<names.length;i++) {
			names[i]=Decks.get(i).getName();
		}
		
		namesOfDecks=names;
		
		AppFrame.Lists = new JComboBox(namesOfDecks);
		
	}
	
	public static void readFolder(File folder) {
		
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				nameFile=file.getName();
				int dotIndex = nameFile.indexOf(".");
				Decks.add(new Deck(nameFile.substring(0, dotIndex)));
			} else {
				readFolder(file);
			}
			
		}
		
		createNames();
		
	}
	
	public static void readFile(String name) {
		
		int indexOfDeck = 0;
		
		for (int i=0; i<Decks.size();i++) {
			
			if (name==Decks.get(i).getName()) {
				Decks.get(i).getWords().clear();
				indexOfDeck=i;
				break;
			}
			
		}

		try {
			
			File file = new File("Deck/"+name+".txt");
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader fr = new InputStreamReader(fis, "UTF-8");
			BufferedReader breader = new BufferedReader(fr);
			
			String line;
			readingWords = new ArrayList<String>();
			
			while((line=breader.readLine()) != null) {

				readingWords.add(line);
				
			}
			
			for (int i=0;i<readingWords.size();i+=2) {
				Decks.get(indexOfDeck).getWords().add(new Word(readingWords.get(i),readingWords.get(i+1)));
			}
			
			breader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}