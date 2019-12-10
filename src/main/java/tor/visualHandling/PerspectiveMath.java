package tor.visualHandling;


import tor.Camera;
import tor.Manager;
import tor.shapeHandling.Point;
import tor.shapeHandling.Side;

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


    //---------------------------------------------------Vector and plane math------------------------------------------------------//

    public static double calculateDistanceToIntersection(Side side, double[] ray){
        //calc plane
        //
        //calc/determine ray as line
        //
        //calc intersect point
        //
        //determine distance

        double[] P1Vector = {
                side.getCorners()[1].getX() - side.getCorners()[0].getX(),
                side.getCorners()[1].getY() - side.getCorners()[0].getY(),
                side.getCorners()[1].getZ() - side.getCorners()[0].getZ(),
        };
        double[] P2Vector = {
                side.getCorners()[2].getX() - side.getCorners()[0].getX(),
                side.getCorners()[2].getY() - side.getCorners()[0].getY(),
                side.getCorners()[2].getZ() - side.getCorners()[0].getZ(),
        };

        //Point (corner 0), and two vectors, P1, P2, describes a plane
        double[] normalToPlane = calculateCrossProduct(P1Vector, P2Vector);
        double added = side.getCorners()[0].getX() * normalToPlane[0] + side.getCorners()[0].getY() * normalToPlane[1] + side.getCorners()[0].getZ() * normalToPlane[2];

        //                               x                  y               z           d (plane equation) = d
        double[] planeEquation = {normalToPlane[0], normalToPlane[1], normalToPlane[2], added};

        double amountOfT = planeEquation[0] * ray[0] + planeEquation[0] * ray[0] + planeEquation[0] * ray[0];
        double addedLooseNumbers = planeEquation[3] + ray[3]  + ray[4] + ray[5];

        double valueAtIntersection = amountOfT / addedLooseNumbers;

        double[] pointOfInterSection = {ray[0] * valueAtIntersection, ray[1] * valueAtIntersection, ray[2] * valueAtIntersection};
        return calculateSpaceDistance(pointOfInterSection, new double[] {ray[3], ray[4], ray[5]});
    }

    public static double[] calculateCrossProduct(double[] vector1, double[] vector2){
        return new double[] {
                (vector1[1] * vector2[2] - vector1[2] * vector2[1]),
                (vector1[0] * vector2[2] - vector1[2] * vector2[0]),
                (vector1[0] * vector2[1] - vector1[1] * vector2[0])
        };
    }


}
