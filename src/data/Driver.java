package data;

import ai.Controller;
import ai.MouseController;
import ai.Player;
import card.Constant;
import card.Group;
import makeTable.ShapeParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Driver {
    private DataModel dataModel;
    private DataChangeListener dataChangeListener;

    public DataModel getDataModel() {
        return dataModel;
    }

    public Driver(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListener = dataChangeListener;
    }

    private final Object lock = new Object();
    private boolean flag;

    public void start() {
        flag = false;//中断前一个线程
        reset();
        synchronized (lock) {

            for (int i = 0; i < 3; i++)
                for (Player player : dataModel.players)
                    for (int j = 0; j < 4; j++) {
                        player.offerCard(1);
                        update(40);
                    }
            for (Player player : dataModel.players) {
                player.offerCard(1);
                update(40);
            }
            flag = true;
            start0(dataModel.players[0].offer());
            flag = false;
        }

    }

    private void start0(Player.Msg msg) {

        Loop:
        while (flag && msg.getType() != Group.CardIsEmpty) {//只要牌还没摸完
            if (msg.getHu() != null) {
                //自摸胡了
                ShapeParser.show(msg);
                break;
            } else {
                switch (msg.getType()) {
                    case Group.Offer://摸牌
                        update(20);
                        msg = msg.getWho().analysis();//
                        update(40);
                        break;
                    case Group.AnGang:
                        msg.getWho().AnGang(msg.getX());
                        update(1000);
                        msg = msg.getWho().offer();
                        break;
                    case Group.BiaoGang:
                        List<Player.Msg>[] listen = listen(msg.getX(), msg.getWho(), true);
                        if (!listen[0].isEmpty()) {//点了其他玩家的炮
                            for (Player.Msg m : listen[0]) {
                                //抢杠胡
                                ShapeParser.show(m);
                                break Loop;
                            }
                        } else {
                            Group group = msg.getWho().getGroups().get(msg.getX());
                            msg.getWho().BiaoGang(msg.getX());
                            update(40);
                            msg = msg.getWho().offer();//摸一张
                            System.out.println(msg.getWho() + "表杠(" + group + ")");
                        }
                        break;
                    case Group.Poll:
                        update(40);
                        listen = listen(msg.getX(), msg.getWho(), false);
                        if (!listen[0].isEmpty()) {//点了其他玩家的炮
                            for (Player.Msg m : listen[0]) {
                                //点炮胡了
                                ShapeParser.show(m);
                                break Loop;
                            }
                        } else if (!listen[1].isEmpty()) {//某个玩家可杠
                            Player who = listen[1].get(0).getWho();
                            who.MingGang(msg.getX(), msg.getWho());//该玩家杠了
                            update(40);
                            msg = who.offer();//杠完就重新摸牌
                        } else if (!listen[2].isEmpty()) {//某个玩家可碰
                            Player who = listen[2].get(0).getWho();
                            System.out.println(who + "碰(" + Constant.get(msg.getX()) + ")" + msg.getWho());
                            who.Peng(msg.getX(), msg.getWho());
                            update(40);
                            msg = who.poll();//碰完打张牌
                        } else {//没人响应这张打出来的牌，就下一位玩家开始
                            msg = msg.getWho().getNext().offer();
                        }
                        break;
                    default:
                        throw new RuntimeException("unknown case: " + msg.getType());
                }
            }
        }

        System.out.println("流局");
    }

    private List<Player.Msg>[] listen(int x, Player cur, boolean qiangGang) {//第三个参数，代表是否抢杠
        List<Player.Msg>[] sortedMsgs = new List[3];//胡，明杠，碰
        for (int i = 0; i < sortedMsgs.length; i++)
            sortedMsgs[i] = new ArrayList<>();
        for (Player player : dataModel.players)
            if (player != cur) {
                Player.Msg m = player.listen(x, qiangGang);
                if (m == null) continue;
                int i = -1;
                if (m.getHu() != null) {
                    i = 0;
                } else if (m.getType() == Group.MingGang) {
                    i = 1;
                } else if (m.getType() == Group.Peng) {//peng
                    i = 2;
                }
                if (i < 0) throw new RuntimeException("不存在的听牌类型: " + m.getType());
                sortedMsgs[i].add(m);
            }
        return sortedMsgs;
    }

    //更新ui，并延时millis毫秒
    private void update(int millis) {
        dataChangeListener.onDataChanged();
        sleep(millis);
    }
    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void reset() {
        dataModel.reset();
        dataModel.wash();
    }

    private void initUI() {
        Random random = new Random();
        while (true) {
            for (Player player : dataModel.players) {
                for (int i = 0; i < player.keys.size(); i++)
                    player.keys.set(i, random.nextInt(34));
                update(40);
            }
            Loop:
            for (; ; ) {
                boolean[] h = new boolean[4];
                boolean[] h0 = new boolean[4];
                Arrays.fill(h0, true);
                int[] n = new int[4];
                for (int i = 0; ; i++, i %= 4) {
                    dataModel.players[i].keys.set(n[i]++, random.nextInt(34));
                    update(20);
                    if (n[i] == dataModel.players[i].keys.size()) {
                        h[i] = true;
                        n[i] = 0;
                    }
                    if (Arrays.equals(h, h0)) break Loop;
                }
            }
        }
    }
}
