package data;

import ai.Player;
import card.Constant;
import data.DataModel;

import javax.swing.*;
import java.awt.*;

public class Painter extends JComponent {
    DataModel model = new DataModel();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D d = (Graphics2D) g;
        int height = getHeight();
        int width = getWidth();
        int x = width >>> 1;
        int y = height >>> 1;
        for (Player player : model.players){
            d.translate(x, y);
            StringBuilder cards = new StringBuilder();
            for (Integer key : player.keys) cards.append(Constant.get(key) + ", ");
            d.drawString(cards.toString(), 0, 4 / 5.0f * y);
            d.rotate(Math.toRadians(-90));
            d.translate(-x, -y);
        }
    }

    public static void main(String[] args) {
        new JFrame(){
            {
                setContentPane(new Painter());
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setSize(800, 600);
                setLocationRelativeTo(null);
            }
        }.setVisible(true);
    }
}
