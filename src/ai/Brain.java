package ai;

import card.StackCard;

import java.util.Iterator;
import java.util.List;

public class Brain {
    //整理牌，用插入排序
    private void arrangeCard(List<Integer> keys, int newCard) {
        Iterator<Integer> each = keys.iterator();
        int index = 0;
        while (each.hasNext())
            if (each.next() >= newCard)
                break;
            else index++;
        keys.add(index, newCard);
    }
    //整理
    void arrange(Player player) {
        analysis(player);
        arrangeCard(player.cards, player.pendingCard);
    }

    public static void main(String[] args) {
        StackCard stackCard =
                new StackCard()
                        .show()
                        .wash()
                        .show();
        Player player = new Player(stackCard, new Brain());
        player
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show()
                .offerCard().show();
        System.out.println(stackCard.left());
        stackCard.show();
    }

    private void analysis(Player player) {


    }

    public void canHu(Player player) {
        int latestKey = player.pendingCard;
        boolean isSelf = player.isSelf();
    }

    public boolean canGang(Player player) {
        return count(player) == 3;//如果手里已经有3张
    }

    public boolean canPeng(Player player) {
        return count(player) == 2;//如果手里已经有2张
    }

    private int count(Player player) {
        List<Integer> keys = player.cards;
        int latestKey = player.pendingCard;
        //统计手里有多少张latestKey牌
        int count = 0;
        for (Integer key : keys)
            if (key == latestKey) {
                count++;
                if (count == 3) break;//因为只有4张相同的牌，所以找到3张后没必要再找了
            }
        return count;
    }



}
