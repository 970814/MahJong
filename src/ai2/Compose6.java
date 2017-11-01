package ai2;

import card.Group;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.List;

/**
 *
 */
@SuppressWarnings("Duplicates")
public class Compose6 {
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

    static HashSet<Integer> compose() {
        HashSet<Integer> shapes = new HashSet<>();
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
                                    h34[s3] += 3;//考虑1顺+3刻
                                    newAlgorithms(shapes, h34);
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
        SevenPairs.addSevenPair(shapes);
        System.out.println(Arrays.toString(h34));
        System.out.printf("%-12s%10s%s%10s%s,\n", "sum:", shapes.size(), "(unique), ", count, "(produced)");
        return shapes;
    }



    static int count = 0;

    private static void newAlgorithms(HashSet<Integer> allSeries, byte[] h34) {
        for (byte p = 0; p < 34; p++)
            if (h34[p] < 3) {//如果少于3张牌，就可以选这个对子
                h34[p] += 2;
                count++;
                int shape = Shape.shapeOfGroup(h34);
                allSeries.add(shape);
//                List<List<Group>> groups = EnumGroup.enumCommon(h34);
//                EnumGroup.unique(groups, groups);
                h34[p] -= 2;
            }
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.nanoTime();
        HashSet<Integer> strings = compose();
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
    }
}
