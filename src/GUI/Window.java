package GUI;

import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {

    private final double x, y;

    public Window(double angle) {
        x = Math.cos(Math.toRadians(angle + 90));
        y = Math.sin(Math.toRadians(angle + 90));
        System.out.println("x = " + x);
        System.out.println("y = " + y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        double halfScreenHeight = (double) getHeight() / 2,
        halfScreenWidth = (double) getWidth() / 2;

        g2d.drawLine((int) halfScreenWidth, (int) halfScreenHeight, (int) (halfScreenWidth + (x*200)), (int) (halfScreenHeight + (-y*200)));
    }
}
