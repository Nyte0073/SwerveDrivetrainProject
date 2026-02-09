package GUI;

import javax.swing.*;
import java.awt.*;

public class RobotWheel extends JPanel {

    private double angle;

    public RobotWheel() {
        setBackground(Color.WHITE);
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int middleX = width / 2;
        int middleY = height / 2;
        int calculatedX = (int) -(Math.cos(Math.toRadians(angle - 90)) * width) + middleX;
        int calculatedY = (int) (Math.sin(Math.toRadians(angle - 90)) * height) + middleY;
        g2d.setColor(Color.BLACK);
        g2d.drawLine(middleX, middleY, calculatedX, calculatedY);
    }
}
