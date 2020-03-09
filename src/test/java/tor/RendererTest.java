package tor;

import org.junit.Test;
import tor.controller.Manager;
import tor.shapeHandling.Point;
import tor.visualHandling.Renderer;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static tor.visualHandling.PerspectiveMath.makeRelative;
import static tor.visualHandling.Renderer.*;
import static tor.visualHandling.Window.height;
import static tor.visualHandling.Window.width;

public class RendererTest
{

    //------------------------one step authenticated tests

    @Test
    public void frustumCornerChecks() throws InterruptedException
    {
        Manager manager = new Manager(new String[]{"true"});
        Point[] frustumCorners = calculateFrustumCorners();
        assertArrayEquals(new int[]{0,0}, makeRelative(frustumCorners[0], manager.getCamera()));
        assertArrayEquals(new int[]{width,0}, makeRelative(frustumCorners[1], manager.getCamera()));
        assertArrayEquals(new int[]{0,height}, makeRelative(frustumCorners[2], manager.getCamera()));
        assertArrayEquals(new int[]{width,height}, makeRelative(frustumCorners[3], manager.getCamera()));
    }

    @Test
    public void testUnpackSimple()
    {
        Integer[] array = new Integer[]{1,2,3,4,5,6};
        int[] primitiveArray = new int[]{1,2,3,4,5,6};
        assertTrue(Arrays.equals(unpack(array), primitiveArray));
    }

    @Test
    public void testUnpackMore()
    {
        Integer[] array = new Integer[]{32,22,453,467,55,643};
        int[] primitiveArray = new int[]{32,22,453,467,55,643};
        assertTrue(Arrays.equals(unpack(array), primitiveArray));
    }

    //------------------------two step authenticated tests
}
