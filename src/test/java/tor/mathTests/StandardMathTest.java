package tor.mathTests;

import org.junit.Test;
import tor.mathHandling.StandardMath;
import tor.shapeHandling.Point;

import static org.junit.Assert.*;
import static tor.mathHandling.StandardMath.*;

public class StandardMathTest
{



    //------------------------one step authenticated tests

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
