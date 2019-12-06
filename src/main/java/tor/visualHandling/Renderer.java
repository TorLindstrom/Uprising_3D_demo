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

    public Renderer(Manager manager){
        this.manager = manager;
    }

    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        ArrayList<Frame> frames = new ArrayList<>();
        for (Shape shape: manager.getScene().getShapes()){
            for (Side side: shape.getSides()){
                int numberOfCorners = side.getCorners().length;
                int[] x = new int[numberOfCorners], y = new int[numberOfCorners];
                int i = 0;
                for (Point point: side.getCorners()){
                    int[] screenPosition = PerspectiveMath.makeRelative(point, manager.getCamera());
                    x[i] = screenPosition[0];
                    y[i] = screenPosition[1];
                }
                frames.add(new Frame(side, new Polygon(x, y, x.length)));
            }
        }
        for (int i = 0; i < Window.width; i++) {
            for (int j = 0; j < Window.height; j++) {
                ArrayList<Frame> containedBy = new ArrayList<>();
                java.awt.Point asPoint = new java.awt.Point(i, j);
                for (Frame frame: frames){
                    if (frame.polygon.contains(asPoint)){
                        containedBy.add(frame);
                    }
                }
            }
        }
    }
}
