package tor.visualHandling;


import tor.Camera;
import tor.Manager;
import tor.shapeHandling.Point;
import tor.shapeHandling.Shape;
import tor.shapeHandling.Side;

import java.util.ArrayList;

import static java.lang.Math.*;
import static tor.visualHandling.Window.*;

public class PerspectiveMath
{

    public static int[] makeRelative(double x, double y, double z, Camera camera)
    {
        //Damn it all, need depth of field anyways
        //TODO: implement depth of field from old makeRelative method, needed for it to make sense to hoomans
        double relativeX = x - camera.getX();
        double relativeY = y - camera.getY();
        double relativeZ = z - camera.getZ();
        int[] screenPos = new int[2];
        double horizontalPlaneAngle = (Math.atan2(relativeY, relativeX) * (180 / PI)) - camera.getHorizontalAngle();
        double verticalPlaneAngle = (Math.atan(relativeZ / calculatePaneDistance(relativeX, relativeY)) * (180 / PI)) - camera.getVerticalAngle();
        screenPos[0] = (int) (-tan((horizontalPlaneAngle * PI / 180) / 4) * width / ((camera.getHorizontalFOV() / 2) / 180)) + width / 2;
        screenPos[1] = (int) (-tan((verticalPlaneAngle * PI / 180) / 4) * height / ((camera.getVerticalFOV() / 2) / 180)) + height / 2;
        return screenPos;
        //TODO: vertical angle near edges need to be determined by a viewing frustum for accuracy, not done here
    }

    public static int[] makeRelative(Point pos, Camera camera)
    {
        return makeRelative(pos.getX(), pos.getY(), pos.getZ(), camera);
        /*double relativeX = pos.getX() - camera.getX();
        double relativeY = pos.getY() - camera.getY();
        double relativeZ = pos.getZ() - camera.getZ();
        int[] screenPos = new int[2];
        double horizontalPlaneAngle = (Math.atan2(relativeY, relativeX) * (180 / PI)) - camera.getHorizontalAngle();
        double verticalPlaneAngle = (Math.atan(relativeZ / calculatePaneDistance(relativeX, relativeY)) * (180 / PI)) - camera.getVerticalAngle();
        screenPos[0] = (int) (tan((horizontalPlaneAngle * PI / 180) / 4) * width / ((camera.getHorizontalFOV() / 2) / 180)) + width / 2;
        screenPos[1] = (int) (tan((verticalPlaneAngle * PI / 180) / 4) * height / ((camera.getVerticalFOV() / 2) / 180)) + height / 2;
        return screenPos;*/
    }

    /*public static int[] makeRelative(double x, double y, double z, Camera camera)
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
    }*/

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

    /*public static int[] makeRelative(Point pos, Camera camera)
    {
        double relativeX = pos.getX() - camera.getX();
        double relativeY = pos.getY() - camera.getY();
        double relativeZ = pos.getZ() - camera.getZ();
        int[] screenPos = new int[2];
        double xyDistance = calculatePaneDistance(relativeX, relativeY);

        double standardHorizontalAngle = acos(relativeX / xyDistance);
        if (isNegative(relativeY)) {
            standardHorizontalAngle *= -1;
        }

        double horizontalPlaneAngle = standardHorizontalAngle + camera.getHorizontalAngle() * PI / 180;

        double relativeXYDepth = Math.cos(horizontalPlaneAngle) * xyDistance;
        double verticalPlaneAngle = Math.atan(relativeZ / relativeXYDepth) - camera.getVerticalAngle() * (PI / 180);
        double zDepthDistance = calculatePaneDistance(relativeZ, relativeXYDepth);

        //TODO: is this not dependent on the point being in front of the camera?
        double relativeDepth = cos(verticalPlaneAngle) * zDepthDistance;

        double halfWidthAtDepth = tan(camera.getHorizontalFOV() / 2 * (PI / 180)) * relativeDepth;
        double halfWidthFromLeft = halfWidthAtDepth + sin(horizontalPlaneAngle) * calculateSpaceDistance(relativeX, relativeY, relativeZ) *//*xyDistance*//*; //width deep from left
        double percentageFromTheLeft = halfWidthFromLeft / (halfWidthAtDepth * 2);

        double halfHeightAtDepth = tan(camera.getVerticalFOV() / 2 * (PI / 180)) * relativeDepth;
        double halfHeightFromUp = halfHeightAtDepth - tan(verticalPlaneAngle) * relativeDepth;
        double percentageFromUp = halfHeightFromUp / (halfHeightAtDepth * 2);

        screenPos[0] = (int) ((width * percentageFromTheLeft) + 0.5);
        screenPos[1] = (int) ((height * percentageFromUp) + 0.5);
        return screenPos;
    }*/

