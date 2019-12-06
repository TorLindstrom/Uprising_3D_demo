package tor.visualHandling;


import tor.Camera;
import tor.shapeHandling.Point;

import static java.lang.Math.*;
import static tor.visualHandling.Window.*;

public class PerspectiveMath
{
    public static int[] makeRelative(double x, double y, double z, Camera camera)
    {
        double relativeX = x - camera.getX();
        double relativeY = y - camera.getY();
        double relativeZ = z - camera.getZ();
        int[] screenPos = new int[2];
        double xyDistance = calculatePaneDistance(relativeX, relativeY);
        double horizontalPlaneAngle = Math.atan(relativeY / relativeX) - camera.getHorizontalAngle() * (PI / 180);
        double relativeXYDepth = Math.cos(horizontalPlaneAngle) * xyDistance;
        double verticalPlaneAngle = Math.atan(relativeZ / relativeXYDepth) - camera.getVerticalAngle() * (PI / 180);
        double zDepthDistance = calculatePaneDistance(relativeZ, relativeXYDepth);

        double relativeDepth = cos(verticalPlaneAngle) * zDepthDistance;

        double halfWidthAtDepth = tan(camera.getHorizontalFOV() / 2 * (PI / 180)) * relativeDepth;
        double halfWidthFromLeft = halfWidthAtDepth + sin(horizontalPlaneAngle) * xyDistance; //width deep from left
        double percentageFromTheLeft = halfWidthFromLeft / (halfWidthAtDepth * 2);

        double halfHeightAtDepth = tan(camera.getVerticalFOV() / 2 * (PI / 180)) * relativeDepth;
        double halfHeightFromUp = halfHeightAtDepth - tan(verticalPlaneAngle) * relativeDepth;
        double percentageFromUp = halfHeightFromUp / (halfHeightAtDepth * 2);

        screenPos[0] = (int) ((width * percentageFromTheLeft) + 0.5);
        screenPos[1] = (int) ((height * percentageFromUp) + 0.5);
        return screenPos;
    }

    public static int[] makeRelative(double[] pos, Camera camera)
    {
        double relativeX = pos[0] - camera.getX();
        double relativeY = pos[1] - camera.getY();
        double relativeZ = pos[2] - camera.getZ();
        int[] screenPos = new int[2];
        double xyDistance = calculatePaneDistance(relativeX, relativeY);
        double horizontalPlaneAngle = Math.atan(relativeY / relativeX) - camera.getHorizontalAngle() * (PI / 180);
        double relativeXYDepth = Math.cos(horizontalPlaneAngle) * xyDistance;
        double verticalPlaneAngle = Math.atan(relativeZ / relativeXYDepth) - camera.getVerticalAngle() * (PI / 180);
        double zDepthDistance = calculatePaneDistance(relativeZ, relativeXYDepth);

        double relativeDepth = cos(verticalPlaneAngle) * zDepthDistance;

        double halfWidthAtDepth = tan(camera.getHorizontalFOV() / 2 * (PI / 180)) * relativeDepth;
        double halfWidthFromLeft = halfWidthAtDepth + sin(horizontalPlaneAngle) * xyDistance; //width deep from left
        double percentageFromTheLeft = halfWidthFromLeft / (halfWidthAtDepth * 2);

        double halfHeightAtDepth = tan(camera.getVerticalFOV() / 2 * (PI / 180)) * relativeDepth;
        double halfHeightFromUp = halfHeightAtDepth - tan(verticalPlaneAngle) * relativeDepth;
        double percentageFromUp = halfHeightFromUp / (halfHeightAtDepth * 2);

        screenPos[0] = (int) ((width * percentageFromTheLeft) + 0.5);
        screenPos[1] = (int) ((height * percentageFromUp) + 0.5);
        return screenPos;
    }

    public static int[] makeRelative(Point pos, Camera camera)
    {
        double relativeX = pos.getX() - camera.getX();
        double relativeY = pos.getY() - camera.getY();
        double relativeZ = pos.getZ() - camera.getZ();
        int[] screenPos = new int[2];
        double xyDistance = calculatePaneDistance(relativeX, relativeY);
        double horizontalPlaneAngle = Math.atan(relativeY / relativeX) - camera.getHorizontalAngle() * (PI / 180);
        double relativeXYDepth = Math.cos(horizontalPlaneAngle) * xyDistance;
        double verticalPlaneAngle = Math.atan(relativeZ / relativeXYDepth) - camera.getVerticalAngle() * (PI / 180);
        double zDepthDistance = calculatePaneDistance(relativeZ, relativeXYDepth);

        double relativeDepth = cos(verticalPlaneAngle) * zDepthDistance;

        double halfWidthAtDepth = tan(camera.getHorizontalFOV() / 2 * (PI / 180)) * relativeDepth;
        double halfWidthFromLeft = halfWidthAtDepth + sin(horizontalPlaneAngle) * xyDistance; //width deep from left
        double percentageFromTheLeft = halfWidthFromLeft / (halfWidthAtDepth * 2);

        double halfHeightAtDepth = tan(camera.getVerticalFOV() / 2 * (PI / 180)) * relativeDepth;
        double halfHeightFromUp = halfHeightAtDepth - tan(verticalPlaneAngle) * relativeDepth;
        double percentageFromUp = halfHeightFromUp / (halfHeightAtDepth * 2);

        screenPos[0] = (int) ((width * percentageFromTheLeft) + 0.5);
        screenPos[1] = (int) ((height * percentageFromUp) + 0.5);
        return screenPos;
    }

    public static int setHorizonLevel(Camera camera)
    {
        double angleToHorizon = -((Math.atan(camera.getPosition()[2] / 100000.0)) * (180 / PI)) - camera.getVerticalAngle();
        return (int) (height - (camera.getVerticalFOV() / 2 + angleToHorizon) / camera.getVerticalFOV() * height);
    }

    public static double calculatePaneDistance(double x, double y)
    {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static double calculatePaneDistance(double x, double y, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));
    }

    public static double calculateSpaceDistance(double x, double y, double z)
    {
        return sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
    }

    public static double calculateSpaceDistance(double x, double y, double z, double x2, double y2, double z2)
    {
        return sqrt(pow(x - x2, 2) + pow(y - y2, 2) + pow(z - z2, 2));
    }

    public static double calculateSpaceDistance(double[] pos1, double[] pos2)
    {
        return sqrt(pow(pos1[0] - pos2[0], 2) + pow(pos1[1] - pos2[1], 2) + pow(pos1[2] - pos2[2], 2));
    }

}
