package subsystems.driveables;

import vectors.Vector;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ThreadBasedSwerveDrivetrain extends Swerve {

    private final EnumMap<Vector.WheelType, Double> wheelPositions = new EnumMap<>(Vector.WheelType.class);

    public ThreadBasedSwerveDrivetrain(Supplier<Vector> vectorSupplier, Supplier<Boolean> robotProgramShouldContinue, Supplier<Double> originalPosSupplier) {
        super(vectorSupplier, robotProgramShouldContinue, originalPosSupplier);

        wheelPositions.put(Vector.WheelType.FRONT_LEFT, 0.0);
        wheelPositions.put(Vector.WheelType.FRONT_RIGHT, 0.0);
        wheelPositions.put(Vector.WheelType.BACK_LEFT, 0.0);
        wheelPositions.put(Vector.WheelType.BACK_RIGHT, 0.0);
    }

    @Override
    public List<Vector> calculateSwerveWheelHeadings(Vector driverVector, boolean rotating, boolean clockwise, double originalPos) {
        List<Vector> translatedAndOrRotatedVectors = new ArrayList<>();
        if(rotating) {
            for(Vector.WheelType wheelType : Vector.WheelType.wheelTypes) {
                Vector rotatedVector, translatedAndRotatedVector;
                double x = driverVector.getX();
                double y = driverVector.getY();
                if(clockwise) {
                    rotatedVector = Vector.rotate90DegreesClockwise.apply(x, y);
                } else {
                    rotatedVector = Vector.rotate90DegreesCounterClockwise.apply(x, y);
                }
                translatedAndRotatedVector = rotatedVector.plus(driverVector);
                translatedAndOrRotatedVectors.add(translatedAndRotatedVector);
            }
        } else {
            double targetAngle = Math.toDegrees(Math.atan2(driverVector.getY(),
                    driverVector.getX())) - 90;

            for(Map.Entry<Vector.WheelType, Double> entry : wheelPositions.entrySet()) {
                double normalizedHeading = normalizeHeading(targetAngle, originalPos + entry.getValue());
                double totalHeading = entry.getValue() + normalizedHeading;
                double reversedHeading = reverseHeading(originalPos, totalHeading, targetAngle);
            }

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
