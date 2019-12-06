package tor.shapeHandling;

public class Point
{
    private double[] position;
    private double x, y, z;

    public Point(double x, double y, double z){
        position = new double[] {x, y, z};
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(Point point){
        position = point.getPosition();
        x = point.getX();
        y = point.getY();
        z = point.getZ();
    }

    public double[] getPosition()
    {
        return position;
    }

    public void setPosition(double[] position)
    {
        this.position = position;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }
}
