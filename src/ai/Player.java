package ai;

import card.Constant;
import card.Group;
import card.StackCard;
import hu.Hu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static card.KeyAnalyzer.*;

public class Player {
    List<Integer> keys;
    private StackCard stackCard;//牌堆
    private Brain myBrain;
    int pendingKey = -1;//待处理的卡片
    private int type;

    public int getType() {
        return type;
    }

    public Player(StackCard card, Brain brain) {
        stackCard = card;
        myBrain = brain;
        keys = new ArrayList<>(20);
    }

    public Player offerCard(int count) {
        for (int i = count; i > 0; i--)
            offer();//抓牌细节
        return this;
    }

    public Player offerCard() {//抓牌
        return offerCard(1);
    }

    public int pollCard() {//出牌
        //先默认出第一张
        return keys.remove(0);
    }

    private void offer() {
        type = Hu.BySelf;//自摸
        pendingKey = stackCard.pop();//牌堆弹出一张牌
        myBrain.arrangeKey(keys, pendingKey);//排序整理好牌
        classify(pendingKey);//hashMap分类
        if (keyCount() < 13)
             myBrain.arrange(this);
    }

    public int keyCount() {
        return keys.size() + groups.size() * 3;//一就牌认为是3张牌
    }

    private int removeSameCard(int otherKey, int n) {
        //删掉与otherKey相同的n张牌,返回实际删除的牌
        Iterator<Integer> each = keys.iterator();
        int count = 0;
        while (each.hasNext() && count < n)
            if (each.next() == otherKey) {
                each.remove();
                count++;
            }
        return count;
    }

    void Peng(int otherKey) {//删掉2张牌
        int count = removeSameCard(otherKey,2);
        if (count < 2) throw new RuntimeException("peng should remove 2, but remove: " + count);
        else groups.add(new Group.Same(otherKey));//加一就牌
    }

    void Gang(int otherKey, int type) {//删掉3张牌
        int count = removeSameCard(otherKey,3);
        if (count < 3) throw new RuntimeException("gang should remove 3, but remove: " + count);
        else groups.add(new Group.$Same(otherKey, type));//加一就牌
    }

    List<Group> groups = new ArrayList<>();//面子,就牌

     Player show() {
        for (Integer card : keys)
            System.out.print(stackCard.get(card) + " ");
        System.out.println();
        return this;
    }

    Player showCard() {
        int count = 4;
        System.out.println("show top: " + count);
        stackCard.showTop(count);
        return this;
    }

//    int[] ws = new int[K];//1-9万
//    int[] ss = new int[ws.length];//1-9索
//    int[] ts = new int[ss.length];//1-9筒
//    int[] cs = new int[Constant.characterTypeCount()];//東南西北中發白(7)
    int[] map = new int[Constant.keyCount()];
    private void classify(int key) {
        map[key]++;
//        //把牌分类
//        if (KeyAnalyzer.isW(key))
//            ws[key - wOffset()]++;
//        else if (KeyAnalyzer.isS(key))
//            ss[key - sOffset()]++;
//        else if (KeyAnalyzer.isT(key))
//            ts[key - tOffset()]++;
//        else if (KeyAnalyzer.isCharacter(key))
//            cs[key - cOffset()]++;
//        else throw new NoSuchElementException("key: " + key);
    }
    void extractFaces(Player player) {
        List<Group> handGroups = new ArrayList<>();

        for (int i = wOffset(); i < sOffset(); i++) {
            int x = map[i];
            if (x > 0) {//至少要有一张某种牌才考虑
                /**
                 * 111,222,333,45,666
                 * 111,222,333,4445,6,
                 * 111,222,333,444,56  4
                 * 111,234,234,234,56  4
                 * 111,222,333,456,44  4  hu
                 * 123,123,123,456,44  4  hu
                 * 111,222,345,33,44,6 no
                 *
                  */
            }
        }





        List<Integer> keys = player.keys;
        List<Group> groups = player.groups;
        //找出顺子和刻子
        if (keys.size() < 3) return;
        Integer base = null;
        boolean same = false;
        int count = 1;
        for (int key : keys) {//2,true,
            if (base == null || base + 1 < key) {//1,1,4,4
                base = key;
                count = 1;//必须的
            } else if (base == key) {//1,1,4,4
                if (same) count++;
                else {
                    count = 2;
                    same = true;
                }
            } else if (base + 1 == key) {
                base = key;
                if (same) {
                    count = 2;
                    same = false;
                } else count++;
            } else throw new RuntimeException("base: " + base + ", key:  " + key);
            if (count == 3) {
                groups.add(same ?
                        new Group.Same(base)//刻子
                        :
                        new Group.Series(base));//顺子
                base = null;
            }
        }
    }
}
