import java.util.LinkedList;
import java.util.Queue;

// Q1 Scheduler class for Round-Robin scheduling
class Q1Scheduler {
   public static Queue<PCB> queue;
    private double timeQuantum;
    private double currentTime;

    // Constructor to initialize Q1 Scheduler with time quantum
    public Q1Scheduler(double timeQuantum) {
        this.queue = new LinkedList<>();
        this.timeQuantum = timeQuantum;
        this.currentTime = 0;
    }

    // Method to add a process to the Q1 queue
    public void addProcess(PCB process) {
        queue.offer(process);
    }

    // Method to execute processes in the Q1 queue using Round-Robin scheduling
    public void executeProcesses() {
        while (!queue.isEmpty()) {
            PCB currentProcess = queue.poll();
            currentProcess.setStartingTime(currentTime);

            double remainingBurstTime = currentProcess.getCpuBurstTime();

            while (remainingBurstTime > 0) {
                if (remainingBurstTime <= timeQuantum) {
                    currentTime += remainingBurstTime;
                    remainingBurstTime = 0;
                } else {
                    currentTime += timeQuantum;
                    remainingBurstTime -= timeQuantum;
                    queue.offer(currentProcess); // Re-add process to the end of the queue
                }
            }

            currentProcess.setTerminationTime(currentTime);
            currentProcess.setTurnAroundTime(currentProcess.getTerminationTime() - currentProcess.getArrivalTime());
            currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getCpuBurstTime());
        }
    }
}