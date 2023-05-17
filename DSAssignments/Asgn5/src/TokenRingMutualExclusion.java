import java.util.concurrent.Semaphore;

class Process implements Runnable {
    private final int processId;
    private final Semaphore token;
    private final Semaphore criticalSection;

    public Process(int processId, Semaphore token, Semaphore criticalSection) {
        this.processId = processId;
        this.token = token;
        this.criticalSection = criticalSection;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Non-critical section

                // Acquire the token
                token.acquire();

                // Critical section
                System.out.println("Process " + processId + " entered the critical section.");
                Thread.sleep(1000); // Simulating some work inside the critical section
                System.out.println("Process " + processId + " exited the critical section.");

                // Release the token
                token.release();

                // Simulating some non-critical work
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class TokenRingMutualExclusion {
    public static void main(String[] args) {
        int numProcesses = 5; // Number of processes
        Semaphore token = new Semaphore(1); // Initial token
        Semaphore criticalSection = new Semaphore(1); // Semaphore for entering critical section

        // Create and start the processes
        Thread[] threads = new Thread[numProcesses];
        for (int i = 0; i < numProcesses; i++) {
            threads[i] = new Thread(new Process(i, token, criticalSection));
            threads[i].start();
        }

        // Wait for all processes to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
