package net.ogiekako.algorithm.geometry;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//
@SuppressWarnings("serial")
public class Vis extends JFrame {
    double D = 1;
    int H = 600, W = 600;
    public Vis() {
        setSize(W + 20, H + 40);
        setVisible(true);
        add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.clearRect(0, 0, H, W);
                g.setColor(Color.blue);
                for (int i = 0; i < ss.size(); i++) {
                    Point s = ss.get(i), t = ts.get(i);
                    g.drawLine(toi(s.x) + W / 2, H - toi(s.y) - H / 2, toi(t.x) + W / 2, H - toi(t.y) - H / 2);
                }
                for (int i = 0; i < os.size(); i++) {
                    g.setColor(ccs.get(i));
                    Point o = os.get(i);
                    double r = rs.get(i);
                    g.drawOval(toi(o.x - r) + W / 2, H - toi(o.y + r) - H / 2, toi(r * 2), toi(r * 2));
                    g.drawString(cnames.get(i), toi(o.x - r) + W / 2, H - toi(o.y + r) - H / 2);
                }
            }
            int toi(double d) {
                return (int) Math.round(d * D);
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                stop = false;
            }
        });
    }
    boolean stop = false;
    void vis() {
        ((JPanel) getContentPane()).paintImmediately(0, 0, 600, 600);
        try {
            stop = true;
            while (stop) Thread.sleep(10);
        } catch (Exception e) {
        }
    }
    ArrayList<Point> ss = new ArrayList<Point>();
    ArrayList<Point> ts = new ArrayList<Point>();
    public void addEdge(Point p1, Point p2) {
        ss.add(p1);
        ts.add(p2);
    }
    ArrayList<Point> os = new ArrayList<Point>();
    ArrayList<Double> rs = new ArrayList<Double>();
    ArrayList<Color> ccs = new ArrayList<Color>();
    ArrayList<String> cnames = new ArrayList<String>();
    public void addCircle(Point o, double r, Color c, String name) {
        os.add(o);
        rs.add(r);
        ccs.add(c);
        cnames.add(name);
    }
    void addCircle(Point o, double r, Color c) {
        addCircle(o, r, c, "");
    }
    void addCircle(Point o, double r) {
        addCircle(o, r, Color.BLUE);
    }
    void clear() {
        ss.clear(); ts.clear(); os.clear(); rs.clear();
        vis();
    }
}
