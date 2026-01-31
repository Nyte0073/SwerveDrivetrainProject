package subsystems.driveables;

import ftclibs.Motor;
import vectors.Vector;

import java.util.EnumMap;
import java.util.function.Supplier;

public abstract class Swerve implements Driveable {

    private final Supplier<Vector> vectorSupplier;
    private final Supplier<Boolean> robotProgramShouldContinue, rotTransOrJustTrans;
    private final Supplier<Double> originalPosSupplier;
    private final Motor[] motors;

    public Swerve(Supplier<Vector> vectorSupplier, Supplier<Boolean> robotProgramShouldContinue, Supplier<Double> originalPosSupplier, Motor[] motors, Supplier<Boolean> rotTransOrJustTrans) {
        this.vectorSupplier = vectorSupplier;
        this.robotProgramShouldContinue = robotProgramShouldContinue;
        this.originalPosSupplier = originalPosSupplier;
        this.rotTransOrJustTrans = rotTransOrJustTrans;
        this.motors = motors;
    }

    @Override
    public void drive() {
        Vector driverVector = vectorSupplier.get();
        boolean robotProgramCon = robotProgramShouldContinue.get();
        double rotationPower = driverVector.getZ();
        double originalPos = originalPosSupplier.get();
        EnumMap<Vector.WheelType, Vector> swerveWheelHeadings = new EnumMap<>(Vector.WheelType.class);

        if(!robotProgramCon) {
            stop();
        } else if(rotationPower >= 0.05) {
            swerveWheelHeadings = calculateSwerveWheelHeadings(driverVector, true, true, originalPos);
        } else {
            swerveWheelHeadings = calculateSwerveWheelHeadings(driverVector, false, false, originalPos);
        }

        swerveWheelHeadings.values().forEach(vector -> {
            System.out.println("Vector x: " + vector.getX() + ", Vector y: " + vector.getY() + "\n\n");
            System.out.println("Vector Magnitude: " + vector.getMagnitude());
        });

        for(Motor motor : motors) {
            Vector vector = swerveWheelHeadings.get(motor.getWheelType());
            if(rotTransOrJustTrans.get()) {
                System.out.println("Translated and rotated.");
            } else {
                double heading = Math.atan2(vector.getY(), vector.getX());
                motor.setWheelHeading(heading);
                motor.setMotorPower(vector.getZ());
            }
        }
    }

    @Override
    public void stop() {
        stopMotors();
        stopThreads();
    }

    public abstract EnumMap<Vector.WheelType, Vector> calculateSwerveWheelHeadings(Vector driverVector, boolean rotating, boolean clockwise, double originalPos);
    public abstract void stopThreads();
    public abstract void stopMotors();
    public abstract double normalizeHeading(double targetPos, double currentPos);
    public abstract double reverseHeading(double originalPos, double totalPos, double targetPos);

}
