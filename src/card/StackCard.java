package card;

import java.util.function.Consumer;

import static card.Constant.getCards;

public class StackCard {//牌堆
    public StackCard() {
        cards = getCards();
        left = cards.length;
    }

    public int pop() {//弹出牌堆顶的卡片
        return cards[--left];
    }
    private int[] cards;
    private int left;
    private Shuffler shuffler = new Shuffler();

    public StackCard wash() {
        return wash(1);
    }

    private StackCard wash(int count) {
        for (int i = count; i > 0; i--)
            shuffler.wash(cards, 0, left);
        return this;
    }

    public String get(int index) {
        return Constant.get(index);
    }

    public int left() {
        return left;
    }

    public static void main(String[] args) {
        StackCard card = new StackCard();
        card.show();
        card.wash();
        card.show();
    }

    public StackCard show() {
        for (int i = 0; i < left(); i++) {
            System.out.print(get(i));
            if (i % 4 == 3) System.out.println();
        }
        return this;
    }

    void forEach(Consumer<Integer> consumer) {
        for (int key : cards) consumer.accept(key);
    }
}
