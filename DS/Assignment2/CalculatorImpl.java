import CalculatorApp.*;

public class CalculatorImpl extends CalculatorPOA {

  public float add(float x, float y) {
    return x + y;
  }

  public float subtract(float x, float y) {
    return x - y;
  }

  public float multiply(float x, float y) {
    return x * y;
  }

  public float divide(float x, float y) {
    return x / y;
  }
}
