package tmp.marathon78;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class App2 extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel nextTileLabel;
    private JLabel boardLabel;

    TwistedGameTester tester;
    String seed = "1";

    public App2() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        addKeyActions();

        tester = new TwistedGameTester(seed);

        showCurTile();
    }

    private void addKeyActions() {
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                put(1);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                put(2);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                put(3);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                put(4);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                turn(3);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                turn(1);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                giveUp();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void giveUp() {

    }

    void put(int t) {
        System.out.println("put " + t);
    }

    String curTileFileName = "curTile.dmp";
    PrintWriter dmpCurTile;
    int[] curTile = new int[]{0, 2, 1, 3, 4, 5, 6, 7};
    int rot;

    void turn(int r) {
        curTile = TwistedGameTester.rotateTile(curTile, r);
        rot = (rot + r) % 4;
        showCurTile();
    }

    private void showCurTile() {
        try {
            dmpCurTile = new PrintWriter(curTileFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        dmpCurTile.println(10);
        for (int i = 0; i < 4; i++) {
            dmpCurTile.println("10 10 " + curTile[2 * i] + " " + curTile[2 * i + 1] + " B");
        }
        dmpCurTile.flush();
        dmpCurTile.close();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(curTileFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        BufferedImage image = TwistedGameDrawer.generateImageFromBuffer(br);

        Icon nextPieceIcon = new ImageIcon(image);
        nextTileLabel.setIcon(nextPieceIcon);
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        App2 dialog = new App2();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
