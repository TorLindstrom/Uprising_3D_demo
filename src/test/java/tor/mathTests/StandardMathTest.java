package tor.mathTests;

import org.junit.Test;
import tor.mathHandling.StandardMath;

import static org.junit.Assert.*;
import static tor.mathHandling.StandardMath.*;

public class StandardMathTest
{



    //------------------------one step authenticated tests

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
