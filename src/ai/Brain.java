package ai;

import card.CardAnalyzer;
import card.Constant;
import card.StackCard;
import hu.Hu;

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
    //整理
    void arrange(Player player) {
        analysis(player);

    }

    public static void main(String[] args) {
//        new Player(new StackCard()
//                .show()
//                .wash()
//                .show()
//                , new Brain()
//        )
//                .offerCard().show().showCard()
//                .offerCard().show().showCard()
//                .offerCard().show().showCard()
//                .offerCard().show().showCard();
//
//        List<Face> faces = new ArrayList<>();
//        new Brain().extractFaces(Arrays.asList(1, 1, 1, 1, 3, 3), faces);
//        System.out.println(faces);
        Player player = new Player(new StackCard().wash().show(), new Brain())
                .offerCard(13);
        while (true) {
            player.show().showCard();
            player.offerCard().pollCard();

        }
    }

    private void analysis(Player player) {
        int insertLocation = arrangeKey(player.keys, player.pendingKey);
        Hu hu = hu(player);
        System.out.println(hu);
        if (hu != null) throw new RuntimeException(hu.toString());

        if (canGang(player)) {
//            player.Gang(player.pendingKey,);
        }
    }

    public Hu hu(Player player) {
        int latestKey = player.pendingKey;
        int type = player.getType();//胡牌方式

        List<Integer> keys = player.keys;
        List<Integer> ws = new ArrayList<>();//万
        List<Integer> ss = new ArrayList<>();//索
        List<Integer> ts = new ArrayList<>();//筒
        List<Integer>[] characters = new List[Constant.characterTypeCount()];//東南西北中發白
        classify(keys, ws, ss, ts, characters);//把牌分类
        List<Face> faces = new ArrayList<>();
        extractFaces(ws, faces);
        extractFaces(ss, faces);
        extractFaces(ts, faces);
        extractFacesFromCharacter(characters, faces);
        faces.addAll(player.faces);
        if (faces.size() == 4) {//不可能超过个4个面子
            int pairKey = findFirstPair(keys);
            if (pairKey != -1) {//平胡
                Face[] faceArray = new Face[4];
                boolean common = false;
                for (int i = faceArray.length - 1; i >= 0; i--) {
                    Face face = faceArray[i] = faces.get(i);
                    if (!common && !(face instanceof Face.Same))//只要某一就牌不满足刻子牌，那么就是平胡
                        common = true;
                }
                return common ? new Hu.Common(type, faceArray) : new Hu.SPCommon(type, faceArray);
            }
        }

//        int faceCount = player.faces.size() +player.

        return null;
    }

    private int findFirstPair(List<Integer> keys) {
        int[] map = new int[Constant.differentKeyCount()];//map[key]代表key出现的次数
        for (Integer key : keys)
            map[key]++;
        for (int key = map.length - 1; key >= 0; key--)
            if (map[key] == 2)
                return key;
        return -1;//不存在对子
    }

    private void extractFacesFromCharacter(List<Integer>[] characters, List<Face> faces) {
        for (List<Integer> character : characters)
            if (character != null && character.size() > 2) //每种字牌大于2张就能形成一个刻子
                faces.add(new Face.Same(character.get(0)));
    }

    private void extractFaces(List<Integer> keys, List<Face> faces) {
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
                faces.add(same ?
                        new Face.Same(base)//刻子
                        :
                        new Face.Series(base));//顺子
                base = null;
            }
        }

    }

    private void classify(List<Integer> keys, List<Integer> ws, List<Integer> ss, List<Integer> ts, List<Integer>[] characters) {
        //把牌分类
        for (Integer key : keys) {
            if (CardAnalyzer.isW(key))
                ws.add(key);
            else if (CardAnalyzer.isS(key))
                ss.add(key);
            else if (CardAnalyzer.isT(key))
                ts.add(key);
            else if (CardAnalyzer.isCharacter(key))
                if (characters[key - Constant.CharacterOffset] == null)
                    characters[key - Constant.CharacterOffset] = new ArrayList<>();
                else characters[key - Constant.CharacterOffset].add(key);
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
        int latestKey = player.pendingKey;
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
