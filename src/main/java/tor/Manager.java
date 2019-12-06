package tor;

import tor.shapeHandling.Point;
import tor.shapeHandling.Scene;
import tor.shapeHandling.Shape;
import tor.shapeHandling.Side;
import tor.visualHandling.Window;

import java.awt.*;

public class Manager
{
    private Scene scene = new Scene();
    private Camera camera = new Camera(new Point(0,0,200));
    private Window window = new Window(this);

    public Manager()
    {
        scene.addShape(new Shape(
                new Side(Color.WHITE, new Point(0, 0, 0), new Point(0, 200, 0), new Point(100, 100, 200)),
                new Side(Color.BLACK, new Point(0, 0, 0), new Point(200, 0, 0), new Point(100, 100, 200)),
                new Side(Color.LIGHT_GRAY, new Point(200, 0, 0), new Point(200, 200, 0), new Point(100, 100, 200)),
                new Side(Color.GRAY, new Point(0, 200, 0), new Point(200, 200, 0), new Point(100, 100, 200))));

        window.repaint();
        movement();
    }

    public void movement(){
            window.repaint();
    }

    public Scene getScene()
    {
        return scene;
    }

    public Camera getCamera()
    {
        return camera;
    }

    public Window getWindow()
    {
        return window;
    }
}
