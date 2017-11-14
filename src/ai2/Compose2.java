package ai2;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

/**
 *
 */
@SuppressWarnings("Duplicates")
public class Compose2 {
    private static boolean[] test = new boolean[5];
    private static boolean useOld = false;
    private static boolean verifyValid = false;
    private static final char[] array = "0123456789:;<=>?@ABCDEFGHIJKLMNOPQ".toCharArray();//不用O(字母O)，用小o代替，避免和0(零)很像
    static {
        test[3] = true;
        useOld = false;
        verifyValid = false;
        String[] filenames = {
                "allSeries",
                "threeSeries",
                "twoSeries",
                "oneSeries",
                "allSames",
        };
        try {
            String filename = "Compose";
            for (int i = 0; i < test.length; i++)
                if (test[i]) {
                    //noinspection ConstantConditions
                    filename = filenames[i].concat(useOld ? ".old" : ".new");
                    break;
                }
            PrintStream stream = new PrintStream(new File(filename));
            System.setOut(stream);
            System.setErr(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }//输出重定向

    private static boolean isValidGroup(byte[] sortedGroup) {
//        return IntStream.range(4, sortedGroup.length).noneMatch(i -> sortedGroup[i] == sortedGroup[i - 4]);
        for (int i = sortedGroup.length - 1; i >= 4; i--)
            if (sortedGroup[i] == sortedGroup[i - 4])
                return false;
        return true;
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
                        if (test[0])//如果为真将测试这个组合
                            for (byte p = 0; p < 34; p++)//考虑4顺
                                if (!deserted[3].contains(p)) {//不能存在相应的对子
                                    count[0]++;
                                    if (useOld) {
                                        byte[] pendingGroup = {//全顺0刻
                                                indexes[0], (byte) (indexes[0] + 1), (byte) (indexes[0] + 2),
                                                indexes[1], (byte) (indexes[1] + 1), (byte) (indexes[1] + 2),
                                                indexes[2], (byte) (indexes[2] + 1), (byte) (indexes[2] + 2),
                                                indexes[3], (byte) (indexes[3] + 1), (byte) (indexes[3] + 2),
                                                p, p,
                                        };
                                        Arrays.sort(pendingGroup);
                                        if (!verifyValid) allSeries.add(byteToString(pendingGroup));
                                        else if (!isValidGroup(pendingGroup)) System.exit(-13);
                                    } else newAlgorithms(allSeries, group, h34, p);//使用新的算法生成一副牌
                                }
                        //相应的这层如果是4张牌变成3张，则不会还原数据
                        removeLast(h34, deserted, indexes[3], true);
                    }
                    if (test[1])//如果为真将测试这个组合
                        for (byte s = 0; s < 34; s++)
                            if (!deserted[2].contains(s)) {
                                addLast(h34, deserted, s, false);
                                for (byte p = 0; p < 34; p++)//考虑3顺+1刻
                                    if (!deserted[3].contains(p)) {
                                        count[1]++;
                                        if (useOld) {
                                            byte[] pendingGroup = {
                                                    s, s, s,//一刻
                                                    indexes[0], (byte) (indexes[0] + 1), (byte) (indexes[0] + 2),
                                                    indexes[1], (byte) (indexes[1] + 1), (byte) (indexes[1] + 2),
                                                    indexes[2], (byte) (indexes[2] + 1), (byte) (indexes[2] + 2),
                                                    p, p,
                                            };
                                            Arrays.sort(pendingGroup);
                                            if (!verifyValid) threeSeries.add(byteToString(pendingGroup));
                                            else if (!isValidGroup(pendingGroup)) System.exit(-13);
                                        } else newAlgorithms(threeSeries, group, h34, p);
                                    }
                                removeLast(h34, deserted, s, false);
                            }
                    removeLast(h34, deserted, indexes[2], true);
                }
                if (test[2])//如果为真将测试这个组合
                    for (byte s = 0; s < 34; s++)
                        if (!deserted[2].contains(s)) {
                            addLast(h34, deserted, s, false);
                            for (byte s2 = 0; s2 < 34; s2++)
                                if (!deserted[2].contains(s2)) {
                                    addLast(h34, deserted, s2, false); //考虑2顺+2刻
                                    for (byte p = 0; p < 34; p++)
                                        if (!deserted[3].contains(p)) {
                                            count[2]++;
                                            if (useOld) {
                                                byte[] pendingGroup = {
                                                        s, s, s,
                                                        s2, s2, s2,//二刻
                                                        indexes[0], (byte) (indexes[0] + 1), (byte) (indexes[0] + 2),
                                                        indexes[1], (byte) (indexes[1] + 1), (byte) (indexes[1] + 2),
                                                        p, p,
                                                };
                                                Arrays.sort(pendingGroup);
                                                if (!verifyValid) twoSeries.add(byteToString(pendingGroup));
                                                else if (!isValidGroup(pendingGroup)) System.exit(-13);
                                            } else newAlgorithms(twoSeries, group, h34, p);
                                        }
                                    removeLast(h34, deserted, s2, false);
                                }
                            removeLast(h34, deserted, s, false);
                        }
                removeLast(h34, deserted, indexes[1], true);
            }
            if (test[3])//如果为真将测试这个组合
                for (byte s = 0; s < 34; s++) {
                    addLast(h34, deserted, s, false);
                    for (byte s2 = 0; s2 < 34; s2++)
                        if (s2 != s) {
                            addLast(h34, deserted, s2, false);
                            for (byte s3 = 0; s3 < 34; s3++)
                                if (s3 != s && s3 != s2) {
                                    addLast(h34, deserted, s3, false);//考虑1顺+3刻
                                    for (byte p = 0; p < 34; p++)
                                        if (!deserted[3].contains(p)) {
                                            count[3]++;
                                            if (useOld) {
                                                byte[] pendingGroup = {
                                                        s, s, s,
                                                        s2, s2, s2,
                                                        s3, s3, s3,//三刻
                                                        indexes[0], (byte) (indexes[0] + 1), (byte) (indexes[0] + 2),
                                                        p, p,
                                                };
                                                Arrays.sort(pendingGroup);
                                                if (!verifyValid) oneSeries.add(byteToString(pendingGroup));
                                                else if (!isValidGroup(pendingGroup)) System.exit(-13);
                                            } else newAlgorithms(oneSeries, group, h34, p);
                                        }
                                    removeLast(h34, deserted, s3, false);
                                }
                            removeLast(h34, deserted, s2, false);
                        }
                    removeLast(h34, deserted, s, false);
                }
            for (int i = 0; i < 3; i++)
                h34[indexes[0] + i]--;
        }
        if (test[4])//如果为真将测试这个组合
            for (byte s = 0; s < 34; s++)
                for (byte s2 = 0; s2 < 34; s2++)
                    if (s2 != s)
                        for (byte s3 = 0; s3 < 34; s3++)
                            if (s3 != s && s3 != s2)
                                for (byte s4 = 0; s4 < 34; s4++)
                                    if (s4 != s && s4 != s2 && s4 != s3)
                                        for (byte p = 0; p < 34; p++)
                                            if (p != s && p != s2 && p != s3 && p != s4) {//考虑全刻
                                                count[4]++;
                                                if (useOld) {
                                                    byte[] pendingGroup = {
                                                            s, s, s,
                                                            s2, s2, s2,
                                                            s3, s3, s3,
                                                            s4, s4, s4,//四刻
                                                            p, p,
                                                    };
                                                    Arrays.sort(pendingGroup);
                                                    if (!verifyValid) allSames.add(byteToString(pendingGroup));
                                                    else if (!isValidGroup(pendingGroup)) System.exit(-13);
                                                } else newAlgorithms(allSames, group, h34, p);
                                            }
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
        if (allSeries.size() > 0) return allSeries;
        else if (threeSeries.size() > 0) return threeSeries;
        else if (twoSeries.size() > 0) return twoSeries;
        else if (oneSeries.size() > 0) return oneSeries;
        else return allSames;
    }

    private static void newAlgorithms(HashSet<String> allSeries, char[] group, int[] h34, byte p) {

//        h34[p] += 2;
//        for (int i = 0, n = 0; i < 34; i++) //traverse h34
//            for (int j = h34[i]; j > 0; j--)
//                group[n++] = array[i];
//        allSeries.addGang(new String(group));
//        h34[p] -= 2;
    }

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

    private static String byteToString(byte[] pendingGroup) {
        StringBuilder builder = new StringBuilder(pendingGroup.length);
        for (int i = 0; i < pendingGroup.length; i++) builder.append(array[pendingGroup[i]]);
        return builder.toString();
    }

    public static void main(String[] args) {
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
