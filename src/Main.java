import ftclibs.Motor;
import subsystems.driveables.ThreadBasedSwerveDrivetrain;
import vectors.Vector;

public class Main {

    public static void main() {
        ThreadBasedSwerveDrivetrain swerveDrivetrain = new ThreadBasedSwerveDrivetrain(
                () -> new Vector(1, 0.5, 1), () -> true, () -> 0.0, new Motor[] {
                        new Motor(Vector.WheelType.FRONT_LEFT), new Motor(Vector.WheelType.FRONT_RIGHT),
                new Motor(Vector.WheelType.BACK_LEFT), new Motor(Vector.WheelType.BACK_RIGHT)
        }
        );
        swerveDrivetrain.drive();
    }
}