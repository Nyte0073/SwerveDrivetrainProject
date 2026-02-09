package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Robot extends JPanel {

    private double frontLeftSwerveWheelAngle, frontRightSwerveWheelAngle, backLeftSwerveWheelAngle,
    backRightSwerveWheelAngle;

    private final LinkedList<RobotWheel> robotWheels = new LinkedList<>();

    public Robot() {
        setBackground(Color.DARK_GRAY);
        for(int i = 0; i < 4; i++) {
            RobotWheel robotWheel = new RobotWheel();
            add(robotWheel);
            robotWheels.add(robotWheel);
        }
    }

    public void setBackLeftSwerveWheelAngle(double backLeftSwerveWheelAngle) {
        this.backLeftSwerveWheelAngle = backLeftSwerveWheelAngle;
    }

    public void setBackRightSwerveWheelAngle(double backRightSwerveWheelAngle) {
        this.backRightSwerveWheelAngle = backRightSwerveWheelAngle;
    }

    public void setFrontLeftSwerveWheelAngle(double frontLeftSwerveWheelAngle) {
        this.frontLeftSwerveWheelAngle = frontLeftSwerveWheelAngle;
    }

    public void setFrontRightSwerveWheelAngle(double frontRightSwerveWheelAngle) {
        this.frontRightSwerveWheelAngle = frontRightSwerveWheelAngle;
    }

    public double getFrontLeftSwerveWheelAngle() {
        return frontLeftSwerveWheelAngle;
    }

    public double getFrontRightSwerveWheelAngle() {
        return frontRightSwerveWheelAngle;
    }

    public double getBackLeftSwerveWheelAngle() {
        return backLeftSwerveWheelAngle;
    }

    public double getBackRightSwerveWheelAngle() {
        return backRightSwerveWheelAngle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        robotWheels.getFirst().setAngle(frontLeftSwerveWheelAngle);
        robotWheels.get(1).setAngle(frontRightSwerveWheelAngle);
        robotWheels.get(2).setAngle(backLeftSwerveWheelAngle);
        robotWheels.get(3).setAngle(backRightSwerveWheelAngle);
        for(RobotWheel wheel : robotWheels) {
            wheel.repaint();
        }
    }
}
