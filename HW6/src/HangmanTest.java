import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

import static org.junit.jupiter.api.Assertions.*;



// Author: Yuhao Wei, Kun Qian
class HangmanTest {

    String file = "short_list.txt";

    Hangman man;

    @BeforeEach
    void setUp() {

        man = new Hangman(file);
    }

    @Test
    void printRevealed() {

        //man.revealed = new {"asdf","asdf"};
        //test the exception returned when there is not the file
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        man.setRevealed(5);
        man.printRevealed();
        assertEquals("_ _ _ _ _", outputStreamCaptor.toString().trim());
    }

    @Test
    void getPossibleLength() {
        HashSet<Integer> result = man.getPossibleLength();
        HashSet<Integer> test = new HashSet<>();
        test.add(3);
        test.add(4);
        test.add(5);
        test.add(6);
        test.add(7);
        test.add(8);
        test.add(9);
        test.add(10);
        test.add(11);
        System.out.println(result.toString());
        assertEquals( test, result);



    }

    @Test
    void getLength() {
    }

    @Test
    void setRevealed() {
        man.setRevealed(5);
        String[] test = {"_","_","_","_","_"};
        System.out.println(man.revealed[0]);
        assertArrayEquals( test, man.revealed);
        boolean[] test2 = {false,false,false,false,false};
        assertArrayEquals( test2, man.isCorrected );
    }

    @Test
    void getGuess() {
        //no
    }

    @Test
    void correctGuess() {
        man.setRevealed(5);
        boolean result = man.correctGuess("b");
        assertEquals(false,result);

        man.revealed = new String[] {"_","_","_","_","a"};
        boolean result2 = man.correctGuess("a");
        assertEquals(true,result2);

    }

    @Test
    void printGuessMessage() {
        man.setRevealed(5);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        man.printGuessMessage("a",false);
        //man.printRevealed();
        String test1 = "Unfortunatly, this guess is wrong.\n" +
                "Your remaining mistake chances left: 6.\n" +
                "Current incorrect guesses:";
        assertEquals(test1, outputStreamCaptor.toString().trim());

        ByteArrayOutputStream outputStreamCaptor2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor2));
        man.revealed = new String[] {"_","_","_","_","a"};
        man.printGuessMessage("a",true);
        //man.printRevealed();
        String test2 = "Congratulation, this is a successful guess.\n" +
                "The letter has been put into the word.\n" +
                "Current incorrect guesses:";
        assertEquals(test2, outputStreamCaptor2.toString().trim());

        ByteArrayOutputStream outputStreamCaptor3 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor3));
        man.mistakeTimes = 7;
        man.revealed = new String[] {"_","_","_","_","_"};
        man.printGuessMessage("a",false);
        //man.printRevealed();
        String test3 = "Unfortunatly, this guess is wrong.\n" +
                "Sorry, you ran out of mistake chances.\n" +
                "Current incorrect guesses:";
        assertEquals(test3, outputStreamCaptor3.toString().trim());

    }

    @Test
    void getInitialWordFamily() {
        ArrayList<String> result = man.getInitialWordFamily(5);
        System.out.println(result.toString());
        ArrayList<String> test= new ArrayList<>();
        test.add("woozy");
        test.add("ultra");
        test.add("train");
        test.add("queue");
        test.add("level");
        test.add("goofy");
        test.add("books");
        test.add("blood");
        test.add("brood");
        test.add("woman");
        test.add("forty");
        test.add("point");
        test.add("house");
        test.add("along");
        test.add("bench");
        assertEquals(test,result);





    }

    @Test
    void getFamilies() {
        ArrayList<String> currWordFamily = man.getInitialWordFamily(5);
        man.setRevealed(5);

        ArrayList<String>  result = man.getFamilies("a", currWordFamily);
        System.out.println(result.toString());
        ArrayList<String> test= new ArrayList<>();
        test.add("woozy");
        test.add("queue");
        test.add("level");
        test.add("goofy");
        test.add("books");
        test.add("blood");
        test.add("brood");
        test.add("forty");
        test.add("point");
        test.add("house");
        test.add("bench");
        assertEquals(test,result);



        ArrayList<String>  result2 = man.getFamilies("o", result);
        System.out.println(result2.toString());
        ArrayList<String> test2= new ArrayList<>();
        test2.add("queue");
        test2.add("level");
        test2.add("bench");
        assertEquals(test2,result2);

    }

    @Test
    void getEvilestWordsFamily() {

        Map<String, ArrayList<String>> diffFamilies = new HashMap<String, ArrayList<String>>();
        ArrayList<String> temp = new ArrayList<>();
        temp.add("queue");
        temp.add("level");
        temp.add("bench");
        diffFamilies.put("_____",temp);
        String result = man.getEvilestWordsFamily(diffFamilies,"a");
        assertEquals("_____",result);



    }

    @Test
    void gameIsOver() {
        man.setRevealed(5);
        boolean result = man.gameIsOver();
        assertEquals(false, result);

        man.mistakeTimes = 7;
         result = man.gameIsOver();
        assertEquals(true, result);

    }

    @Test
    void printResult() {

        man.setRevealed(5);
        man.success = false;
        man.mistakeTimes = 7;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        man.printResult();
        String test1 = "The Hangman game is now over.\n" +
                "You guessed 7 times.\n" +
                "You made 7 mistakes.\n" +
                "Thanks for playing.";
        assertEquals(test1, outputStreamCaptor.toString().trim());

        man.success = true;
        man.mistakeTimes = 7;
        ByteArrayOutputStream outputStreamCaptor2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor2));
        man.printResult();
        test1 = "Congratulations! You win the game.You guessed 7 times.\n" +
                "You made 7 mistakes.\n" +
                "Thanks for playing.";
        assertEquals(test1, outputStreamCaptor2.toString().trim());

    }


}