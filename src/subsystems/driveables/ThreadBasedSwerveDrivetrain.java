package subsystems.driveables;

import vectors.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ThreadBasedSwerveDrivetrain extends Swerve {


    public ThreadBasedSwerveDrivetrain(Supplier<Vector> vectorSupplier, Supplier<Boolean> robotProgramShouldContinue, Supplier<Double> originalPosSupplier) {
        super(vectorSupplier, robotProgramShouldContinue, originalPosSupplier);
    }

    @Override
    public List<Vector> calculateSwerveWheelHeadings(Vector driverVector, boolean rotating, boolean clockwise, double originalPos) {
        List<Vector> translatedAndOrRotatedVectors = new ArrayList<>();
        if(rotating) {
            for(Vector.WheelType wheelType : Vector.WheelType.wheelTypes) {

            }
        } else {
            double targetAngle = Math.toDegrees(Math.atan2(driverVector.getY(),
                    driverVector.getX())) - 90;
            double normalizedHeading = normalizeHeading(targetAngle, originalPos);
            double totalHeading = originalPos + normalizedHeading;
            double reversedHeading = reverseHeading(originalPos, totalHeading, targetAngle);
        }

        return translatedAndOrRotatedVectors;
    }

    @Override
    public void stopThreads() {

    }

    @Override
    public void stopMotors() {

    }

    @Override
    public double normalizeHeading(double targetPos, double currentPos) {
        return (targetPos - currentPos + 540) % 360 - 180;
    }

    @Override
    public double reverseHeading(double originalPos, double totalPos, double targetPos) {
        return Math.abs(totalPos) > 180 ? normalizeHeading(totalPos < 0 ? totalPos + 180 : totalPos - 180, originalPos) : targetPos;
    }
}
