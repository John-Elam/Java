
/**
 *
 * @author Elam, Jonathan
 * @course CS360 - Computer Networking
 * @project RDT_Packet_Class
 * @summary The Packet class creates all of the data structures needed for
 * init_Tranfer and each driver file. Each structure listed below managements a
 * different piece of data in order to maintain object orientation.
 *
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Packet {

    private long charValue;
    private int seqnum;
    private int acknum;
    private int checksum;
    private ArrayList<Long> data;
    private Object flag;
    static public Boolean flagprint = false;
    private long[] outputBuffer = Receiver.outputBuffer;

    public Packet(Packet p) {
        seqnum = p.getSeqnum();
        acknum = p.getAcknum();
        checksum = p.getChecksum();
        data = p.getData();
    } // Construtor

    Packet(int seq, int ack, Object dataPacket) {
        seqnum = seq;
        acknum = ack;
        flag = dataPacket;
    } // Ack_Packet

    Packet(int seq, int ack, int check, ArrayList<Long> dataPacket) {
        seqnum = seq;
        acknum = ack;
        checksum = check;
        data = dataPacket;
    } // Data_Packet

    public Packet getPacket() {
        return this;
    }

    public int getSeqnum() {
        return seqnum;
    }

    public void setSeqnum(int seqnum) {
        this.seqnum = seqnum;
    }

    public int getAcknum() {
        return acknum;
    }

    public void setAcknum(int acknum) {
        this.acknum = acknum;
    }

    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {

        this.checksum = checksum;
    }

    public ArrayList<Long> getData() {
        return data;
    }

    public void setData(ArrayList<Long> packet) {

        this.data = packet;
    }

    public Object getDataFlag() {

        return flag;
    }

    void setDataFlag(Object object) {

        this.flag = object;
    }

    public String printBit(int value) {
        return Integer.toBinaryString(value);
    }

    public long interateArray(int location, Packet data) {
        int asciiValue = 0;
        int loc = location;
        ArrayList<Long> packetData = data.getData();
        asciiValue += packetData.get(loc);
        return asciiValue;
    }

    /**
     * Calculate checksum followed the details provided by the project desciption
     * and the book. The method accepts a int sequence, int ack, and long data
     * type ( only to streamline data handling ) the bits are packed in the
     * init_Transfer class in the method toBytesArray.
     * 
     * @param seq - int sequence number
     * @param ack - int ack number
     * @param data  - long data values.
     * @return the value returned in a long ~sum, used later in the receiver
     * class to determine if the data is correct. 
     */
    public long calculateChecksum(int seq, int ack, long data) {
        long sum = 0;
        this.checksum = (int) sum;
        sum = seq + ack;
        sum += data;
        return ~sum;
    }

    /**
     * Message translation was centralized to prevent redundant code throughout
     * the drivers. Creating a single method to return the translated string
     * followed the simplicity of object orientation. 
     * @return String message translation from packet data to a string of chars.
     */
    public String messageTranslate() {
        Packet p = this.getPacket();
        char[] c = new char[p.getData().size()];
        p.getData().iterator();
        long[] longArray = p.getData().stream().mapToLong(Long::longValue).toArray();
        for (int i = 0; i < p.getData().size(); ++i) {
            c[i] = (char) longArray[i];
            int charValue = c[i];
            if (longArray[i] == 0) {
                c[i] = ' ';
            }
        }
        String message = new String(c);
        return message;
    }

    /**
     * In an effort to minimize the code for each driver, the input prompt was
     * centralized in the packet class. The following parameters are accepted
     * for driving state based on entries, and error handling. 
     * 
     * @param data - string input form the user.
     * @param mode - mode input for error handling. 
     * @param input - communicates input via the Scanner class. 
     * 
     */
    public static void inputPrompt(String data, String mode, Scanner input) {
        if ("-n".contains(mode)) {
            System.out.print(">/packetSim/normalMode/> ");
            data = input.nextLine();
            if ("-v".equals(data)) {
                inputPrompt(data, "-v", input);
            }
            System.out.println("\nInput: " + data);
            init_Transfer.ini_State(data, mode);

        } else if ("-v".contains(mode)) {

            System.out.print(">/packetSim/verboseMode/> ");
            data = input.nextLine();
            if ("-n".equals(data)) {
                inputPrompt(data, "-n", input);
            }
            System.out.println("\nInput: " + data);
            init_Transfer.ini_State(data, mode);
        }
    }

    /**
     *
     * @param p modeChoice accepts a Packet p for implementing verbose, and 
     * normal settings.
     * @param mode modeChoice accepts a String mode which is required for driving
     * choice between modes.
     */
    public void modeChoice(Packet p, String mode) {
        int length = 0;
        if ("-v".equals(mode)) {
            p.verboseMode(p);
        } else if ("-n".equals(mode)) {

            p.loadOutputBuffer(outputBuffer);
            normalMode(length, flagprint);
        }
    }

    /**
     *
     * @param p verboseMode accepts a Packet p and uses that data to output
     * packet binary bits, and translate each message in the packet. 
     * 
     */
    public void verboseMode(Packet p) {

        System.out.println(p.toString());

        for (int i = 0; i < p.getData().size(); ++i) {
            charValue = p.getData().get(i);
            if (i > 7 && i % 8 == 0) {
                System.out.println("\n");
            }
            System.out.append(p.printBit((int) charValue) + " ");
        }
        System.out.println("\nPacket String: " + p.messageTranslate() + "\n");
    }

    /**
     *
     * @param length important to let the array size when decoding the buffer.
     * @param flag this boolean flag designates the outputBuffer can be dumped.
     * 
     */
    public void normalMode(int length, Boolean flag) {
        String outBuff = "";
        if (flag == true) {
            char[] c = new char[length];
            for (int i = 0; i < length; ++i) {
                c[i] = (char) outputBuffer[i];
                if (c[i] == 0) {
                    c[i] = ' ';
                }
                outBuff = new String(c);
            }
            System.out.append("\nOutput: " + outBuff + "\n");
        }
    }

    /**
     *
     * @param buffer loadOutputBuffer accepts a long[] buffer and stores those
     * values in the outputBuffer for later retrieval.
     * 
     */
    public void loadOutputBuffer(long[] buffer) {

        outputBuffer = buffer;
    }

    /**
     *
     * @return returns data in the form of a char[] data.
     * 
     */
    public static char[] msg() {

        char[] data = new char[20];
        return data;
    }

    /**
     *
     * @return toString prints the data in the necessary format for each driver.
     * The data provided is sequence, acknowledge, checksum, and the data bits.
     */
    @Override
    public String toString() {

        return ("\n----=======Packet[" + (seqnum / 20) + "]=======----" + "\nSEQ#:  " + seqnum + "\nACK#:  " + (acknum + 20) + "\nCkSum#:  " + checksum + "\nData: " + data + "\nBinary Output>");
    }

}
