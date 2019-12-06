package tor.shapeHandling;

public class Shape
{
    private Side[] sides;

    public Shape(Side...sides){
        this.sides = sides;
    }

    public Side[] getSides()
    {
        return sides;
    }

    public void setSides(Side[] sides)
    {
        this.sides = sides;
    }
}
