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
    this.terminationTime=terminationTime;
    this.turnAroundTime=terminationTime-arrivalTime;
    this.waitingTime=turnAroundTime-cpuBurstTime;
    this.performanceTime=startingTime-arrivalTime;
}







}