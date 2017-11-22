package data;

import ai.Player;
import card.Group;

import javax.swing.*;
import java.awt.*;

public class Painter extends JComponent{
    private DataModel model;

    public Painter(DataModel model) {
        this.model = model;
    }

//    private int getLength(String strValue) {
//        FontMetrics fm = getFontMetrics(getFont());
//        Rectangle2D bounds = fm.getStringBounds(strValue, null);
//        return (int) bounds.getWidth();
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D d = (Graphics2D) g;
        int height = getHeight();
        int width = getWidth();
        int x = width >>> 1;
        int y = height >>> 1;
        int from;
        int size;
        boolean flag = false;
        boolean first = true;
        boolean pressed = false;
        for (Player player : model.players) {
            int z = flag ? x : y;
            flag = !flag;
            d.translate(x, y);

            size = player.getGroups().size();
            if (size > 0) {//把已经杠的或碰的牌画出来
                from = -((size * 2 - 1) * GP.w >>> 1);
                for (Group group : player.getGroups()) {
                    for (int i = 3; i > 0; i--) {
                        d.drawImage(GP.getImage(group.getKey())
                                , from, z - GP.h, null);
                        from += GP.w;
                    }
                    if (group instanceof Group.$Same) {
                        switch (((Group.$Same) group).getType()) {
                            case Group.AnGang:
                                d.translate(from, z - GP.h);
                                d.rotate(Math.toRadians(-90));
                                d.drawImage(GP.getImage(group.getKey())
                                        , -(GP.h - (GP.h - GP.w) / 2)
                                        , -(3 * GP.w - (3 * GP.w - GP.h) / 2), null);
                                d.rotate(Math.toRadians(90));
                                d.translate(-from, -z + GP.h);
                                break;
                            case Group.BiaoGang:
                                d.translate(from - GP.w * 2, z - GP.h);
                                d.rotate(Math.toRadians(-45));
                                d.drawImage(GP.getImage(group.getKey())
                                        , -GP.w / 2
                                        , 0, null);
                                d.rotate(Math.toRadians(45));
                                d.translate(-from + GP.w * 2, -z + GP.h);
                                break;
                            case Group.MingGang:
                                d.translate(from - GP.w * 2, z - GP.h);
                                d.drawImage(GP.getImage(group.getKey())
                                        , -GP.w / 2
                                        , -(int) (GP.h / 4.0), null);
                                d.translate(-from + GP.w * 2, -z + GP.h);
                                break;
                            case Group.Peng:
                                break;
                            default:
                                throw new RuntimeException("unknown type: " + ((Group.$Same) group).getType());
                        }
                    }
                    from += GP.w >>> 1;
                }
            }
//            StringBuilder cards = new StringBuilder();
//            for (Integer key : player.keys) cards.append(Constant.get(key)).append(", ");
//            String str = cards.toString();
//            int strW = getLength(str);
//            d.drawString(str, -(strW >>> 1), 0.8f * y);
            int z0 = z;
            z *= 0.74f;
            size = player.keys.size();
            int w = Material.w * size;
            from = -((w + Material.w) >>> 1) - (Material.w);//奇数的情况下左边多一个,预留一个大空位
            int index = 0;
            for (Integer key : player.keys) {
                if (player.selectedKey == Player.Empty) {//如果还没有任何选中的突出的牌
                    if (player.pressedPoint != null) {
                        int x0 = x + from;
                        int y0 = z0 + z;
                        if (withinBounds(x0, y0, Material.w, Material.h, player.pressedPoint)) {
                            pressed = true;
                            z -= Material.h >>> 1;//点击的牌要突出
                            player.selectedKey = index;//选中一张牌
                        }
                    }
                } else {
                    if (index == player.selectedKey) {//如果已经选中了一张牌并且是当前要绘制的
                        pressed = true;
                        z -= Material.h >>> 1;
                        if (player.pressed2 != null) {//如果点击了第二次
                            int x0 = x + from;
                            int y0 = z0 + z;
                            if (withinBounds(x0, y0, Material.w, Material.h, player.pressed2))
                                player.selected = true;//开始打出一张牌
                            else {
                                pressed = false;
                                z += Material.h >>> 1;
                                player.selectedKey = Player.Empty;//这个时候并没有改变pressedPoint，设置selectedKey为Empty，回到第一次开始判断的状态
                                player.pressed2 = null;
                            }
                        }
                    }
                }
                if (first) {//控制移动的蓝色边框
                    if (player.movePoint != null) {
                        int x0 = x + from;
                        int y0 = z0 + z;
                        if (withinBounds(x0, y0, Material.w, Material.h, player.movePoint)) {
                            first = false;
                            xy.setLocation(x0, y0);
                        } /*else {
                            player.pressedPoint = null;//movePoint最后一次的值和pressedPoint和pressed2的值是一样的，
                            //如果有pressedPoint点击，那么如果没有出现蓝色边框，说明没有点击任何牌
                        }*/
                    }
                }
                d.drawString(String.valueOf(index++), from, z);
                d.drawImage(Material.getImg(key), from, z, null);
                if (pressed) {//其他牌的位置要还原
                    pressed = false;
                    z += Material.h >>> 1;
                }
                from += Material.w;
            }
            if (player.getPendingKey() != Player.Empty) {
                d.drawString(String.valueOf(-1), from + (Material.w >>> 1), z);
                d.drawImage(Material.getImg(player.getPendingKey())
                        , from + (Material.w >>> 1), z, null);
            }
            d.rotate(Math.toRadians(-90));
            d.translate(-x, -y);
        }
        if (!first) {
            d.setColor(Color.BLUE);
            d.setStroke(new BasicStroke(6.0f));
            d.drawRect(xy.x, xy.y, Material.w, Material.h);
        } else {

        }
    }

    private Point xy = new Point();

    private boolean withinBounds(int x0, int y0, int w, int h, Point point) {
        int x2 = x0 + w;
        int y2 = y0 + h;
        return point.x > x0 && point.x < x2 && point.y > y0 && point.y < y2;
    }

}
