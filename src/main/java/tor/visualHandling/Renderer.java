package tor.visualHandling;

import org.jcp.xml.dsig.internal.dom.DOMUtils;
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
        Graphics2D graphics2D = (Graphics2D) graphics;
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
        for (int i = 0; i < Window.width; i++) {
            for (int j = 0; j < Window.height; j++) {
                ArrayList<Frame> containedBy = new ArrayList<>();
                java.awt.Point asPoint = new java.awt.Point(i, j);
                for (Frame frame : frames) {
                    if (frame.polygon.contains(asPoint)) {
                        containedBy.add(frame);
                    }
                }


                /*double addedHorizontalAngle;
                if (i < Window.width / 2) {
                    addedHorizontalAngle = -((Window.width / 2. - i) / Window.width) * manager.getCamera().getHorizontalFOV();
                } else {
                    addedHorizontalAngle = ((Window.width - i) / (double) Window.width) * manager.getCamera().getHorizontalFOV();
                }
                double addedVerticalAngle;
                if (j < Window.height / 2) {
                    addedVerticalAngle = -((Window.height / 2. - j) / Window.height) * manager.getCamera().getVerticalFOV();
                } else {
                    addedVerticalAngle = ((Window.height - j) / (double) Window.height) * manager.getCamera().getVerticalFOV();
                }

                double horizontalRayAngle = (manager.getCamera().getHorizontalAngle() + addedHorizontalAngle);
                double verticalRayAngle = (manager.getCamera().getVerticalAngle() + addedVerticalAngle);

                double xSlopeRay = (Math.cos(horizontalRayAngle * (Math.PI / 180)));
                double ySlopeRay = (Math.sin(horizontalRayAngle * (Math.PI / 180)));
                double zSlopeRay = Math.cos(verticalRayAngle * (Math.PI / 180));*/

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
                if (currentClosestSide == null){
                    graphics2D.setColor(Color.BLACK);
                    //continue;
                } else if (currentClosestSide.getColor() == null){
                    graphics2D.setColor(Color.ORANGE);
                } else {
                    graphics2D.setColor(currentClosestSide.getColor());
                }
                graphics2D.fillRect(i,j, 1, 1);
            }
        }
    }
}
