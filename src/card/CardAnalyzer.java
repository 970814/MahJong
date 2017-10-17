package card;

import ai.Player;

public class CardAnalyzer {
    private static int K = 9;
    public static boolean isW(int key) {
        return 0 <= key && key < K;
    }

    public static boolean isS(int key) {
        return K <= key && key < 2 * K;
    }

    public static boolean isT(int key) {
        return 2 * K <= key && key < 3 * K;
    }

    public static boolean isCharacter(int key) {
        return 3 * K <= key && key < 3 * K + Constant.characterTypeCount();
    }

    public int analysis(Player player) {

        return 0;
    }

    public static void main(String[] args) {
//        for (int i = 0; i < cardCount(); i++) {
////            if (isW(i)) System.out.println(get(i));
////            if (isS(i)) System.out.println(get(i));
////            if (isT(i)) System.out.println(get(i));
//            if (isCharacter(i)) System.out.println(get(i));
//        }

        StackCard stackCard = new StackCard();
        stackCard.show();

        stackCard.forEach(key -> {
            if (isT(key)) System.out.println(stackCard.get(key));
        });


    }
}
