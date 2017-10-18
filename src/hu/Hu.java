package hu;

import ai.Face;

import java.util.Arrays;

public abstract class Hu {
    public Hu(int type) {
        this.type = type;
    }
    protected int type;//胡牌方式
    public static final int Fired = 0;//点炮
    public static final int BySelf = Fired + 1;//自摸
    public static final int Grab = BySelf + 1;//抢杠
    public static class Common extends Hu {
        protected Face[] faces;

        public Common(int type, Face[] faces) {
            super(type);
            this.faces = faces;
        }

        @Override
        public String toString() {
            return Arrays.toString(faces);
        }
    }
    public static class SPCommon extends Common {
        public SPCommon(int type, Face[] faces) {
            super(type, faces);
        }
    }
}
