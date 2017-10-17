package card;

import java.util.Arrays;
import java.util.Random;

public class Shuffler {
    private Random random = new Random();

    void wash(int[] keys, int from, int to) {
        if (to - from < 2) return;
        for (; from < to; from++)
            exchange(keys, from, random.nextInt(to - from) + from);
    }

    private void exchange(int[] keys, int i, int j) {
        if (i == j) return;
        keys[i] ^= keys[j];
        keys[j] ^= keys[i];
        keys[i] ^= keys[j];
    }

    public static void main(String[] args) {
        int[] keys = {Integer.MAX_VALUE, -Integer.MAX_VALUE};
        System.out.println(Arrays.toString(keys));
        new Shuffler().wash(keys, 0, keys.length);
        System.out.println(Arrays.toString(keys));
//        new Shuffler().wash(null, 5, 9);

    }
}
