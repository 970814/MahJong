package other;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Test4 {
    public static void main(String[] args) throws FileNotFoundException {
        //7对只有64种情况
        Scanner scanner = new Scanner(new File("7"));
        int count = 0;
        String line = null;
        HashSet<String> set = new HashSet<>();
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            set.add(line);
            count++;
        }
        System.out.println(line);//32333
        System.out.println(count);//260
        System.out.println(set.size());//80
        for (String sevenPair : set) {
            System.out.println(sevenPair);
        }
        //22 2 22 2 2  0,0,1,1,3,3,5,5,6,6,8,8,10,10
        //22 2 2 222   0,0,1,1,3,3,5,5,9,9,10,10,11,11
    }
}
