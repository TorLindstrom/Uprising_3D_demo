package tor.visualHandling;

import tor.Camera;
import tor.Manager;
import tor.shapeHandling.Shape;
import tor.shapeHandling.Side;
import tor.shapeHandling.Point;

import static tor.visualHandling.PerspectiveMath.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Renderer extends JPanel
{
    public static Manager manager;

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

    public static ArrayList<Integer[]> putWithinView(Side side, Camera camera, Side[] frustumSides)
    {
        int numOfCorners = side.getCorners().length, i = 0;
        ArrayList<Integer[]> finalScreenPos = new ArrayList<>();
        for (Point point : side.getCorners()) {
            int[] screenPos = makeRelative(point.getX(), point.getY(), point.getZ(), camera);
            //determines if the point is good, or needs to check for frustum intersections as substitute
            if (isOnScreen(screenPos)) {
                finalScreenPos.add(new Integer[]{screenPos[0], screenPos[1]});
            } else {

            }
        }


        return finalScreenPos;
    }

    private void paintHorizon(Graphics graphics)
    {
        graphics.setColor(new Color(255, 255, 83));
        graphics.fillRect(0, 0, manager.getWindow().getWidth(), manager.getWindow().getHeight());
        graphics.setColor(new Color(255, 153, 0));
        graphics.fillRect(0, setHorizonLevel(manager.getCamera()), manager.getWindow().getWidth(), manager.getWindow().getHeight());
    }

    private void paintPixel(Graphics2D graphics2D, Side side, int i, int j)
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

    private ArrayList<Frame> findContainedBy(ArrayList<Frame> frames, int i, int j)
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

    private Side findClosestSide(ArrayList<Frame> containedBy, int i, int j)
    {
        double distance = Double.MAX_VALUE;
        Side currentClosestSide = null;
        double[] secondRayPosition;
        if (!containedBy.isEmpty()) {
            secondRayPosition = createSecondRayPosition(i, j, manager);
            for (Frame frame : containedBy) {
                double newDistance = calculateSpaceDistance(manager.getCamera().getPosition(), calculateIntersectionPoint(frame.side, manager.getCamera().getPosition(), secondRayPosition));

                if (newDistance < distance) {
                    distance = newDistance;
                    currentClosestSide = frame.side;
                }
            }
        }
        return currentClosestSide;
    }

    private ArrayList<Frame> calculateFrames()
    {
        //TODO: construct frustum sides AS THE CAMERA IS SET for the frame
        Point[] frustumCorners = calculateFrustumCorners();
        Side[] frustumSides = calculateFrustumSides(frustumCorners);
        ArrayList<Frame> frames = new ArrayList<>();
        for (Shape shape : manager.getScene().getShapes()) {
            for (Side side : shape.getSides()) {
                /*int numberOfCorners = side.getCorners().length;
                int[] x = new int[numberOfCorners], y = new int[numberOfCorners];
                int i = 0;
                for (Point point : side.getCorners()) {
                    //int[] screenPosition = PerspectiveMath.makeRelative(point, manager.getCamera());
                    int[] screenPosition = makeRelative(point.getX(), point.getY(), point.getZ(), manager.getCamera());
                    x[i] = screenPosition[0];
                    y[i++] = screenPosition[1];
                }*/
                //is boss
                ArrayList<Integer> finalScreenPosX = new ArrayList<>();
                ArrayList<Integer> finalScreenPosY = new ArrayList<>();
                boolean lastOutside = true;
                for (int i = 0; i < side.getCorners().length; i++) {
                    Point[] sideCorners = side.getCorners();
                    Point point = side.getCorners()[i];
                    int[] screenPos = makeRelative(point.getX(), point.getY(), point.getZ(), manager.getCamera());
                    if (isOnScreen(screenPos)) {
                        finalScreenPosX.add(screenPos[0]);
                        finalScreenPosY.add(screenPos[1]);
                        lastOutside = false;
                    } else {
                        //TODO: add a check that checks if an intersection actually is on the edge of the field of view
                        //or that could come to knowledge if the relative point then isn't on screen, that I can see more easily
                        ArrayList<Point> validIntersections = new ArrayList<>();
                        Point lastPoint = side.getCorners()[chooseIndex(sideCorners.length,i - 1)];
                        Point nextPoint = side.getCorners()[chooseIndex(sideCorners.length,i + 1)];
                        ArrayList<Point> onLineBack = new ArrayList<>();
                        ArrayList<Point> onLineForward = new ArrayList<>();
                        if (!lastOutside) {
                            for (Side frustumSide : frustumSides) {
                                Point intersection = new Point(calculateIntersectionPoint(frustumSide, point.getPosition(), lastPoint.getPosition()));
                                if (isWithinSpaceRange(intersection, point, lastPoint)) {
                                    onLineBack.add(intersection);
                                }
                            }
                            //TODO: check before if a point is even visible on screen before adding it, if it isn't then skip it, add the rest
                            //duplicate makeRelative call
                            validIntersections.addAll(sortByFurthestDistance(removeInvisible(onLineBack), point));
                        }
                        //corner test
                        /*//TODO: check if a corner is contained by the triangle that is formed by this point, the one previous and the one in front,
                        //if so add it to finalScreenPos before the entry points
                        int[] triangleTestX = new int[3];
                        int[] triangleTestY = new int[3];
                        int[] cornerPos = makeRelative(point, manager.getCamera());
                        triangleTestX[0] = cornerPos[0];
                        triangleTestY[0] = cornerPos[1];
                        cornerPos = makeRelative(side.getCorners()[chooseIndex(sideCorners.length,i + 1)], manager.getCamera());
                        triangleTestX[1] = cornerPos[0];
                        triangleTestY[1] = cornerPos[1];
                        cornerPos = makeRelative(side.getCorners()[chooseIndex(sideCorners.length,i + 2)], manager.getCamera());
                        triangleTestX[2] = cornerPos[0];
                        triangleTestY[2] = cornerPos[1];
                        Polygon polyCheck = new Polygon(triangleTestX, triangleTestY, triangleTestX.length);
                        for (Point corner : frustumCorners) {
                            int[] cornerCheckPos = makeRelative(corner, manager.getCamera());
                            if (polyCheck.contains(cornerCheckPos[0], cornerCheckPos[1])) {
                                onLineForward.add(corner);
                            }
                        }*/

                        //now check if a ray traced from a corner intersects the side that is formed by the active, former, and next points, if so add it, then sort by distance
                        for (Point corner: frustumCorners){
                            //but! all corners will connect at some point, so which ones are valid?

                        }

                        for (Side frustumSide : frustumSides) {
                            Point intersection = new Point(calculateIntersectionPoint(frustumSide, point.getPosition(), nextPoint.getPosition()));
                            if (isWithinSpaceRange(intersection, point, nextPoint)) {
                                onLineForward.add(intersection);
                            }
                        }
                        //duplicate makeRelative call
                        validIntersections.addAll(sortByShortestDistance(removeInvisible(onLineForward), point));
                        //remove duplicate corners
                        for (Point corner: frustumCorners){
                            while (validIntersections.indexOf(corner) != validIntersections.lastIndexOf(corner)){
                                validIntersections.remove(validIntersections.get(validIntersections.lastIndexOf(corner)));
                            }
                        }
                        //make relative, all intersection points
                        for (Point preRelative: validIntersections) {
                            if (preRelative == null){
                                continue;
                            }
                            //duplicate makeRelative call, called before to check points before being added to list, save the processing power!!
                            int[] preliminaryScreenPos = makeRelative(preRelative, manager.getCamera());
                            //and save to finalScreenPositions
                            finalScreenPosX.add(preliminaryScreenPos[0]);
                            finalScreenPosY.add(preliminaryScreenPos[1]);
                        }
                        lastOutside = true;
                    }
                }
                int[] x = unpack(finalScreenPosX.toArray(new Integer[1]));
                int[] y = unpack(finalScreenPosY.toArray(new Integer[1]));
                frames.add(new Frame(side, new Polygon(x, y, x.length)));
            }
        }
        return frames;
    }

    public ArrayList<Point> removeInvisible(ArrayList<Point> toBeQueried){
        ArrayList<Point> visible = new ArrayList<>();
        for (Point point: toBeQueried){
            if (point == null){
                continue;
            }
            if (isOnScreen(makeRelative(point, manager.getCamera()))){
                visible.add(point);
            }
        }
        return visible;
    }

    public static int chooseIndex(int size, int wantedIndex)
    {
        if (wantedIndex < size && wantedIndex >= 0) {
            return wantedIndex;
        } else if (wantedIndex >= size) {
            return 0;
        } else {
            return size - 1;
        }
    }

    public static ArrayList<Point> sortByFurthestDistance(ArrayList<Point> intersectionPoints, Point activePoint)
    {
        ArrayList<Point> sorted = new ArrayList<>();
        while (true) {
            double currentFurthestDistance = 0;
            Point currentFurthestPoint = null;
            for (Point point : intersectionPoints) {
                double distance = calculateSpaceDistance(point.getPosition(), activePoint.getPosition());
                if (distance > currentFurthestDistance) {
                    currentFurthestPoint = point;
                    currentFurthestDistance = distance;
                }
            }
            sorted.add(currentFurthestPoint);
            intersectionPoints.remove(currentFurthestPoint);
            if (intersectionPoints.size() == 0) {
                return sorted;
            }
        }
    }

    public static ArrayList<Point> sortByShortestDistance(ArrayList<Point> intersectionPoints, Point activePoint)
    {
        ArrayList<Point> sorted = new ArrayList<>();
        while (true) {
            double currentClosestDistance = Double.MAX_VALUE;
            Point currentClosestPoint = null;
            for (Point point : intersectionPoints) {
                if (point == null){
                    return sorted;
                }
                double distance = calculateSpaceDistance(point.getPosition(), activePoint.getPosition());
                if (distance < currentClosestDistance) {
                    currentClosestPoint = point;
                    currentClosestDistance = distance;
                }
            }
            sorted.add(currentClosestPoint);
            intersectionPoints.remove(currentClosestPoint);
            if (intersectionPoints.size() == 0) {
                return sorted;
            }
        }
    }

    public static int[] unpack(Integer[] wrapper)
    {
        int[] primitive = new int[wrapper.length];
        int i = 0;
        for (Integer I : wrapper) {
            if (I == null){
                break;
            }
            primitive[i++] = I;
        }
        return primitive;
    }

    public static Point[] calculateFrustumCorners()
    {
        //TODO: Move this method to be called when the camera has moved, only! saves processing power
        Camera camera = manager.getCamera();
        double horizontalAngle = camera.getHorizontalAngle() + camera.getHorizontalFOV() / 2;
        double verticalAngle = camera.getVerticalAngle() + camera.getVerticalFOV() / 2;
        double topPos = 100 * sin(verticalAngle * PI / 180) + camera.getZ();
        verticalAngle -= camera.getVerticalFOV();
        double botPos = 100 * sin(verticalAngle * PI / 180) + camera.getZ();
        double leftYPos = 100 * sin(horizontalAngle * PI / 180) + camera.getY();
        double leftXPos = 100 * cos(horizontalAngle * PI / 180) + camera.getX();
        horizontalAngle -= camera.getHorizontalFOV();
        double rightYPos = 100 * sin(horizontalAngle * PI / 180) + camera.getY();
        double rightXPos = 100 * cos(horizontalAngle * PI / 180) + camera.getX();

        //TODO: check if correctly implemented and calculated
        //topleft, topright, botleft, botright
        return new Point[]{new Point(leftXPos, leftYPos, topPos), new Point(rightXPos, rightYPos, topPos), new Point(leftXPos, leftYPos, botPos), new Point(rightXPos, rightYPos, botPos)};
    }

    public Side[] calculateFrustumSides(Point[] corners)
    {
        Camera camera = manager.getCamera();
        Side[] frustumSides = new Side[4];
        //Top
        frustumSides[0] = new Side(new Point(camera.getX(), camera.getY(), camera.getZ()), corners[0], corners[1]);
        //Bottom
        frustumSides[1] = new Side(new Point(camera.getX(), camera.getY(), camera.getZ()), corners[2], corners[3]);
        //Left
        frustumSides[2] = new Side(new Point(camera.getX(), camera.getY(), camera.getZ()), corners[0], corners[2]);
        //Right
        frustumSides[3] = new Side(new Point(camera.getX(), camera.getY(), camera.getZ()), corners[1], corners[3]);
        return frustumSides;
    }
}