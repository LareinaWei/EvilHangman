import java.util.*;

public class HangmanRunner {
	
	void playHangmanGame(String path, Scanner scnr) {
		Hangman hangman = new Hangman(path);
		int length;
		boolean correct;
		boolean end = false;
		String guess;
		length = hangman.getLength(scnr);
		hangman.setInitialWordFamily(length);
		hangman.setRevealed(length);
		System.out.println("The game has started.");
		hangman.printRevealed();
		while(end == false) {
			guess = hangman.getGuess(scnr);
			
			System.out.println("guess: "+ guess +"\n");
			
			hangman.getFamilies(guess);

			correct = hangman.correctGuess(guess);
			
			if(!correct) {
				hangman.printMistakeGuess(guess);
			}
			
			System.out.println("Now the word is: ");
			
			hangman.printRevealed();
			
			end = hangman.gameIsOver();
			

		}
		
		hangman.printResult();
	}
	
	
	public static void main(String[] args) {
		
		Scanner scnr = new Scanner(System.in);
		
		HangmanRunner runner = new HangmanRunner();
		runner.playHangmanGame("engDictionary.txt", scnr);
		
	}
	
	
}
