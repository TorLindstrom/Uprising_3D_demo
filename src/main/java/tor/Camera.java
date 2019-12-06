package tor;

import tor.shapeHandling.Point;

public class Camera extends Point
{
    private double horizontalAngle, verticalAngle, rollAngle;
    private double horizontalFOV, verticalFOV;

    public Camera(Point point){
        super(point);
    }

    public double getHorizontalAngle()
    {
        return horizontalAngle;
    }

    public void setHorizontalAngle(double horizontalAngle)
    {
        this.horizontalAngle = horizontalAngle;
    }

    public double getVerticalAngle()
    {
        return verticalAngle;
    }

    public void setVerticalAngle(double verticalAngle)
    {
        this.verticalAngle = verticalAngle;
    }

    public double getRollAngle()
    {
        return rollAngle;
    }

    public void setRollAngle(double rollAngle)
    {
        this.rollAngle = rollAngle;
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
