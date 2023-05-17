import java.util.concurrent.Semaphore;

class TokenRing {
    private static final int NUM_PROCESSES = 3; // number of processes in the ring
    private static Semaphore[] semaphores = new Semaphore[NUM_PROCESSES];
    private static volatile int tokenIndex = 0;

    public static void main(String[] args) {
        // Initialize semaphores
        for (int i = 0; i < NUM_PROCESSES; i++) {
            semaphores[i] = new Semaphore(1);
        }

        // Create processes
        for (int i = 0; i < NUM_PROCESSES; i++) {
            final int processId = i;
            new Thread(() -> {
                while (true) {
                    try {
                        // Wait for token
                        while (tokenIndex != processId) {
                            Thread.sleep(100);
                        }

                        // Enter critical section
                        System.out.println("Process " + processId + " entered critical section.");
                        Thread.sleep(1000);
                        System.out.println("Process " + processId + " exited critical section.");

                        // Pass token to next process
                        tokenIndex = (tokenIndex + 1) % NUM_PROCESSES;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        // Start with token at process 0
        tokenIndex = 0;
    }
}
