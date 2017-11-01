package card;

public abstract class Group {//一就牌


    public static class Series extends Group {//顺子,仅仅只能是数牌
        protected int lastKey;//3=<lastKey<=9

        @Override
        public int hashCode() {
            return Integer.parseInt(lastKey - 2 + "" + (lastKey - 1) + lastKey);
        }

        public int getKey() {
            return lastKey;
        }

        @Override
        public String toString() {
            return lastKey - 2 + "" + (lastKey - 1) + lastKey;
        }

        public Series(int lastKey) {
            this.lastKey = lastKey;
        }
    }
    public static class Same extends Group {//刻子，或者是碰
        protected int SameKey;

        public Same(int sameKey) {
            SameKey = sameKey;
        }
        @Override
        public int hashCode() {
            return Integer.parseInt(toString());
        }
        @Override
        public String toString() {
            return "" + SameKey + SameKey + SameKey;
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

        @Override
        public String toString() {
            return super.toString() + SameKey;
        }
    }
}
