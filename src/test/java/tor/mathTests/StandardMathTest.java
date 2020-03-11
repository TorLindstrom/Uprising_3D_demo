package tor.mathTests;

import org.junit.Test;
import tor.controller.Camera;
import tor.mathHandling.StandardMath;
import tor.shapeHandling.Point;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.*;
import static tor.mathHandling.StandardMath.*;
import static tor.visualHandling.PerspectiveMath.*;

public class StandardMathTest
{
    @Test
    public void tryingOutBigDecimal(){
        /*System.out.println(new BigDecimal(100).divide(new BigDecimal(3), MathContext.DECIMAL32));
        System.out.println(new BigDecimal(100).divide(new BigDecimal(3), new MathContext(7, RoundingMode.HALF_EVEN)));
        System.out.println(new BigDecimal(100).divide(new BigDecimal(3), MathContext.DECIMAL64));
        System.out.println(new BigDecimal(100).divide(new BigDecimal(3), MathContext.DECIMAL128));
        System.out.println(100/3.);
        System.out.println(new BigDecimal(1000).divide(new BigDecimal(3), 10, RoundingMode.HALF_EVEN));
        System.out.println(new BigDecimal(1000).divide(new BigDecimal(3), 20, RoundingMode.HALF_EVEN));
        System.out.println(new BigDecimal(1000).divide(new BigDecimal(3), 30, RoundingMode.HALF_EVEN));*/
        /*System.out.println(new BigDecimal(10).multiply(new BigDecimal("3.3")));
        System.out.println(new BigDecimal(10).setScale(10).multiply(new BigDecimal("3.3")));
        System.out.println(new BigDecimal(10).setScale(10, RoundingMode.HALF_EVEN).multiply(new BigDecimal("3.3")));
        System.out.println(new BigDecimal(10).setScale(10).multiply(new BigDecimal("3.3").setScale(10)));
        System.out.println(new BigDecimal(10).setScale(10).multiply(new BigDecimal("3.3").setScale(10)).doubleValue());*/
        System.out.println(5.6 + 5.8);
        System.out.println((5.6 * 100 + 5.8 * 100) / 100);
        System.out.println(0.56 + 0.58);
        System.out.println((0.56 * 1000 + 0.58 * 1000) / 1000);
        System.out.println();
    }

    @Test
    public void createSecondRaySpecial1(){
        Camera camera = new Camera(0, 0, 0);
        Point point = createSecondRayPosition(196, 376, camera);
        int[] screenPos =  makeRelative(point, camera);
        assertEquals(196,screenPos[0]);
        assertEquals(376,screenPos[1]);
    }

    //------------------------one step validated tests

    @Test
    public void createSecondRayTopLeft(){
        Camera camera = new Camera(0, 0, 0);
        Point point = createSecondRayPosition(0, 0, camera);
        int[] screenPos =  makeRelative(point, camera);
        assertEquals(0,screenPos[0]);
        assertEquals(0,screenPos[1]);
    }

    @Test
    public void createSecondRayTopRight(){
        Camera camera = new Camera(0, 0, 0);
        Point point = createSecondRayPosition(800, 0, camera);
        int[] screenPos =  makeRelative(point, camera);
        assertEquals(800,screenPos[0], 1);
        assertEquals(0,screenPos[1], 1);
    }

    @Test
    public void createSecondRayBotLeft(){
        Camera camera = new Camera(0, 0, 0);
        Point point = createSecondRayPosition(0, 500, camera);
        int[] screenPos =  makeRelative(point, camera);
        assertEquals(0,screenPos[0], 1);
        assertEquals(500,screenPos[1], 1);
    }

    @Test
    public void createSecondRayBotRight(){
        Camera camera = new Camera(0, 0, 0);
        Point point = createSecondRayPosition(800, 500, camera);
        int[] screenPos =  makeRelative(point, camera);
        assertEquals(800,screenPos[0], 1);
        assertEquals(500,screenPos[1], 1);
    }

    @Test
    public void createSecondRayMiddle(){
        Camera camera = new Camera(0, 0, 0);
        Point point = createSecondRayPosition(400, 250, camera);
        int[] screenPos =  makeRelative(point, camera);
        assertEquals(400,screenPos[0]);
        assertEquals(250,screenPos[1]);
    }

    @Test
    public void calcPaneDistanceDouble(){
        assertEquals(Math.sqrt(34.0), calculatePaneDistance(3.0, 5.0), 0.002);
    }

    @Test
    public void calcPaneDistanceInt(){
        assertEquals(Math.sqrt(34), calculatePaneDistance(3, 5), 0.002);
    }

    @Test
    public void isWithinRangeTrue(){
        assertTrue(isWithinRange(0, 1, -1));
    }

    @Test
    public void isWithinRangeFalse(){
        assertFalse(isWithinRange(0, 1, 2));
    }

    @Test
    public void isWithinSpaceRangeFalse(){
        assertFalse(isWithinSpaceRange(new Point(0, 0, 0), new Point(1,1,1), new Point(2, 2, 2)));
    }

    @Test
    public void isWithinSpaceRangeTrue(){
        assertTrue(isWithinSpaceRange(new Point(0, 0, 0), new Point(1,-1,0), new Point(-2, 2, 2)));
    }

    @Test
    public void isNegativeTrue(){
        assertTrue(isNegative(-1));
    }

    @Test
    public void isNegativeFalse(){
        assertFalse(isNegative(1));
    }

    @Test
    public void truncatingSignificantDigits(){
        assertEquals(230.23, StandardMath.determineSignificantDigits(230.23456789, 2), 0);
    }
}
