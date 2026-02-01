import GUI.Window;
import ftclibs.Motor;
import subsystems.Constants;
import subsystems.driveables.ThreadBasedSwerveDrivetrain;
import vectors.Vector;

import javax.swing.*;

void main() {
         Vector.Pos originalPose = new Vector.Pos();
         Vector driverVector = new Vector();
         Vector.ProgramShouldContinue programShouldContinue = new Vector.ProgramShouldContinue();
         programShouldContinue.setShouldProgramContinue(true);
         Motor[] motors = new Motor[] {
                 new Motor(Constants.front_left),
                 new Motor(Constants.front_right),
                 new Motor(Constants.back_left),
                 new Motor(Constants.back_right)
         };
         ThreadBasedSwerveDrivetrain drivetrain = new ThreadBasedSwerveDrivetrain(
                () -> driverVector, () -> programShouldContinue, () -> originalPose, motors
        );
        originalPose.setPos(112);
        driverVector.setX(-1);
        driverVector.setY(-1);
        driverVector.setZ(0);
        drivetrain.drive();

    JFrame frame = new JFrame();
    frame.setSize(500, 500);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.add(new Window(-90));
    frame.setVisible(true);

    JFrame frame2 = new JFrame();
    frame2.setSize(500, 500);
    frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame2.add(new Window(0));
    frame2.setVisible(true);

    JFrame frame3 = new JFrame();
    frame3.setSize(500, 500);
    frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame3.add(new Window(-90));
    frame3.setVisible(true);

    JFrame frame4 = new JFrame();
    frame4.setSize(500, 500);
    frame4.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame4.add(new Window(-45));
    frame4.setVisible(true);
    }
