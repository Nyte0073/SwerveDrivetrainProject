package subsystems.driveables;

import vectors.Vector;

import java.util.List;
import java.util.function.Supplier;

public abstract class Swerve implements Driveable {

    private final Supplier<Vector> vectorSupplier;
    private final Supplier<Boolean> robotProgramShouldContinue;
    private final Supplier<Double> originalPosSupplier;

    public Swerve(Supplier<Vector> vectorSupplier, Supplier<Boolean> robotProgramShouldContinue, Supplier<Double> originalPosSupplier) {
        this.vectorSupplier = vectorSupplier;
        this.robotProgramShouldContinue = robotProgramShouldContinue;
        this.originalPosSupplier = originalPosSupplier;
    }

    @Override
    public void drive() {
        Vector driverVector = vectorSupplier.get();
        boolean robotProgramCon = robotProgramShouldContinue.get();
        double rotationPower = driverVector.getZ();
        double originalPos = originalPosSupplier.get();
        List<Vector> swerveWheelHeadings;

        if(!robotProgramCon) {
            stop();
        } else if(rotationPower >= 0.05) {
            swerveWheelHeadings = calculateSwerveWheelHeadings(driverVector, true, true, originalPos);
        } else {
            swerveWheelHeadings = calculateSwerveWheelHeadings(driverVector, false, false, originalPos);
        }

        /*Set the power of the motors down here in Android Studio.*/
    }

    @Override
    public void stop() {
        stopMotors();
        stopThreads();
    }

    public abstract List<Vector> calculateSwerveWheelHeadings(Vector driverVector, boolean rotating, boolean clockwise, double originalPos);
    public abstract void stopThreads();
    public abstract void stopMotors();
    public abstract double normalizeHeading(double targetPos, double currentPos);
    public abstract double reverseHeading(double originalPos, double totalPos, double targetPos);

}
