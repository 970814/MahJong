package ai2;

import card.Group;
import card.KeyAnalyzer;

import java.util.*;

import static card.Constant.CharacterOffset;

@SuppressWarnings("Duplicates")
public class EnumGroup {

    public static int findFirstNotZeroKey(byte[] map, int fomKey) {
        int size = map.length;
        int key;
        for (key = fomKey; key < size; key++)
            if (map[key] > 0)
                break;
        return key;
    }
    public static boolean canFormSeries(byte[] map, int... keys) {//判断是否能够形成顺子
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

    private static void unique(List<int[]> set, int[] msg) {
        for (int[] m : set)
            if (m[0] == msg[0] && m[1] == msg[1]) {
                boolean equal = true;
                for (int i = 0; i < msg[0]; i++)
                    if (m[2 + i] != msg[2 + i]) {
                        equal = false;
                        break;
                    }
                if (equal)
                    for (int i = 0; i < msg[1]; i++)
                        if (m[6 + i] != msg[6 + i]) {
                            equal = false;
                            break;
                        }
                if (equal)
                    return;//如果已经存在相同的牌型,忽略
            }
        set.add(msg.clone());
    }



    //h34是在无对子的情况下选择的
    public static void enumUnique(List<int[]> set, int fromKey, int[] msg, byte[] h34) {
        for (; fromKey < 34; fromKey++)
            if (h34[fromKey] > 0)
                break;
        if (fromKey == 34) {//如果没牌了
            unique(set, msg);
            return;
        }
        if (h34[fromKey] >= 3) {//case 1
            h34[fromKey] -= 3;
            msg[2 + msg[0]++] = fromKey;
            enumUnique(set, fromKey, msg, h34);//recursive call枚举接下来的组合
            msg[0]--;
            h34[fromKey] += 3;
        }
        if (fromKey < CharacterOffset - 2 //首先只能是数牌才可能形成顺子
                && canFormSeries(h34, fromKey, fromKey + 1, fromKey + 2)) {
            h34[fromKey]--;
            h34[fromKey + 1]--;
            h34[fromKey + 2]--;
            msg[6 + msg[1]++] = fromKey;
            enumUnique(set, fromKey, msg, h34);//recursive call枚举接下来的组合
            //restore
            msg[1]--;
            h34[fromKey]++;
            h34[fromKey + 1]++;
            h34[fromKey + 2]++;
        }
        //不能形成刻子，也不能形成顺子，那这就是孤立的牌，不能胡
    }
}
