import java.util.*;

public class PCB {
private String ProcessID;
private int processPriority;
private double arrivalTime;
private double cpuBurstTime;
private double startingTime;
private double terminationTime; 
private double turnAroundTime; //turnaroundTime=terminationTime - arrivalTime=cpuBurst+waitingTime
private double waitingTime; //waitingTime=turnAroundTime – cpuBurstTime
private double performanceTime; //performanceTime=StartingTime - ArrivalTime

public PCB(String ProcessID,double cpuBurstTime,double startingTime,double terminationTime){
    this.ProcessID=ProcessID;
    this.cpuBurstTime=cpuBurstTime;
    this.startingTime=startingTime;
    this.terminationTime = 0;
    this.turnAroundTime = 0;
    this.waitingTime = 0;
    this.performanceTime = 0;
    //this.terminationTime=terminationTime;
    //this.turnAroundTime=terminationTime-arrivalTime;
    //this.waitingTime=turnAroundTime-cpuBurstTime;
    //this.performanceTime=startingTime-arrivalTime;
}

public String getProcessID(){
    return ProcessID; }
public int getProcessPriority(){
    return processPriority;
}
public double getArrivalTime(){
    return arrivalTime ;
}
public double getCpuBurstTime(){
    return cpuBurstTime;
}
public double getStartingTime() {
return startingTime ;
}
public double getTerminationTime() {
	return terminationTime;
}
public double getTurnAroundTime() {
	return turnAroundTime ; 
}

public double getWaitingTime() {
	return waitingTime ; 
}

public double getPerformanceTime() {
	return performanceTime ; 
}

public void SJFScheduling( List <PCB> Q2){
        
    double currentTime = 0;

    //sort each process depending on it's Burst time 
    Collections.sort(Q2, new Comparator<PCB>() {
        @Override
        public int compare(PCB p1, PCB p2) {
            return Double.compare(p1.cpuBurstTime, p2.cpuBurstTime);
        }
    });

    // loop through processes
    for (PCB process : Q2){
        //the start time for the process
        process.startingTime = currentTime;

        currentTime += process.getCpuBurstTime();

        //calculate termination time, performance time, turn around time and waiting time
        process.terminationTime = currentTime;
        process.performanceTime = process.startingTime - process.arrivalTime;
        process.turnAroundTime = process.terminationTime - process.arrivalTime;
        process.waitingTime = process.turnAroundTime - process.cpuBurstTime;
    }


    }





}