    public static int setHorizonLevel(Camera camera)
    {
        double angleToHorizon = -((Math.atan(camera.getPosition()[2] / Double.MAX_VALUE)) * (180 / PI)) - camera.getVerticalAngle();
        return (int) (tan((camera.getVerticalAngle() * PI / 180) / 4) * height / ((camera.getVerticalFOV() / 2) / 180)) + height / 2;
        //return (int) (height - (camera.getVerticalFOV() / 2 + angleToHorizon) / camera.getVerticalFOV() * height);
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

    public static boolean isNegative(double value)
    {
        return value / abs(value) < 0;
    }

    public static boolean isOnScreen(int[] screenPos)
    {
        return isWithinRange(screenPos[0], 0, width) && isWithinRange(screenPos[1], 0, height);
    }

    public static boolean isWithinRange(double value, double lower, double upper)
    {
        return value <= upper && value >= lower;
    }

    public static boolean isWithinSpaceRange(Point point, Point borderingTop, Point borderingBot)
    {
        return point.getX() <= max(borderingBot.getX(), borderingTop.getX()) &&
                point.getX() >= min(borderingBot.getX(), borderingTop.getX()) &&
                point.getY() <= max(borderingBot.getY(), borderingTop.getY()) &&
                point.getY() >= min(borderingBot.getY(), borderingTop.getY()) &&
                point.getZ() <= max(borderingBot.getZ(), borderingTop.getZ()) &&
                point.getZ() >= min(borderingBot.getZ(), borderingTop.getZ());
    }

    public static Integer[] findFrustumIntersection(Side[] frustumSides, double[] currentPos, double[] checkingPos)
    {
        return null;
    }


    //---------------------------------------------------Vector and plane math------------------------------------------------------//

    public static double[] calculateIntersectionPoint(Side side, double[] firstPos, double[] secondPos)
    {
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
        double[] ray = new double[]{
                secondPos[0] - firstPos[0],
                secondPos[1] - firstPos[1],
                secondPos[2] - firstPos[2]
        };

        //Point (corner 0), and two vectors, P1, P2, describes a plane

        double[] normalToPlane = calculateCrossProduct(P1Vector, P2Vector);
        double added = -((side.getCorners()[0].getX() * normalToPlane[0]) + (side.getCorners()[0].getY() * normalToPlane[1]) + (side.getCorners()[0].getZ() * normalToPlane[2]));

        //                               x                  y               z             d (plane equation) = d
        double[] planeEquation = {normalToPlane[0], normalToPlane[1], normalToPlane[2], added};

        double toBeDividedBy = (planeEquation[0] * ray[0] + planeEquation[1] * ray[1] + planeEquation[2] * ray[2]);

        return new double[]{
                firstPos[0] - ((ray[0] * (planeEquation[0] * firstPos[0] + planeEquation[1] * firstPos[1] + planeEquation[2] * firstPos[2] + planeEquation[3])) / toBeDividedBy),
                firstPos[1] - ((ray[1] * (planeEquation[0] * firstPos[0] + planeEquation[1] * firstPos[1] + planeEquation[2] * firstPos[2] + planeEquation[3])) / toBeDividedBy),
                firstPos[2] - ((ray[2] * (planeEquation[0] * firstPos[0] + planeEquation[1] * firstPos[1] + planeEquation[2] * firstPos[2] + planeEquation[3])) / toBeDividedBy),
        };
    }

    public static double[] calculateCrossProduct(double[] vector1, double[] vector2)
    {
        return new double[]{
                ((vector1[1] * vector2[2]) - (vector1[2] * vector2[1])),
                ((vector1[2] * vector2[0]) - (vector1[0] * vector2[2])),
                ((vector1[0] * vector2[1]) - (vector1[1] * vector2[0]))
        };
    }

    public static double[] createSecondRayPosition(int x, int y, Manager manager)
    {
        //for making a triangle, with the side 1 (half width of screen)
        double fromMiddleHorizontal = (x - (width / 2.));
        double fromMiddleVertical = (y - (height / 2.));

        //length of middle spar, determined from FOV
        double lengthOfMiddleHorizontal = (width / 2.) / tan((manager.getCamera().getHorizontalFOV() / 2) * (PI / 180));
        double lengthOfMiddleVertical = (height / 2.) / tan((manager.getCamera().getVerticalFOV() / 2) * (PI / 180));

        //angles of ray
        double horizontalAngle = atan(fromMiddleHorizontal / lengthOfMiddleHorizontal) + (manager.getCamera().getHorizontalAngle() * (PI / 180));
        double verticalAngle = atan(-fromMiddleVertical / lengthOfMiddleVertical) + (manager.getCamera().getVerticalAngle() * (PI / 180));

        return new double[]{
                cos(horizontalAngle) * 10 + manager.getCamera().getX(),
                sin(horizontalAngle) * 10 + manager.getCamera().getY(),
                sin(verticalAngle) * 10 + manager.getCamera().getZ()
        };
    }

}
