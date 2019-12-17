package tor;

import tor.shapeHandling.Point;
import tor.shapeHandling.Scene;
import tor.shapeHandling.Shape;
import tor.shapeHandling.Side;
import tor.visualHandling.Window;

import java.awt.*;
import java.util.Scanner;

public class Manager
{
    private Scene scene = new Scene();
    final static private Camera camera = new Camera(new Point(-300, 200, 200));
    private Window window = new Window(this);

    public Manager() throws InterruptedException
    {
        scene.addShape(new Shape(
                new Side(Color.CYAN, new Point(0, 0, 0), new Point(0, 200, 0), new Point(100, 100, 200)),
                new Side(Color.BLUE, new Point(0, 0, 0), new Point(200, 0, 0), new Point(100, 100, 200)),
                new Side(Color.LIGHT_GRAY, new Point(200, 0, 0), new Point(200, 200, 0), new Point(100, 100, 200)),
                new Side(Color.GRAY, new Point(0, 200, 0), new Point(200, 200, 0), new Point(100, 100, 200))));

        window.repaint();
        movement();
    }

    public void movement() throws InterruptedException
    {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        long refreshRate = 40;
        for (int i = 0; i < 40; i++) {
            camera.setY(camera.getY() - 4.2);
            camera.setX(camera.getX()  - 0.2);
            camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.8);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 80; i++) {
            camera.setY(camera.getY() - 4);
            camera.setX(camera.getX()  + 1);
            camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.6);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 80; i++) {
            camera.setY(camera.getY() - 2.2);
            camera.setX(camera.getX()  + 4);
            camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.5);
            window.repaint();
            Thread.sleep(refreshRate);
        }
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
