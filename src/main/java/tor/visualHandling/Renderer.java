package tor.visualHandling;

import tor.Manager;
import tor.shapeHandling.Shape;
import tor.shapeHandling.Side;
import tor.shapeHandling.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Renderer extends JPanel
{
    Manager manager;

    public Renderer(Manager manager)
    {
        this.manager = manager;
        setPreferredSize(new Dimension(Window.width, Window.height));
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        paintHorizon(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        ArrayList<Frame> frames = calculateFrames();

        for (int i = 0; i < Window.width; i++) {
            for (int j = 0; j < Window.height; j++) {
                ArrayList<Frame> containedBy = findContainedBy(frames, i, j);
                Side currentClosestSide = findClosestSide(containedBy, i, j);
                paintPixel(graphics2D, currentClosestSide, i, j);
            }
        }
    }

    public void paintHorizon(Graphics graphics)
    {
        graphics.setColor(new Color(204, 255, 153));
        graphics.fillRect(0, 0, manager.getWindow().getWidth(), manager.getWindow().getHeight());
        graphics.setColor(new Color(153, 204, 0));
        graphics.fillRect(0, PerspectiveMath.setHorizonLevel(manager.getCamera()), manager.getWindow().getWidth(), manager.getWindow().getHeight());
    }

    public void paintPixel(Graphics2D graphics2D, Side side, int i, int j)
    {
        if (side == null) {
            return;
        } else if (side.getColor() == null) {
            graphics2D.setColor(Color.ORANGE);
        } else {
            graphics2D.setColor(side.getColor());
        }
        graphics2D.fillRect(i, j, 1, 1);
    }

    public ArrayList<Frame> findContainedBy(ArrayList<Frame> frames, int i, int j)
    {
        ArrayList<Frame> containedBy = new ArrayList<>();
        java.awt.Point asPoint = new java.awt.Point(i, j);
        for (Frame frame : frames) {
            if (frame.polygon.contains(asPoint)) {
                containedBy.add(frame);
            }
        }
        return containedBy;
    }

    public Side findClosestSide(ArrayList<Frame> containedBy, int i, int j)
    {
        double distance = Double.MAX_VALUE;
        Side currentClosestSide = null;
        double[] secondRayPosition;
        if (!containedBy.isEmpty()) {
            secondRayPosition = PerspectiveMath.createSecondRayPosition(i, j, manager);
            for (Frame frame : containedBy) {
                double newDistance = PerspectiveMath.calculateDistanceToIntersection(frame.side, manager.getCamera().getPosition(), secondRayPosition);

                if (newDistance < distance) {
                    distance = newDistance;
                    currentClosestSide = frame.side;
                }
            }
        }
        return currentClosestSide;
    }

    public ArrayList<Frame> calculateFrames()
    {
        ArrayList<Frame> frames = new ArrayList<>();
        for (Shape shape : manager.getScene().getShapes()) {
            for (Side side : shape.getSides()) {
                int numberOfCorners = side.getCorners().length;
                int[] x = new int[numberOfCorners], y = new int[numberOfCorners];
                int i = 0;
                for (Point point : side.getCorners()) {
                    int[] screenPosition = PerspectiveMath.makeRelative(point, manager.getCamera());
                    x[i] = screenPosition[0];
                    y[i++] = screenPosition[1];
                }
                frames.add(new Frame(side, new Polygon(x, y, x.length)));
            }
        }
        return frames;
    }
}
