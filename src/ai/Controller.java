package ai;

import java.util.Random;

public class Controller {
    private final static Random random = new Random();
    protected Player player;
    public Controller(Player player) {
        this.player = player;
    }

    //打牌
    public int poll(int size) {
        try {
            Thread.sleep(100 + random.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return random.nextInt(size + 1);//随机
    }

    //是否胡
    public boolean hu() {
        return true;
    }

    //要不要杠
    public boolean gang() {
        return true;
    }

    //要不要碰
    public boolean peng() {
        return true;
    }
    public void reset() {
        //mouseController阻塞的一些打牌判断可能阻塞了线程,重置来中断他们
    }
}
