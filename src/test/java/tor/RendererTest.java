package tor;

import org.junit.Test;
import tor.shapeHandling.Point;
import tor.visualHandling.Renderer;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tor.visualHandling.PerspectiveMath.makeRelative;
import static tor.visualHandling.Renderer.*;
import static tor.visualHandling.Window.width;

public class RendererTest
{



    //------------------------one step authenticated tests

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
