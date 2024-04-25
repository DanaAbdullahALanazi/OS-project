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
public static void main(String[] args) {
     System.out.println("Welcome to the process scheduling program!");
     int choice ;
     int processesNum;
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
                        primitivePriorityScheduling();
                        // Add scheduling algorithm call
                    }
                    break;

                case 2:
                    processesReport(Queue1, Queue2);
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
    double currentTime = 0;
    double timeQuantum = 3; // Time quantum for Round-Robin

    // Loop through the processes in queue1
    while (!Queue1.isEmpty()) {
        PCB process = Queue1.poll(); // Retrieve and remove the first process from queue1

        // Set the starting time for the process
        process.setStartingTime(currentTime);

        // Determine the remaining time for the process
        double remainingTime = process.getCpuBurstTime();

        // Process the time quantum or the remaining time, whichever is smaller
        double processingTime = Math.min(timeQuantum, remainingTime);

        // Update the current time
        currentTime += processingTime;

        // Check if the process is not yet finished
        if (remainingTime > timeQuantum) {
            // Re-add the process to the end of queue1
            Queue1.add(process);
        } else {
            // Calculate termination time, turnaround time, waiting time, and performance time
            process.setTerminationTime(currentTime);
            process.setTurnAroundTime(process.getTerminationTime() - process.getArrivalTime());
            process.setWaitingTime(process.getTurnAroundTime() - process.getCpuBurstTime());

            // Add the processed process to the cpuExecutionQueue
            cpuExecutionQueue.add(process);
        }
    }
}



public static void SJFScheduling(){ 
        
    // Sort the processes based on CPU burst time
    List<PCB> sortedProcesses = new ArrayList<>(Queue2);
    Collections.sort(sortedProcesses, Comparator.comparingDouble(PCB::getCpuBurstTime));

    //store the sorted processes in a "Queue" type 
    Queue2 = new LinkedList<>(sortedProcesses);

    double currentTime = 0;

    // loop through processes
    for (PCB process : Queue2){

        //the start time for the process
        process.setStartingTime(currentTime);

        currentTime += process.getCpuBurstTime();

        //calculate termination time, performance time, turn around time and waiting time
        process.setTerminationTime(currentTime);
        process.setTurnAroundTime(process.getTerminationTime() - process.getArrivalTime());
        process.setWaitingTime(process.getTurnAroundTime() - process.getCpuBurstTime());
        process.setPerformanceTime(process.getStartingTime() - process.getArrivalTime()); 

        //add each process after execution to cpuExecutionQueue 
        cpuExecutionQueue.add(process);

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


     public static void processesReport(Queue<PCB> queue1, Queue<PCB> queue2){ //P1|P2 add 
          double totalTurnaround = 0 ;
          double totalWaitingTime =0;
          double totalResponseTime =0;
          double numberOfProcesses = queue1.size()+queue2.size();
            try {
                 
               // Delete the existing file if it exists
                  File file = new File("Report.txt");
                  if (file.exists()) {
                      file.delete();
                  }
                  // Open the file for writing
                  FileWriter fileWriter = new FileWriter("Report.txt");
                  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
     
                  // Write and print data from queue1 RR
                  for (PCB pcb : queue1) {
                      String data = "Process ID: " + pcb.getProcessID() +
                             ", Process Priority: " + pcb.getProcessPriority() +
                             ", Arrival Time: " + pcb.getArrivalTime()+"ms" +
                             ", CPU Burst Time: " + pcb.getCpuBurstTime() +"ms" +
                             ", Starting Time: " + pcb.getStartingTime()+"ms" +
                             ", Termination Time: " + pcb.getTerminationTime()+"ms" +
                             ", Turnaround Time: " + pcb.getTurnAroundTime()+"ms" +
                             ", Waiting Time: " + pcb.getWaitingTime()+"ms" +
                             ", Response Time: " + pcb.getPerformanceTime() +"ms";
                      bufferedWriter.write(data);
                      bufferedWriter.newLine();
                      System.out.println(data); // Print to console
                      totalTurnaround += pcb.getTurnAroundTime();
                      totalWaitingTime += pcb.getWaitingTime();
                      totalResponseTime += pcb.getPerformanceTime();
                     
                      
                      
                  }//end for
     
                  // Write and print data from queue2 SJF
                  for (PCB pcb : queue2) {
                      String data =  "Process ID: " + pcb.getProcessID() +
                             ", Process Priority: " + pcb.getProcessPriority() +
                             ", Arrival Time: " + pcb.getArrivalTime()+"ms" +
                             ", CPU Burst Time: " + pcb.getCpuBurstTime() +"ms" +
                             ", Starting Time: " + pcb.getStartingTime()+"ms" +
                             ", Termination Time: " + pcb.getTerminationTime()+"ms" +
                             ", Turnaround Time: " + pcb.getTurnAroundTime()+"ms" +
                             ", Waiting Time: " + pcb.getWaitingTime()+"ms" +
                             ", Response Time: " + pcb.getPerformanceTime() +"ms";
                      bufferedWriter.write(data);
                      bufferedWriter.newLine();
                      System.out.println(data); // Print to console
                      totalTurnaround += pcb.getTurnAroundTime();
                      totalWaitingTime += pcb.getWaitingTime();
                      totalResponseTime += pcb.getPerformanceTime();
                  }//end for
                  
                  //Processes average calculations
                  double averTurnaround = totalTurnaround/numberOfProcesses;
                  double averWaitingTime = totalWaitingTime/numberOfProcesses;
                  double averResponseTime = totalResponseTime/numberOfProcesses;
                  String calculation = "Average turnaround time for all processes in the system is : "+ averTurnaround +"ms\n" +
                            "Average waiting time for all processes in the system is : "+averWaitingTime +"ms\n"
                            +"Average response time for all processes in the system is : "+averResponseTime +"ms\n";
                  bufferedWriter.write(calculation);
                 bufferedWriter.newLine();
                 System.out.println(calculation);
                  
                  // Close the Report file
                  bufferedWriter.close();
              
              } catch (IOException e) {
                  System.err.println("Error writing to file: " + e.getMessage());
              }//end catch
          
          
     }//end report method

}//end driver class
