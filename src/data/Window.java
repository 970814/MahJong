package data;

import ai.Controller;
import ai.MouseController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Window extends JFrame implements DataChangeListener {
    private Painter painter;
    private Driver driver;
    public Window(Painter painter, Driver driver) throws HeadlessException {
        this.painter = painter;
        this.driver = driver;
        init();
        initComponent();
    }

    private void initComponent() {
        setJMenuBar(new JMenuBar(){
            {
                add(new JMenu("开始") {
                    {
                        add(new JMenuItem("重置"){
                            {
                                addActionListener(e -> reset());
                            }
                        });
                    }
                });
            }
        });
        Controller controller = driver.getDataModel().getController(0);
        if (controller instanceof MouseController) {
            getContentPane().addMouseMotionListener((MouseMotionListener) controller);
            getContentPane().addMouseListener((MouseListener) controller);
        }
    }

    private void reset() {
        new Thread(() -> {
            driver.start();
        }).start();
    }

    public void init() {
        setContentPane(painter);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 840);
        setLocationRelativeTo(null);
        driver.setDataChangeListener(this);
        setAlwaysOnTop(true);
    }

    {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(32);
                    repaint();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    @Override
    public void onDataChanged() {

    }
}
