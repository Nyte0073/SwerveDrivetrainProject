package subsystems.driveables;

import ftclibs.Motor;
import vectors.Vector;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class ThreadBasedSwerveDrivetrain extends Swerve {

    private final EnumMap<Vector.WheelType, Double> wheelPositions = new EnumMap<>(Vector.WheelType.class);
    private static boolean rotTransOrJustTrans = false;

    public ThreadBasedSwerveDrivetrain(Supplier<Vector> vectorSupplier, Supplier<Boolean> robotProgramShouldContinue, Supplier<Double> originalPosSupplier, Motor[] motors) {
        super(vectorSupplier, robotProgramShouldContinue, originalPosSupplier, motors, () -> rotTransOrJustTrans);

        wheelPositions.put(Vector.WheelType.FRONT_LEFT, 180.0);
        wheelPositions.put(Vector.WheelType.FRONT_RIGHT, 180.0);
        wheelPositions.put(Vector.WheelType.BACK_LEFT, 180.0);
        wheelPositions.put(Vector.WheelType.BACK_RIGHT, 180.0);
    }

    @Override
    public EnumMap<Vector.WheelType, Vector> calculateSwerveWheelHeadings(Vector driverVector, boolean rotating, boolean clockwise, double originalPos) {
        EnumMap<Vector.WheelType, Vector> translatedAndOrRotatedVectors = new EnumMap<>(Vector.WheelType.class);
        if(rotating) {
            System.out.println("Rotating ran.");
            for(Vector.WheelType wheelType : Vector.WheelType.wheelTypes) {
                Vector rotatedVector, translatedAndRotatedVector;
                double x = wheelType.getVector().getX();
                double y = wheelType.getVector().getY();
                if(clockwise) {
                    rotatedVector = Vector.rotate90DegreesClockwise.apply(x, y);
                } else {
                    rotatedVector = Vector.rotate90DegreesCounterClockwise.apply(x, y);
                }
                translatedAndRotatedVector = rotatedVector.plus(driverVector);
                double translatedAndRotatedX = translatedAndRotatedVector.getX();
                double translatedAndRotatedY = translatedAndRotatedVector.getY();
                double targetHeading = Math.toDegrees(Math.atan2(translatedAndRotatedY, translatedAndRotatedX)) - 90;
                double normalizedHeading = normalizeHeading(targetHeading, originalPos + wheelPositions.get(wheelType));
                double totalHeading = wheelPositions.get(wheelType) + normalizedHeading;
                double reversedHeading = reverseHeading(wheelPositions.get(wheelType), totalHeading, normalizedHeading);
                translatedAndOrRotatedVectors.put(wheelType, new Vector(
                        Math.cos(Math.toRadians(reversedHeading)), Math.sin(Math.toRadians(reversedHeading)), driverVector.getMagnitude()
                ));
                rotTransOrJustTrans = true;

                /*TODO needs work.*/
            }
        } else {
            if(Math.abs(driverVector.getY()) < 0.1 && Math.abs(driverVector.getX()) < 0.1) {
                translatedAndOrRotatedVectors.put(Vector.WheelType.FRONT_LEFT, new Vector(0, 0));
                translatedAndOrRotatedVectors.put(Vector.WheelType.FRONT_RIGHT, new Vector(0, 0));
                translatedAndOrRotatedVectors.put(Vector.WheelType.BACK_LEFT, new Vector(0, 0));
                translatedAndOrRotatedVectors.put(Vector.WheelType.BACK_RIGHT, new Vector(0, 0));
                return translatedAndOrRotatedVectors;
            }
            double targetAngle = Math.toDegrees(Math.atan2(driverVector.getY(),
                    driverVector.getX())) - 90;
            System.out.println("targetAngle = " + targetAngle);

            for(Map.Entry<Vector.WheelType, Double> entry : wheelPositions.entrySet()) {
                System.out.println("targetAngle = " + targetAngle);
                System.out.println("(originalPos + entry.getValue()) = " + (originalPos + entry.getValue()));
                double normalizedHeading = normalizeHeading(targetAngle, originalPos + entry.getValue());
                double totalHeading = entry.getValue() + normalizedHeading;
                System.out.println("totalHeading = " + totalHeading);
                double reversedHeading = reverseHeading(entry.getValue(), totalHeading, normalizedHeading);
                System.out.println("reversedHeading = " + reversedHeading);
                translatedAndOrRotatedVectors.put(entry.getKey(), new Vector(
                        Math.cos(Math.toRadians(reversedHeading)), Math.sin(Math.toRadians(reversedHeading)), driverVector.getMagnitude()
                ));
                wheelPositions.put(entry.getKey(), reversedHeading);
                rotTransOrJustTrans = false;
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
