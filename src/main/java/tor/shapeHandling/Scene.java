package tor.shapeHandling;

import java.util.ArrayList;

public class Scene
{
    private ArrayList<Shape> shapes = new ArrayList<>();

    public void addShape(Shape shape){
        shapes.add(shape);
    }

    public ArrayList<Shape> getShapes()
    {
        return shapes;
    }
}
