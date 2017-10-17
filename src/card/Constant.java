package card;

public class Constant {
    public static final int K = 9;//1种数牌有9张不同的卡
    public static final int M = 4;//同一张卡有4张重复的
    private static final String[] Map;//通过key得到这张牌
    private static final int[] keys;//初始状态下的牌
    private static final String[] cardType;//生成不同的牌
    private static char[] D = {'萬', '索', '筒',};
    private static char[] C = {'東', '南', '西', '北', '中', '發', '白',};
    private static char[] I = {'一', '二', '三', '四', '伍', '六', '七', '八', '九',};

    static {
        cardType = new String[cardTypeCount()];
        for (int i = 0; i < D.length; i++)
            for (int j = 0; j < K; j++)
                cardType[i * K + j] = new String(new char[]{I[j], D[i]});
        int offset = K * D.length;
        for (int i = offset; i < cardType.length; i++)
            cardType[i] = String.valueOf(C[i - offset]);

        Map = new String[cardType.length * M];
        keys = new int[Map.length];
        for (int i = 0; i < cardType.length; i++)
            for (int j = 0; j < M; j++) {
                int x = i * M + j;
                Map[keys[x] = x] = cardType[i];
            }
    }

    public static int[] getCards() {
        return keys.clone();
    }

    public static String get(int key) {
        return Map[key];
    }

    public static int cardTypeCount() {
        return K * D.length + C.length;
    }
    public static int cardCount() {
        return cardTypeCount() * 4;
    }

    public static void main(String[] args) {

    }
}
