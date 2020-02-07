package tor.mathHandling;

import tor.Manager;
import tor.shapeHandling.Point;
import tor.shapeHandling.Side;

import static java.lang.Math.*;
import static java.lang.Math.sin;
import static tor.visualHandling.Window.height;
import static tor.visualHandling.Window.width;

public class StandardMath
{

    public static boolean isNegative(double value)
    {
        return value / abs(value) < 0;
    }

    public static double determineSignificantDigits(double originalValue, int significantDecimals)
    {
        double modifier = pow(10, significantDecimals);
        //cuts the ones that don't make it
        int truncated = (int) (originalValue * modifier);
        //return the original, trimmed to size
        return truncated / modifier;
    }

    //---------------------------------------------------Line math------------------------------------------------------------------//

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
