package other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Compare {
    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println((int)Long.parseLong("b6ddb777", 16));
        HashSet<Integer> set = read("table");
        HashSet<Integer> mySet = read("myTable");
        System.out.println(set.size());
        System.out.println(mySet.size());
        mySet.removeIf(set::remove);
        System.out.println(set.size());
        System.out.println(mySet.size());
        PrintStream table2 = new PrintStream(new File("table2"));
        PrintStream myTable2 = new PrintStream(new File("myTable2"));
        for (Integer x : set) table2.printf("%#x\n", x);
        for (Integer x : mySet) myTable2.printf("%#x\n", x);
//        0b111011011101101110111011
//        0b111011011101101110111011
    }

    private static HashSet<Integer> read(String pathname) throws FileNotFoundException {
        HashSet<Integer> set = new HashSet<>();
        Scanner scanner = new Scanner(new File(pathname));
        while (scanner.hasNext()) {
            String line = scanner.nextLine().substring(2);
            int shape = (int)Long.parseLong(line, 16);
            if(!set.add(shape))
                System.out.println("重复数据：" + shape);
        }
        scanner.close();
        return set;
    }
}
