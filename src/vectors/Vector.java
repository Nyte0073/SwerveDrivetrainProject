package vectors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class Vector {

    /*X and Y coordinates representing the
    * slope of the vector.*/
    private double x, y, z;

    public static final BiFunction<Double, Double, Vector> rotate90DegreesClockwise = (x, y) -> new Vector(-y, x),
    rotate90DegreesCounterClockwise = (x, y) -> new Vector(y, -x);

    public enum WheelType {
        FRONT_LEFT(new Vector(-1, 1)),
        FRONT_RIGHT(new Vector(1, 1)),
        BACK_LEFT(new Vector(-1, -1)),
        BACK_RIGHT(new Vector(1, -1));

        public static final List<WheelType> wheelTypes = List.of(
                FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT
        );

        private final Vector vector;

        WheelType(Vector vector) {
            this.vector = vector;
        }

        public Vector getVector() {
            return vector;
        }
    }

    public Vector(){}

    public Vector(double x, double y) {
        setX(x);
        setY(y);
    }

    public Vector(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    /*Calculates the magnitude by determining the
    * resultant factor using Pythagorean Theorem.*/
    public double getMagnitude() {
        return Math.hypot(x, y) / Math.sqrt(2);
    }

    public Vector plus(Vector vector) {
        return new Vector(getX() + vector.getX(), getY() + vector.getY());
    }

    public Vector minus(Vector vector) {
        return new Vector(getX() - vector.getX(), getY() - vector.getY());
    }

    public Vector times(double multiplier) {
        return new Vector(getX() * multiplier, getY() * multiplier);
    }

    public Vector dividedBy(double divisor) {
        return new Vector(getX() / divisor, getY() / divisor);
    }

    public List<Vector> calculateRotatedAndTranslatedVectors(boolean clockwise) {
        List<Vector> translatedAndRotatedVectors = new ArrayList<>();
        for(WheelType wheelType : WheelType.wheelTypes) {
            double x = wheelType.getVector().getX();
            double y = wheelType.getVector().getY();

            Vector rotatedVector, rotatedAndTranslatedVector;
            if(clockwise) {
                rotatedVector = rotate90DegreesClockwise.apply(x, y);
            } else {
                rotatedVector = rotate90DegreesCounterClockwise.apply(x, y);
            }
            rotatedAndTranslatedVector = rotatedVector.plus(this);
            translatedAndRotatedVectors.add(rotatedAndTranslatedVector);
        }
        return translatedAndRotatedVectors;
    }
}
