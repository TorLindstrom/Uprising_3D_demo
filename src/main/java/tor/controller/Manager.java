package tor.controller;

import tor.shapeHandling.Point;
import tor.shapeHandling.Scene;
import tor.shapeHandling.Shape;
import tor.shapeHandling.Side;
import tor.visualHandling.Window;

import java.awt.*;

public class Manager
{
    private Scene scene = new Scene();
    final static private Camera camera = new Camera(new Point(-300, 200, 200));
    private Window window = new Window(this);
    private Transformer mover = new Transformer(this);

    public Manager() throws InterruptedException
    {
        scene.addShape(new Shape(
                new Side(Color.CYAN, new Point(0, 0, 0), new Point(0, 200, 0), new Point(100, 100, 200)),
                new Side(Color.BLUE, new Point(0, 0, 0), new Point(200, 0, 0), new Point(100, 100, 200)),
                new Side(Color.LIGHT_GRAY, new Point(200, 0, 0), new Point(200, 200, 0), new Point(100, 100, 200)),
                new Side(Color.GRAY, new Point(0, 200, 0), new Point(200, 200, 0), new Point(100, 100, 200))));
        scene.addShape(new Shape(
                new Side(Color.ORANGE, new Point(300, 200, 0), new Point(300, 400, 0), new Point(400, 300, 300)),
                new Side(Color.YELLOW, new Point(300, 200, 0), new Point(500, 200, 0), new Point(400, 300, 300)),
                new Side(Color.LIGHT_GRAY, new Point(500, 200, 0), new Point(500, 400, 0), new Point(400, 300, 300)),
                new Side(Color.GRAY, new Point(300, 400, 0), new Point(500, 400, 0), new Point(400, 300, 300))));
        scene.addShape(new Shape(
            new Side(Color.GRAY, new Point(2000, -500, 0), new Point(-1000, -500, 0), new Point(-1000, -500, 500), new Point(2000, -500, 500))));
        scene.addShape(new Shape(
                new Side(Color.LIGHT_GRAY, new Point(2000, 900, 0), new Point(-1000, 900, 0), new Point(-1000, 900, 500), new Point(2000, 900, 500))));
        scene.addShape(new Shape(
                new Side(Color.BLUE, new Point(2000, -500, 1500), new Point(-500, -500, 1500), new Point(-500, 500, 1500), new Point(2000, 500, 1500))));
        scene.addShape(new Shape(
                new Side(Color.CYAN, new Point(1000, -200, 1000), new Point(1000, 200, 1000), new Point(1000, 200, 0), new Point(1000, -200, 0))));
        scene.addShape(new Shape(
                new Side(Color.CYAN, new Point(0, 400, 0), new Point(0, 600, 0), new Point(100, 500, 200)),
                new Side(Color.BLUE, new Point(0, 400, 0), new Point(200, 400, 0), new Point(100, 500, 200)),
                new Side(Color.LIGHT_GRAY, new Point(200, 400, 0), new Point(200, 600, 0), new Point(100, 500, 200)),
                new Side(Color.GRAY, new Point(0, 600, 0), new Point(200, 600, 0), new Point(100, 500, 200))));

        window.repaint();
        manage();
    }

    public void manage() throws InterruptedException
    {
        //move
        mover.movement();
        //read user input
        //refresh
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
