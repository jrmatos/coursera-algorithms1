import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        String currentWord;

        while (!StdIn.isEmpty()) {
            currentWord = StdIn.readString();

            if(StdRandom.bernoulli()) {
                champion = currentWord;
            }
        }

        System.out.println(champion);
    }
}
