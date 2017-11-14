package card;

import ai.Player;

public abstract class Group {//一就牌
    public static final int Other = 0;
    public static final int Peng = Other + 1;
    public static final int MingGang = Peng + 1;
    public static final int BiaoGang = MingGang + 1;
    public static final int AnGang = BiaoGang + 1;
    public static final int CardIsEmpty = AnGang + 1;
    protected int key;

    public Group(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public static class Series extends Group {//顺子,仅仅只能是数牌

        @Override
        public String toString() {
            return "" + key + (key + 1) + (key + 2);
        }

        public Series(int key) {
            super(key);
        }
    }

    public static class Same extends Group {//刻子，或者是碰


        public Same(int key) {
            this(key, null);
        }

        Player who;//被碰牌的人

        public Player getWho() {
            return who;
        }

        public Same(int key, Player player) {
            super(key);
            who = player;
        }

        @Override
        public String toString() {
            return "" + key + key + key;
        }
    }

    public static class $Same extends Same {//杠

        public $Same(int sameKey, int type, Player who) {
            super(sameKey, who);
            this.type = type;
        }

        private int type;//杠的类型
        public int getType() {
            return type;
        }

        @Override
        public String toString() {
            return super.toString() + key;
        }
    }
}
