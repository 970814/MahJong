package ai2;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class Compose {
    static {
        try {
            PrintStream stream = new PrintStream(new File(".\\compose"));
            System.setOut(stream);
            System.setErr(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }//输出重定向
    final static byte[] keyType = new byte[34];

    private static boolean isValidGroup(byte[] sortedGroup) {
//        return IntStream.range(4, sortedGroup.length).noneMatch(i -> sortedGroup[i] == sortedGroup[i - 4]);
        for (int i = sortedGroup.length - 1; i >= 4; i--)
            if (sortedGroup[i] == sortedGroup[i - 4])
                return false;
        return true;
    }

    private static byte[][] searchPairs() {
        byte[][] pairs = new byte[34][];
        for (byte key = 0; key < pairs.length; key++)
            pairs[key] = new byte[]{key, key};
        return pairs;
    }

    private static byte[][] searchGroups() {
        byte types = 34 + (9 - 2) * 3;
        byte[][] groups = Arrays.copyOf(searchSame(), types);
        System.arraycopy(searchSeries(), 0, groups, 34, 21);
        return groups;
    }

    private static byte[][] searchSame() {
        byte[][] sames = new byte[34][];
        for (byte key = 0; key < sames.length; key++)
            sames[key] = new byte[]{key, key, key};
        return sames;
    }

    private static byte[][] searchSeries() {
        byte[][] bounds = {
                {0, 7,},
                {9, 16,},
                {18, 25,},
        };
        byte[][] series = new byte[(9 - 2) * 3][];
        int from = 0;
        for (byte[] bound : bounds)
            for (byte key = bound[0]; key < bound[1]; key++)
                series[from++] = new byte[]{key, (byte) (key + 1), (byte) (key + 2)};
        return series;
    }

//        private static class HashKey {
//        byte[] groups;
//        long keyA;
//        long keyB;
//
//        public HashKey(byte[] groups) {
//            this.groups = groups;
////            for (int i = 0; i < 7; i++) {
////                keyA &= groups[i] << (i << 3);
////                keyB &= groups[7 + i] << (i << 3);
////            }
//        }
//
//        @Override
//        public int hashCode() {
//            return keyA;
//        }
//    }//8 + 6

    private static HashSet<String> compose() {
        HashSet<String> validGroups = new HashSet<>();
        byte[][] pairs = searchPairs();
        byte[][] groups = searchGroups();
        for (byte[] pair : pairs)
            for (byte[] g : groups)
                for (byte[] g2 : groups)
                    for (byte[] g3 : groups)
                        for (byte[] g4 : groups) {
                            byte[] pendingGroup = Arrays.copyOf(pair, 14);
                            System.arraycopy(g, 0, pendingGroup, 2, 3);
                            System.arraycopy(g2, 0, pendingGroup, 5, 3);
                            System.arraycopy(g3, 0, pendingGroup, 8, 3);
                            System.arraycopy(g4, 0, pendingGroup, 11, 3);
                            Arrays.sort(pendingGroup);
                            if (isValidGroup(pendingGroup)
                                    && validGroups.add(byteToString(pendingGroup)))
//                                System.out.println(Arrays.toString(pendingGroup));
                                ;
                        }
        return validGroups;
    }

    private static byte convert(int e) {
        return (byte) (e % 7 + e / 7 * 9);
    }

//    static HashSet<String> compose2() {
//        HashSet<String> validGroups = new HashSet<>();
//        byte[][] pairs = searchPairs();
//        byte[][] sames = searchSame();
//        byte[][] series = searchSeries();
//        int[] h4 = new int[21];
//        //考虑全顺子
//        for (int e = 0; e < 21; e++) {
//            h4[e]++;
//            for (int e2 = 0; e2 < 21; e2++) {
//                h4[e2]++;
//                for (int e3 = 0; e3 < 21; e3++) {
//                    h4[e3]++;
//                    for (int e4 = 0; e4 < 21; e4++) {
//                        h4[e4]++;
//                        int[] indexes = {e, e2, e3, e4};
//                        Arrays.sort(indexes);
//                        int[][] ks = classify(h4, indexes);//ks[i]为重复出现i次的所有不同顺子集合
//                        int vk = getMaxIndex(h4, indexes);//重复最多次数的key
//                        int k = convert(vk);
//                        for (int p = 0; p < 34; p++) {
//                            switch (h4[vk]) {//重复次数
//                                case 4:
//                                    if (p != k
//                                            && p != k + 1
//                                            && p != k + 2) {//4顺不能存在相应的对子
//                                        //考虑这个组合series[k,k,k,k,]+pairs[p]
//                                    }
//                                case 3:
//                                    if (p != k
//                                            && p != k + 1
//                                            && p != k + 2) {//3顺不能存在相应的对子
//                                        //考虑这个组合series[k,k,k,ks[1][0],]+pairs[p]
//                                    }
//                                case 2:
//                                    List<Integer> list = new LinkedList<>();//重叠的牌
//                                    if (ks[2].length == 2) {//两种顺子都重复2次
//                                        int diff = ks[2][1] - ks[2][0];
//                                        int count = 3 - Math.abs(diff);//重叠次数，不存在相应对子
//                                        if (count > 0) {
//                                            list.addGang(diff > 0 ? ks[2][0] + 2 : ks[2][0]);//考虑两端重叠的部分
//                                            if (count == 2) list.addGang(ks[2][0] + 1);
//                                        }
//                                    } else {//1种顺子重复2次+2种各出现一次的顺子
//                                        boolean[] h = new boolean[21];
//                                        for (int k2 : ks[1])
//                                            for (int i = 0; i < 3; i++)
//                                                h[k2 + i] = true;//覆盖区域
//                                        for (int i = 0; i < 3; i++)
//                                            if (h[ks[2][0] + i])//如果这些区域被覆盖了，那么则这段区域不能存在相应对子
//                                                list.addGang(ks[2][0] + i);
//                                    }
//                                    for (Integer x : list)
//                                        if (p != x) {
//                                            //考虑这个组合series[convert(indexes)]+pairs[p]
//                                        }
//                                case 1:
//
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }

    static HashSet<String> compose2() {
        HashSet<String> allSeries = new HashSet<>();
        HashSet<String> threeSeries = new HashSet<>();
        HashSet<String> twoSeries = new HashSet<>();
        HashSet<String> oneSeries = new HashSet<>();
        HashSet<String> allSames = new HashSet<>();

        int[] h34 = new int[34];
        byte[] indexes = new byte[4];
        LinkedList[] deserted = {
                new LinkedList(),
                new LinkedList(),
                new LinkedList(),
                new LinkedList(),
        };//deserted[i]用在第i-1层循环
        final char[] group = new char[14];
        int[] count = new int[5];
        //考虑全顺子
        for (int e = 0; e < 21; e++) {
            indexes[0] = convert(e);//备份并且还原值
            for (int i = 0; i < 3; i++)
                h34[indexes[0] + i]++;
            for (int e2 = 0; e2 < 21; e2++) {
                indexes[1] = convert(e2);
                addLast(h34, deserted, indexes[1], true);
                for (int e3 = 0; e3 < 21; e3++) {
                    indexes[2] = convert(e3);
                    addLast(h34, deserted, indexes[2], true);
                    for (int e4 = 0; e4 < 21; e4++) {
                        indexes[3] = convert(e4); //可能在上层满足3张，在这一层不会重复添加这个牌
                        addLast(h34, deserted, indexes[3], true);
                        for (byte p = 0; p < 34; p++)//考虑4顺
                            if (!deserted[3].contains(p)) {//不能存在相应的对子
                                count[0]++;
                                byte[] pendingGroup = {//全顺
                                        indexes[0], (byte) (indexes[0] + 1), (byte) (indexes[0] + 2),
                                        indexes[1], (byte) (indexes[1] + 1), (byte) (indexes[1] + 2),
                                        indexes[2], (byte) (indexes[2] + 1), (byte) (indexes[2] + 2),
                                        indexes[3], (byte) (indexes[3] + 1), (byte) (indexes[3] + 2),
                                        p, p,
                                };
                                group[0] = array[indexes[0]];
                                group[0] = array[indexes[0] + 1];
                                group[0] = array[indexes[0] + 2];
                                group[0] = array[indexes[0]];
                                group[0] = array[indexes[0] + 1];
                                group[0] = array[indexes[0] + 2];
                                Arrays.sort(pendingGroup);
                                allSeries.add(byteToString(pendingGroup));
//                                if (!isValidGroup(pendingGroup)) System.exit(1);
                            }
                        //相应的这层如果是4张牌变成3张，则不会还原数据
                        removeLast(h34, deserted, indexes[3], true);
                    }
//                    for (byte s = 0; s < 34; s++)
//                        if (!deserted[2].contains(s)) {
//                            addLast(h34, deserted, s, false);
//                            for (byte p = 0; p < 34; p++)//考虑3顺+1刻
//                                if (!deserted[3].contains(p)) {
//                                    count[1]++;
//                                    byte[] pendingGroup = {
//                                            s, s, s,//一刻
//                                            indexes[0], (byte) (indexes[0] + 1), (byte) (indexes[0] + 2),
//                                            indexes[1], (byte) (indexes[1] + 1), (byte) (indexes[1] + 2),
//                                            indexes[2], (byte) (indexes[2] + 1), (byte) (indexes[2] + 2),
//                                            p, p,
//                                    };
//                                    Arrays.sort(pendingGroup);
//                                    threeSeries.addGang(byteToString(pendingGroup).intern());
////                                    if (!isValidGroup(pendingGroup)) {
////                                        System.exit(1);
////                                    }
//                                }
//                            removeLast(h34, deserted, s, false);
//                        }
                    removeLast(h34, deserted, indexes[2], true);
                }
//                for (byte s = 0; s < 34; s++)
//                    if (!deserted[2].contains(s)) {
//                        addLast(h34, deserted, s, false);
//                        for (byte s2 = 0; s2 < 34; s2++)
//                            if (!deserted[2].contains(s2)) {
//                                addLast(h34, deserted, s2, false); //考虑2顺+2刻
//                                for (byte p = 0; p < 34; p++)
//                                    if (!deserted[3].contains(p)) {
//                                        count[2]++;
//                                        byte[] pendingGroup = {
//                                                s, s, s,
//                                                s2, s2, s2,//二刻
//                                                indexes[0], (byte) (indexes[0] + 1), (byte) (indexes[0] + 2),
//                                                indexes[1], (byte) (indexes[1] + 1), (byte) (indexes[1] + 2),
//                                                p, p,
//                                        };
//                                        Arrays.sort(pendingGroup);
//                                        twoSeries.addGang(byteToString(pendingGroup).intern());
////                                        if (!isValidGroup(pendingGroup)) {
////                                            System.exit(1);
////                                        }
//                                    }
//                                removeLast(h34, deserted, s2, false);
//                            }
//                        removeLast(h34, deserted, s, false);
//                    }
                removeLast(h34, deserted, indexes[1], true);
            }
//            for (byte s = 0; s < 34; s++) {
//                addLast(h34, deserted, s, false);
//                for (byte s2 = 0; s2 < 34; s2++)
//                    if (s2 != s) {
//                        addLast(h34, deserted, s2, false);
//                        for (byte s3 = 0; s3 < 34; s3++)
//                            if (s3 != s && s3 != s2) {
//                                addLast(h34, deserted, s3, false);//考虑1顺+3刻
//                                for (byte p = 0; p < 34; p++)
//                                    if (!deserted[3].contains(p)) {
//                                        count[3]++;
//                                        byte[] pendingGroup = {
//                                                s, s, s,
//                                                s2, s2, s2,
//                                                s3, s3, s3,//三刻
//                                                indexes[0], (byte) (indexes[0] + 1), (byte) (indexes[0] + 2),
//                                                p, p,
//                                        };
//                                        Arrays.sort(pendingGroup);
//                                        oneSeries.addGang(byteToString(pendingGroup).intern());
////                                        if (!isValidGroup(pendingGroup)) {
////                                            System.exit(1);
////                                        }
//                                    }
//                                removeLast(h34, deserted, s3, false);
//                            }
//                        removeLast(h34, deserted, s2, false);
//                    }
//                removeLast(h34, deserted, s, false);
//            }
            for (int i = 0; i < 3; i++)
                h34[indexes[0] + i]--;
        }
//        for (byte s = 0; s < 34; s++)
//            for (byte s2 = 0; s2 < 34; s2++)
//                if (s2 != s)
//                    for (byte s3 = 0; s3 < 34; s3++)
//                        if (s3 != s && s3 != s2)
//                            for (byte s4 = 0; s4 < 34; s4++)
//                                if (s4 != s && s4 != s2 && s4 != s3)
//                                    for (byte p = 0; p < 34; p++)
//                                        if (p != s && p != s2 && p != s3 && p != s4) {//考虑全刻
//                                            count[4]++;
//                                            byte[] pendingGroup = {
//                                                    s, s, s,
//                                                    s2, s2, s2,
//                                                    s3, s3, s3,
//                                                    s4, s4, s4,//四刻
//                                                    p, p,
//                                            };
//                                            Arrays.sort(pendingGroup);
//                                            allSames.addGang(byteToString(pendingGroup).intern());
////                                            if (!isValidGroup(pendingGroup)) {
////                                                System.exit(1);
////                                            }
//                                        }
        System.out.println(Arrays.toString(h34));
        System.out.println(Arrays.toString(indexes));
        System.out.println(Arrays.toString(deserted));
        Object[] tips = {
                "allSeries:", allSeries.size(), count[0],
                "threeSeries:", threeSeries.size(), count[1],
                "twoSeries:", twoSeries.size(), count[2],
                "oneSeries:", oneSeries.size(), count[3],
                "allSames:", allSames.size(), count[4],
        };
        int unique = 0;
        int produced = 0;
        final String format = "%-12s%10s%s%10s%s,\n";
        for (int i = 0; i < tips.length; i += 3) {
            System.out.printf(format, tips[i], tips[i + 1], "(unique), ", tips[i + 2], "(produced)");
            unique += (int) tips[i + 1];
            produced += (int) tips[i + 2];
        }
        System.out.printf(format, "sum:", unique, "(unique), ", produced, "(produced)");
        return allSeries;
    }

    //    private static void removeLast(int[] h21, LinkedList<Integer>[] deserted, int firstKey) {
//        for (int i = 2; i >= 0; i--)//统计出现了i次的牌分别添加到deserted[i]中
//            switch (h21[firstKey + i]--) {//当一张牌出现n次及以上的时候，只会统计一次相应的牌
//                case 3:
//                    deserted[3].removeLast();
//                    break;
//            }
//    }
    private static void removeLast(int[] h34, LinkedList[] deserted, int firstKey, boolean series) {
        for (int i = 2; i >= 0; i--)//addLast的还原过程，
            switch (h34[firstKey + (series ? i : 0)]--) {
                case 3:
                    deserted[3].removeLast();
                    break;
                case 2:
                    deserted[2].removeLast();
            }
    }

    private static void addLast(int[] h34, LinkedList[] deserted, int firstKey, boolean series) {
        for (int i = 0; i < 3; i++) {//统计出现了i次的牌分别添加到deserted[i]中
            byte key = (byte) (firstKey + (series ? i : 0));
            switch (++h34[key]) {//当一张牌出现n次及以上的时候，只会统计一次相应的牌
                case 3:
                    deserted[3].addLast(key);
                    break;
                case 2:
                    deserted[2].addLast(key);
                    break;
            }
        }
    }

    private static int find(int[] h4, int[] indexes, int i) {
        return indexes[0];
    }

    private static int[][] classify(int[] h4, int... indexes) {//返回一个数组ks，使得ks[i]为重复出现i次的所有不同顺子
        for (int index : indexes) h4[index]++;
        int[] sizes = new int[5];//sizes[i]的值为重复出现i次的顺子的不同顺子种类的个数
        for (int v : h4)
            if (v > 0)
                sizes[v]++;
        int[][] ks = new int[5][];
        for (int i = 0; i < ks.length; i++)
            ks[i] = new int[sizes[i]];
        for (int i = 1; i < ks.length; i++)
            for (int j = 0; j < ks[i].length; )
                for (int k = 0; k < h4.length; k++)
                    if (h4[k] == i)
                        ks[i][j++] = convert(k);
        return ks;
    }

    private static int getMaxIndex(int[] h4, int... indexes) {
        int maxIndex = indexes[0];
        for (int i = 1; i < indexes.length; i++)
            if (h4[indexes[i]] > h4[maxIndex])
                maxIndex = indexes[i];
        return maxIndex;
    }

//    private static final char[] array = "0123456789ABCDEFGHIJKLMNoPQRSTUVWXYZ".toCharArray();//不用O(字母O)，用小o代替，避免和0(零)很像
    private static final char[] array = "0123456789:;<=>?@ABCDEFGHIJKLMNOPQ".toCharArray();//不用O(字母O)，用小o代替，避免和0(零)很像
    private static String byteToString(byte[] pendingGroup) {
        StringBuilder builder = new StringBuilder(pendingGroup.length);
        for (int i = 0; i < pendingGroup.length; i++) builder.append(array[pendingGroup[i]]);
        return builder.toString();
    }

    public static void main(String[] args) {
//        long startTime = System.nanoTime();
//        HashSet<String> set = compose();
//        long estimatedTime = System.nanoTime() - startTime;
//        System.out.println(estimatedTime + " ns");//624403303543ns=624s=10.4min
//        System.out.println(set);
//        System.out.println(set.size());//11498658
//        System.out.println(Arrays.deepToString(searchGroups()));
//        int[][] array = classify(new int[21], 1, 2, 1, 1, 1, 2, 2, 4, 5, 5, 3, 8, 9, 0, 11, 11, 12, 12, 11, 6, 6, 15, 15, 15, 15);
//        for (int i = 0; i < array.length; i++)
//            if (array[i].length > 0)
//                System.out.println("重复出现" + i + "次的顺子:" + Arrays.toString(array[i]));
        long startTime = System.nanoTime();
        HashSet<String> strings = compose2();
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println(estimatedTime + " ns");//624403303543ns=624s=10.4min
        System.out.println("(?<=\\d)(?=(?:\\d{3})++\\b)");
        for (int i = 0; i < 34; i++) System.out.printf("%3s", i);
        System.out.println();
        for (int i = 0; i < 34; i++) System.out.printf("%3s", array[i]);
        System.out.println();
        for (String shape : strings) System.out.println(shape);
        Toolkit.getDefaultToolkit().beep();
    }
}
