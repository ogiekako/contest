package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

public class Paint {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        final int Dx = in.nextInt(), Dy = in.nextInt();
        final int Fx = in.nextInt(), Fy = in.nextInt();
        final int[] x = new int[n], y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt(); y[i] = in.nextInt();
        }
        final int OFF = 250;
        final int R = 4;
        final int M = 10;
        JPanel panel = new JPanel() {
            Graphics g;
            @Override
            protected void paintComponent(Graphics g) {
                this.g = g;
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, OFF + OFF, OFF + OFF);
                g.setColor(Color.RED);
                g.drawLine(OFF - 1, OFF - 1, Dx * M + OFF - 1, -Dy * M + OFF - 1);
                g.setColor(Color.GREEN);
                draw(0, 0, Shape.Circle);
                g.setColor(new Color(165, 42, 42));// brown
                draw(Dx, Dy, Shape.Triangle);
                g.setColor(Color.CYAN);
                draw(Fx, Fy, Shape.Square);
                g.setColor(Color.GREEN);
                for (int i = 0; i < x.length; i++) {
                    draw(x[i], y[i], Shape.Circle);
                }
            }

            private void draw(int x, int y, Shape shape) {
                y = -y;
                x *= M; y *= M;
                x += OFF; y += OFF;
                if (shape.equals(Shape.Circle))
                    g.fillOval(x - R, y - R, R + R, R + R);
                else if (shape.equals(Shape.Square)) {
                    g.fillRect(x - R, y - R, R + R, R + R);
                } else if (shape.equals(Shape.Triangle)) {
                    int[] xs = {x, x - R, x + R};
                    int[] ys = {y - R, y + R, y + R};
                    g.fillPolygon(new Polygon(xs, ys, 3));
                } else throw new RuntimeException();
            }
        };
        panel.setPreferredSize(new Dimension(OFF + OFF, OFF + OFF));
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    enum Shape {
        Triangle,
        Square,
        Circle;
    }
}
