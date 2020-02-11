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
                new Side(Color.BLUE, new Point(2000, -500, 1000), new Point(-500, -500, 1000), new Point(-500, 500, 1000), new Point(2000, 500, 1000))));
        scene.addShape(new Shape(
                new Side(Color.CYAN, new Point(1000, -200, 1000), new Point(1000, 200, 1000), new Point(1000, 200, 0), new Point(1000, -200, 0))));

        window.repaint();
        movement();
    }

    public void movement() throws InterruptedException
    {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        long refreshRate = 30;
        for (int i = 0; i < 70; i++) {
            camera.setZ(camera.getZ() + 1);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 70; i++) {
            camera.setZ(camera.getZ() - 1);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 500; i++) {
            camera.setX(camera.getX() - 3);
            camera.setZ(camera.getZ() + 0.9);
            camera.setVerticalAngle(camera.getVerticalAngle() - 0.05);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 500; i++) {
            camera.setX(camera.getX() + 3);
            camera.setZ(camera.getZ() - 0.9);
            camera.setVerticalAngle(camera.getVerticalAngle() + 0.05);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 70; i++) {
            camera.setY(camera.getY() + 3);
            camera.setX(camera.getX() - 0.2);
            //camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.2);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 70; i++) {
            camera.setY(camera.getY() - 3);
            camera.setX(camera.getX() + 0.2);
            //camera.setHorizontalAngle(camera.getHorizontalAngle() - 0.2);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 40; i++) {
            camera.setY(camera.getY() - 5);
            camera.setX(camera.getX() - 0.3);
            camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.6);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 60; i++) {
            camera.setZ(camera.getZ() + 0.7);
            camera.setX(camera.getX() + 1);
            camera.setVerticalAngle(camera.getVerticalAngle() - 0.4);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 60; i++) {
            camera.setZ(camera.getZ() - 0.7);
            camera.setX(camera.getX() - 1);
            camera.setVerticalAngle(camera.getVerticalAngle() + 0.4);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 200; i++) {
            camera.setY(camera.getY() - 2.2);
            camera.setX(camera.getX() + 4);
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
