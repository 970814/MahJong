package makeTable;

import ai2.EnumGroup;
import ai2.SevenPairs;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


/**
 *
 */
@SuppressWarnings("Duplicates")
public class Maker {
    private static final char[] array = "0123456789:;<=>?@ABCDEFGHIJKLMNOPQ".toCharArray();//不用O(字母O)，用小o代替，避免和0(零)很像

    static {
        try {
            String filename = "Compose";
            PrintStream stream = new PrintStream(new File(filename));
            System.setOut(stream);
            System.setErr(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }//输出重定向

    static HashSet<Integer> search() {
        HashSet<Integer> shapes = new HashSet<>();
        SevenPairs.addSevenPair(shapes);//先考虑7对
        byte[] h34 = new byte[34];
        newAlgorithms(shapes, h34);//考虑0顺+0刻
        for (byte s = 0; s < 34; s++) {
            h34[s] += 3;
            newAlgorithms(shapes, h34);//考虑0顺+1刻
            for (byte s2 = 0; s2 < 34; s2++)
                if (h34[s2] < 2) {
                    h34[s2] += 3;
                    newAlgorithms(shapes, h34);//考虑0顺+2刻
                    for (byte s3 = 0; s3 < 34; s3++)
                        if (h34[s3] < 2) {
                            h34[s3] += 3;
                            newAlgorithms(shapes, h34);//考虑0顺+3刻
                            for (byte s4 = 0; s4 < 34; s4++)
                                if (h34[s4] < 2) {
                                    h34[s4] += 3;
                                    newAlgorithms(shapes, h34);//考虑0顺+4刻
                                    h34[s4] -= 3;
                                }
                            h34[s3] -= 3;
                        }
                    h34[s2] -= 3;
                }
            h34[s] -= 3;
        }
        for (byte e = 0; e < 27; e++)
            if (e % 9 < 7) {
                for (int i = 0; i < 3; i++)
                    h34[e + i]++;
                newAlgorithms(shapes, h34);//考虑1顺+0刻
                /////////////////////////////////
                for (byte s = 0; s < 34; s++) {
                    h34[s] += 3;
                    newAlgorithms(shapes, h34);//考虑1顺+1刻
                    for (byte s2 = 0; s2 < 34; s2++)
                        if (h34[s2] < 2) {
                            h34[s2] += 3;
                            newAlgorithms(shapes, h34);//考虑1顺+2刻
                            for (byte s3 = 0; s3 < 34; s3++)
                                if (h34[s3] < 2) {
                                    h34[s3] += 3;
                                    newAlgorithms(shapes, h34);//考虑1顺+3刻
                                    h34[s3] -= 3;
                                }
                            h34[s2] -= 3;
                        }
                    h34[s] -= 3;
                }
                ///////////////////////////////
                for (int e2 = 0; e2 < 27; e2++)
                    if (e2 % 9 < 7) {
                        for (int i = 0; i < 3; i++)
                            h34[e2 + i]++;
                        newAlgorithms(shapes, h34);//考虑2顺+0刻
                        for (byte s = 0; s < 34; s++)
                            if (h34[s] < 2) {
                                h34[s] += 3;
                                newAlgorithms(shapes, h34);//考虑2顺+1刻
                                for (byte s2 = 0; s2 < 34; s2++)
                                    if (h34[s2] < 2) {
                                        h34[s2] += 3;
                                        newAlgorithms(shapes, h34);//考虑2顺+2刻
                                        h34[s2] -= 3;
                                    }
                                h34[s] -= 3;
                            }
                        for (int e3 = 0; e3 < 27; e3++)
                            if (e3 % 9 < 7) {
                                for (int i = 0; i < 3; i++)
                                    h34[e3 + i]++;
                                newAlgorithms(shapes, h34);//考虑3顺+0刻
                                for (byte s = 0; s < 34; s++)
                                    if (h34[s] < 2) {
                                        h34[s] += 3;
                                        newAlgorithms(shapes, h34);//考虑3顺+1刻
                                        h34[s] -= 3;
                                    }
                                for (int e4 = 0; e4 < 27; e4++)
                                    if (e4 % 9 < 7) {
                                        for (int i = 0; i < 3; i++)
                                            h34[e4 + i]++;
                                        newAlgorithms(shapes, h34);//考虑4顺+0刻
                                        for (int i = 0; i < 3; i++)
                                            h34[e4 + i]--;
                                    }
                                for (int i = 0; i < 3; i++)
                                    h34[e3 + i]--;
                            }
                        for (int i = 0; i < 3; i++)
                            h34[e2 + i]--;
                    }
                for (int i = 0; i < 3; i++)
                    h34[e + i]--;
            }
        //考虑7对
//        for (int p = 0; p < 34; p++) {
//            h34[p] += 2;
//            for (int p2 = 0; p2 < 34; p2++) {
//                h34[p2] += 2;
//                for (int p3 = 0; p3 < 34; p3++)
//                    if (h34[p3] < 3) {
//                        h34[p3] += 2;
//                        for (int p4 = 0; p4 < 34; p4++)
//                            if (h34[p4] < 3) {
//                                h34[p4] += 2;
//                                for (int p5 = 0; p5 < 34; p5++)
//                                    if (h34[p5] < 3) {
//                                        h34[p5] += 2;
//                                        for (int p6 = 0; p6 < 34; p6++)
//                                            if (h34[p6] < 3) {
//                                                h34[p6] += 2;
//                                                newAlgorithms(shapes, h34);
//                                                h34[p6] -= 2;
//                                            }
//                                        h34[p5] -= 2;
//                                    }
//                                h34[p4] -= 2;
//                            }
//                        h34[p3] -= 2;
//                    }
//                h34[p2] -= 2;
//            }
//            h34[p] -= 2;
//        }

        System.out.println(Arrays.toString(h34));
        System.out.printf("%-12s%10s%s%10s%s,\n", "sum:", shapes.size(), "(unique), ", count, "(produced)");
        return shapes;
    }
    public static void printTable(int shape, int[] search) {
        printTable(shape, search, false);
    }

    public static Printer printer;


    public static void printTable(int shape, int[] search, boolean isSevenPair) {
        if (!isSevenPair && search.length == 0) //如果不能胡牌直接异常
            throw new RuntimeException("array length is zero");
        StringBuilder sb = new StringBuilder("map.put(");
        sb.append("0x").append(Integer.toHexString(shape))
                .append(", new int[]{");
        //如果是7对子
        if (isSevenPair) sb.append("0x").append(Integer.toHexString(1 << 26)).append(", ");
        if (search.length > 0) {
            for (int x : search) sb.append("0x").append(Integer.toHexString(x)).append(", ");
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("})");
        printer.println(sb);
    }
    static int max = Integer.MIN_VALUE;//一副牌最多可以有多少种胡牌方法，经过检查这个值是4

    public static int[] search(byte[] h34) {
        for (byte i = 0, pre = 0; i < 34; i++)//给定一张牌，通过kh34能够瞬间找到它在手牌中的索引
            if (h34[i] > 0) {
                kh[i] = pre;
                pre += h34[i];
            }
        int[] m = new int[4];
        int n = 0;
        for (byte p = 0; p < 34; p++)
            if (h34[p] > 1) {//如果有2张以上，就可以选这个对子
                h34[p] -= 2;
                List<int[]> set = new ArrayList<>();
                EnumGroup.enumUnique(set, 0, msg, h34);//不能有对子
                if (set.size() > 0) {
                    for (int[] ints : set)
                        m[n++] = parse(p, ints);
//                    if (has) {
//                        System.out.println('[');
//                        has = false;
//                    }
//                    StringBuilder sb = new StringBuilder().append(kh[p]).append('{').append('\n');
//                    for (int[] ints : set) {
//                        for (int i = 0; i < ints[0]; i++) {
//                            int ch = ints[2 + i];
//                            ch = kh[ch];
//                            if (ch > 13) System.out.println("large than 13: " + Arrays.toString(kh));
//                            sb.append(ch)
//                                    .append(ch)
//                                    .append(ch)
//                                    .append(',');
//                        }
//                        for (int i = 0; i < ints[1]; i++) {
//                            int ch = ints[6 + i];
//                            ch = kh[ch];
//                            if (ch > 13) System.out.println("large than 13: " + Arrays.toString(kh));
//                            sb.append(ch).append(',');
//                        }
//                        sb.append('\n');
//                    }
//                    sb.append('}');
//                    System.out.println(sb);
                }
                h34[p] += 2;
            }
        return Arrays.copyOf(m, n);
    }

    private static int parse(int p, int[] msg) {
        int result = 0;//kh可以帮助寻找某张牌所在索引
        int offset = 0;
        result |= (kh[p] & 0b1111) << offset;//对子考虑最大值为13，二进制为0b1101,只需要4位
        offset += 4;
        result |= (msg[0] & 0b111) << offset;//刻子数或顺子数最多4副
        offset += 3;
        result |= (msg[1] & 0b111) << offset;//顺子数
        offset += 3;
        for (int i = 0; i < msg[0]; i++) {
            result |= (kh[msg[2 + i]] & 0b1111) << offset;//刻子的第一张牌的位置只要4位
            offset += 4;
        }
        for (int i = 0; i < msg[1]; i++) {
            result |= (kh[msg[6 + i]] & 0b1111) << offset;//顺子的第一张牌的位置只要4位
            offset += 4;
        }
        return result;
    }

    private static int count = 0;
    private static int[] msg = new int[10];
    private static byte[] kh = new byte[34];

    private static void newAlgorithms(HashSet<Integer> allSeries, byte[] h34) {
        for (byte p0 = 0; p0 < 34; p0++)
            if (h34[p0] < 3) {//如果少于3张牌，就可以选这个对子
                count++;
                h34[p0] += 2;
                int shape = Shape.shapeOfGroup(h34);
                if (allSeries.add(shape)) {//考虑的时候没有必要把对子放进去进行解析

                    int[] search = search(h34);
                    printTable(shape, search);
                    if (search.length == 0)
                        throw new RuntimeException(Arrays.toString(h34));
                }
                h34[p0] -= 2;
            }
    }




    public static void main(String[] args) throws FileNotFoundException {
        printer = new Printer("Map.java");
        long startTime = System.nanoTime();
        HashSet<Integer> strings = search();
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println(estimatedTime + " ns");//624403303543ns=624s=10.4min
        System.out.println("(?<=\\d)(?=(?:\\d{3})++\\b)");
        for (int i = 0; i < 34; i++) System.out.printf("%3s", i);
        System.out.println();
        for (int i = 0; i < 34; i++) System.out.printf("%3s", array[i]);
        System.out.println();
        PrintStream out = new PrintStream(new File("myTable"));
        for (Integer shape : strings) out.printf("%#x\n",shape);
        Toolkit.getDefaultToolkit().beep();
        System.out.println(max);
//        int[] ints = {4, 0, 1, 2, 3, 4, 0, 0, 1, 0};
//        System.out.println(parse(0, ints));
        printer.end();
    }
}

