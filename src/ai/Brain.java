package ai;

import card.CardAnalyzer;
import card.Constant;
import card.StackCard;

import java.util.*;

public class Brain {
    //整理牌，用插入排序，返回卡片插入的位置
    private int arrangeCard(List<Integer> keys, int newCard) {
        Iterator<Integer> each = keys.iterator();
        int index = 0;
        while (each.hasNext())
            if (each.next() >= newCard)
                break;
            else index++;
        keys.add(index, newCard);
        return index;//0-keys.size
    }
    //整理
    void arrange(Player player) {
        analysis(player);

    }

    public static void main(String[] args) {
        new Player(new StackCard()
                .show()
                .wash()
                .show()
                , new Brain()
        )
                .offerCard().show().showCard()
                .offerCard().show().showCard()
                .offerCard().show().showCard()
                .offerCard().show().showCard();


    }

    private void analysis(Player player) {
        int insertLocation = arrangeCard(player.keys, player.pendingCard);
        if (canHu(player)) {

        }
    }

    public boolean canHu(Player player) {
        int latestKey = player.pendingCard;
        boolean isSelf = player.isSelf();

        List<Integer> keys = player.keys;
        List<Integer> ws = new ArrayList<>();//万
        List<Integer> ss = new ArrayList<>();//索
        List<Integer> ts = new ArrayList<>();//筒
        List<Integer>[] characters = new List[Constant.characterTypeCount()];//東南西北中發白
        classify(keys, ws, ss, ts, characters);//把牌分类

        

//        int faceCount = player.faces.size() +player.

        return false;
    }

    public void classify(List<Integer> keys, List<Integer> ws, List<Integer> ss, List<Integer> ts, List<Integer>[] characters) {
        //把牌分类
        for (Integer key : keys) {
            if (CardAnalyzer.isW(key))
                ws.add(key);
            else if (CardAnalyzer.isS(key))
                ss.add(key);
            else if (CardAnalyzer.isT(key))
                ts.add(key);
            else if (CardAnalyzer.isCharacter(key))
                characters[key - Constant.CharacterOffset].add(key);
            else throw new NoSuchElementException("key: " + key);
        }
    }
//1,2,3 333 白白白索索索，北北
    public int faceCount(List<Integer> keys) {//统计有多少就牌
        return 0;
    }

    public boolean canGang(Player player) {
        return count(player) == 3;//如果手里已经有3张
    }

    public boolean canPeng(Player player) {
        return count(player) == 2;//如果手里已经有2张
    }

    private int count(Player player) {
        List<Integer> keys = player.keys;
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
