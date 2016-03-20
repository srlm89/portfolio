package xxl.java.container.range;

import static java.lang.Math.abs;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import xxl.java.container.pair.Pair;

public class SpiralRange {

	public SpiralRange(int step, int scale) {
		this.step = step;
		this.scale = scale;
	}

	public Pair<Integer, Integer> rangeFor(int value) {
		int unsigned = abs(value);
		int factorPower = coefficient(unsigned);
		int area = unsigned / factorPower / step();
		int lowValue = step() * (area) + ((factorPower > 1 && area == 0) ? 1 : 0);
		int highValue = step() * (area + 1);
		return pairCorrectingSign(value < unsigned, factorPower * lowValue, factorPower * highValue);
	}

	private int coefficient(int value) {
		if (value == 0) {
			return 1;
		}
		Double logFactor = log10(value) / log10(scale());
		Double power = pow(scale(), logFactor.intValue());
		return power.intValue();
	}

	private Pair<Integer, Integer> pairCorrectingSign(boolean negative, int lowValue, int highValue) {
		if (negative) {
			return Pair.from(-1 * highValue, -1 * lowValue);
		}
		return Pair.from(lowValue, highValue);
	}

	private int scale() {
		return scale;
	}

	private int step() {
		return step;
	}

	private int step;
	private int scale;
}
