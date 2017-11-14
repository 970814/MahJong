package algorithm;

import card.Group;
import card.KeyAnalyzer;

import java.util.*;

import static card.Constant.CharacterOffset;
@SuppressWarnings("Duplicates")
public class AlgorithmImp {
    public static boolean common(int[] map, List<Group> groups) {//贪心算法
        return common(map, 0, groups);
    }
    public static List<List<Group>> enumCommon(int[] map) {
        List<List<Group>> combination = new LinkedList<>();
        enumCommon(map, 0, new Stack<>(), combination);
        return combination;
    }

    //枚举出所有的可能，复杂度T(n) = 2T(n-1) = O(2^n) = O(2^4 = n) = O(n)
    //1, 1, 1, 1, 2, 3, 4, 4, 4, 4, 5, 6 出现4种同一个组合
    private static void enumCommon(int[] map, int from, Stack<Group> groups, List<List<Group>> combination) {
        int key = findFirstNotZeroKey(map, from);
        if (key >= map.length) {
            combination.add((List<Group>) groups.clone());
            return;
        }
        if (map[key] >= 3) {//case 1
            map[key] -= 3;
            groups.add(new Group.Same(key));
            enumCommon(map, from, groups, combination);//recursive call枚举接下来的组合
            //restore
            groups.pop();
            map[key] += 3;
        }
//        if (map[key] < 4)//1, 1, 1, 1, 2, 3, 4, 4, 4, 4, 5, 6 避免出现4种同一个组合的不同排列重复情况
            //map[key] must > 0
            if (key < CharacterOffset - 2 //首先只能是数牌才可能形成顺子
                    && canFormSeries(map, key, key + 1, key + 2)) {
                map[key]--;
                map[key + 1]--;
                map[key + 2]--;
                groups.add(new Group.Series(key));
                enumCommon(map, key, groups, combination);//recursive call枚举接下来的组合
                //restore
                groups.pop();
                map[key]++;
                map[key + 1]++;
                map[key + 2]++;
            }
        //不能形成刻子，也不能形成顺子，那这就是孤立的牌，不能胡
    }

    public static int findFirstNotZeroKey(int[] map, int fomKey) {
        int size = map.length;
        int key;
        for (key = fomKey; key < size; key++)
            if (map[key] > 0)
                break;
        return key;
    }

    private static boolean common(int[] map, int from, List<Group> groups) {//O(n)
        boolean ret = true;
        int key = findFirstNotZeroKey(map, from);
        if (key < map.length)//如果为假，说明已经胡牌了
            if (map[key] >= 3) {//形成刻子
                map[key] -= 3;
                groups.add(new Group.Same(key));
                ret = common(map, key, groups);
                map[key] += 3;
            } else if (key < CharacterOffset - 2 //形成顺子,首先只能是数牌才可能形成顺子
                    && canFormSeries(map, key, key + 1, key + 2)) {
                map[key]--;
                map[key + 1]--;
                map[key + 2]--;
                groups.add(new Group.Series(key));
                ret = common(map, key, groups);
                map[key]++;
                map[key + 1]++;
                map[key + 2]++;
            } else ret = false;//不能形成刻子，也不能形成顺子，那这就是孤立的牌，不能胡
        if (!ret) groups.clear();//如果无解
        return ret;
    }

    public static boolean canFormSeries(int[] map, int... keys) {//判断是否能够形成顺子
        for (int key : keys)
            if (map[key] == 0)//首先3张牌都至少有1张
                return false;
        boolean w = true;
        boolean s = true;
        boolean t = true;
        for (int key : keys) {
            w &= KeyAnalyzer.isW(key);
            s &= KeyAnalyzer.isS(key);
            t &= KeyAnalyzer.isT(key);
        }
        return w || s || t;//符合其中任何一个类型即可
    }
}
