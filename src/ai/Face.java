package ai;

public class Face {//一就牌

    public static class Series extends Face {//顺子,仅仅只能是数牌
        protected int firstKey;//1=<firstKey<=7

        public Series(int firstKey) {
            this.firstKey = firstKey;
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

        public int type;//杠的类型
        public static final int MingGang = 0;
        public static final int BiaoGang = MingGang + 1;
        public static final int AnGang = BiaoGang + 1;
    }
}