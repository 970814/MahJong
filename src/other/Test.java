package other;

import java.util.HashSet;

public class Test {
    public static void main(String[] args) {
        long sum = 1;
        for (int i = 136; i > 122; i--)//123*124*...*136
            sum *= i;
        for (int i = 14; i > 0; i--)
            sum /= i;
        System.out.println(sum);//82883351

        int key = 0b1110111111101111110111111;
        int[] value = new int[]{0b10000100000011001011
                , 0b1000_00000000000000000011100000};
//        11498658

        HashSet<String> strings = new HashSet<>();
        strings.add("str");
        strings.add("str");
        strings.add("s"+"tr");
        System.out.println(strings);
    }
}
