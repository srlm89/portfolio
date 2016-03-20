package xxl.java.container.range;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import xxl.java.container.pair.Pair;

public class SpiralRangeTest {

	@Test
	public void pairsWithStep1Scale1() {
		SpiralRange ranger = new SpiralRange(1, 1);
		checkRanges(ranger, 0, 1);
		checkRanges(ranger, 1, 2);
		checkRanges(ranger, 2, 3);
		checkRanges(ranger, 3, 4);
		checkRanges(ranger, 99, 100);
		checkRanges(ranger, 300, 301);
		checkRanges(ranger, 100041, 10042);
	}
	
	@Test
	public void pairsWithStep2Scale1() {
		SpiralRange ranger = new SpiralRange(2, 1);
		checkRanges(ranger, 0, 2);
		checkRanges(ranger, 2, 4);
		checkRanges(ranger, 4, 6);
		checkRanges(ranger, 6, 8);
		checkRanges(ranger, 16, 18);
		checkRanges(ranger, 32, 34);
		checkRanges(ranger, 64, 66);
		checkRanges(ranger, 128, 130);
	}
	
	@Test
	public void pairsWithStep10Scale1() {
		SpiralRange ranger = new SpiralRange(10, 1);
		checkRanges(ranger, 0, 10);
		checkRanges(ranger, 10, 20);
		checkRanges(ranger, 20, 30);
		checkRanges(ranger, 90, 100);
		checkRanges(ranger, 100, 110);
		checkRanges(ranger, 110, 120);
		checkRanges(ranger, 1010, 1020);
	}
	
	@Test
	public void pairsWithStep1Scale2() {
		SpiralRange ranger = new SpiralRange(1, 2);
		checkRanges(ranger, 0, 1);
		checkRanges(ranger, 1, 2);
		checkRanges(ranger, 2, 4);
		checkRanges(ranger, 4, 8);
		checkRanges(ranger, 8, 16);
		checkRanges(ranger, 16, 32);
		checkRanges(ranger, 32, 64);
		checkRanges(ranger, 64, 128);
	}
	
	@Test
	public void pairsWithStep2Scale2() {
		SpiralRange ranger = new SpiralRange(2, 2);
		checkRanges(ranger, 0, 2);
		checkRanges(ranger, 2, 4);
		checkRanges(ranger, 4, 8);
		checkRanges(ranger, 8, 16);
		checkRanges(ranger, 16, 32);
		checkRanges(ranger, 32, 64);
		checkRanges(ranger, 64, 128);
		checkRanges(ranger, 128, 256);
	}
	
	@Test
	public void pairsWithStep4Scale8() {
		SpiralRange ranger = new SpiralRange(4, 8);
		checkRanges(ranger, 0, 4);
		checkRanges(ranger, 4, 8);
		checkRanges(ranger, 8, 32);
		checkRanges(ranger, 32, 64);
		checkRanges(ranger, 64, 256);
		checkRanges(ranger, 256, 512);
		checkRanges(ranger, 512, 2048);
		checkRanges(ranger, 2048, 4096);
		checkRanges(ranger, 4096, 16384);
	}
	
	@Test
	public void pairsWithStep1Scale10() {
		SpiralRange ranger = new SpiralRange(1, 10);
		checkRanges(ranger, 0, 1);
		checkRanges(ranger, 1, 2);
		checkRanges(ranger, 2, 3);
		checkRanges(ranger, 9, 10);
		checkRanges(ranger, 10, 20);
		checkRanges(ranger, 20, 30);
		checkRanges(ranger, 30, 40);
		checkRanges(ranger, 40, 50);
		checkRanges(ranger, 50, 60);
		checkRanges(ranger, 90, 100);
		checkRanges(ranger, 100, 200);
		checkRanges(ranger, 200, 300);
		checkRanges(ranger, 300, 400);
		checkRanges(ranger, 900, 1000);
		checkRanges(ranger, 1000, 2000);
	}
	
	@Test
	public void pairsWithStep2Scale10() {
		SpiralRange ranger = new SpiralRange(2, 10);
		checkRanges(ranger, 0, 2);
		checkRanges(ranger, 2, 4);
		checkRanges(ranger, 4, 6);
		checkRanges(ranger, 6, 8);
		checkRanges(ranger, 8, 10);
		checkRanges(ranger, 10, 20);
		checkRanges(ranger, 20, 40);
		checkRanges(ranger, 40, 60);
		checkRanges(ranger, 60, 80);
		checkRanges(ranger, 80, 100);
		checkRanges(ranger, 100, 200);
		checkRanges(ranger, 200, 400);
		checkRanges(ranger, 800, 1000);
		checkRanges(ranger, 1000, 2000);
		checkRanges(ranger, 2000, 4000);
	}

	@Test
	public void pairsWithStep5Scale10() {
		SpiralRange ranger = new SpiralRange(5, 10);
		checkRanges(ranger, 0, 5);
		checkRanges(ranger, 5, 10);
		checkRanges(ranger, 10, 50);
		checkRanges(ranger, 50, 100);
		checkRanges(ranger, 100, 500);
		checkRanges(ranger, 500, 1000);
		checkRanges(ranger, 1000, 5000);
		checkRanges(ranger, 5000, 10000);
		checkRanges(ranger, 10000, 50000);
	}

	@Test
	public void negativePairsWithStep5Scale10() {
		SpiralRange ranger = new SpiralRange(5, 10);
		checkNegativeRanges(ranger, 0, -5);
		checkNegativeRanges(ranger, -5, -10);
		checkNegativeRanges(ranger, -10, -50);
		checkNegativeRanges(ranger, -50, -100);
		checkNegativeRanges(ranger, -100, -500);
		checkNegativeRanges(ranger, -500, -1000);
		checkNegativeRanges(ranger, -1000, -5000);
		checkNegativeRanges(ranger, -5000, -10000);
		checkNegativeRanges(ranger, -10000, -50000);
	}

	private void checkRanges(SpiralRange ranger, Integer low, Integer high) {
		for (int value = low; value < high; value += 1) {
			Pair<Integer,Integer> pair = ranger.rangeFor(value);
			assertEquals(format("%s: [%s, %s]", value, low, high), low, pair.first());
			assertEquals(format("%s: [%s, %s]", value, low, high), high, pair.second());
		}
	}
	
	private void checkNegativeRanges(SpiralRange ranger, Integer low, Integer high) {
		for (int value = high; value > low; value -= 1) {
			Pair<Integer,Integer> pair = ranger.rangeFor(value);
			assertEquals(format("%s: [%s, %s]", value, low, high), low, pair.first());
			assertEquals(format("%s: [%s, %s]", value, low, high), high, pair.second());
		}
	}
}
