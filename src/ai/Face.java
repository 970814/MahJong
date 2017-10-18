package ai;

public abstract class Face {//一就牌

    public static class Series extends Face {//顺子,仅仅只能是数牌
        protected int lastKey;//3=<lastKey<=9

        public Series(int lastKey) {
            this.lastKey = lastKey;
        }
    }
    public static class Same extends Face {//刻子，或者是碰
        protected int SameKey;

        public Same(int sameKey) {
            SameKey = sameKey;
        }
    }

    public static class $Same extends Same {//杠

        public $Same(int sameKey, int type) {
            super(sameKey);
            this.type = type;
        }
        private int type;//杠的类型
        public static final int MingGang = 0;
        public static final int BiaoGang = MingGang + 1;
        public static final int AnGang = BiaoGang + 1;
        public int getType() {
            return type;
        }
    }
}
