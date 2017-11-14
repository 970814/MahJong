package makeTable;

@SuppressWarnings("Duplicates")
public class Shape {
    public static int shapeOfGroup(int[] h34) {
        int shape = 0;
        int pointer = -1;
        boolean series = false;
        //考虑数牌
        for (int key = 0; key < 27; key++) {
            if (h34[key] > 0) {
                pointer++;
                series = true;
                switch (h34[key]) {
                    case 1:
                        break;
                    case 2:
                        shape |= 0b11 << pointer;
                        pointer += 2;
                        break;
                    case 3:
                        shape |= 0b1111 << pointer;
                        pointer += 4;
                        break;
                    case 4:
                        shape |= 0b111111 << pointer;
                        pointer += 6;
                        break;
                    default:
                        throw new RuntimeException("more than 4 times");
                }
            } else if (series) {//==0并且前面有牌，然后这个位置断开了
                series = false;
                shape |= 1 << pointer++;
            }
            if (series && key % 9 == 8) {//数牌中最大的牌和下一张必须的断开的,但是这张牌需要有才计算，前面已经断开了这个位置
                series = false;
                shape |= 1 << pointer++;
            }
        }
        //考虑字牌
        for (int key = 27; key < 34; key++)
            if (h34[key] > 0) {
                pointer++;
                switch (h34[key]) {
                    case 1:
                        break;
                    case 2:
                        shape |= 0b11 << pointer;
                        pointer += 2;
                        break;
                    case 3:
                        shape |= 0b1111 << pointer;
                        pointer += 4;
                        break;
                    case 4:
                        shape |= 0b111111 << pointer;
                        pointer += 6;
                        break;
                    default:
                        throw new RuntimeException("more than 4");
                }
                shape |= 1 << pointer++;//字牌每张都是断开的
            }
        return shape;
    }
    //把手牌抽象成一个形状,首先手牌必须是已经排好序的
    //http://blog.csdn.net/panshiqu/article/details/58610958 这个人的算法不能用，不可逆，且会冲突，如果胡牌不足四个刻字的牌情况，
    public static int shapeOfGroup(byte[] h34) {
        int shape = 0;
        int pointer = -1;
        boolean series = false;
        //考虑数牌
        for (int key = 0; key < 27; key++) {
            if (h34[key] > 0) {
                pointer++;
                series = true;
                switch (h34[key]) {
                    case 1:
                        break;
                    case 2:
                        shape |= 0b11 << pointer;
                        pointer += 2;
                        break;
                    case 3:
                        shape |= 0b1111 << pointer;
                        pointer += 4;
                        break;
                    case 4:
                        shape |= 0b111111 << pointer;
                        pointer += 6;
                        break;
                    default:
                        throw new RuntimeException("more than 4 times");
                }
            } else if (series) {//==0并且前面有牌，然后这个位置断开了
                series = false;
                shape |= 1 << pointer++;
            }
            if (series && key % 9 == 8) {//数牌中最大的牌和下一张必须的断开的,但是这张牌需要有才计算，前面已经断开了这个位置
                series = false;
                shape |= 1 << pointer++;
            }
        }
        //考虑字牌
        for (int key = 27; key < 34; key++)
            if (h34[key] > 0) {
                pointer++;
                switch (h34[key]) {
                    case 1:
                        break;
                    case 2:
                        shape |= 0b11 << pointer;
                        pointer += 2;
                        break;
                    case 3:
                        shape |= 0b1111 << pointer;
                        pointer += 4;
                        break;
                    case 4:
                        shape |= 0b111111 << pointer;
                        pointer += 6;
                        break;
                    default:
                        throw new RuntimeException("more than 4");
                }
                shape |= 1 << pointer++;//字牌每张都是断开的
            }
        return shape;
    }
}
