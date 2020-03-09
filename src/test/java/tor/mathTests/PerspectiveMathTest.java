package tor.mathTests;

import static java.lang.Math.*;
import static tor.visualHandling.PerspectiveMath.*;

import org.junit.Test;
import tor.controller.Camera;
import tor.controller.Manager;
import tor.shapeHandling.Point;
import tor.shapeHandling.Side;

import static tor.visualHandling.Window.*;

import static org.junit.Assert.*;

public class PerspectiveMathTest
{
    @Test
    public void frustumCornerCheck()
    {
        Camera camera = new Camera(new Point(-300, 200, 200));
        //center
        Point center = new Point(-200, 200, 200);
        assertEquals(400, makeRelative(center, camera)[0], 1);
        assertEquals(250, makeRelative(center, camera)[1], 1);
        //Top left
        double[] rawPos = new double[]{-215.6605, 284.3395, 253.729};
        Point pos = new Point(rawPos);
        assertEquals(0, makeRelative(pos, camera)[0], 1);
        assertEquals(0, makeRelative(pos, camera)[1], 1);
        //Top right
        pos = new Point(new double[]{-215.6605, 115.6605, 253.729});
        assertEquals(800, makeRelative(pos, camera)[0], 1);
        assertEquals(0, makeRelative(pos, camera)[1], 1);
        //Bot left
        pos = new Point(new double[]{-215.6605, 284.3395, 146.271});
        assertEquals(0, makeRelative(pos, camera)[0], 1);
        assertEquals(500, makeRelative(pos, camera)[1], 1);
        //Bot right
        pos = new Point(new double[]{-215.6605, 115.6605, 146.271});
        assertEquals(800, makeRelative(pos, camera)[0], 1);
        assertEquals(500, makeRelative(pos, camera)[1], 1);
    }

    @Test
    public void frustumCornerChecks() throws InterruptedException
    {
        Manager manager = new Manager(new String[]{"true"});
    }

    //@Test
    public void makeRelativeDebugTestCheck()
    {
        Camera camera = new Camera(new Point(-300, 200, 200));
        camera.setHorizontalAngle(camera.getHorizontalAngle() + 5);
        //center
        Point center = new Point(-200, 200, 200);
        assertEquals(400, makeRelative(center, camera)[0], 1);
        assertEquals(250, makeRelative(center, camera)[1], 1);
    }


    //------------------------one step validated tests

    @Test
    public void angleFlips(){
        Camera camera = new Camera(0,0,0);
        assertEquals(120, flipAroundAngle(camera, 120), 0);
        assertEquals(-140, flipAroundAngle(camera, 220), 0);
        assertEquals(140, flipAroundAngle(camera, -220), 0);
        assertEquals(60, flipAroundAngle(camera, 420), 0);
    }

    @Test
    public void halfAngleCheck()
    {
        Camera camera = new Camera(0,0,0);
        //these should be the same screen distance away from each other, is?
        System.out.println(makeRelative(new Point(100, 0, 0), camera)[0]);
        System.out.println(makeRelative(new Point(100, 20, 0), camera)[0]);
        System.out.println(makeRelative(new Point(100, 40, 0), camera)[0]);
        System.out.println(makeRelative(new Point(100, 60, 0), camera)[0]);
        System.out.println(makeRelative(new Point(100, 80, 0), camera)[0]);
        System.out.println(makeRelative(new Point(100, 100, 0), camera)[0]);
        System.out.println(makeRelative(new Point(100, 120, 0), camera)[0]);
        double widthWithFOV = tan(55 * PI / 180) * 100;
        double heightWithFOV = tan(42.5 * PI / 180) * 100;
        assertEquals(width / 4., makeRelative(new Point(100, widthWithFOV / 2, 0), camera)[0], 1);
        assertEquals((width / 8.) * 3, makeRelative(new Point(100, widthWithFOV / 4, 0), camera)[0], 1);
        assertEquals(height / 4., makeRelative(new Point(100, 0, heightWithFOV / 2), camera)[1], 1);

        //FOV change
        camera.setHorizontalFOV(80);
        camera.setVerticalFOV(50);
        widthWithFOV = tan(40 * PI / 180) * 100;
        heightWithFOV = tan(25 * PI / 180) * 100;
        assertEquals(width / 4., makeRelative(new Point(100, widthWithFOV / 2, 0), camera)[0], 1);
        assertEquals((width / 8.) * 3, makeRelative(new Point(100, widthWithFOV / 4, 0), camera)[0], 1);
        assertEquals(height / 4., makeRelative(new Point(100, 0, heightWithFOV / 2), camera)[1], 1);
        //assertEquals(height / 4., makeRelative(new Point(100, 50, 0), new Camera(0,0,0))[0], 1);
    }

    @Test
    public void isRayInsideFinitePlaneInFront(){
        assertTrue(isRayInsideFinitePlane(new Side(new Point(200, 200, 0),
                        new Point(200, -200, 0),
                        new Point(200, -200, 200),
                        new Point(200, 200, 200)),
                new Point(0, 0, 100).getPosition(),
                new Point(50, 0, 100).getPosition()));
    }
    @Test
    public void isRayInsideFinitePlaneSkewed(){
        assertTrue(isRayInsideFinitePlane(new Side(new Point(200, 200, 0),
                        new Point(200, -200, 0),
                        new Point(200, -200, 200),
                        new Point(200, 200, 200)),
                new Point(0, 0, 100).getPosition(),
                new Point(50, 5, 100).getPosition()));
    }

    @Test
    public void isRayOutsideFinitePlaneSkewed(){
        assertFalse(isRayInsideFinitePlane(new Side(new Point(200, 200, 0),
                        new Point(200, -200, 0),
                        new Point(200, -200, 200),
                        new Point(200, 200, 200)),
                new Point(0, 0, 100).getPosition(),
                new Point(50, 60, 100).getPosition()));
    }

    @Test
    public void isRayInsideFiniteSkewedPlane(){
        assertTrue(isRayInsideFinitePlane(new Side(new Point(200, 200, 0),
                        new Point(250, -200, 0),
                        new Point(250, -180, 200),
                        new Point(200, 220, 200)),
                new Point(0, 0, 100).getPosition(),
                new Point(50, 0, 100).getPosition()));
    }

    @Test
    public void isSkewedRayInsideFiniteSkewedPlane(){
        assertTrue(isRayInsideFinitePlane(new Side(new Point(200, 200, 0),
                        new Point(250, -200, 0),
                        new Point(250, -180, 200),
                        new Point(200, 220, 200)),
                new Point(0, 0, 100).getPosition(),
                new Point(50, 5, 105).getPosition()));
    }

    @Test
    public void testCenterCalibrationOld()
    {
        Camera camera = new Camera(new Point(-300, 200, 200));
        //Top left
        double[] pos = new double[]{300, 200, 200};
        assertEquals(width/2, makeRelative(pos, camera)[0]);
        assertEquals(height/2, makeRelative(pos, camera)[1]);

    }

    @Test
    public void testCenterCalibrationNew()
    {
        Camera camera = new Camera(new Point(-300, 200, 200));
        //Top left
        double[] pos = new double[]{300, 200, 200};
        Point point = new Point(pos);
        assertEquals(width/2, makeRelative(point, camera)[0]);
        assertEquals(height/2, makeRelative(point, camera)[1]);

    }

    //------------------------two step validated tests
}
