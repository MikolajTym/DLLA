import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	
	public Writer() {
		AppFrame.reader = new Reader();
	}
	
	public static void createFile(String name) {
		
		Reader.Decks.add(new Deck(name));
		File file = new File("Deck/"+name+".txt");
		
		try {
			FileWriter fwriter = new FileWriter(file);
			BufferedWriter bwriter = new BufferedWriter(fwriter);
			bwriter.write("");
			
			bwriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Reader.readFolder(new File("Deck"));
		
	}
	
	public static void saveFiles() {
		
	}

}