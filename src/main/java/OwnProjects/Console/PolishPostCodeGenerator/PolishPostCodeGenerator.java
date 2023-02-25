package OwnProjects.Console.PolishPostCodeGenerator;

import java.util.Random;

public class PolishPostCodeGenerator {
    public static void main(String[] args) {

        Random random = new Random();
        int first = random.nextInt(99);
        int second = random.nextInt(999);
        String f = Integer.toString(first);
        String s = Integer.toString(second);
        if (first < 10) {
            f = "0" + f;
        }
        if (second < 100 && second > 9) {
            s = "0" + s;
        } else if (second < 10) {
            s = "00" + s;
        }
        System.out.println("Your random post code is: " + f + "-" + s);
    }
}
