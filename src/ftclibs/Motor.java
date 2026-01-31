package ftclibs;

import vectors.Vector;

public final class Motor {
    private double motorPower = 0, wheelHeading = 0;
    private Vector.WheelType wheelType;

    public Motor() {

    }

    public Motor(Vector.WheelType wheelType) {
        setWheelType(wheelType);
    }

    public void setMotorPower(double motorPower) {
        this.motorPower = motorPower;
    }

    public void setWheelHeading(double wheelHeading) {
        this.wheelHeading = wheelHeading;
    }

    public double getMotorPower() {
        return motorPower;
    }

    public double getWheelHeading() {
        return wheelHeading;
    }

    public void setWheelType(Vector.WheelType wheelType) {
        this.wheelType = wheelType;
    }

    public Vector.WheelType getWheelType() {
        return wheelType;
    }
}
