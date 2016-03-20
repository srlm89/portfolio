package xxl.java.container.classic;

import static org.junit.Assert.assertTrue;
import static xxl.java.container.classic.EasyArray.objectArray;

import java.util.Arrays;

import org.junit.Test;


public class EasyArrayTest {

	@Test
	public void toObjectArray() {
		assertTrue(Arrays.equals(new Byte[] {1, 2, 3}, objectArray(new byte[] {1, 2, 3})));
		assertTrue(Arrays.equals(new Character[] {1, 2, 3}, objectArray(new char[] {1, 2, 3})));
		assertTrue(Arrays.equals(new Short[] {1, 2, 3}, objectArray(new short[] {1, 2, 3})));
		assertTrue(Arrays.equals(new Integer[] {1, 2, 3}, objectArray(new int[] {1, 2, 3})));
		assertTrue(Arrays.equals(new Long[] {1L, 2L, 3L}, objectArray(new long[] {1, 2, 3})));
		assertTrue(Arrays.equals(new Float[] {1f, 2f, 3f}, objectArray(new float[] {1, 2, 3})));
		assertTrue(Arrays.equals(new Double[] {1d, 2d, 3d}, objectArray(new double[] {1, 2, 3})));
	}
}
