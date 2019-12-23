package tor;

import tor.shapeHandling.Point;

public class Camera extends Point
{
    private double horizontalAngle = 0, verticalAngle = 0, rollAngle = 0;
    private double horizontalFOV = 100, verticalFOV = 75;

    public Camera(Point point)
    {
        super(point);
    }

    public double getHorizontalAngle()
    {
        return horizontalAngle;
    }

    public void setHorizontalAngle(double horizontalAngle)
    {
        this.horizontalAngle = horizontalAngle;
        this.horizontalAngle %= 360;
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
}
