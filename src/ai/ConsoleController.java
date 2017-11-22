package ai;

import card.Constant;

import java.util.Scanner;

public class ConsoleController extends Controller {
    Scanner scanner;
    public ConsoleController(Player player, Scanner scanner) {
        super(player);
        this.scanner = scanner;
    }

    @Override
    public int poll(int size) {
        System.out.println("轮到" + player + "出牌");
        int index;
        do {
            String word = scanner.next();
            try {
                index = Integer.parseInt(word);//按索引位置
            } catch (Exception e) {
                int key;
                System.out.println("try to parse an key: " + word);
                key = Constant.get(word);
                index = player.keys.indexOf(key);
                if (index == -1) break;
            }
            if (index >= -1 && index < size) break;
            else System.out.println("不存在这张牌");
        } while (true);
        return index;
    }

    @Override
    public boolean hu() {
        return super.hu();
    }

    @Override
    public boolean gang() {
        return super.gang();
    }

    @Override
    public boolean peng() {
        return super.peng();
    }
}
