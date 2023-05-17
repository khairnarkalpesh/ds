import mpi.*;

public class MPISum {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.getRank();
        int size = MPI.COMM_WORLD.getSize();
        int N = 100; // number of array elements
        int n = size; // number of processors
        int[] arr = new int[N];
        int[] subArr = new int[N/n];
        int subSum = 0;
        int sum = 0;

        // initialize the array
        for (int i = 0; i < N; i++) {
            arr[i] = i;
        }

        // divide the array into parts and send to processors
        MPI.COMM_WORLD.scatter(arr, N/n, MPI.INT, subArr, N/n, MPI.INT, 0);

        // calculate the sum of the part
        for (int i = 0; i < N/n; i++) {
            subSum += subArr[i];
        }

        // send the sum to the master processor
        MPI.COMM_WORLD.reduce(subSum, sum, 1, MPI.INT, MPI.SUM, 0);

        if (rank == 0) {
            // display the intermediate sums calculated at different processors
            System.out.println("Intermediate sums:");
            for (int i = 0; i < size; i++) {
                System.out.println("Processor " + i + ": " + sum);
            }

            // display the final sum
            System.out.println("Final sum: " + sum);
        }

        MPI.Finalize();
    }
}
