import java.util.ArrayList;

public class BerkeleyClockSync {

    private static final int ROUND_TRIP_DELAY = 100; // in milliseconds
    private static final int NUM_ITERATIONS = 3;

    public static void main(String[] args) {
        // Create a list of clock times for each computer
        ArrayList<Long> clockTimes = new ArrayList<>();
        clockTimes.add(1000L); // Computer A
        clockTimes.add(1500L); // Computer B
        clockTimes.add(2000L); // Computer C

        // Run the algorithm
        long offset = runAlgorithm(clockTimes);

        // Print the results
        System.out.println("Clock offset: " + offset + " milliseconds");
    }

    private static long runAlgorithm(ArrayList<Long> clockTimes) {
        // Calculate the average clock time
        long sum = 0;
        for (long clockTime : clockTimes) {
            sum += clockTime;
        }
        long averageTime = sum / clockTimes.size();

        // Calculate the time offsets
        ArrayList<Long> offsets = new ArrayList<>();
        for (long clockTime : clockTimes) {
            offsets.add(averageTime - clockTime);
        }

        // Repeat the algorithm for a number of iterations
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            // Calculate the new average clock time with the adjusted offsets
            sum = 0;
            for (int j = 0; j < clockTimes.size(); j++) {
                long adjustedTime = clockTimes.get(j) + offsets.get(j);
                sum += adjustedTime;
            }
            long newAverageTime = sum / clockTimes.size();

            // Calculate the new offsets based on the new average time
            offsets.clear();
            for (long clockTime : clockTimes) {
                offsets.add(newAverageTime - clockTime);
            }
        }

        // Calculate the final clock offset
        long sumOffsets = 0;
        for (long offset : offsets) {
            sumOffsets += offset;
        }
        long averageOffset = sumOffsets / offsets.size();
        long finalOffset = averageOffset - (ROUND_TRIP_DELAY / 2);

        return finalOffset;
    }
}
