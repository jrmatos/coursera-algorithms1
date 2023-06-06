import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        String currentWord;
        int count = 1;

        while (!StdIn.isEmpty()) {
            currentWord = StdIn.readString();

            if (StdRandom.bernoulli((double) 1 / count)) {
                champion = currentWord;
            }
        }

        System.out.println(champion);
    }
}
