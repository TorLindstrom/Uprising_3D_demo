package tor.shapeHandling;

import java.awt.*;

public class Side
{
    private Point[] corners;
    private Color color;

    public Side(Point...corners){
        this.corners = corners;
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

}
