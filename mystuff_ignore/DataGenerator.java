import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataGenerator {
    static Random rand = new Random();

    private static final String FILENAME = "data.txt";
    private static final int NUM_UNIQUE_LETTERS = 20; // Change this value to adjust the number of unique letters
    private static final int MAX_WEIGHT = 10; // Change this value to adjust the maximum weight value


    private static List<String> generateUniqueLetters(int numLetters) {
        List<String> letters = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numLetters; i++) {
            char c;
            do {
                c = (char) (random.nextInt(26) + 'A'); // Generate a random uppercase letter
            } while (letters.contains(c + "")); // Ensure that the letter has not already been added
            letters.add(c + "");
        }
        return letters;
    }

    public static void main(String[] args) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {

            List<String> uniqueLetters = generateUniqueLetters(NUM_UNIQUE_LETTERS);
            Random random = new Random();

            for (String letter : uniqueLetters) {
                int numAppearances = random.nextInt(2) + 1; // Generate a random number of appearances between 1 and 2
                for (int i = 0; i < numAppearances; i++) {
                    String source = letter;
                    String dest;
                    do {
                        dest = uniqueLetters.get(random.nextInt(uniqueLetters.size())); // Choose a random destination letter
                    } while (dest.equals(source)); // Ensure that the source and destination letters are not the same
                    int weight = random.nextInt(MAX_WEIGHT) + 1;
                    bw.write(source + " " + dest + " " + weight);
                    bw.newLine();
                }
            }

            System.out.println("Data generation complete.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
