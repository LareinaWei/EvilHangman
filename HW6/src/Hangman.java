import java.awt.desktop.SystemSleepEvent;
import java.util.*;

public class Hangman {
	
	ArrayList<String> wordList;
	String word;
	String[] revealed;
	ArrayList<String> correct;
	ArrayList<String> mistakes;
	int mistakeTimes;
	ArrayList<String> wordFamily;
	Map<String, ArrayList<String>> diffFamilies = new HashMap<String, ArrayList<String>>();


	
	
	public Hangman(String path) {
		FileReader fileReader = new FileReader(path);
		this.wordList = fileReader.getWordList();
	}


//	String getRandomWord(ArrayList<String> wordList) {
//		Random random = new Random();
//		int randomIndex = random.nextInt(wordList.size());
//		return wordList.get(randomIndex);
//	}
	public void printRevealed() {
		for(int i=0; i<this.revealed.length; i++) {
			System.out.print(this.revealed[i]+" ");
		}
		System.out.print("\n");
	}
	
	/**
	 * Ask the user for the length of word the user want to guess
	 * @param scnr
	 * @return
	 */
	public int getLength(Scanner scnr) {
		System.out.println("Welcome to the Hangman game. You are allowed to make mistakes 6 times." + "\n" + "How long would you want the word to be?");
		int length;
		while(true) {
			length = scnr.nextInt();
			if(length>21||length<2) {
				System.out.println("Please input a number between 2 and 20");
				continue;
			}
			break;
		}
		
		return length;
	}
	
	/**
	 * Get the user input for guessing
	 * and check if the input is valid or have guessed before,
	 * then turn the guessed letter to lower case
	 * @param scnr
	 * @return the letter that user guesses
	 */
	public String getGuess(Scanner scnr) {
		System.out.println("Please guess a letter:");
		String input;
		String guess;
		while(true) {
			input = scnr.next();
			var ch = input.charAt(0);
			guess = Character.toString(ch);
			if(!((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z'))) {
				System.out.println("This is not a valid guess, please try another one!");
				continue;
			}else if(ch >= 'A' && ch <= 'Z') {
				guess = guess.toLowerCase();
			}else if(this.correct.contains(guess)||this.mistakes.contains(guess)) {
				System.out.println("This character has been guessed before, pleas try another one!");
				continue;
			}else {
				break;
			}
		}
		return guess;
	}
	
	/**
	 * Check if the user's guess is correct
	 * and update the correct, mistake guess and mistake times
	 * @param guess
	 * @return boolean value of the guess is correct or not
	 */
	public boolean correctGuess(String guess) {
		boolean correct = false;
		for(int i=0; i<this.revealed.length; i++) {
			if(this.revealed[i].contains(guess)) {
				correct = true;
				this.correct.add(guess);
			}
		}
		
		if(!correct) {
			this.mistakes.add(guess);
			this.mistakeTimes += 1;
		}
		return correct;
	}
	
	
//	public boolean isCorrect(String letter) {
//		boolean correct = false;
//		for(int i =0; i < this.revealed.length(); i++) {
//			
//		}
//	}
	
	
	/**
	 * Given a set length, return a Array list of words of that length
	 * @param length: length of the word user want to guess
	 */
	void setInitialWordFamily(int length){
		ArrayList<String> sameLengthList = new ArrayList<String>();
		for (String word: this.wordList) {
			if(word.length() == length) {
				sameLengthList.add(word);
			}
		}
		this.wordFamily = sameLengthList;
	}

	void getFamilies(String letter) {
		this.diffFamilies.clear();
		String key;
		String word;
		String[] currPattern;
		ArrayList<String> wordList;
		for(int i=0; i<this.wordFamily.size(); i++) {
			key = "";
			wordList = new ArrayList<>();
			currPattern = this.revealed.clone();
			word = this.wordFamily.get(i);
			for(int j=0; j<word.length(); j++) {
				if(currPattern[j].contains("_")){
					if(letter.charAt(0) == word.charAt(j)) {
						currPattern[j] = letter;
						System.out.println(currPattern);
					}
				}
			}
			
			for(int n=0; n<currPattern.length; n++) {
				key += currPattern[n];
			}
			if(!this.diffFamilies.containsKey(key)) {
				wordList.add(word);
				this.diffFamilies.put(key, wordList);
			}else {
				wordList = this.diffFamilies.get(key);
			}
			
		}
		
		//find the biggest word family
		String biggestPattern;
		biggestPattern = this.getBiggestWordsFamily(this.diffFamilies);
		ArrayList<String> newWordFamily = this.diffFamilies.get(biggestPattern);
		
		//update the word family and revealed word
		this.wordFamily.clear();
		this.wordFamily = newWordFamily;
	}
	
	
	/**
	 * Find the biggest word family and returns the key(pattern)
	 * @param wordFamilies
	 * @return
	 */
	String getBiggestWordsFamily(Map<String, ArrayList<String>> wordFamilies) {
		int max = 0;
		int num;
		String key = null;
		for(Map.Entry<String, ArrayList<String>> entry: wordFamilies.entrySet()) {
			num = entry.getValue().size();
			if(num > max) {
				max = num;
				key = entry.getKey();
			}
		}
		return key;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hangman hangman = new Hangman("short_list.txt");
		hangman.revealed = new String[]{"_","_","_","_"};
		Scanner s = new Scanner(System.in);
		//hangman.getLength(s);
		//System.out.println();
		hangman.printRevealed();
	}

}
