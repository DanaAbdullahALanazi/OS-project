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

//dana//
//call this method inside case1?
public static void RRScheduling(List<PCB> Q1) {
    int quantum = 3; // Time quantum for Round-Robin scheduling
    double currentTime = 0;

    // Queue to store processes that have arrived but not yet executed in this time slice
    Queue<PCB> readyQueue = new LinkedList<>();

    // Map to store the remaining burst time of each process
    Map<PCB, Double> remainingBurstTime = new HashMap<>();

    // Initialize the remaining burst time for each process
    for (PCB process : Q1) {
        remainingBurstTime.put(process, process.getCpuBurstTime());
    }

    // Loop until all processes in Q1 are executed
    while (!remainingBurstTime.isEmpty()) {
        for (PCB process : Q1) {
            if (remainingBurstTime.containsKey(process)) {
                double burstTime = remainingBurstTime.get(process);

                // Record the start time of the process
                process.startingTime = currentTime;

                // Simulate executing the process for one time quantum
                double executionTime = Math.min(quantum, burstTime);
                currentTime += executionTime;
                remainingBurstTime.put(process, burstTime - executionTime);

                // If the process is finished, calculate termination time, turnaround time, waiting time
                if (burstTime <= quantum) {
                    process.terminationTime = currentTime;
                    process.turnAroundTime = process.terminationTime - process.arrivalTime;
                    process.waitingTime = process.turnAroundTime - process.getCpuBurstTime();
                    remainingBurstTime.remove(process);
                }

                // Handle processes arriving simultaneously
                for (PCB newProcess : Q1) {
                    if (!remainingBurstTime.containsKey(newProcess) && newProcess.getArrivalTime() <= currentTime) {
                        readyQueue.offer(newProcess);
                        remainingBurstTime.put(newProcess, newProcess.getCpuBurstTime());
                    }
                }

                // Put the process back in the ready queue if it's not finished
                if (burstTime > quantum) {
                    readyQueue.offer(process);
                }
            }
        }

        // Move processes from ready queue to execution
        while (!readyQueue.isEmpty()) {
            PCB nextProcess = readyQueue.poll();
            Q1.remove(nextProcess);
            Q1.add(nextProcess); // Move the process to the end of the queue to simulate Round-Robin
        }
    }
}//end rr method


public String validateProcessID(String ID){
int pNum = Integer.parseInt(ID.substring(1));
String pID=ID;
while(ID.charAt(0)!='P'||pNum<1||pNum>100){
     pID= scanner.next();
     pNum = Integer.parseInt(pID.substring(1));
}
return pID;
}//end validateProcessID()




     public static void processesReport(Queue<PCB> queue1, Queue<PCB> queue2){
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