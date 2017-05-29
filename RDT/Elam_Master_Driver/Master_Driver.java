
import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author Elam, Jonathan 
 * @course CS360 - Computer Networking 
 * @project RDT_Master_Driver 
 * @summary Master Driver routes the user to the inputPrompt method which drives
 * the state for the Master_Driver. The user can choose between two modes,
 * verbose (-v) and normal (-n).
 * 
 */


public class Master_Driver  {

    static long[] outputBuffer = Sender.inputBuffer;
    static ArrayList<Long> tempBuffer;

    public static void main(String[] args) {
        int i = 0;
        Scanner input = new Scanner(System.in);
       
        while (true) {
            System.out.println("Select : -v [verbose mode] OR -n [normal mode]");
            System.out.print(">/packetSim/> ");
            String data = "";
            String mode = input.nextLine();
            Packet.inputPrompt(data, mode, input);

        } // END_while-True Dialog Loop
    } // END_main
} // END_Master_Driver
