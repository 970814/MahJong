package other;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {
    public static void main(String[] args) {
        boolean series = new Random().nextBoolean();
        if (series | (series = false))
            System.out.println(series);
    }
}
