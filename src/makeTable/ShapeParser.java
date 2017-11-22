package makeTable;

import ai.Player;

import java.util.Arrays;

public class ShapeParser {
    public static int[] parse(int[] h34) {
        return Map.get(Shape.shapeOfGroup(h34));
    }
    public static int[] parse(byte[] h34) {
        return Map.get(Shape.shapeOfGroup(h34));
    }

    public static void main(String[] args) {
        int[] k14 = new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7};//麻7
//        k14 = new int[]{5, 6, 7, 8, 5, 6, 7, 8, 5, 6, 7, 8, 11, 11};
        Arrays.sort(k14);
        System.out.println(Arrays.toString(k14));
        byte[] h34 = new byte[34];
        for (int k : k14) h34[k]++;
        int[] res = parse(h34);
        System.out.println(Arrays.toString(res));
        for (int re : res) {
            int pki = re & 0xf;
            int pk = k14[pki];
            int offset = 4;
            int sc = (re & 0b111 << offset) >>> offset;
            offset += 3;
            int ec = (re & (0b111 << offset)) >>> offset;
            offset += 3;
            String r = "" + pk + pk;
            String index = "" + pki;
            for (int i = 0; i < sc; i++) {
                int si = (re & 0xf << offset + i * 4) >>> offset + i * 4;
                int s = k14[si];
                r += " " + s + s + s;
                index += " " + si;
            }
            offset += sc * 4;
            for (int i = 0; i < ec; i++) {
                int ei = (re & 0xf << offset + i * 4) >>> offset + i * 4;
                int s = k14[ei];
                r += " " + s + (s + 1) + (s + 2);
                index += " " + ei;
            }
            System.out.println(r);
            System.out.println(index);

        }
    }
    public static void show(Player.Msg msg) {
        int[] h34 = msg.getWho().getH34().clone();
        if (msg.getListenKey() != -1)
            h34[msg.getListenKey()]++;
        int[] k14 = new int[14];
        int n = 0;
        for (int key = 0; key < h34.length; key++) {
            for (int i = h34[key]; i > 0; i--) {
                k14[n++] = key;
            }
        }
        int[] res = msg.getHu();
        System.out.println("表: "+Arrays.toString(res));
        for (int re : res) {
            int pki = re & 0xf;
            int pk = k14[pki];
            int offset = 4;
            int sc = (re & 0b111 << offset) >>> offset;
            offset += 3;
            int ec = (re & (0b111 << offset)) >>> offset;
            offset += 3;
            String r = "" + pk + pk;
            String index = "" + pki;
            for (int i = 0; i < sc; i++) {
                int si = (re & 0xf << offset + i * 4) >>> offset + i * 4;
                int s = k14[si];
                r += " " + s + s + s;
                index += " " + si;
            }
            offset += sc * 4;
            for (int i = 0; i < ec; i++) {
                int ei = (re & 0xf << offset + i * 4) >>> offset + i * 4;
                int s = k14[ei];
                r += " " + s + (s + 1) + (s + 2);
                index += " " + ei;
            }
            System.out.println(r);
            System.out.println(index);

        }
        System.out.println(msg.getWho().getName() + "胡了");
    }
}
