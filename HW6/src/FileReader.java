import java.util.*;
import java.io.*;
// Author: Yuhao Wei, Kun Qian

public class FileReader {
	
	File dictionary;
	ArrayList<String> wordList = new ArrayList<String>();
	String word;
	


	/**
	 * Constructor of FileReader
	 * @param path: the path of the dictionary file
	 */
	public FileReader(String path) {
		File dictionary = new File(path);
		this.dictionary = dictionary;
		this.wordList = fileRead(dictionary);
	}
	
	
	/**
	 * Read the dictionary file and create the word list
	 * @param file: the dictionary file
	 */
	public ArrayList<String> fileRead(File file){
		ArrayList<String> wordList = new ArrayList<String>();
		try {
			Scanner s = new Scanner(file);
			while(s.hasNextLine()) {
				String word = s.nextLine();
				wordList.add(word);
			}
			s.close();
			
		}catch(FileNotFoundException e) {
			System.out.println("The file doesn't exist. Please try other file.");
			e.printStackTrace();
		}
		
		
		return wordList;
	}
	
	
	
	
	public File getDictionary() {
		return dictionary;
	}


	public ArrayList<String> getWordList() {
		return wordList;
	}


	public String getWord() {
		return word;
	}


}
