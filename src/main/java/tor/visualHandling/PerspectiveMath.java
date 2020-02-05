package tor.visualHandling;


import tor.Camera;
import tor.Manager;
import tor.shapeHandling.Point;
import tor.shapeHandling.Shape;
import tor.shapeHandling.Side;

import java.util.ArrayList;

import static java.lang.Math.*;
import static tor.visualHandling.Window.*;
import static tor.visualHandling.Renderer.*;

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
        //something wrong with the FOV alter, too big zoom effect, frustum corners are not visible
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

    /*public static boolean isRayInsideFinitePlane(Side side, double[] camera, double[] corner)
    {
        double[] planeIntersectionPoint = calculateIntersectionPoint(side, camera, corner);
        Point[] corners = side.getCorners();
        //the line between 0 and 1 is already used below, only need to check the other sides
        double[] midpoint = {(corners[0].getX() + corners[1].getX()) / 2,
                (corners[0].getY() + corners[1].getY()) / 2,
                (corners[0].getZ() + corners[1].getZ()) / 2,};
        double xDeltaIntersectionHitLine = planeIntersectionPoint[0] - midpoint[0];
        double yDeltaIntersectionHitLine = planeIntersectionPoint[1] - midpoint[1];
        double zDeltaIntersectionHitLine = planeIntersectionPoint[2] - midpoint[2];
        int intersectionsWithAreaSides = 0;
        for (int i = 1; i < corners.length; i++) {
            //point on line is corner[i]
            int index = chooseIndex(corners.length, i + 1);
            double[] slopeOfCheckLine = {corners[index].getX() - corners[i].getX(),
                    corners[index].getY() - corners[i].getY(),
                    corners[index].getZ() - corners[i].getZ()};
            //check if intersecting lines

            double[] tValueRelativeS = new double[2];
            double[] sValueRelativeS = new double[2];
            boolean sRelSet = false;
            boolean tRelSet = false;
            double tValue = -1;
            double sValue = -1;
            boolean sSet = false;
            boolean tSet = false;
            char setIn = ' ';

            if (xDeltaIntersectionHitLine == 0) {
                if (slopeOfCheckLine[0] != 0) {
                    sValue = (midpoint[0] - corners[i].getX()) / slopeOfCheckLine[0];
                    sRelSet = true;
                } else if (midpoint[0] != corners[i].getX()) {
                    //then can't intersect, continue
                    continue;
                }
            } else {
                if (slopeOfCheckLine[0] != 0) {
                    tValueRelativeS[0] = (corners[i].getX() - midpoint[0]) / xDeltaIntersectionHitLine;
                    tValueRelativeS[1] = slopeOfCheckLine[0] / xDeltaIntersectionHitLine;
                    tRelSet = true;
                } else {
                    tValue = (corners[i].getX() - midpoint[0]) / xDeltaIntersectionHitLine;
                    tSet = true;
                }
                setIn ='x';
            }
            if (yDeltaIntersectionHitLine == 0) {
                if (slopeOfCheckLine[1] != 0 && !sRelSet) {
                    sValue = (midpoint[1] - corners[i].getY()) / slopeOfCheckLine[1];
                    sRelSet = true;
                } else if (midpoint[1] != corners[i].getY()) {
                    //then can't intersect, continue
                    continue;
                }
            } else if (!tRelSet) {
                if (slopeOfCheckLine[1] != 0) {
                    tValueRelativeS[0] = (corners[i].getY() - midpoint[1]) / yDeltaIntersectionHitLine;
                    tValueRelativeS[1] = slopeOfCheckLine[1] / yDeltaIntersectionHitLine;
                    tRelSet = true;
                } else {
                    tValue = (corners[i].getY() - midpoint[1]) / yDeltaIntersectionHitLine;
                    tSet = true;
                }
                setIn = 'y';
            }
            if (zDeltaIntersectionHitLine == 0) {
                if (slopeOfCheckLine[2] != 0 && !sRelSet) {
                    sValue = (midpoint[2] - corners[i].getZ()) / slopeOfCheckLine[2];
                    sRelSet = true;
                } else if (midpoint[2] != corners[i].getZ()) {
                    //then can't intersect, continue
                    continue;
                }
            } else if (!tRelSet) {
                if (slopeOfCheckLine[2] != 0) {
                    tValueRelativeS[0] = (corners[i].getZ() - midpoint[2]) / zDeltaIntersectionHitLine;
                    tValueRelativeS[1] = slopeOfCheckLine[2] / zDeltaIntersectionHitLine;
                    tRelSet = true;
                } else {
                    tValue = (corners[i].getZ() - midpoint[2]) / zDeltaIntersectionHitLine;
                    tSet = true;
                }
                setIn = 'z';
            }

            //should be done on the s side
            //now to actually solve for the one not set
            //TODO: FAULTY
            if (tRelSet && setIn != ' ') {
                double xSplit = xDeltaIntersectionHitLine * tValueRelativeS[1] - slopeOfCheckLine[0];
                double ySplit = yDeltaIntersectionHitLine * tValueRelativeS[1] - slopeOfCheckLine[1];
                double zSplit = zDeltaIntersectionHitLine * tValueRelativeS[1] - slopeOfCheckLine[2];
                //solve for actual
                if (*//*setIn != 'x' &&*//* xDeltaIntersectionHitLine != 0 && slopeOfCheckLine[0] != 0 && xSplit != 0) {
                        //sValue = (corners[i].getX() - tValueRelativeS[0] + slopeOfCheckLine[0] * tValueRelativeS[1]) / xDeltaIntersectionHitLine;
                        sValue = (corners[i].getX() - midpoint[0] - xDeltaIntersectionHitLine * tValueRelativeS[0]) / xSplit;
                    sRelSet = !(sValue < 0);
                } else if (*//*setIn != 'y' &&*//* yDeltaIntersectionHitLine != 0 && slopeOfCheckLine[1] != 0 && ySplit != 0) {
                    //sValue = (corners[i].getY() - tValueRelativeS[0] + slopeOfCheckLine[1] * tValueRelativeS[1]) / yDeltaIntersectionHitLine;
                    sValue = (corners[i].getY() - midpoint[1] - yDeltaIntersectionHitLine * tValueRelativeS[0]) / ySplit;
                    sRelSet = !(sValue < 0);
                } else if (*//*setIn != 'z' &&*//* zDeltaIntersectionHitLine != 0 && slopeOfCheckLine[2] != 0 && zSplit != 0) {
                    //sValue = (corners[i].getZ() - tValueRelativeS[0] + slopeOfCheckLine[2] * tValueRelativeS[1]) / zDeltaIntersectionHitLine;
                    sValue = (corners[i].getZ() - midpoint[2] - zDeltaIntersectionHitLine * tValueRelativeS[0]) / zSplit;
                    sRelSet = !(sValue < 0);
                }
            }
            //set tValue
            if (!tSet){
                if (xDeltaIntersectionHitLine != 0) {
                    tValue = (corners[i].getX() - midpoint[0] + slopeOfCheckLine[0]) / xDeltaIntersectionHitLine;
                } else if (yDeltaIntersectionHitLine != 0) {
                    tValue = (corners[i].getY() - midpoint[1] + slopeOfCheckLine[1]) / yDeltaIntersectionHitLine;
                } else if (zDeltaIntersectionHitLine != 0){
                    tValue = (corners[i].getZ() - midpoint[2] + slopeOfCheckLine[2]) / zDeltaIntersectionHitLine;
                }
            }

            //sValue or tValue?
            //both, not at same place though, s might be something, and t doesn't have to be the same!
            Point interSectionPoint = new Point(midpoint[0] + xDeltaIntersectionHitLine * tValue,
                    midpoint[1] + yDeltaIntersectionHitLine * tValue,
                    midpoint[2] + zDeltaIntersectionHitLine * tValue);
            if ((midpoint[0] + xDeltaIntersectionHitLine * tValue
                    == corners[i].getX() + slopeOfCheckLine[0] * sValue
                    && midpoint[1] + yDeltaIntersectionHitLine * tValue
                    == corners[i].getY() + slopeOfCheckLine[1] * sValue
                    && midpoint[2] + zDeltaIntersectionHitLine * tValue
                    == corners[i].getZ() + slopeOfCheckLine[2] * sValue)
                    && isWithinSpaceRange(interSectionPoint, corners[i], corners[index])) {
                intersectionsWithAreaSides++;
            }
        }
        return intersectionsWithAreaSides == 1;
    }*/

    public static boolean isRayInsideFinitePlane(Side side, double[] camera, double[] frustumCorner)
    {
        double[] planeIntersectionPoint = calculateIntersectionPoint(side, camera, frustumCorner);
        Point[] corners = side.getCorners();
        //the line between 0 and 1 is already used below, only need to check the other sides
        double[] midpoint = {(corners[0].getX() + corners[1].getX()) / 2,
                (corners[0].getY() + corners[1].getY()) / 2,
                (corners[0].getZ() + corners[1].getZ()) / 2,};
        double xHitDelta = planeIntersectionPoint[0] - midpoint[0];
        double yHitDelta = planeIntersectionPoint[1] - midpoint[1];
        double zHitDelta = planeIntersectionPoint[2] - midpoint[2];
        int intersectionsWithAreaSides = 0;
        for (int i = 1; i < corners.length; i++) {
            //point on line is corner[i]
            Point corner = corners[i];
            int index = chooseIndex(corners.length, i + 1);
            double[] slopeOfCheckLine = {corners[index].getX() - corner.getX(),
                    corners[index].getY() - corner.getY(),
                    corners[index].getZ() - corner.getZ()};

            double tValue = -1;
            double sValue = -1;
            boolean tQuickSet = false;
            boolean sQuickSet = false;
            boolean done = false;

            //quick elimination check, if any true, then simply can't intersect
            if ((xHitDelta == slopeOfCheckLine[0] && corner.getX() != midpoint[0])
                    || (yHitDelta == slopeOfCheckLine[1] && corner.getY() != midpoint[1])
                    || (zHitDelta == slopeOfCheckLine[2] && corner.getZ() != midpoint[2])) {
                continue;
            }
            //either I can solve for one immediately, or quickly eliminate

            //this can short circuit the equation for the value t
            if (xHitDelta != 0 && slopeOfCheckLine[0] == 0){
                tValue = (corner.getX() - midpoint[0]) / xHitDelta;
                tQuickSet = true;
            } else if (yHitDelta != 0 && slopeOfCheckLine[1] == 0){
                tValue = (corner.getY() - midpoint[1]) / yHitDelta;
                tQuickSet = true;
            } else if (zHitDelta != 0 && slopeOfCheckLine[2] == 0){
                tValue = (corner.getZ() - midpoint[2]) / zHitDelta;
                tQuickSet = true;
            }
            //this can short circuit the equation for the value of s
            if (slopeOfCheckLine[0] != 0 && xHitDelta == 0){
                sValue = (corner.getX() - midpoint[0]) / slopeOfCheckLine[0];
                sQuickSet = true;
            } else if (slopeOfCheckLine[1] != 0 && yHitDelta == 0){
                sValue = (corner.getY() - midpoint[1]) / slopeOfCheckLine[1];
                sQuickSet = true;
            } else if (slopeOfCheckLine[2] != 0 && zHitDelta == 0){
                sValue = (corner.getZ() - midpoint[2]) / slopeOfCheckLine[2];
                sQuickSet = true;
            }

            //if any is already set, check and determine
            if (tQuickSet){
                //if both set, done
                if (!sQuickSet){
                    //solve for s
                    if (xHitDelta != 0 && slopeOfCheckLine[0] != 0){
                        sValue = (midpoint[0] - corner.getX() + xHitDelta * tValue) / slopeOfCheckLine[0];
                    } else if (yHitDelta != 0 && slopeOfCheckLine[1] != 0){
                        sValue = (midpoint[1] - corner.getY() + yHitDelta * tValue) / slopeOfCheckLine[1];
                    } else if (zHitDelta != 0 && slopeOfCheckLine[2] != 0){
                        sValue = (midpoint[2] - corner.getZ() + zHitDelta * tValue) / slopeOfCheckLine[2];
                    }
                }
                done = true;
            } else if (sQuickSet){
                //solve for t
                if (xHitDelta != 0 && slopeOfCheckLine[0] != 0){
                    tValue = (corner.getX() - midpoint[0] + slopeOfCheckLine[0] * sValue) / xHitDelta;
                } else if (yHitDelta != 0 && slopeOfCheckLine[1] != 0){
                    tValue = (corner.getY() - midpoint[1] + slopeOfCheckLine[1] * sValue) / yHitDelta;
                } else if (zHitDelta != 0 && slopeOfCheckLine[2] != 0){
                    tValue = (corner.getZ() - midpoint[2] + slopeOfCheckLine[2] * sValue) / zHitDelta;
                }
                done = true;
            }

            //[0] is constant, and [1] is slope, IMPORTANT is [0] - [1] in actuality
            double[] tRelS = new double[2];
            double[] sRelT = new double[2];
            int equationSolved = 0;

            //here I should know that there is no equation that hasn't got a valid slope on both sides, or they got 0 in slope gradient on both
            if (!done){
                if (xHitDelta != 0 && slopeOfCheckLine[0] != 0){
                    tRelS[0] = (corner.getX() - midpoint[0]) / xHitDelta;
                    tRelS[1] = slopeOfCheckLine[0] / xHitDelta;
                } else if (yHitDelta != 0 && slopeOfCheckLine[1] != 0){
                    tRelS[0] = (corner.getY() - midpoint[1]) / yHitDelta;
                    tRelS[1] = slopeOfCheckLine[1] / yHitDelta;
                } else if (zHitDelta != 0 && slopeOfCheckLine[2] != 0){
                    tRelS[0] = (corner.getZ() - midpoint[2]) / zHitDelta;
                    tRelS[1] = slopeOfCheckLine[2] / zHitDelta;
                }
                //now to solve for s value
                //using chooseIndex to avoid redoing the same equation twice, would not work
                switch (chooseIndex(3, equationSolved + 1)){
                    case 0:
                        sValue =
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                }

                //now to solve for t value
            }





            //check if actually valid answers, if so, add it as a valid intersection
            //space range, universal t- and s- values

        }
        return false;
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
