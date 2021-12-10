import java.io.*;

public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {

        InputStreamReader isr = new InputStreamReader(System.in); // InputStreamReader
        BufferedReader brInput = new BufferedReader(isr); // BufferedReader for reading text input
        String fileName = "dictionary.txt"; // Setting the filename for the FileReader to read from.
        BufferedReader brFile = new BufferedReader(new FileReader(fileName)); // File Reader for reading input from files.

        int bufferLength = (int) (new File(fileName).length()); // Creates int for total amount of characters in the file.
        char[] buffer = new char[bufferLength];
        int charsRead = brFile.read(buffer, 0, bufferLength);
        brFile.close(); // Closes the BufferedReader File Stream

        String text = new String(buffer); // Creates a new string version of the 'buffer' char array.
        text = text.trim(); // Trims the text string of any whitespace before and after each word/character
        String[] words = text.split("\n"); /* Creates new String array containing Strings from 'text' String
        splitting them using linebreak */
        for (int i = 0; i < words.length; i++) {        // Taking each word in the WORDS array and trimming whitespace from them
            words[i] = words[i].trim();                 // This was a really important process of the code for the users input to access the dictionary.
        }

        Game.Rules();       // Printing the rules of the game

        boolean validInput = false;
        int roundLimit = 0;
        String roundInput = null;

        do {
            System.out.println("How many rounds do you want to play?");
            roundInput = brInput.readLine();                        // Takes user input to select how many rounds they want to play
            try{
                roundLimit = Integer.parseInt(roundInput);          // User input validation to confirm user input was an integer.
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Wrong input, enter a number.");
                validInput = false;
            }
        } while (!validInput);

        int randomWord = Game.generateWord();       // Generating a random word to begin the game using generateWord method.

        System.out.println("Game starting for " + roundLimit + " rounds ...");

        Thread.sleep(1500);     // Adds 1500 millisecond (1.5 second) pause to the game to allow user to read instructions
        int roundCount = 1;
        String usedWords[] = new String[roundLimit + 1];   // Creating a new String array for all the used words.
        usedWords[0] = words[randomWord]; // Setting the first element (first word in String array) to a random word generated previously

        System.out.println("The first word is " + usedWords[0]); // Prints the first word of the game.


        for (roundCount = 1; roundCount <= roundLimit; roundCount++) { // Starts the game loop lasting for as many rounds as user selects.
            System.out.println("Round " + roundCount); // Prints the current round
            System.out.println("Previous word: " + usedWords[roundCount - 1]);

            System.out.println("Enter next word: ");

            String input = brInput.readLine(); // Prompts user for word
            usedWords[roundCount] = input; // Adds users inputted word to the usedWords array.

            if (input == null) {
                System.exit(0);
            }

            Game.validWord(input, roundCount, words); // Runs a valid word check to see if the word has been used already
            Game.gameStart(input, usedWords, roundCount); // Proceeds with the game.
        }
        if (roundCount > roundLimit) {
            System.out.println("Congratulations, you won. You lasted " + roundLimit + " rounds!"); // Prints end message.
            System.out.println("All the words used: ");
            for (int i = 0; i < roundLimit + 1; i++) {
                System.out.println(i + ". " + usedWords[i]); // Prints a numbered list of all the used words.
            }
        }
    }
}