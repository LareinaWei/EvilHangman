import java.util.*;

public class HangmanRunner {
	
	void playHangmanGame(String path, Scanner scnr) {
		Hangman hangman = new Hangman(path);
		int length;
		boolean correct;
		boolean end = false;
		String guess;
		ArrayList<String> currWordFamily = new ArrayList<String>();
		HashSet<Integer> possibleLength = new HashSet<Integer>();
		
		possibleLength = hangman.getPossibleLength();
		length = hangman.getLength(scnr, possibleLength);
		currWordFamily = hangman.getInitialWordFamily(length);
		hangman.setRevealed(length);
		System.out.println("The game has started.");
		hangman.printRevealed();
		while(end == false) {
			guess = hangman.getGuess(scnr);
			
//			System.out.println("guess: "+ guess +"\n");
			
			currWordFamily = hangman.getFamilies(guess, currWordFamily);
			

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
		runner.playHangmanGame("short_list.txt", scnr);
		
	}
	
	
}
