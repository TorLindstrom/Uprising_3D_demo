package tor.controller;

import tor.mathHandling.StandardMath;
import tor.visualHandling.Renderer;
import tor.visualHandling.Window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.function.Consumer;

public class Transformer implements KeyListener
{
    private Camera camera;
    private Window window;
    private Renderer renderer;
    //59 hz is 16.949 ms in between frames
    //30 hz is 33.333 ms in between frames
    private long refreshRateMilli = 33;

    Transformer(Manager manager)
    {
        this.camera = manager.getCamera();
        this.window = manager.getWindow();
        this.renderer = window.getRenderer();
    }

    public void movement() throws InterruptedException
    {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        //while (true) {
        //movementInstance(480, () -> camera.setVerticalAngle(camera.getVerticalAngle() + 0.75));
        movementInstance(100, () -> camera.setHorizontalFOV(camera.getHorizontalFOV() + 1));
        movementInstance(100, () -> camera.setHorizontalFOV(camera.getHorizontalFOV() - 1));
        movementInstance(200, () -> camera.setVerticalAngle(camera.getVerticalAngle() - 0.05));
        movementInstance(200, () -> camera.setVerticalAngle(camera.getVerticalAngle() + 0.05));
        movementInstance(100, () -> camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.2));
        movementInstance(100, () -> camera.setHorizontalAngle(camera.getHorizontalAngle() - 0.2));
        movementInstance(200, () -> camera.setVerticalAngle(camera.getVerticalAngle() - 0.1));
        movementInstance(200, () -> camera.setVerticalAngle(camera.getVerticalAngle() + 0.1));
        movementInstance(100, () -> camera.setX(camera.getX() + 2));
        movementInstance(200, () -> camera.setVerticalAngle(camera.getVerticalAngle() + 0.2));
        movementInstance(100, () -> camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.2));
        movementInstance(100, () -> camera.setHorizontalAngle(camera.getHorizontalAngle() - 0.2));
        movementInstance(200, () -> camera.setVerticalAngle(camera.getVerticalAngle() - 0.2));
        //}
        /*for (int i = 0; i < 70; i++) {
            camera.setVerticalAngle(camera.getVerticalAngle() + 0.2);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 70; i++) {
            camera.setVerticalAngle(camera.getVerticalAngle() - 0.2);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 200; i++) {
            camera.setX(camera.getX() - 3);
            camera.setZ(camera.getZ() + 0.9);
            camera.setVerticalAngle(camera.getVerticalAngle() - 0.05);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 200; i++) {
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
        }*/
    }

    public void movementInstance(int duration, Move move) throws InterruptedException
    {
        for (int i = 0; i < duration; i++) {
            LocalTime start = LocalTime.now();
            move.move();
            window.repaint();
            long millis = /*refreshRateMilli -*/ Duration.between(start, LocalTime.now()).toMillis();
            millis = refreshRateMilli - millis;
            if (millis < 0) {
                millis = 1;
            }
            Thread.sleep(millis);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        System.out.println("type");
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        System.out.println("press");
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        System.out.println("released");
    }
}
