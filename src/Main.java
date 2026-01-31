import ftclibs.Motor;
import subsystems.Constants;
import subsystems.driveables.ThreadBasedSwerveDrivetrain;
import vectors.Vector;

     void main() {
         Vector.Pos originalPose = new Vector.Pos();
         Vector driverVector = new Vector();
         Vector.ProgramShouldContinue programShouldContinue = new Vector.ProgramShouldContinue();
         programShouldContinue.setShouldProgramContinue(false);
         Motor[] motors = new Motor[] {
                 new Motor(Constants.front_left),
                 new Motor(Constants.front_right),
                 new Motor(Constants.back_left),
                 new Motor(Constants.back_right)
         };
         ThreadBasedSwerveDrivetrain drivetrain = new ThreadBasedSwerveDrivetrain(
                () -> driverVector, () -> programShouldContinue, () -> originalPose, motors
        );
        originalPose.setPos(0);
        driverVector.setX(1);
        driverVector.setY(1);
        drivetrain.drive();
        originalPose.setPos(100);
        drivetrain.drive();
    }