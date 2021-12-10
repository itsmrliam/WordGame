import java.io.*;
import java.util.Locale;

public class Game {
    /* Creating a static method allows it to be used in the other class without accessing it as an object.
     if it was a public method, it would need to be accessed by creating an object of this class. */

    public static String[] fileReader() throws IOException {
        /* Creates fileReader method in Game class. This is used to handle the file reader to
                                  access the dictionary and create the words array used for the game. */

        String fileName = "dictionary.txt"; // Setting the filename for the FileReader to read from.
        BufferedReader brFile = new BufferedReader(new FileReader(fileName)); // File Reader for reading input from files.

        int bufferLength = (int) (new File(fileName).length()); // Creates int for total amount of characters in the file.
        char[] buffer = new char[bufferLength];
        int charsRead = brFile.read(buffer, 0, bufferLength);
        brFile.close(); // Closes the BufferedReader File Stream
        //System.out.println(bufferLength); // Prints Buffer Length (not needed)

        String text = new String(buffer); // Creates a new string version of the 'buffer' char array.
        text = text.trim(); // Trims the text string of any whitespace before and after each word/character
        String[] words = text.split("\n"); /* Creates new String array containing Strings from 'text' String
        splitting them using linebreak */

        for (int i = 0; i < words.length; i++) {           // loops through the 'words' array to get every String in the array
            words[i] = words[i].trim();            // 'i' represents each string in the array. each time the i is incremented,
            // System.out.println(words[i]);       // it represents a new element/String.
            // The above line prints each word in the array.
        }
        return words;
    }

    static void Rules() { // Prints the rules of the game.
        System.out.println("Here are the rules for the game:\n " +
                "1. The FIRST character of the next word should start with the LAST character of the previous word" +
                " and they are CASE SENSITIVE!.\n 2. You CAN NOT use a word more than once. If it has been used in the game" +
                " it cannot be used again. \n 3. You can only use alphanumeric characters (a-z or A-Z). No numbers or special" +
                " characters. \n 4. The word must be a VALID WORD in the dictionary. \n 5. If you do not enter an input, the game" +
                " will restart.");
    }

    static int generateWord() throws IOException { // This creates a method to generate a random word from the dictionary to begin the game.
        fileReader(); // Accesses fileReader method to get the dictionary.

        double x = Math.random();
        x = 1.0 + ((fileReader().length - 1) * x); // Randomises the word to return a random element in array.

        int returnWord = (int) Math.floor(x);

        return returnWord;
    }

    static String gameOver(int error, int round) { /* This method handles the gameOver sequence when the player loses.
                                Each way you can lose has an individual message to display.*/
        String endGame = "Game over. You lost on round " + round;
        System.out.println(endGame);

        switch (error) {
            case 0:
                System.out.println("The word didn't begin with the last letter of the previous word!");
                break;
            case 1:
                System.out.println("Word doesn't exist in the dictionary!");
                break;
            case 2:
                System.out.println("Word has already been used!");
                break;
            case 3:
                System.out.println("Word doesn't match alphanumeric input requirements!");
                break;
        }

        System.exit(0);
        return endGame;
    }

    static void validWord(String input, int round, String[] words) {
        boolean wordExists = true;          // This method is used to check if the user input word exists in the current dictionary.

        int i;
        for (i = 0; i < words.length - 1; i++) {
            if (input.equals(words[i])) {       // Checking input against every word in the dictionary (words array) to see if it matches.
                wordExists = true;
                break;      // If the word exists in the dictionary, breaks out of the loop and continues the game
            } else {
                wordExists = false;
            }
        }
        if (!wordExists) {
            gameOver(1, round);         // If the word exists, runs the gameOver method with error code 1.
        }
    }

    static void gameStart(String input, String[] usedWords, int rounds) throws IOException {
        fileReader(); // Accesses the fileReader method

        boolean wordUnused = true;

        for (int i = 0; i < rounds; i++) {      // This loops through all the usedWords array to check if the user input
            if (input.equals(usedWords[i])) {   // has already been used before.
                wordUnused = false;
                break;
            } else {
                wordUnused = true;
            }
        }

        char[] inputToChar = input.toCharArray(); // Converts user input into char array to access each character.
        char[] previousWordChar = usedWords[rounds - 1].toCharArray(); /* converts previous word into a char array
                                                                          to access the last letter of the word. */

        if (inputToChar[0] != previousWordChar[usedWords[rounds - 1].length() - 1]) {
            gameOver(0, rounds);        // If word doesn't begin with the character the previous word ended in, ends game with code 0.
        } else if (!wordUnused) {
            gameOver(2, rounds);        // If word has already been used, ends game with code 2.
        } else if (!(input.matches("[ a-zA-Z']+"))) {
            gameOver(3, rounds); // If word doesn't match alphanumeric requirements, ends game with code 3.
        }
    }
}


