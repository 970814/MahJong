package ai;

import card.Constant;
import card.StackCard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
    List<Integer> cards;
    private StackCard stackCard;//牌堆
    private Brain myBrain;
    int pendingCard = -1;//待处理的卡片
    private boolean isSelf;

    public boolean isSelf() {
        return isSelf;
    }

    public Player(StackCard card, Brain brain) {
        stackCard = card;
        myBrain = brain;
        cards = new ArrayList<>(20);
    }

    private void offerCard(int count) {
        for (int i = count; i > 0; i--)
            offer();//抓牌细节
    }

    public Player offerCard() {//抓牌
        offerCard(1);
        return this;
    }

    public int pollCard() {//出牌
        //先默认出第一张
        return cards.remove(0);
    }

    private void offer() {
        pendingCard = stackCard.pop();
        myBrain.arrange(this);
    }

    private int removeSameCard(int otherKey, int n) {
        //删掉与otherKey相同的n张牌
        Iterator<Integer> each = cards.iterator();
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

    public Player show() {
        for (Integer card : cards)
            System.out.print(Constant.get(card) + " ");
        System.out.println();
        return this;
    }
}
