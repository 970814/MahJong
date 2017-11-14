package ai;

import card.Group;
import makeTable.ShapeParser;

import java.util.*;

public class Brain {
    //整理牌，用插入排序，返回卡片插入的位置
    int arrangeKey(List<Integer> keys, int newKey) {
        Iterator<Integer> each = keys.iterator();
        int index = 0;
        while (each.hasNext())
            if (each.next() >= newKey)
                break;
            else index++;
        keys.add(index, newKey);
        return index;//0-keys.size
    }

    public static void main(String[] args) {

    }

    public Message analysis(Player player, int key) {//听牌
        int[] h34 = player.h34;
        Message message = new Message();
        h34[key]++;
        message.hu = ShapeParser.parse(h34);
        h34[key]--;
        for (int i = h34.length - 1; i >= 0; i--)
            if (i == key && (h34[i] == 2 || h34[i] == 3)) {//可碰可明杠
                message.key = key;
                message.isPeng = h34[i] == 2;//手上2张牌则为碰，否则为明杠
                break;//立即停止判断
            }
        return message;
    }

    static class Message{
        List<Integer> ABgang;//正值代表(表杠)在玩家groups中的位置，否则其(相反数-1)就是暗杠的第一张牌值值
        int[] hu;//能胡多少种牌型
        int key = -1;//-1代表不能碰也不能杠
        boolean isPeng;
        public void addGang(int x) {
            if (ABgang == null) ABgang = new ArrayList<>();
            ABgang.add(x);
        }
    }

    Message analysis(Player player) {//分析自己摸的牌
        int[] h34 = player.h34;
        List<Group> groups = player.groups;
        Message message = new Message();
        message.hu = ShapeParser.parse(h34);
        for (int key = h34.length - 1; key >= 0; key--) {
            //看看能不能表杠
            if (h34[key] == 1)
                for (int i = 0, groupsSize = groups.size(); i < groupsSize; i++) {
                    Group group = groups.get(i);
                    if (group.getKey() == key & group instanceof Group.Same)
                        message.addGang(i);
                }
            //看看能不能暗杠
            if (h34[key] == 4)
                message.addGang(-(key + 1));
        }
        return message;
    }
}
