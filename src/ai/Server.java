package ai;

import card.Constant;
import card.Group;
import card.StackCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        new Server().start();
    }
    List<Player> players = new ArrayList<>();
    StackCard stackCard;
    Brain brain;
    public Server() {
        stackCard = new StackCard();
        brain = new Brain();
        for (int i = 0; i < 4; i++) newPlayer();
    }

    private void newPlayer() {
        players.add(new Player(stackCard, brain));
    }

    public void start() {
        init();
//        show();
        Player.Msg msg = players.get(0).offer();//该玩家开始摸牌
        while (true) {
            if (msg.hu != null) {
                System.out.println(msg.who + " 玩家胡了");
                return;
            } else {
                switch (msg.type) {
                    case Group.Poll://打牌
                        List<Player.Msg>[] listen = listen(msg.x, msg.who, false);
                        if (!listen[0].isEmpty()) {//点了其他玩家的炮
                            for (Player.Msg m : listen[0]) {
                                System.out.println(m.getWho() + " 玩家胡了");
                            }
                            return;//game over
                        } else if (!listen[1].isEmpty()) {//某个玩家可杠
                            Player who = listen[1].get(0).who;
                            who.MingGang(msg.x, msg.who);//该玩家杠了
                            msg = who.offer();//杠完就重新摸牌
                            System.out.println(who + "杠(" + Constant.get(msg.x) + ")" + msg.who);
                        } else if (!listen[2].isEmpty()) {//某个玩家可碰
                            Player who = listen[2].get(0).who;
                            System.out.println(who + "碰(" +  Constant.get(msg.x) + ")" + msg.who);
                            who.Peng(msg.x, msg.who);
                            msg = who.poll();//碰完打张牌
                        } else {//没人响应这张打出来的牌，就下一位玩家开始
                            msg = msg.getWho().next.offer();
                        }

                        break;
                    case Group.BiaoGang://表杠
                        listen = listen(msg.x, msg.who, true);
                        if (!listen[0].isEmpty()) {//点了其他玩家的炮
                            for (Player.Msg m : listen[0]) {
                                System.out.println(m.getWho() + " 玩家抢杠胡了");
                            }
                            return;//game over
                        } else {
                            Group group = msg.who.groups.get(msg.x);

                            msg.who.BiaoGang(msg.x);
                            msg = msg.who.offer();//摸一张打一张
                            System.out.println(msg.who + "表杠(" + group + ")");
                        }
                        break;
                    case Group.AnGang://暗杠
                        msg.who.AnGang(msg.x);
                        msg = msg.who.offer();
                        System.out.println(msg.who + "暗杠(" + Constant.get(msg.x) + ")");
                        break;
                    case Group.CardIsEmpty:
                        System.out.println("没牌了");
                        return;//game over流局
                }
            }
//            show();
        }
    }



    private List<Player.Msg>[] listen(int x, Player cur, boolean qiangGang) {//第三个参数，代表是否抢杠
        List<Player.Msg>[] sortedMsgs = new List[3];//胡，明杠，碰
        for (int i = 0; i < sortedMsgs.length; i++)
            sortedMsgs[i] = new ArrayList<>();
        for (Player player : players)
            if (player != cur) {
                Player.Msg m = player.listen(x, qiangGang);
                if (m == null) continue;
                int i = -1;
                if (m.hu != null) {
                    i = 0;
                } else if (m.type == Group.MingGang) {
                    i = 1;
                } else if (m.type == Group.Peng) {//peng
                    i = 2;
                }
                if (i < 0) throw new RuntimeException("不存在的听牌类型: " + i);
                sortedMsgs[i].add(m);
            }
        return sortedMsgs;
    }

    public void init() {
        stackCard.reset().wash();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            player.setNext(players.get((i + 1) % players.size()));
            if (i == 0) {
                player.setController(new ConsoleController(player, new Scanner(System.in)));
            }else
            player.setController(new Controller(player));
        }
        for (Player player : players)
            player.offerCard(13);
    }
}
