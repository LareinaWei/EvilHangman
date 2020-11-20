import java.util.*;

public class Hangman {
	
	
	ArrayList<String> wordList;
	String word;
	String[] revealed;
	boolean[] isCorrected;
	boolean success;
	ArrayList<String> correctGuess = new ArrayList<String>();
	ArrayList<String> mistakeGuess = new ArrayList<String>();
	int mistakeTimes;

	
	
	public Hangman(String path) {
		FileReader fileReader = new FileReader(path);
		this.wordList = fileReader.getWordList();
	}

	/**
	 * Print the current revealed pattern
	 */
	public void printRevealed() {
		for(int i=0; i<this.revealed.length; i++) {
			System.out.print(this.revealed[i]+" ");
		}
		System.out.print("\n");
	}
	
	/**
	 * Get all the possible length of word of the wordList
	 * @return a HaseSet contains all possible length
	 */
	public HashSet<Integer> getPossibleLength(){
		int length;
		HashSet<Integer> possibleLength = new HashSet<Integer>();
		for(String word: this.wordList) {
			length = word.length();
			if(!possibleLength.contains(length)) {
				possibleLength.add(length);
			}
		}
		
		return possibleLength;
	}
	
	
	/**
	 * Ask the user for the length of word the user want to guess
	 * @param scnr, HashSet of possibleLength
	 * @return
	 */
	public int getLength(Scanner scnr, HashSet<Integer> possibleLength) {
		int min = Collections.min(possibleLength);
		int max = Collections.max(possibleLength);
		System.out.println("Welcome to the Hangman game. You are allowed to make mistakes 6 times." + "\n" + "How long would you want the word to be?");
		System.out.println("Please try input a number between: "+ min + " and " + max +".");
		String input;
		int length;
		while(true) {
			try {
				input = scnr.next();
				length = Integer.parseInt(input);
				if(!possibleLength.contains(length)) {
					System.out.println("Unfortunately we don't have a word of this length. Please try another number.");
					continue;
				}
			}catch(Exception e){
				System.out.println("Please input a number.");
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
			if(input.length()>1) {
				System.out.println("This is not a valid guess, please only input one letter.");
				continue;
			}else if(!((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z'))) {
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
	void printGuessMessage(String guess, boolean correct) {
		if(!correct){
			System.out.println("Unfortunatly, this guess is wrong.");
			if(this.mistakeTimes <= 6) {
				System.out.println("Your remaining mistake chances left: " + (6-this.mistakeTimes)+".");
			}else {
				System.out.println("Sorry, you ran out of mistake chances.");
			}
		}else {
			System.out.println("Congratulation, this is a successful guess."
					+ "\nThe letter has been put into the word.");
		}
		System.out.print("Current incorrect guesses: ");
		for(int i=0; i<this.mistakeGuess.size(); i++) {
			System.out.print(this.mistakeGuess.get(i)+" ");
		}
		System.out.print("\n");
	}
	
	
	
	/**
	 * Given a set length, return a Array list of words of that length
	 * @param length: length of the word user want to guess
	 */
	public ArrayList<String> getInitialWordFamily(int length){
		ArrayList<String> sameLengthList = new ArrayList<String>();
		for (String word: this.wordList) {
			if(word.length() == length) {
				sameLengthList.add(word);
			}
		}
		return sameLengthList;
	}

	
	/**
	 * update the word families every time user guessed a letter
	 * @param letter
	 */
	public ArrayList<String> getFamilies(String letter, ArrayList<String> currWordFamily) {
		String key;
		String word;
		String[] currPattern;
		ArrayList<String> currPatternWordList; //words that belong to a word family
		Map<String, ArrayList<String>> diffFamilies = new HashMap<String, ArrayList<String>>();
		for(int i=0; i<currWordFamily.size(); i++) {
			key = "";
			currPatternWordList = new ArrayList<>();
			currPattern = this.revealed.clone();
			word = currWordFamily.get(i);
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
			if(!diffFamilies.containsKey(key)) {
				currPatternWordList.add(word);
				diffFamilies.put(key, currPatternWordList);
			}else {
				currPatternWordList = diffFamilies.get(key);
				currPatternWordList.add(word);
				diffFamilies.replace(key, currPatternWordList);
			}
			
		}
		
		//find the biggest word family
		String biggestPattern;
		biggestPattern = this.getEvilestWordsFamily(diffFamilies, letter);
		ArrayList<String> newWordFamily = diffFamilies.get(biggestPattern);

		//update the word family and revealed word
		for(int i=0; i<this.revealed.length; i++) {
			this.revealed[i] = String.valueOf(biggestPattern.charAt(i));
		}
		
		return newWordFamily;
	}
	
	
	/**
	 * Find the biggest word family and returns the key(pattern)
	 * @param wordFamilies
	 * @return
	 */
	String getEvilestWordsFamily(Map<String, ArrayList<String>> wordFamilies, String letter) {
		int max = 0;
		int num;
		String key = null;
		
		//get length of the largest word families 
		for(Map.Entry<String, ArrayList<String>> entry: wordFamilies.entrySet()) {
			num = entry.getValue().size();
			if(num > max) {
				max = num;
			}
		}
		
		//get the keys of the largest families and store it in an ArrayList
		ArrayList<String> familiesKeys = new ArrayList<String>();
		for(Map.Entry<String, ArrayList<String>> entry: wordFamilies.entrySet()) {
			if(max == entry.getValue().size()) {
				familiesKeys.add(entry.getKey());
			}	
		}
		
		//find the evilest pattern!

		int min = familiesKeys.get(0).length();
		for(String pattern: familiesKeys) {
			int count = 0;
			for(int i=0; i<pattern.length(); i++) {
				if(pattern.charAt(i) == letter.charAt(0)) {
					count += 1;
				}
			}
			if(count<min) {
				min = count;
				key = pattern;
			}
		}
		
		return key;
	}
	
	/**
	 * Determine whether the game is now over
	 * The game is over if all the letters are guessed
	 * or the mistakes times is more than 6
	 * @return boolean of game over status
	 */
	public boolean gameIsOver() {
		boolean end = false;
		int count = 0;
		for(int i=0; i<this.isCorrected.length; i++) {
			if(isCorrected[i] == true) {
				count++;
			}
		}
		if(count == this.isCorrected.length | this.mistakeTimes>6) {
			this.success = true;
			end = true;
		}
		
		return end;
	}
	
	/**
	 * Print the final result of the game
	 */
	public void printResult() {
		if(this.success) {
			System.out.print("Congratulations! You win the game.");
		}else {
			System.out.println("The Hangman game is now over.");
		}
		System.out.println("You guessed "+ (this.correctGuess.size()+this.mistakeTimes) + " times.");
		System.out.println("You made " + this.mistakeTimes + " mistakes.\nThanks for playing.");
	}
	
		


}
