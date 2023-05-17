import org.omg.CORBA.*;
import org.omg.CosNaming.*;

import CalculatorApp.*;

public class Server {

  public static void main(String args[]) {
    try {
      // Create and initialize the ORB
      ORB orb = ORB.init(args, null);

      // Create the server object
      CalculatorImpl calculatorImpl = new CalculatorImpl();

      // Activate the server object
      POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootPOA.activate_object(calculatorImpl);

      // Get a reference to the object
      org.omg.CORBA.Object ref = rootPOA.servant_to_reference(calculatorImpl);
      Calculator calculator = CalculatorHelper.narrow(ref);

      // Register the object with the Naming Service
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
      NameComponent path[] = ncRef.to_name("Calculator");
      ncRef.rebind(path, calculator);

      // Wait for client requests
      System.out.println("Calculator server ready and waiting...");
      orb.run();
    } catch (Exception e) {
      System.err.println("Error: " + e);
      e.printStackTrace(System.out);
    }
  }
}
