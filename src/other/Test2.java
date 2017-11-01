package other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

public class Test2 {
    static {
        try {
            System.setOut(new PrintStream(new File(".\\compose")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(500);
            System.out.println(random.nextInt());
        }
    }
}
