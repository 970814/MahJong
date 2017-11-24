package ai;

import card.Constant;
import card.Group;
import card.StackCard;
import hu.Hu;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Player {
    static String[] names = {"A", "B", "C", "D"};
    static int autoId = 0;

    {
        name = names[autoId++];
    }

    String name;

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

    public void reset() {
        keys.clear();
        groups.clear();
        pendingKey = Empty;
        Arrays.fill(h34, 0);
        pollKeys.clear();

        //reset controller
        pressed2 = null;
        pressedPoint = null;
        selected = false;
        selectedKey = Empty;
        movePoint = null;
    }

    public int getPendingKey() {
        return pendingKey;
    }

    public List<Group> getGroups() {

        return groups;
    }

    public String getName() {
        return name;
    }

    public class Msg {
        Player who = Player.this;

        public Msg(int[] hu, int key) {
            this.hu = hu;
            listenKey = key;
        }
        
        public Msg(int[] hu) {
            this(hu, -1);
        }

        public Msg(int x, int type) {
            this.x = x;
            this.type = type;
        }

        public int getListenKey() {
            return listenKey;
        }

        int listenKey = -1;
        int[] hu;
        int x;//可能是随便打出的一张牌，也可能是控制player各种Gang的方法的参数
        int type;//碰或各种杠是什么类型



        public Player getWho() {
            return who;
        }

        public int getType() {
            return type;
        }

        public int getX() {
            return x;
        }

        public int[] getHu() {
            return hu;
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
        int size = keys.size();
        int index = controller.poll(size);//由控制器来决定出哪一张牌
        int key;
        if (index >= size) {
            key = pendingKey;
            pendingKey = Empty;
        } else {
            key = keys.remove(index);
            if (pendingKey != Empty) {
                myBrain.arrangeKey(keys, pendingKey);
                pendingKey = Empty;
            }
        }
        h34[key]--;
        System.out.println(name + " poll " + Constant.get(key));
        pollKeys.add(key);
        return key;
    }

    public Msg poll() {
        return new Msg(pollCard(), Group.Poll);
    }

    int pendingKey = Empty;
    public static final int Empty = -1;

    //摸牌阶段1
    public Msg offer() {
        if (stackCard.left() == 0) return new Msg(-1, Group.CardIsEmpty);
        pendingKey = stackCard.pop();
        System.out.println(name + "摸到一张：" + Constant.get(pendingKey));
        return new Msg(pendingKey, Group.Offer);
    }
    //摸牌不加入keys中，加入h34中,存在pendingKey中
    //听牌不加入keys中，不加入h34中,不加入pendingKey中
    //

    //摸牌阶段2：分析
    public Msg analysis() {
        Brain.Message message = myBrain.analysis(this, pendingKey, false);
        if (message.hu != null)
            if (controller.hu())//由控制器来决定是否要胡
                return new Msg(message.hu);//能胡则胡，
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
        Brain.Message message = myBrain.analysis(this, key, true);
        if (message.hu != null) {
            if (controller.hu())
                return new Msg(message.hu, key);//能胡则胡，
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

    public void AnGang(int otherKey) {
        int count = removeSameCard(otherKey, 4);
        if (count < 4) throw new RuntimeException("an ABgang should remove 4, but remove: " + count);
        else groups.add(new Group.$Same(otherKey, Group.AnGang, null));//加一就牌
        if (pendingKey != Empty) {//如果暗杠不是杠新摸的牌，则加入到keys中
            myBrain.arrangeKey(keys, pendingKey);
            pendingKey = Empty;
        }
        System.out.println(name + " AnGang(" + Constant.get(otherKey) + ")");
    }

    public void BiaoGang(int where) {
        Group.Same same = (Group.Same) groups.get(where);
        h34[same.getKey()]--;
        pendingKey = Empty;//表杠删掉一张手牌,其实是删除pendingKey
        groups.set(where, new Group.$Same(same.getKey(), Group.BiaoGang, same.getWho()));
    }

    public void MingGang(int otherKey, Player who) {//删掉手上3张牌
        int count = removeSameCard(otherKey, 3);
        if (count < 3) throw new RuntimeException("peng should remove 3, but remove: " + count);
        else groups.add(new Group.$Same(otherKey, Group.MingGang, who));
    }

    public void Peng(int otherKey, Player who) {//删掉2张牌
        int count = removeSameCard(otherKey, 2);
        if (count < 2) throw new RuntimeException("peng should remove 2, but remove: " + count);
        else groups.add(new Group.Same(otherKey, who));//加一就牌
    }

    private int removeSameCard(int otherKey, int n) {
        h34[otherKey] -= n;

        //删掉与otherKey相同的n张牌,返回实际删除的牌
        Iterator<Integer> each = keys.iterator();
        int count = 0;
        if (pendingKey == otherKey) {//如果加上刚刚摸到的牌形成杠，则删除量加1
            count++;
            pendingKey = Empty;
        }
        while (each.hasNext() && count < n)
            if (each.next() == otherKey) {
                each.remove();
                count++;
            }
        return count;
    }


    List<Group> groups = new ArrayList<>();//面子,就牌

    public int[] getH34() {
        return h34;
    }

    public Point movePoint;
    public Point pressedPoint;
    public int selectedKey = Empty;
    public Point pressed2;
    public boolean selected;
    public List<Integer> pollKeys = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}
