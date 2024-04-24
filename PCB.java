import java.util.*;

public class PCB {
private String ProcessID;
private int processPriority;
private double arrivalTime;
private double cpuBurstTime;

// الاتربيوت الي تحت تنحسب جوا الميثودز sjf + rr
private double startingTime;
private double terminationTime; 
private double turnAroundTime; //turnaroundTime=terminationTime - arrivalTime=cpuBurst+waitingTime
private double waitingTime; //waitingTime=turnAroundTime – cpuBurstTime
private double performanceTime; //performanceTime=StartingTime - ArrivalTime

public PCB(int processPriority,double arrivalTime,double cpuBurstTime){ //change parameters
    this.processPriority=processPriority;
    this.cpuBurstTime=cpuBurstTime;
    this.cpuBurstTime=cpuBurstTime;
}

public String getProcessID(){
    return ProcessID; 
}

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

public String setProcessID(String id ){
    ProcessID = id;

}

public double setStartingTime(double startingTime ){
    this.startingTime = startingTime;

}

public double setTerminationTime(double terminationTime ){
    this.terminationTime = terminationTime;

}

public double setTurnAroundTime(double turnAroundTime){
    this.turnAroundTime = turnAroundTime;

}

public double setWaitingTime(double waitingTimev){
    this.waitingTime = waitingTime;

}

public double setPerformanceTime(double performanceTime){
    this.performanceTime = performanceTime;

}

public void SJFScheduling( List <PCB> Q2){ // after sorting add it to queue + move this method to driver
        
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