import java.util.*;
public class Driver {
public static Scanner scanner = new Scanner(System.in);










public String validateProcessID(String ID){
int pNum = Integer.parseInt(ID.substring(1));
String pID=ID;
while(ID.charAt(0)!='P'||pNum<1||pNum>100){
     pID= scanner.next();
     pNum = Integer.parseInt(pID.substring(1));
}
return pID;
}//end validateProcessID()

}