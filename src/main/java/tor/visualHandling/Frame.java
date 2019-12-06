package tor.visualHandling;

import tor.shapeHandling.Side;

import java.awt.*;

public class Frame
{
    Side side;
    Polygon polygon;

    public Frame(Side side, Polygon polygon){
        this.side = side;
        this.polygon = polygon;
    }
}
