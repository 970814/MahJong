package ai;

import card.Constant;
import card.StackCard;
import hu.Hu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

        if (keyCount() < 13)
            myBrain.arrangeKey(keys, pendingKey);
        else myBrain.arrange(this);
    }

    public int keyCount() {
        return keys.size() + faces.size() * 3;//一就牌认为是3张牌
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
        else faces.add(new Face.Same(otherKey));//加一就牌
    }

    void Gang(int otherKey, int type) {//删掉3张牌
        int count = removeSameCard(otherKey,3);
        if (count < 3) throw new RuntimeException("gang should remove 3, but remove: " + count);
        else faces.add(new Face.$Same(otherKey, type));//加一就牌
    }

    List<Face> faces = new ArrayList<>();//面子,就牌

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
    List<Integer> ws = new ArrayList<>();//万
    List<Integer> ss = new ArrayList<>();//索
    List<Integer> ts = new ArrayList<>();//筒
    List<Integer>[] characters = new List[Constant.characterTypeCount()];//東南西北中發白

    public void c() {
        classify(keys, ws, ss, ts, characters);//把牌分类
        List<Face> faces = new ArrayList<>();
        extractFaces(ws, faces);
        extractFaces(ss, faces);
        extractFaces(ts, faces);
        extractFacesFromCharacter(characters, faces);
    }
}
