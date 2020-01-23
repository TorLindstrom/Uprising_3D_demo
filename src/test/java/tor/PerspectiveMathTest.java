package tor;

import static tor.visualHandling.PerspectiveMath.*;

import org.junit.Test;
import tor.shapeHandling.Point;

import static org.junit.Assert.*;

public class PerspectiveMathTest
{
    @Test
    public void shouldAnswerWithTrue()
    {
        Camera camera = new Camera(new Point(-300, 200, 200));
        double[] pos = new double[]{-299.33254278397163, 200.90333911076652, 200.5159768315343};
        assertEquals(0, makeRelative(pos, camera)[0], 1);
        assertEquals(0, makeRelative(pos, camera)[1], 1);
        pos = new double[]{-298.07262960835345, 199.64342593514834, 200.5159768315343};
        assertEquals(0, makeRelative(pos, camera)[0], 1);
        assertEquals(0, makeRelative(pos, camera)[1], 1);
        pos = new double[]{-299.33254278397163, 200.90333911076652, 199.4840231684657};
        assertEquals(0, makeRelative(pos, camera)[0], 1);
        assertEquals(0, makeRelative(pos, camera)[1], 1);
        pos = new double[]{-298.07262960835345, 199.64342593514834, 199.4840231684657};
        assertEquals(0, makeRelative(pos, camera)[0], 1);
        assertEquals(0, makeRelative(pos, camera)[1], 1);
    }
}
