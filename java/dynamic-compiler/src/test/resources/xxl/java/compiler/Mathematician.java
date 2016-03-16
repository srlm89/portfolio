package test.dynamic.math;

import test.dynamic.math.Calculator;

public class Mathematician {

    public Mathematician(Calculator calculator) {
        this.calculator = calculator;
    }

    public int sum(int a, int b) {
        return calculator.sum(a, b);
    }

    public int multiply(int a, int b) {
        return calculator.multiply(a, b);
    }

    private Calculator calculator;
}
