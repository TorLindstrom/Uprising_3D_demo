package tor.visualHandling;


import tor.Camera;
import tor.shapeHandling.Point;
import tor.shapeHandling.Side;

import static java.lang.Math.*;
import static tor.visualHandling.Window.*;
import static tor.visualHandling.Renderer.*;
import static tor.mathHandling.StandardMath.*;

public class PerspectiveMath
{
    //main-dog makeRelative method, is boss
    public static int[] makeRelative(double x, double y, double z, Camera camera)
    {
        //TODO: caching the angle values, reuse if the next point have the same values, then can just take the saved data and save a lot of processing power
        double relativeX = x - camera.getX();
        double relativeY = y - camera.getY();
        double relativeZ = z - camera.getZ();
        int[] screenPos = new int[2];

        double xyDistance = calculatePaneDistance(relativeX, relativeY);
        double rawHorizontalPlaneAngle = (atan2(relativeY, relativeX) * (180 / PI)) - camera.getHorizontalAngle();
        double relativeXYDepth = cos(rawHorizontalPlaneAngle * (PI / 180)) * xyDistance;
        double verticalPlaneAngle = atan(relativeZ / relativeXYDepth) * (180 / PI) - camera.getVerticalAngle();
        double sidewaysDistance = (sin(rawHorizontalPlaneAngle * PI / 180) * xyDistance);
        int positiveOrNegative = isNegative(rawHorizontalPlaneAngle) ? -1 : 1;
        double checkAngle = (camera.getHorizontalAngle() + positiveOrNegative * 90) * PI / 180;
        double[] checkPos = {cos(checkAngle) * abs(sidewaysDistance), sin(checkAngle) * abs(sidewaysDistance), 0};
        double correctedRadius = calculateSpaceDistance(checkPos, new double[]{relativeX, relativeY, relativeZ});
        double correctedVerticalAngle = asin(relativeZ / correctedRadius);
        //not accurate, seems broken when behind camera
        //TODO: fix, flips when behind camera, loses data when calculating the horizontal angle
        double actualDepth = cos(correctedVerticalAngle - camera.getVerticalAngle() * PI / 180) * correctedRadius;
        double horizontalPlaneAngle = atan(sidewaysDistance / actualDepth) * 180 / PI;

        //TODO: move the FOV division to the camera class, and save it there, changed when either screen size or FOV changes
        //I don't know if this actually is fully functional, it is a good equation though, nicely calibrated
        //as soon as it is off screen, then it doesn't matter, as I then should go by absolute math with frustum intersections and corner checks

        if (false){
        //if (horizontalPlaneAngle > camera.getHorizontalFOV() / 2 || horizontalPlaneAngle < camera.getHorizontalFOV() / -2){
            screenPos[0] = -1;
            screenPos[1] = -1;
        } else {
            screenPos[0] = (int) (-tan(((horizontalPlaneAngle * PI / 180) / 4) / ((camera.getHorizontalFOV() / 2) / 180)) * width / 2 + width / 2);
            screenPos[1] = (int) (-tan(((verticalPlaneAngle * PI / 180) / 4) / ((camera.getVerticalFOV() / 2) / 180)) * height / 2 + height / 2);
        }
        return screenPos;
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

    public static int[] makeRelative(double[] pos, Camera camera)
    {
        return makeRelative(pos[0], pos[1], pos[2], camera);
    }

    public static int setHorizonLevel(Camera camera)
    {
        //TODO: should only be called after movement, or even only after vertical movement, either moving up-down, or angling up-down
        //should be right in front when the camera is plain
        double angleToHorizon = -camera.getVerticalAngle();
        //double angleToHorizon = -((Math.atan(camera.getPosition()[2] / Double.MAX_VALUE)) * (180 / PI)) - camera.getVerticalAngle();
        return (int) (tan((camera.getVerticalAngle() * PI / 180) / 4) * height / ((camera.getVerticalFOV() / 2) / 180)) + height / 2;
        //return (int) (height - (camera.getVerticalFOV() / 2 + angleToHorizon) / camera.getVerticalFOV() * height);
    }

    public static boolean isOnScreen(int[] screenPos)
    {
        return isWithinRange(screenPos[0], 0, width) && isWithinRange(screenPos[1], 0, height);
    }

    public static Integer[] findFrustumIntersection(Side[] frustumSides, double[] currentPos, double[] checkingPos)
    {
        return null;
    }

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
            if (xHitDelta != 0 && slopeOfCheckLine[0] == 0) {
                tValue = (corner.getX() - midpoint[0]) / xHitDelta;
                tQuickSet = true;
            } else if (yHitDelta != 0 && slopeOfCheckLine[1] == 0) {
                tValue = (corner.getY() - midpoint[1]) / yHitDelta;
                tQuickSet = true;
            } else if (zHitDelta != 0 && slopeOfCheckLine[2] == 0) {
                tValue = (corner.getZ() - midpoint[2]) / zHitDelta;
                tQuickSet = true;
            }
            //this can short circuit the equation for the value of s
            if (slopeOfCheckLine[0] != 0 && xHitDelta == 0) {
                sValue = (midpoint[0] - corner.getX()) / slopeOfCheckLine[0];
                sQuickSet = true;
            } else if (slopeOfCheckLine[1] != 0 && yHitDelta == 0) {
                sValue = (midpoint[1] - corner.getY()) / slopeOfCheckLine[1];
                sQuickSet = true;
            } else if (slopeOfCheckLine[2] != 0 && zHitDelta == 0) {
                sValue = (midpoint[2] - corner.getZ()) / slopeOfCheckLine[2];
                sQuickSet = true;
            }
            //if any is already set, check and determine
            if (tQuickSet) {
                //if both set, done
                if (!sQuickSet) {
                    //solve for s
                    if (xHitDelta != 0 && slopeOfCheckLine[0] != 0) {
                        sValue = (midpoint[0] - corner.getX() + xHitDelta * tValue) / slopeOfCheckLine[0];
                    } else if (yHitDelta != 0 && slopeOfCheckLine[1] != 0) {
                        sValue = (midpoint[1] - corner.getY() + yHitDelta * tValue) / slopeOfCheckLine[1];
                    } else if (zHitDelta != 0 && slopeOfCheckLine[2] != 0) {
                        sValue = (midpoint[2] - corner.getZ() + zHitDelta * tValue) / slopeOfCheckLine[2];
                    }
                }
                done = true;
            } else if (sQuickSet) {
                //solve for t
                if (xHitDelta != 0 && slopeOfCheckLine[0] != 0) {
                    tValue = (corner.getX() - midpoint[0] + slopeOfCheckLine[0] * sValue) / xHitDelta;
                } else if (yHitDelta != 0 && slopeOfCheckLine[1] != 0) {
                    tValue = (corner.getY() - midpoint[1] + slopeOfCheckLine[1] * sValue) / yHitDelta;
                } else if (zHitDelta != 0 && slopeOfCheckLine[2] != 0) {
                    tValue = (corner.getZ() - midpoint[2] + slopeOfCheckLine[2] * sValue) / zHitDelta;
                }
                done = true;
            }
            //[0] is constant, and [1] is slope, IMPORTANT is [0] + [1] in actuality
            double[] tRelS = new double[2];
            double[] sRelT = new double[2];
            int equationSolved = 0;

            //here I should know that there is no equation that hasn't got a valid slope on both sides, or they got 0 in slope gradient on both
            if (!done) {
                if (xHitDelta != 0 && slopeOfCheckLine[0] != 0) {
                    tRelS[0] = (corner.getX() - midpoint[0]) / xHitDelta;
                    tRelS[1] = slopeOfCheckLine[0] / xHitDelta;
                    equationSolved = 0;
                } else if (yHitDelta != 0 && slopeOfCheckLine[1] != 0) {
                    tRelS[0] = (corner.getY() - midpoint[1]) / yHitDelta;
                    tRelS[1] = slopeOfCheckLine[1] / yHitDelta;
                    equationSolved = 1;
                } else if (zHitDelta != 0 && slopeOfCheckLine[2] != 0) {
                    tRelS[0] = (corner.getZ() - midpoint[2]) / zHitDelta;
                    tRelS[1] = slopeOfCheckLine[2] / zHitDelta;
                    equationSolved = 2;
                }
                //now to solve for s value
                //using chooseIndex to avoid redoing the same equation twice, would not work
                switch (chooseIndex(3, equationSolved + 1)) {
                    case 0:
                        if (slopeOfCheckLine[0] != 0 && xHitDelta != 0) {
                            sValue = (corner.getX() - midpoint[0] - xHitDelta * tRelS[0]) / (xHitDelta * tRelS[1] - slopeOfCheckLine[0]);
                            equationSolved = 0;
                            break;
                        }
                    case 1:
                        if (slopeOfCheckLine[1] != 0 && yHitDelta != 0) {
                            sValue = (corner.getY() - midpoint[1] - yHitDelta * tRelS[0]) / (yHitDelta * tRelS[1] - slopeOfCheckLine[1]);
                            equationSolved = 1;
                            break;
                        }
                    case 2:
                        if (slopeOfCheckLine[2] != 0 && zHitDelta != 0) {
                            sValue = (corner.getZ() - midpoint[2] - zHitDelta * tRelS[0]) / (zHitDelta * tRelS[1] - slopeOfCheckLine[2]);
                            equationSolved = 2;
                            break;
                        }
                }
                //now to solve for t value
                //TODO make sure that it's no copy-paste errors
                switch (chooseIndex(3, equationSolved + 1)) {
                    case 0:
                        if (slopeOfCheckLine[0] != 0 && xHitDelta != 0) {
                            tValue = (corner.getX() - midpoint[0] + slopeOfCheckLine[0] * sValue) / xHitDelta;
                            equationSolved = 0;
                            break;
                        }
                    case 1:
                        if (slopeOfCheckLine[1] != 0 && yHitDelta != 0) {
                            tValue = (corner.getY() - midpoint[1] + slopeOfCheckLine[1] * sValue) / yHitDelta;
                            equationSolved = 1;
                            break;
                        }
                    case 2:
                        if (slopeOfCheckLine[2] != 0 && zHitDelta != 0) {
                            tValue = (corner.getZ() - midpoint[2] + slopeOfCheckLine[2] * sValue) / zHitDelta;
                            equationSolved = 2;
                            break;
                        }
                }
            }
            //check if actually valid answers, if so, add it as a valid intersection
            //space range, universal t- and s- values
            Point intersectionPoint = new Point(determineSignificantDigits(midpoint[0] + xHitDelta * tValue, 5),
                    determineSignificantDigits(midpoint[1] + yHitDelta * tValue, 5),
                    determineSignificantDigits(midpoint[2] + zHitDelta * tValue, 5));
            if (tValue >= 0 && sValue >= 0
                    && !isWithinSpaceRange(intersectionPoint, new Point(midpoint), new Point(planeIntersectionPoint))
                    && isWithinSpaceRange(intersectionPoint, corner, corners[chooseIndex(corners.length, i + 1)])
                    && intersectionPoint.getX() == determineSignificantDigits(corner.getX() + slopeOfCheckLine[0] * sValue, 5)
                    && intersectionPoint.getY() == determineSignificantDigits(corner.getY() + slopeOfCheckLine[1] * sValue, 5)
                    && intersectionPoint.getZ() == determineSignificantDigits(corner.getZ() + slopeOfCheckLine[2] * sValue, 5)) {
                intersectionsWithAreaSides++;
            }
        }
        //valid if exiting, or exiting and then penetrating the object again for more complex polygons
        return intersectionsWithAreaSides == 1 || intersectionsWithAreaSides == 3;
    }


}
