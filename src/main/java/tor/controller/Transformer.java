package tor.controller;

import tor.visualHandling.Window;

import java.util.Scanner;

public class Transformer
{
    private Camera camera;
    private Window window;

    Transformer(Manager manager){
        this.camera = manager.getCamera();
        this.window = manager.getWindow();
    }

    public void movement() throws InterruptedException
    {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        //59 hz is 16.949 ms in between frames
        long refreshRate = 17;

        for (int i = 0; i < 360; i++) {
            camera.setHorizontalAngle(camera.getHorizontalAngle() + 1);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 100; i++) {
            camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.2);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 100; i++) {
            camera.setHorizontalAngle(camera.getHorizontalAngle() - 0.2);
            window.repaint();
            Thread.sleep(refreshRate);
        }
        for (int i = 0; i < 70; i++) {
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
        }
    }
}
