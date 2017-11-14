package ai;

import card.Constant;
import card.Group;
import card.StackCard;
import hu.Hu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    Player next;

    public void setNext(Player next) {
        this.next = next;
    }

    public Player getNext() {

        return next;
    }


    class Msg {
        Player who = Player.this;

        public Msg(int[] hu) {
            this.hu = hu;
        }

        public Msg(int x, int type) {
            this.x = x;
            this.type = type;
        }

        int[] hu;
        int x;//可能是随便打出的一张牌，也可能是控制player各种Gang的方法的参数
        int type;//碰或各种杠是什么类型

        public Player getWho() {
            return who;
        }
    }
    public List<Integer> keys;
    int[] h34;
    private StackCard stackCard;//牌堆
    private Brain myBrain;
    private int type;

    public Player(StackCard card, Brain brain) {
        this.controller = controller;
        stackCard = card;
        myBrain = brain;
        keys = new ArrayList<>(20);
        h34 = new int[34];
    }

    int last = -1;
    public Player offerCard(int count) {
        for (int i = count; i > 0; i--) {
            int key = stackCard.pop();//牌堆弹出一张牌
            last = myBrain.arrangeKey(keys, key);//排序整理好牌
            h34[key]++;
        }
        return this;
    }

    public int pollCard() {//出牌
        //先默认出第一张
        int index = controller.poll(keys.size());//由控制器来决定出哪一张牌
        int key = keys.remove(index);
        h34[key]--;
        System.out.println(this + "打出一张：" + Constant.get(key) + "位置在: " + index);
        show();
        return key;
    }

    public Msg poll() {
        return new Msg(pollCard(), Group.Other);
    }

    public Msg offer() {
        //如果牌被摸完了
        if (stackCard.left() == 0) return new Msg(-1, Group.CardIsEmpty);
        type = Hu.BySelf;//自摸
        offerCard(1);
        System.out.println(this + "摸到一张：" + Constant.get(keys.get(last)));
        show();
        Brain.Message message = myBrain.analysis(this);
        if (message.hu != null) {
            if (controller.hu())//由控制器来决定是否要胡
                return new Msg(message.hu);//能胡则胡，
        }
        if (message.ABgang != null)//随便选一个杠
            for (Integer x : message.ABgang)//能杠则杠
                if (x < 0) {
                    if (controller.gang())
                        return new Msg(-x - 1, Group.AnGang);//告诉服务器自己接下来要做的事情
                } else {
                    if (controller.gang())
                        return new Msg(x, Group.BiaoGang);
                }
        return poll();
    }

    public Msg listen(int key, boolean qiangGang) {
        type = Hu.Fired;//点炮
        Brain.Message message = myBrain.analysis(this, key);
        if (message.hu != null) {
            if (controller.hu())
                return new Msg(message.hu);//能胡则胡，
        }
        if (!qiangGang && message.key != -1) {//碰，杠不能抢杠
            if (message.isPeng) {//能碰则碰，
                if (controller.peng())
                    return new Msg(key, Group.Peng);
            } else {//能杠则杠
                if (controller.gang())
                    return new Msg(key, Group.MingGang);
            }
        }
        return null;
    }

    void AnGang(int otherKey) {
        int count = removeSameCard(otherKey, 4);
        if (count < 4) throw new RuntimeException("an ABgang should remove 4, but remove: " + count);
        else groups.add(new Group.$Same(otherKey,  Group.AnGang, null));//加一就牌
        show();
    }

    void BiaoGang(int where) {
        Group.Same same = (Group.Same) groups.get(where);
        int count = removeSameCard(same.getKey(), 1);//表杠删掉一张手牌
        if (count < 1) throw new RuntimeException("biao ABgang should remove 1, but remove: " + count);
        else groups.set(where, new Group.$Same(same.getKey(), Group.BiaoGang, same.getWho()));
        show();
    }

    void MingGang(int otherKey, Player who) {//删掉手上3张牌
        int count = removeSameCard(otherKey, 3);
        if (count < 3) throw new RuntimeException("peng should remove 3, but remove: " + count);
        else groups.add(new Group.$Same(otherKey, Group.MingGang, who));
        show();
    }

    void Peng(int otherKey, Player who) {//删掉2张牌
        int count = removeSameCard(otherKey, 2);
        if (count < 2) throw new RuntimeException("peng should remove 2, but remove: " + count);
        else groups.add(new Group.Same(otherKey, who));//加一就牌
        show();
    }

    private int removeSameCard(int otherKey, int n) {
        h34[otherKey] -= n;
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




    List<Group> groups = new ArrayList<>();//面子,就牌
    private void show() {
        System.out.println("---------------start----------------");
        show2();
        Player p = this.next;
        while (p != this) {
            p.show2();
            p = p.next;
        }
        System.out.println("---------------end----------------");
    }
    Player show2() {



        System.out.printf(String.valueOf(keys.size())+": ");

        for (Integer card : keys)
            System.out.print(stackCard.get(card) + " ");
//            System.out.print(card + " ");
        System.out.println(this + groups.toString());
        return this;
    }

    Player showCard() {
        int count = 4;
        System.out.println("show top: " + count);
        stackCard.showTop(count);
        return this;
    }
}
