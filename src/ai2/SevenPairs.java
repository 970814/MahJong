package ai2;

import other.AgariIndex;

import java.util.Arrays;
import java.util.HashSet;

@SuppressWarnings("Duplicates")
public class SevenPairs {
    static void addSevenPair(HashSet<Integer> shapes) {//Ass
        byte[] h13 = new byte[34];
        for (int i = 0; i < 7; i++)
            h13[i] += 2;
        traverse(shapes, h13, 0, 6);
        System.out.println(Arrays.toString(h13));
    }

    private static void traverse(HashSet<Integer> shapes, byte[] h13, int L, int H) {
        if (L < H) traverse(shapes, h13, L + 1, H);
        else {
            byte[] copy = null;
            if (h13[9] != 0) {//保证形状没有被破坏
//                System.out.println(Arrays.toString(h13));
                copy = h13.clone();
                int from;
                for (from = 8; from >= 0; from--) if (h13[from] == 0) break;
                if (h13[from] == 0) from++;//因为只有7对，所以from不可能为0,if必然为真
                System.arraycopy(h13, from, h13, 9, H - from + 1);
                for (int i = from; i < 9; i++) h13[i] = 0;//这些要归零
            }
            int shape = Shape.shapeOfGroup(h13);
            System.out.println(Arrays.toString(h13) + " Shape: " + Integer.toHexString(shape));
            shapes.add(shape);
            if (copy != null)
                System.arraycopy(copy, 0, h13, 0, copy.length);
            return;
        }
        System.arraycopy(h13, L + 1, h13, L + 2, H - L);
        h13[L + 1] = 0;
        traverse(shapes, h13, L + 2, H + 1);
        System.arraycopy(h13, L + 2, h13, L + 1, H - L);
        h13[H + 1] = 0;
    }

    //Java 的arraycopy是先搬运到一个内存，在搬运到指定位置,类似memcpy
    public static void main(String[] args) {
//        byte[] h13 = {2, 2, 2, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        byte[] h13 = {2, 2, 2, 0, 2, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        byte[] h13 = {2, 2, 2, 0, 2, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        byte[] h13 = {2, 2, 2, 0, 2, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

//        move(h13, 6, 8);
//        System.out.println(Arrays.toString(h13));
        HashSet<Integer> shapes = new HashSet<>();
        addSevenPair(shapes);
//        for (Integer shape : shapes) System.out.println(shape);
        System.out.println(shapes.size());
//        2, 2, 2, 0, 2, 0, 2, 0, 2, 2, 0,
//        2, 2, 2, 0, 2, 0, 2, 0, 2, 0, 2,

//        byte[] h34 = new byte[]{2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};// 125269879
//        h34 = new byte[]{2, 0, 2, 0, 2, 0, 2, 0, 2, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};// 125269879
//        int[] h342 = new int[]{2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};// 125269879
//
////              h342 = new int[]{2, 0, 2, 0, 2, 0, 2, 0, 2, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};// 125269879
//
//        int[] ar = {0, 0, 2, 2, 4, 4, 6, 6, 9, 9, 10, 10, 12, 12};//62617463
//        int[] ar2 = {0, 0, 2, 2, 4, 4, 6, 6, 9, 9, 11, 11, 13, 13};//125269879
////[2, 0, 2, 0, 2, 0, 2, 0, 0, 2, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
////[2, 0, 2, 0, 2, 0, 2, 0, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
//        System.out.println(Shape.shapeOfGroup(h34));
//        int[] analyse = AgariIndex.analyse(ar);
//        System.out.println(Arrays.toString(analyse) + AgariIndex.calc_key(analyse, new int[34]));
//        int[] analyse2 = AgariIndex.analyse(ar2);
//        System.out.println(Arrays.toString(analyse2) + AgariIndex.calc_key(analyse2, new int[34]));
    }
}
