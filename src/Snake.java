import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.Timer;
import javax.swing.text.TabableView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.*;


/**
 * Created by Хускар on 08.03.2016.
 */
public class Snake implements ActionListener {

    int t;
    int n = 100;

    JFrame frame = new JFrame("Snake");
    JPanel panel = new JPanel();
    JLabel a[] = new JLabel[1200];
    JButton b[] = new JButton[1200];

    int[] znach = new int[1200];
    int head = 453;
    int naprav = 1;
    ArrayList<Integer> mask = new ArrayList<Integer>();

    ImageIcon X = new ImageIcon(Snake.class.getResource("1.jpg"));
    ImageIcon O = new ImageIcon(Snake.class.getResource("2.jpg"));
    ImageIcon Z = new ImageIcon(Snake.class.getResource("3.jpg"));
    ImageIcon Y = new ImageIcon(Snake.class.getResource("4.jpg"));

    Timer timer = new Timer(n, this);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Snake());
    }

    Snake() {
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
        panel.setFocusable(true);
        GridLayout layout = new GridLayout(30, 40);
        layout.setVgap(0);
        panel.setLayout(layout);


        for (int i = 0; i < a.length; i++) {
            a[i] = new JLabel();
            a[i].setSize(10, 10);
            a[i].setIcon(X);
            znach[i] = 0;
            panel.add(a[i]);

        }
        for (int k = 0; k < 40; k++) {
            if (k < 30) {
                if (k > 0) {
                    znach[k * 40] = 3;
                    znach[(k * 40) - 1] = 3;
                    a[k * 40].setIcon(Y);
                    a[(k * 40) - 1].setIcon(Y);
                }
            }
            znach[k] = 3;
            znach[k + 1160] = 3;
            a[k].setIcon(Y);
            a[k + 1160].setIcon(Y);
        }

        frame.setVisible(true);
        start();

    }

    public void start() {
        //mask = {450, 451, 452, 453};
        mask.add(450);
        mask.add(451);
        mask.add(452);
        mask.add(453);
        snakePaint(mask);
        randomapple();
        timer.start();
        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println(head);
                if ((e.getKeyCode() == 40) && znach[head + 40] == 0) {
                    naprav = 40;
                }
                if ((e.getKeyCode() == 39) && znach[head + 1] == 0) {

                    naprav = 1;
                }
                if ((e.getKeyCode() == 38) && znach[head - 40] == 0) {
                    naprav = -40;
                }
                if ((e.getKeyCode() == 37) && znach[head - 1] == 0) {

                    naprav = -1;
                }
            }
        });
    }

    public void snakePaint(ArrayList<Integer> t) {
        for (int i = 0; i < t.size(); i++) {
            a[t.get(i)].setIcon(O);
            znach[t.get(i)] = 1;
        }
    }

    public void randomapple() {
        Random random = new Random();
        do {
            t = random.nextInt(1200);
        } while (znach[t] != 0);
        a[t].setIcon(Z);
        znach[t] = 2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        runs();
        repaint(head);
        panel.repaint();

    }

    public void runs() {

        if (znach[head + naprav] == 3 || znach[head + naprav] == 1 || znach[head + naprav] == 4) {
            timer.stop();
        }
        head = head + naprav;
        mask.add(head);

    }

    public void repaint(int perehod) {
        try {

            if (znach[perehod] == 0) {
                a[perehod].setIcon(O);
                a[mask.get(0)].setIcon(X);
                znach[perehod] = 1;
                znach[mask.get(0)] = 0;
                mask.remove(0);

            } else if (znach[perehod] == 2) {
                a[perehod].setIcon(O);
                znach[perehod] = 1;
                randomapple();
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            frame.setVisible(false);
        }
    }
}
