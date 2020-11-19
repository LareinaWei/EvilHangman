import java.awt.desktop.SystemSleepEvent;
import java.util.*;

public class Hangman {
	
	
	ArrayList<String> wordList;
	String word;
	String[] revealed;
	boolean[] isCorrected;
	ArrayList<String> correctGuess = new ArrayList<String>();
	ArrayList<String> mistakeGuess = new ArrayList<String>();
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
	 * Set the guess word pattern according to the length user choose
	 * Also initialize the correctness array
	 * @param length
	 */
	public void setRevealed(int length) {
		this.revealed = new String[length];
		this.isCorrected = new boolean[length];
		for(int i=0; i<length; i++) {
			this.revealed[i] = "_";
			this.isCorrected[i] = false;
		}
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
			guess = guess.toLowerCase();
			if(!((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z'))) {
				System.out.println("This is not a valid guess, please try another one!");
				continue;
			}else if(this.correctGuess.contains(guess)||this.mistakeGuess.contains(guess)) {
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
				this.correctGuess.add(guess);
				this.isCorrected[i] = true;
			}
		}
		
		if(!correct) {
			this.mistakeGuess.add(guess);
			this.mistakeTimes += 1;
		}
		return correct;
	}
	
	/**
	 * Update mistake guess arrayList and print the current mistakes array
	 * and the remaining mistake times
	 * @param guess
	 */
	void printMistakeGuess(String guess) {
		System.out.println("Unfortunatly, this guess is wrong.");
		System.out.print("Current mistakes: ");
		for(int i=0; i<this.mistakeGuess.size(); i++) {
			System.out.print(this.mistakeGuess.get(i)+" ");
		}
		if(this.mistakeTimes <= 6) {
			System.out.print("\nYour remaining mistake chances left: " + (6-this.mistakeTimes) + "\n");
		}else {
			System.out.println("Sorry, you ran out of mistake chances.\n");
		}
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

	public void getFamilies(String letter) {
		this.diffFamilies.clear();

		String key;
		String word;
		String[] currPattern;
		ArrayList<String> wordList; //words that belong to a word family
		for(int i=0; i<this.wordFamily.size(); i++) {
			key = "";
			wordList = new ArrayList<>();
			currPattern = this.revealed.clone();
			word = this.wordFamily.get(i);
			for(int j=0; j<word.length(); j++) {
				if(currPattern[j].contains("_")){
					if(letter.charAt(0) == word.charAt(j)) {
						currPattern[j] = letter;
//						System.out.println(currPattern);
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
		for(int i=0; i<this.revealed.length; i++) {
			this.revealed[i] = String.valueOf(biggestPattern.charAt(i));
		}
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
	
	
	public boolean gameIsOver() {
		boolean end = false;
		int count = 0;
		for(int i=0; i<this.isCorrected.length; i++) {
			if(isCorrected[i] == true) {
				count++;
			}
		}
		if(count == this.isCorrected.length | this.mistakeTimes>6) {
			end = true;
		}
		
		return end;
	}
	
	
	public void printResult() {
		System.out.println("The Hangman game is now over.");
		System.out.println("You guessed "+ (this.correctGuess.size()+this.mistakeTimes) + " times.");
		System.out.println("You made " + this.mistakeTimes + " mistakes.\nThanks for playing.");
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hangman hangman = new Hangman("short_list.txt");
//		hangman.revealed = new String[]{"_","_","_","_"};
		Scanner s = new Scanner(System.in);
		//hangman.getLength(s);
		//System.out.println();
//		hangman.printRevealed();
//		hangman.isCorrected = new boolean[] {true, false, true, true};
//		hangman.mistakeTimes = 6;
//		System.out.println(hangman.gameIsOver());
//		hangman.getGuess(s);
//		String a = "Asdf";
//		a = a.toLowerCase();
//		System.out.println(a);
//		hangman.printMistakeGuess("a");
		
//		
//		hangman.setInitialWordFamily(4);
//		hangman.setRevealed(4);
//		String letter = hangman.getGuess(s);
//		hangman.getFamilies(letter);

	}

}
