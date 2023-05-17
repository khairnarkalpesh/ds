import org.omg.CORBA.*;
import org.omg.CosNaming.*;

import CalculatorApp.*;

public class Client {

  public static void main(String args[]) {
    try {
      // Create and initialize the ORB
      ORB orb = ORB.init(args, null);

      // Get a reference to the object from the Naming Service
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
      Calculator calculator = CalculatorHelper.narrow(ncRef.resolve_str("Calculator"));

      // Perform some calculations
      float x = 10;
      float y = 5;

      System.out.println("x + y = " + calculator.add(x, y));
      System.out.println("x - y = " + calculator.subtract(x, y));
      System.out.println("x * y = " + calculator.multiply(x, y));
      System.out.println("x / y = " + calculator.divide(x, y));

      // Shutdown the ORB
      orb.shutdown(true);
    } catch (Exception e) {
      System.err.println("Error: " + e);
      e.printStackTrace(System.out);
    }
  }
}
