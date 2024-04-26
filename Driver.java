import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
public class Driver {
public static Scanner input = new Scanner(System.in);
public static Queue<PCB> cpuExecutionQueue = new LinkedList<>();
public static Queue<PCB> Queue2 = new LinkedList<>();
public static Queue<PCB> readyQueue = new LinkedList<>();
public static Queue<PCB> Queue1 = new LinkedList<>();
static int processesNum;
public static void main(String[] args) {
     System.out.println("Welcome to the process scheduling program!");
     int choice ;
//     int static processesNum;
     int priorityLvl ;
     double processArrivalTime;
     double burstTime;
     PCB newProcess;

      do {
            System.out.println("Please choose an option:");
            System.out.println("1. Enter process' information.");
            System.out.println("2. Display a detailed report about each process and different scheduling criteria.");
            System.out.println("3. Exit the program.");
            System.out.print("Enter your choice: ");

            // Validate choice input
            while (!input.hasNextInt()) {
                System.out.println("Invalid input! Please enter an integer value.");
                input.next();
            }
            choice = input.nextInt();
            input.nextLine(); // Clear the newline character from the input stream

            switch (choice) {
                case 1:
                    System.out.println("Please enter the number of processes to be scheduled:");
                    
                    // Validate processesNum input
                    processesNum = 0;
                    while (!input.hasNextInt()) {
                        System.out.println("Invalid input! Please enter an integer value.");
                        input.next();
                    }
                    processesNum = input.nextInt();
                    input.nextLine(); // Clear the newline character from the input stream
                    
                    for (int i = 1; i <= processesNum; i++) {
                        System.out.println("Please enter the priority of process " + i + ":");
                        // Validate priorityLvl input
                        priorityLvl = 1;
                        boolean validPriority = false;

                        // Validate priorityLvl input
                        while (!validPriority) {
                            if (!input.hasNextInt()) {
                                System.out.println("Invalid input! Please enter an integer value.");
                                input.next();
                                continue;
                            }
                            priorityLvl = input.nextInt();
                            input.nextLine(); // Clear the newline character from the input stream

                            if (priorityLvl != 1 && priorityLvl != 2) {
                                System.out.println("Invalid input! Please enter either 1 or 2.");
                            } else {
                                validPriority = true;
                            }
                        }
                        
                        System.out.println("Please enter the arrival time of process " + i + ":");
                        // Validate processArrivalTime input
                        processArrivalTime = 0;
                        while (!input.hasNextDouble()) {
                            System.out.println("Invalid input! Please enter a numeric value.");
                            input.next();
                        }
                        processArrivalTime = input.nextDouble();
                        input.nextLine(); // Clear the newline character from the input stream
                        
                        System.out.println("Please enter the CPU burst of process " + i + ":");
                        // Validate burstTime input
                        burstTime = 0;
                        while (!input.hasNextDouble()) {
                            System.out.println("Invalid input! Please enter a numeric value.");
                            input.next();
                        }
                        burstTime = input.nextDouble();
                        input.nextLine(); // Clear the newline character from the input stream
                        
                        // Creating a PCB object to schedule it
                        newProcess = new PCB(priorityLvl, processArrivalTime, burstTime);
                        newProcess.setProcessID("P" + i);
                        readyQueue.add(newProcess);
                        // Add scheduling algorithm call
                    }
                    primitivePriorityScheduling();
                    RRScheduling();
                    SJFScheduling();
                    break;

                case 2:
                	if (cpuExecutionQueue.isEmpty()) {
                	    System.out.println("There are no scheduled processes to be displayed.");
                	} else {
                		processesReport();
                	}
                    
                    break;

                default:
                if (choice != 3) {
                    System.out.println("Wrong input! Please try again.\n");
                }
                break;
            }
        } while (choice != 3);
}//end main


public static void RRScheduling() {
    int currentTime = 0;
    int timeQuantum = 3; // Time quantum for Round-Robin

    // Keep track of the starting time for each process
    Map<String, Double> startingTimeMap = new HashMap<>();

    // Loop through the processes in Queue1
    while (!Queue1.isEmpty()) {
        PCB process = Queue1.poll(); // Retrieve and remove the first process from Queue1

        // If the process is not in the starting time map, set its starting time
        if (!startingTimeMap.containsKey(process.getProcessID())) {
            startingTimeMap.put(process.getProcessID(), (double) currentTime);
        }

        // Set the starting time for the process
        process.setStartingTime(startingTimeMap.get(process.getProcessID()));

        // Process the time quantum or the remaining time, whichever is smaller
        int processingTime = Math.min(timeQuantum, process.getRemainingTime());

        // Update the current time
        currentTime += processingTime;

        // Update the remaining time for the process
        process.setRemainingTime(process.getRemainingTime() - processingTime);

        // Check if the process is not yet finished
        if (process.getRemainingTime() > 0) {
            // Re-add the process to the end of Queue1
            Queue1.add(process);
        } else {
            // Calculate termination time
            double terminationTime = currentTime;
            process.setTerminationTime(terminationTime);

            // Calculate turnaround time, waiting time, and performance time
            double arrivalTime = process.getArrivalTime();
            double cpuBurstTime = process.getCpuBurstTime();
            double turnAroundTime = terminationTime - arrivalTime;
            double waitingTime = turnAroundTime - cpuBurstTime;
            double performanceTime = startingTimeMap.get(process.getProcessID()) - arrivalTime;

            // Set calculated attributes
            process.setTurnAroundTime(turnAroundTime);
            process.setWaitingTime(waitingTime);
            process.setPerformanceTime(performanceTime);
        }

        // Add the processed process to the cpuExecutionQueue
        cpuExecutionQueue.add(process);
    }
}




public static void SJFScheduling() {

    double currentTime;
  
    PCB lastProcessExecuted = null;

    //find the last process in the cpuExecutionQueue to assign it's Termination time to the current time
    for (PCB process : cpuExecutionQueue) {
      lastProcessExecuted = process;
    }
    if (lastProcessExecuted != null) {
      currentTime = lastProcessExecuted.getTerminationTime();
      //if no processes gone through the Q1
    } else {
      currentTime = 0;
    }
  
    // Loop through the processes in Queue2
    while (!Queue2.isEmpty()) {

     //if the arrival times are the same of a two processes then determine the one with the shortest burst
      PCB currentProcess = Queue2.stream().min(Comparator.comparingDouble(PCB::getArrivalTime).thenComparingDouble(PCB::getCpuBurstTime)).get();

      // remove the currently executing process to make sure each one is processed only once 
      Queue2.remove(currentProcess); 
  
      // Set starting time for the current process
      currentProcess.setStartingTime(currentTime);
  
      // Update remaining burst time and calculate termination time
      currentTime += currentProcess.getCpuBurstTime();
      //tracking the completion a process
      currentProcess.setRemainingTime(0);
      currentProcess.setTerminationTime(currentTime);
  
      // Calculate turnaround time, waiting time, and performance time
      currentProcess.setTurnAroundTime(currentProcess.getTerminationTime() - currentProcess.getArrivalTime());
      currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getCpuBurstTime());
      currentProcess.setPerformanceTime(currentProcess.getStartingTime() - currentProcess.getArrivalTime());
  
      // Add the processed PCB to the cpuExecutionQueue
      cpuExecutionQueue.add(currentProcess);
    }
  }



