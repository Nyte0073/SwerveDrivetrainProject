package ftclibs;

public class Motor {
    private double targetPosition = 0;
    private double motorPower = 0;

    public void setTargetPosition(double targetPosition) {
        this.targetPosition = targetPosition;
    }

    public double getCurrentPosition() {
        return targetPosition;
    }

    public void set(double motorPower) {
        this.motorPower = motorPower;
    }

    public double getMotorPower() {
        return motorPower;
    }

    public void stopAndResetEncoder() {}

    public void setRunMode(RunMode runMode) {}

    public enum RunMode {
        RawPower,
        PositionControl
    }
}
