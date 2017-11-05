package other;

import ai2.Shape;
import card.Constant;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class AgariIndex {
    static final int[] n_zero = new int[34];
    static final TreeMap<Integer, int[]> tbl = new TreeMap<>();

    static {
        Map.init(tbl);
    }

    // 牌の種類ごとの個数を求める
    public static int[] analyse(int[] hai) {
        int[] n = n_zero.clone();
        for (int i : hai) {
            n[i]++;
        }
        return n;
    }


    public static int[] agari(int key) {
        return tbl.get(key);
    }

    public static void main(String[] args) {
        int[] hai = {
                0, 0, 0,
                1, 2, 3,
                5, 6, 7,
                27, 27, 27,
                30, 30};

//        hai = new int[]{0, 0, 0, 2, 3, 4, 6, 7, 8, 10, 10, 10, 12, 12};
        hai = new int[]{0, 1, 2, 9, 10, 11, 12, 13, 14, 15, 16, 16, 16, 17};
        hai = new int[]{1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 6, 6};//15711727: 111011111011110111101111
        hai = new int[]{1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5};//7847407:   11101111011110111101111
        hai = new int[]{0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8};
        hai = new int[]{0, 0, 1, 1, 3, 3, 5, 5, 6, 6, 8, 8, 10, 10,};//31316923
        hai = new int[]{0, 0, 1, 1, 3, 3, 5, 5, 9, 9, 10, 10, 11, 11};//15580091
//        hai = new int[]{0,0,1,1,2,2,4,4,6,6,9,9,10,10,};//15588827   [2, 2, 2, 0, 2, 0, 2, 0, 0, 2, 2,
        hai = new int[]{0,0,1,1,2,2,4,4,6,6,9,9,11,11,};//31317467 [2, 2, 2, 0, 2, 0, 2, 0, 0, 2, 0, 2,
        hai = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 3, 4, 5, 6, 6, 6};//31317467 [2, 2, 2, 0, 2, 0, 2, 0, 0, 2, 0, 2,
        hai = new int[]{0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4,};//4个胡
//        2202022022

//        0x1f1edb
//        0x1dddbbb
//        0xedbbbb

        int[] pos = new int[14];
        int[] n = analyse(hai);
        int x = calc_key(n, pos);
        byte[] group = new byte[34];
        for (int z : hai) group[z]++;

        int[] ret = agari(x);
        System.out.println(x + ": " + Integer.toBinaryString(x));
        System.out.println(Arrays.toString(group));
        int shape = Shape.shapeOfGroup(group);
        System.out.println(Arrays.toString(group));
        System.out.println(shape + ": " + Integer.toBinaryString(shape));
//        0b10111100000000100
//          10111100000000100
        for (int r : ret) {
            System.out.println(r);
            int pairIndex = (r >> 6) & 0xF;//4
            System.out.println("pair=" + Constant.get(pos[pairIndex]));
            int num_kotsu = r & 0x7;
            int num_shuntsu = (r >> 3) & 0x7;
            for (int i = 0; i < num_kotsu; i++)
                System.out.println("same=" + Constant.get(pos[(r >> (10 + i * 4)) & 0xF]));
            for (int i = 0; i < num_shuntsu; i++)
                System.out.println("series=" + Constant.get(pos[(r >> (10 + num_kotsu * 4 + i * 4)) & 0xF]));
        }
    }
    {
//        int a = 0b1111_1111_1111_1111___1111__         111             111
        //          4       就       牌    对子          顺子数       刻子数
    }

   public static int calc_key(int[] n, int[] pos) {
        int p = -1;
        int x = 0;
        int pos_p = 0; // posの配列インデックス
        boolean b = false; // ひとつ前が0以外
        // 数牌
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int key = i * 9 + j;
                if (n[key] == 0) {
                    if (b) {
                        b = false;
                        x |= 0x1 << p;
                        p++;
                    }
                } else {
                    p++;
                    b = true;
                    pos[pos_p++] = key;
                    switch (n[key]) {
                        case 2:
                            x |= 0b11 << p;
                            p += 2;
                            break;
                        case 3:
                            x |= 0b1111 << p;
                            p += 4;
                            break;
                        case 4:
                            x |= 0b111111 << p;
                            p += 6;
                            break;
                    }
                }
            }
            if (b) {
                b = false;
                x |= 0x1 << p;
                p++;
            }
        }
        // 字牌
        for (int i = 27; i <= 33; i++) {
            if (n[i] > 0) {
                p++;
                pos[pos_p++] = i;
                switch (n[i]) {
                    case 2:
                        x |= 0x3 << p;
                        p += 2;
                        break;
                    case 3:
                        x |= 0xF << p;
                        p += 4;
                        break;
                    case 4:
                        x |= 0x3F << p;
                        p += 6;
                        break;
                }
                x |= 0x1 << p;
                p++;
            }
        }
        return x;

    }

}