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
import java.util.concurrent.BrokenBarrierException;
import java.util.function.Consumer;

public class Transformer implements KeyListener
{
    private Manager manager;
    private Camera camera;
    private Window window;
    private Renderer renderer;
    //59 hz is 16.949 ms in between frames
    //30 hz is 33.333 ms in between frames
    private long refreshRateMilli = 33;
    boolean move = false;

    Transformer(Manager manager)
    {
        this.manager = manager;
        this.camera = manager.getCamera();
        this.window = manager.getWindow();
        this.renderer = window.getRenderer();
    }

    public void movement() throws InterruptedException, BrokenBarrierException
    {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        //while (true) {
        //movementInstance(480, () -> camera.setVerticalAngle(camera.getVerticalAngle() + 0.75));
        movementInstance(500, () -> camera.setY(camera.getY() - 1));
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
    }

    public void movementInstance(int duration, Move move) throws InterruptedException, BrokenBarrierException
    {
        for (int i = 0; i < duration; i++) {
            LocalTime start = LocalTime.now();
            move.move();
            window.repaint();
            long millis = refreshRateMilli - Duration.between(start, LocalTime.now()).toMillis();
            if (millis < 0) {
                millis = 1;
            }
            Thread.sleep(millis);
            manager.renderBlock.await();
            if (this.move){
                System.out.println("moved");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        System.out.println("type");
        move = true;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        System.out.println("press");
        move = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        System.out.println("released");
        move = true;
    }

}
