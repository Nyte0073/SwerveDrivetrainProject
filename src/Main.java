import GUI.Robot;
import ftclibs.Motor;
import subsystems.Constants;
import subsystems.driveables.SwerveDrive;
import vectors.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class Main {

    static void main() {
        Vector driverVector = new Vector();
        Vector.Pos currentIMUOrientation = new Vector.Pos();
        Supplier<Vector> driverVectorSupplier = () -> driverVector;
        Supplier<Double> currentIMUOrientationSupplier = currentIMUOrientation::getPos;
        SwerveDrive swerveDrive = new SwerveDrive(driverVectorSupplier, currentIMUOrientationSupplier);
        List<Motor> turningMotors = List.of(
              new Motor(), new Motor(), new Motor(), new Motor()
        ), drivingMotors = List.of(
               new Motor(), new Motor(), new Motor(), new Motor()
        );
        Constants.initConstants(turningMotors, drivingMotors);
        currentIMUOrientation.setPos(0);
        driverVector.setX(1);
        driverVector.setY(-1);

        System.out.println("Target Angle: " + (Math.toDegrees(Math.atan2(driverVector.getY(), driverVector.getX())) - 90));

        driverVector.setZ(1);
        swerveDrive.drive();

        Constants.motorsAndTheirRotatedAndTranslatedVectors.values().forEach(vector ->
                    System.out.println("Vector X: " + vector.getX() + ", Vector Y: " + vector.getY())
        );

        System.out.println();

        List<Double> swerveModAngles = new LinkedList<>();
        for(Motor turningMotor : Constants.turningMotors) {
            System.out.println("Turning motor heading: " + (turningMotor.getCurrentPosition() / 1440 * 360));
            swerveModAngles.add(turningMotor.getCurrentPosition() / 1440 * 360);
        }

        System.out.println();

        for(Motor motor : Constants.drivingMotors) {
            System.out.println("Motor driving magnitude: " + motor.getMotorPower());
        }

            Robot robot = new Robot();
            robot.setLayout(new GridLayout(2, 2, 10, 10));
            robot.setFrontLeftSwerveWheelAngle(swerveModAngles.getFirst());
            robot.setFrontRightSwerveWheelAngle(swerveModAngles.get(1));
            robot.setBackLeftSwerveWheelAngle(swerveModAngles.get(2));
            robot.setBackRightSwerveWheelAngle(swerveModAngles.get(3));

            JFrame frame = new JFrame("Swerve Robot Visualizer");
            frame.setSize(new Dimension(1000, 1000));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.getContentPane().add(robot);
            frame.setVisible(true);


    }
}

