package tor.controller;

import tor.shapeHandling.Point;

import static tor.mathHandling.StandardMath.*;

public class Camera extends Point
{
    private double horizontalAngle = 0, verticalAngle = 0, rollAngle = 0;
    private double horizontalFOV = 110, verticalFOV = 85;

    public Camera(Point point)
    {
        super(point);
    }
    public Camera(double[] pos){
        super(pos);
    }
    public Camera(int x, int y, int z){
        super(x, y, z);
    }

    public double getHorizontalAngle()
    {
        return horizontalAngle;
    }

    public void setHorizontalAngle(double horizontalAngle)
    {
        this.horizontalAngle = horizontalAngle % 360;
    }

    public double getVerticalAngle()
    {
        return verticalAngle;
    }

    public void setVerticalAngle(double verticalAngle)
    {
        this.verticalAngle = verticalAngle;
        this.verticalAngle %= 360;
    }

    public double getRollAngle()
    {
        return rollAngle;
    }

    public void setRollAngle(double rollAngle)
    {
        this.rollAngle = rollAngle;
        this.horizontalAngle %= 360;
    }

    public double getHorizontalFOV()
    {
        return horizontalFOV;
    }

    public void setHorizontalFOV(double horizontalFOV)
    {
        this.horizontalFOV = horizontalFOV;
    }

    public double getVerticalFOV()
    {
        return verticalFOV;
    }

    public void setVerticalFOV(double verticalFOV)
    {
        this.verticalFOV = verticalFOV;
    }

    @Override
    public String toString()
    {
        return new StringBuilder(String.valueOf(determineSignificantDigits(getX(), 3)))
                .append(" : ").append(determineSignificantDigits(getY(), 3))
                .append(" : ").append(determineSignificantDigits(getZ(), 3))
                .append(" :: ").append(determineSignificantDigits(horizontalAngle, 3))
                .append(" : ").append(determineSignificantDigits(verticalAngle, 3))
                .toString();
    }
}
