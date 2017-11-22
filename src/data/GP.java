package data;

import card.Group;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

public class GP {
    private final static double sx = 0.8;
    private final static double sy = 0.8;
    public static int w = (int) (Material.w * sx);
    public static int h = (int) (Material.h * sy);
    private static Image[] images = new Image[34];

    static {
        AffineTransformOp ato
                = new AffineTransformOp(AffineTransform.getScaleInstance(sx, sy), null);
        for (int i = images.length - 1; i >= 0; i--)
            images[i] = ato.filter((BufferedImage) Material.getImg(i), null);
    }

    public static Image getImage(int index) {
        return images[index];
    }

    public static Image getImage(List<Group> groups) {
        Image[] images = new Image[groups.size()];
        int[] types = new int[images.length];
        int i = 0;
        AffineTransformOp ato
                = new AffineTransformOp(AffineTransform.getScaleInstance(0.8, 0.8), null);
        for (Group group : groups) {
            images[i] = ato.filter((BufferedImage) Material.getImg(group.getKey()), null);
            if (group instanceof Group.Same) {
                types[i] = Group.Peng;
            } else if (group instanceof Group.$Same) {
                types[i] = ((Group.$Same) group).getType();
            } else {
                throw new RuntimeException("unknown Type: " + group.getClass());
            }
        }
        int w = (int) (Material.w * 0.8);
        int W = (groups.size() * 2 - 1) * w;
        int H = (int) (Material.h * 0.8);
        BufferedImage bufferedImage = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        int from = 0;
        for (int j = 0; j < images.length; j++) {
            for (int k = 0; k < 3; k++) {
                graphics.drawImage(images[i], from, 0, null);
                from += images[i].getWidth(null);
            }
            from += w >>> 1;
        }
        return bufferedImage;
    }
}
