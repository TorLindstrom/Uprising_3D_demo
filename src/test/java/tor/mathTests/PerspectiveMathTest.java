package tor.mathTests;

import static tor.visualHandling.PerspectiveMath.*;

import org.junit.Test;
import tor.Camera;
import tor.mathHandling.StandardMath;
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
    public void makeRelativeDebugTestCheck()
    {
        Camera camera = new Camera(new Point(-300, 200, 200));
        camera.setHorizontalAngle(camera.getHorizontalAngle() + 5);
        //center
        Point center = new Point(-200, 200, 200);
        assertEquals(400, makeRelative(center, camera)[0], 1);
        assertEquals(250, makeRelative(center, camera)[1], 1);
    }


    //------------------------one step authenticated tests

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

    //------------------------two step authenticated tests
}
