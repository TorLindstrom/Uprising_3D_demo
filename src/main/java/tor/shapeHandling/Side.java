package tor.shapeHandling;

import java.awt.*;

public class Side
{
    private Point[] corners;
    private Color color;
    private double xySlope, xzSlope;

    public Side(Point...corners){
        this.corners = corners;
        xySlope = (corners[1].getX() - corners[0].getX()) / (corners[1].getY() - corners[0].getY());
        xzSlope = (corners[2].getZ() - corners[1].getZ()) / (corners[2].getX() - corners[1].getX());
    }

    public Side(Color color, Point...corners){
        this(corners);
        this.color = color;
    }

    public Point[] getCorners()
    {
        return corners;
    }

    public void setCorners(Point[] corners)
    {
        this.corners = corners;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public double getXySlope()
    {
        return xySlope;
    }

    public double getXzSlope()
    {
        return xzSlope;
    }
}
