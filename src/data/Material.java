package data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Material {
    public static Image getImg(int index) {
        return imgs[index];
    }
    public static int w = 40;
    public static int h = 51;
    public static double w0 = 72.0;
    public static double h0 = 92.0;
    private static Image[] imgs;
    static {
        String parent = ".\\img\\";
        String suffix = ".png";
        try {
            AffineTransformOp ato
                    = new AffineTransformOp(AffineTransform.getScaleInstance(w / w0, h / h0)
                    , null);
            imgs = new Image[34];
            for (int i = imgs.length - 1; i >= 0; i--)
                imgs[i] = ato.filter(ImageIO.read(new File(parent, i + suffix)), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