public static void primitivePriorityScheduling(){
for (PCB process : readyQueue) {
    if(process.getProcessPriority()==1)
    Queue1.add(process);
    else 
    Queue2.add(process);
}


}



//suggested method (dana)
public static void processesReport() {
    double totalTurnaround = 0;
    double totalWaitingTime = 0;
    double totalResponseTime = 0;
    double numberOfProcesses = cpuExecutionQueue.size();
    String order = "";
    int count = 0;

    try {
        // Delete the existing file if it exists
        File file = new File("Report.txt");
        if (file.exists()) {
            file.delete();
        }
        // Open the file for writing
        FileWriter fileWriter = new FileWriter("Report.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        order += "The scheduling order of the processes :[";
      for (PCB pccb : cpuExecutionQueue) {
      	 if (count < numberOfProcesses - 1) {
      	        order += pccb.getProcessID() + "|";
      	    } else {
      	        order += pccb.getProcessID();
      	    }
      	    count++; }
      order += "]";
      bufferedWriter.write(order);
      bufferedWriter.newLine();
      System.out.println(order);

        Map<String, PCB> lastProcessMap = new HashMap<>();
        // Iterate over the CPU execution queue 
        for (PCB pcb : cpuExecutionQueue) {
            lastProcessMap.put(pcb.getProcessID(), pcb);
        }

        // Iterate over the last instance of each process to print its information
        for (PCB pcb : lastProcessMap.values()) {
            // Write process information to the file
            bufferedWriter.write("Process ID: " + pcb.getProcessID() +
                    " \nProcess Priority: " + pcb.getProcessPriority() +
                    " \nArrival Time: " + pcb.getArrivalTime() + "ms" +
                    " \nCPU Burst Time: " + pcb.getCpuBurstTime() + "ms" +
                    " \nStarting Time: " + pcb.getStartingTime() + "ms" +
                    " \nTermination Time: " + pcb.getTerminationTime() + "ms" +
                    " \nTurnaround Time: " + pcb.getTurnAroundTime() + "ms" +
                    " \nWaiting Time: " + pcb.getWaitingTime() + "ms" +
                    " \nResponse Time: " + pcb.getPerformanceTime() + "ms");
            bufferedWriter.newLine();

            // Print process information to console
            System.out.println("Process ID: " + pcb.getProcessID() +
                    " \nProcess Priority: " + pcb.getProcessPriority() +
                    " \nArrival Time: " + pcb.getArrivalTime() + "ms" +
                    " \nCPU Burst Time: " + pcb.getCpuBurstTime() + "ms" +
                    " \nStarting Time: " + pcb.getStartingTime() + "ms" +
                    " \nTermination Time: " + pcb.getTerminationTime() + "ms" +
                    " \nTurnaround Time: " + pcb.getTurnAroundTime() + "ms" +
                    " \nWaiting Time: " + pcb.getWaitingTime() + "ms" +
                    " \nResponse Time: " + pcb.getPerformanceTime() + "ms");
            
            // Add a separator line
            bufferedWriter.write("\n----------------------------------------------------------------\n");
            System.out.println("----------------------------------------------------------------");

           
            totalTurnaround += pcb.getTurnAroundTime();
            totalWaitingTime += pcb.getWaitingTime();
            totalResponseTime += pcb.getPerformanceTime();
        }//end for

        
        double averTurnaround = totalTurnaround / processesNum;
        double averWaitingTime = totalWaitingTime / processesNum;
        double averResponseTime = totalResponseTime / processesNum ;

        String calculation = "Average turnaround time for all processes in the system is : " + (double) Math.round(averTurnaround * 1000) / 1000 + "ms\n" +
                "Average waiting time for all processes in the system is : " + (double) Math.round(averWaitingTime * 1000) / 1000 + "ms\n" +
              "Average response time for all processes in the system is : " +(double) Math.round(averResponseTime * 1000) / 1000 + "ms\n";
        bufferedWriter.write(calculation);
        bufferedWriter.newLine();
        System.out.println(calculation);

        // Close the Report file
        bufferedWriter.close();

    } catch (IOException e) {
        System.err.println("Error writing to file: " + e.getMessage());
    }
} 
}//end class
