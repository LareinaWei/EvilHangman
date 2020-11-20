import java.util.*;

public class HangmanRunner {
	
	void playHangmanGame(String path, Scanner scnr) {
		//initialize a new game
		Hangman hangman = new Hangman(path);
		
		int length;
		boolean correct;
		boolean end = false;
		String guess;
		ArrayList<String> currWordFamily = new ArrayList<String>();
		HashSet<Integer> possibleLength = new HashSet<Integer>();
		
		//get all possible length for the words
		possibleLength = hangman.getPossibleLength();
		
		//get the length user inputed
		length = hangman.getLength(scnr, possibleLength);
		
		//set the initial word family
		currWordFamily = hangman.getInitialWordFamily(length);
		
		//set the reveal pattern
		hangman.setRevealed(length);
		
		//print the game start message
		System.out.println("The game has started.");
		hangman.printRevealed();
		
		//loop until the game is end
		while(end == false) {
			//get user guess
			guess = hangman.getGuess(scnr);
			
			//update the current word family using the guess user made 
			currWordFamily = hangman.getFamilies(guess, currWordFamily);
			
			//check if the user's guess is correct
			correct = hangman.correctGuess(guess);
			
			//print guess related message
			hangman.printGuessMessage(guess, correct);
			
			//print the updated revealed pattern
			System.out.println("Now the word is: ");
			hangman.printRevealed();
			
			//check if the game is over
			end = hangman.gameIsOver();
			

		}
		
		//print the final result of the game
		hangman.printResult();
	}
	
	
	public static void main(String[] args) {
		
		Scanner scnr = new Scanner(System.in);
		
		HangmanRunner runner = new HangmanRunner();
		runner.playHangmanGame("engDictionary.txt", scnr);
		
	}
	
	
}
