import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame {
    private Game game;
    private final int COLLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 6;
    private final int IMAGE = 50;
    private JPanel panel;
    private JLabel label;

    public static void main(String[] args) {

        new JavaSweeper();
    }

    private JavaSweeper() {
        game = new Game(COLLS, ROWS, BOMBS);
        game.start();
        setImages();
        initPanel();
        initLabel();
        initFrame();

    }

    private void initLabel() {
        label = new JLabel(getMessage());
        Font font = new Font("Tahoma", Font.BOLD, 20);
        label.setFont(font);
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                for (Coord coord : Ranges.getAllCoords())

                    graphics.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE, coord.y * IMAGE, this);

            }
        };


        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE;
                int y = e.getY() / IMAGE;
                Coord coord = new Coord(x, y);
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        game.pressLeftButton(coord);
                        break;
                    case MouseEvent.BUTTON3:
                        game.pressRightButton(coord);
                        break;
                    case MouseEvent.BUTTON2:
                        game.start();
                        break;
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE, Ranges.getSize().y * IMAGE));
        add(panel);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("JavaSweeper");


        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void setImages() {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
        setIconImage(getImage("icon"));
    }

    private Image getImage(String name) {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    private String getMessage() {
        String d;
        switch (game.getState()) {
            case IS_BOMBED:
                return "Ba-Da-Boom!You Lose!";
            case WON:
                return "Congratulation!All bombs have been marked";
            case PLAY:
            default:
                return d = (game.getTotalFlaged() == 0) ? "Welcome" : "Think twice! Flagged" + game.getTotalFlaged() + " of "
                        + game.getTotalBombs() + " bombs. ";
        }
    }


}
