import java.util.*;
import java.io.*;

public class FileReader {
	
	File dictionary;
	ArrayList<String> wordList = new ArrayList<String>();
	String word;
	
	
	
	
	


	public FileReader(String path) {
		File dictionary = new File(path);
		this.dictionary = dictionary;
		this.wordList = fileRead(dictionary);
	}

	ArrayList<String> fileRead(File file){
		ArrayList<String> wordList = new ArrayList<String>();
		try {
			Scanner s = new Scanner(file);
			while(s.hasNextLine()) {
				String word = s.nextLine();
				
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("The file doesn't exist. Please try other file.");
			e.printStackTrace();
		}
		
		
		
		return wordList;
	}
	
	
	
	
	public File getDictionary() {
		return dictionary;
	}



	public void setDictionary(File dictionary) {
		this.dictionary = dictionary;
	}



	public ArrayList<String> getWordList() {
		return wordList;
	}



	public void setWordList(ArrayList<String> wordList) {
		this.wordList = wordList;
	}



	public String getWord() {
		return word;
	}



	public void setWord(String word) {
		this.word = word;
	}
	
}
