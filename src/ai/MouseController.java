package ai;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

public class MouseController extends Controller implements MouseListener,MouseMotionListener {
    public boolean notReset = true;
    public boolean flags[] = {false};
    public boolean F[] = {true};
    public MouseController(Player player) {
        super(player);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("mousePressed.selectedKey: " + player.selectedKey + player);
        if (player.selectedKey != Player.Empty) {
            player.pressed2 = e.getPoint();
        } else {
            player.pressed2 = null;
        }
        player.pressedPoint = e.getPoint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.movePoint = e.getPoint();
//        System.out.println("current location: " + e.getPoint().x + ", " + e.getPoint().y);
    }

    @Override
    public int poll(int size) {
        flags[0] = false;
        while (notReset && !player.selected) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int key = player.selectedKey;
        player.selectedKey = Player.Empty;
        player.selected = false;
        player.pressedPoint = null;
        player.pressed2 = null;

        flags[0] = true;
        return key;
    }

    private boolean first = true;
    @Override
    public void reset() {
        if (first) {
            first = false;
            return;
        }
        notReset = false;
        while (!Arrays.equals(flags, F)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notReset = true;
    }
}
