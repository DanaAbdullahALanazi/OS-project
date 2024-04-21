import java.util.*;
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
                    processesReport();

                    break;

               

                    default:
				System.out.println("Wrong input! try again" + "\n");

               }



     }while(choice != 3);










public String validateProcessID(String ID){
int pNum = Integer.parseInt(ID.substring(1));
String pID=ID;
while(ID.charAt(0)!='P'||pNum<1||pNum>100){
     pID= scanner.next();
     pNum = Integer.parseInt(pID.substring(1));
}
return pID;
}//end validateProcessID()

}//end main

public static void processesReport(){

}
}//end driver class