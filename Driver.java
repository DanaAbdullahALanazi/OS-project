import java.util.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
public class Driver {
public static Scanner input = new Scanner(System.in);
public static void main(String[] args) {
     System.out.println("Welcome to the process scheduling program!");
     int choice ;
     int processesNum;
     int priorityLvl ;
     double processArrivalTime;
     double burstTime;
     PCB newProcess;
     Queue<PCB> readyQueue = new LinkedList<>();
     Queue<PCB> Queue1 = new LinkedList<>();
     Queue<PCB> Queue2 = new LinkedList<>();
     do{
          System.out.println("Please choose an option:");
          System.out.println("1. Enter process' information.");
          System.out.println("2. Displaying a detailed report about each process\nand
           diffrenet shculing critera.");
           System.out.println("3. Exit the program.");
           System.out.print("Enter your choice:");
			choice = input.nextInt();
			input.nextLine();// for garbage
          
               switch(choice){
                    
                    case 1 : //entering process' info
                    System.out.println("Please enter the number of processes to be scheduled:");
                    processesNum = input.nextInt();
                     for(int i = 1 ; i<=processesNum ; i++){
                         System.out.println("Please enter the priority of process "+i+" :");
                         priorityLvl = input.nextInt();
                         System.out.println("Please enter the arrival time of process "+i+" :");
                         processArrivalTime = input.nextDouble();
                         System.out.println("Please enter the CPU burst of process "+i+" :");
                         burstTime = input.nextDouble();
                         
                         //creating a pcb object to schedule it
                         newProcess= new PCB(priorityLvl,processArrivalTime,burstTime);
                         newProcess.setID("P"+i);
                         readyQueue.add(newProcess);
                         primitivePriorityScheduling(readyQueue);
                         //add schudling algorthim call
                         }//end for

                    break;




                    case 2 : //displaying the processes report 
                    processesReport( queue1 ,  queue2); 

                    break;

               

                    default:
				System.out.println("Wrong input! try again" + "\n");

               }



     }while(choice != 3);
}//end main


public static String RRScheduling(Queue<PCB> readyQueue) {
    StringBuilder schedulingOrder = new StringBuilder(); // Use StringBuilder for efficiency

    double currentTime = 0;
    double timeQuantum = 3; // Time quantum for Round-Robin

    // Loop through the processes in the readyQueue
    while (!readyQueue.isEmpty()) {
        PCB process = readyQueue.poll(); // Retrieve and remove the first process from the queue

        // Set the starting time for the process
        process.setStartingTime(currentTime);

        // Determine the remaining time for the process
        double remainingTime = process.getCpuBurstTime();

        // Process the time quantum or the remaining time, whichever is smaller
        double processingTime = Math.min(timeQuantum, remainingTime);

        // Update the current time
        currentTime += processingTime;

        // Update the scheduling order
        schedulingOrder.append(process.getProcessID()).append(" | ");

        // Check if the process is not yet finished
        if (remainingTime > timeQuantum) {
            // Re-add the process to the end of the queue
            readyQueue.add(process);
        } else {
            // Calculate termination time, turnaround time, waiting time, and performance time
            process.setTerminationTime(currentTime);
            process.setTurnAroundTime(process.getTerminationTime() - process.getArrivalTime());
            process.setWaitingTime(process.getTurnAroundTime() - process.getCpuBurstTime());
            process.setPerformanceTime(process.getStartingTime() - process.getArrivalTime());
        }
    }

    // Remove the last " | " from the scheduling order if it's not empty
    if (schedulingOrder.length() > 0) {
        schedulingOrder.setLength(schedulingOrder.length() - 3);
    }

    // Return the scheduling order string
    return schedulingOrder.toString();
}



public primitivePriorityScheduling(Queue<PCB> readyQueue){
for (PCB process : readyQueue) {
    if(process.getProcessPriority()=1)
    queue1.add(process);
    else 
    queue2.add(process);
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
