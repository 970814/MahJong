package card;

public class Constant {
    public static final int K = 9;//1种数牌有9张不同的卡
    public static final int M = 4;//同一张卡有4张重复的
    private static final int[] keys;//初始状态下的牌
    private static final String[] Map;//通过key得到这张牌
    private static char[] NumberType = {'萬', '索', '筒',};
    private static char[] Character = {'東', '南', '西', '北', '中', '發', '白',};
    private static char[] OrdinalNumber = {'一', '二', '三', '四', '伍', '六', '七', '八', '九',};
    public static final int CharacterOffset = K * NumberType.length;//东的key值
    static {
        Map = new String[differentKeyCount()];
        for (int i = 0; i < NumberType.length; i++)
            for (int j = 0; j < K; j++)
                Map[i * K + j] = new String(new char[]{OrdinalNumber[j], NumberType[i]});
        for (int i = CharacterOffset; i < Map.length; i++)
            Map[i] = String.valueOf(Character[i - CharacterOffset]);

        keys = new int[Map.length * 4];
        for (int i = 0; i < Map.length; i++)
            for (int j = 0, x = i * M; j < M; j++)
                keys[x + j] = i;
//                Map[keys[x + j] = x / 4] = Map[i];
    }

    public static int[] getCards() {
        return keys.clone();
    }

    public static String get(int key) {
        return Map[key];
    }

    public static int differentKeyCount() {
        return K * numberTypeCount() + characterTypeCount();
    }
    public static int keyCount() {
        return differentKeyCount() * M;
    }

    public static int characterTypeCount() {
        return Character.length;
    }
    public static int numberTypeCount() {
        return NumberType.length;
    }

    public static int numberCount() {
        return K;
    }
    public static void main(String[] args) {
        for (int key : keys)
            System.out.println(key + ":" + get(key));
    }
}
