package ai;

import ai2.EnumGroup;
import algorithm.Algorithm;
import algorithm.AlgorithmImp;
import card.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static card.Constant.*;

public class Judge {

    public void common(Player player) {
//        common(player.map.clone(), map -> false);
    }

    public static class Combination {
        int key;//对子
        List<List<Group>> combination;

        public Combination(int key, List<List<Group>> combination) {
            this.key = key;
            this.combination = combination;
        }

        private static Matcher find = Pattern.compile("\\d:\\{}").matcher("");
        private static Matcher insert = Pattern.compile("(?<=[{}])").matcher("");

        @Override
        public String toString() {
            StringBuilder toString = new StringBuilder(String.valueOf(key));
            toString.append(":{");
            for (List<Group> groups : combination) toString.append('\t').append(groups).append('\n');
            toString.append("}");
            return find.reset(toString).matches()
                    ?
                    toString.toString()
                    :
                    insert.reset(toString).replaceAll("\n");
        }
    }

    //O(n)
    public List<Combination> enumCommon(int[] map, Algorithm<int[], List<List<Group>>> algorithm) {//抽将，剩下的牌由自定义算法决定
        List<Combination> combinations = new LinkedList<>();
        for (int key = map.length - 1; key >= 0; key--)
            if (map[key] >= 2) {
                map[key] -= 2;
                List<List<Group>> combination = algorithm.search(map);
                if (!combination.isEmpty())
                    combinations.add(new Combination(key, combination));
                map[key] += 2;
            }
        return combinations;
    }
    public boolean common(int[] map, Algorithm<int[], Boolean> algorithm) {//抽将，剩下的牌由自定义算法决定
        for (int i = map.length - 1; i >= 0; i--)
            if (map[i] >= 2) {
                map[i] -= 2;
                boolean find = algorithm.search(map);
                map[i] += 2;
                if (find) return true;
                //groups.clear();
            }
        return false;
    }

    public static void main(String[] args) {
        int[] array;
//        array = new int[]{1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5};
//        array = new int[]{1, 2, 3, 1, 2, 3, 2, 2, 4, 4, 4, 5, 5, 5};
//        array = new int[]{1, 1, 2, 2, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8};//不行
//        array = new int[]{1, 1, 2, 2, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5};//为什么可以
        array = new int[]{1, 1, 1, 2, 3, 3, 4, 4, 4, 5, 5, 6, 8, 8};//111,234,345,456
//        array = new int[]{1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9};//不能胡，因为9是1索
//        array = new int[]{0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8};
//        array = new int[]{1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 5};
        array = new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7};//麻7
        array = new int[]{0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4,};//4个胡
//        array = new int[]{1, 1, 3, 3, 5, 5, 7, 7, 9, 9, 11, 11, 13, 13};//麻7
//        array = new int[]{1, 1, 1, 1, 2, 3, 4, 4, 4, 4, 5, 6, 8, 8};//可能会照成4中不同排列的同一个组合
//        array = new int[]{1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 8, 8};//
//        array = new int[]{0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8};//
//        array = new int[]{0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 8, 8};//同一个将，但有不同牌型
//        array = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6,
//                7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16, 16, 17, 17,
//                18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25, 26, 26,
//                27, 27, 27, 28, 28, 28, 29, 29, 29, 30, 30};
        int k = CharacterOffset;
//        array = new int[]{1, 2, 3, 1, 2, 3, 2, 3, 3, 4, 4, 4, k, k, k};
        int[] map = new int[differentKeyCount()];
        byte[] h34 = new byte[34];
        for (int x : array) {
            map[x]++;
            h34[x]++;
        }
        System.out.println(Arrays.toString(map));
        List<Group> groups = new ArrayList<>();
        System.out.println(new Judge().common(map, m -> AlgorithmImp.common(m, groups)));
        System.out.println(groups);
//        System.out.println();
        System.out.println(Arrays.toString(map));
        System.out.println(new Judge().enumCommon(map, AlgorithmImp::enumCommon));//O(n^2)
        System.out.println(Arrays.toString(map));


        newAlgorithms(h34);
    }

    private static void show(List<Group> groups) {
        for (Group group : groups) System.out.print(group + ", ");
        System.out.println();
    }

    private static void newAlgorithms(byte[] h34) {
        byte[] kh = new byte[34];
//        Arrays.fill(kh, (byte) -1);
        for (byte i = 0, pre = 0; i < h34.length; i++)
            if (h34[i] > 0) {
                kh[i] = pre;
                pre += h34[i];
            }
        int[] msg = new int[10];
        for (byte p = 0; p < 34; p++) {
            if (h34[p] > 1) {//如果少于3张牌，就可以选这个对子
                h34[p] -= 2;
                List<int[]> set = new ArrayList<>();
                EnumGroup.enumUnique(set, 0,  msg, h34);
                if (set.size() > 0) System.out.println("pair: " + p);
                for (int[] ints : set) {
                    System.out.println(Arrays.toString(ints));
                    for (int i = 0; i < ints[0]; i++) {
                        Group.Same same = new Group.Same(ints[2 + i]);
                        System.out.print(same + "(" + kh[ints[2 + i]] + "), ");
                    }
                    for (int i = 0; i < ints[1]; i++) {
                        Group.Series series = new Group.Series(ints[6 + i]);
                        System.out.print(series + "(" + kh[ints[6 + i]] + "), ");
                    }
                    System.out.println();
                }
                h34[p] += 2;
            }
        }
    }
}
