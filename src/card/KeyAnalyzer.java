package card;

import ai.Player;

public class KeyAnalyzer {
    private static int K = Constant.numberCount();
    private static int WOffset = 0;
    private static int SOffset = WOffset + K;
    private static int TOffset = SOffset + K;
    private static int COffset = TOffset + K;

    public static boolean isW(int key) {
        return wOffset() <= key && key < sOffset();
    }

    public static boolean isS(int key) {
        return sOffset() <= key && key < tOffset();
    }

    public static boolean isT(int key) {
        return tOffset() <= key && key < cOffset();
    }

    public static boolean isCharacter(int key) {
        return cOffset() <= key && key < cOffset() + Constant.characterTypeCount();
    }

    public static int cOffset() {
        return COffset;
    }

    public static int wOffset() {
        return WOffset;
    }

    public static int sOffset() {
        return SOffset;
    }

    public static int tOffset() {
        return TOffset;
    }

    public int analysis(Player player) {

        return 0;
    }

    public static void main(String[] args) {
//        for (int i = 0; i < keyCount(); i++) {
////            if (isW(i)) System.out.println(get(i));
////            if (isS(i)) System.out.println(get(i));
////            if (isT(i)) System.out.println(get(i));
//            if (isCharacter(i)) System.out.println(get(i));
//        }

//        StackCard stackCard = new StackCard();
//        stackCard.show();
//
//        stackCard.forEach(key -> {
//            if (isT(key)) System.out.println(stackCard.get(key));
//        });


    }
}
