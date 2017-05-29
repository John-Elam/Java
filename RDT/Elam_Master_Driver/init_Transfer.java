

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * 
 * @author Elam, Jonathan 
 * @course CS360 - Computer Networking 
 * @project RDT_init_Transfer 
 * @summary The init_Transfer class manages the state, buffers and packets
 * for each driver.
 * 
 */

public class init_Transfer {
    
  
    static long[] inputBuffer ;
    static long[] outputBuffer;
    static ArrayList<Long> tempBuffer;
    public static final int packetSize = 20;
    /**
     * ini_State is used to manage the state of the Master_Driver. 
     * @param dataInput accepts a string as input, used to determine length
     * @param mode accepts a string value to indicate either verbose or normal 
     * mode of display.
     */
    public static void ini_State(String dataInput, String mode) {
        String data = dataInput;
        int i = 0, length = 0, qtyOfPackets = 0;
        length = data.length();
        qtyOfPackets = (length / 20) + 1;
        inputBuffer = new long[qtyOfPackets * 20];
        tempBuffer = new ArrayList<>();
        Packet p = new Packet(0, 0, 0, tempBuffer);
        ini_InputBuffer(length, data);
        while (p.getSeqnum() <= (qtyOfPackets * 20)) {
            try {
                loadPacket(p.getAcknum(), p);
                if (inputBuffer == null || p.getSeqnum() >= (qtyOfPackets * 20)) {
                    p.normalMode((qtyOfPackets * 20),Boolean.TRUE);
                    System.exit(0);
                }
                Sender.Send(p);
                Router.Route(p);
                Receiver.Receive(p, mode);
            } 
            catch (UnsupportedEncodingException ex) {
                System.out.println("Encoding Error");
            } // simple error handling 
        } // verifies the sequence isnt higher than qty of packets
    } // END_ini_State
    
    /**
     * Initializes the inputBuffer array with long data type.
     *
     * @param length communicates a string length to the inputBuffer array
     * @param list supplies a string list of characters
     */
    public static void ini_InputBuffer(int length, String list)  {
        Character letters = ' ';
        for (int i = 0; i < length; ++i) {
            letters = list.charAt(i);
            byte[] bytes = toByteArray(letters);
            long integers = fromByteArray(bytes);
            inputBuffer[i] = integers;
            letters++;
        } // initializes the inputBuffer with data
    } // END_ini_InputBuffer

    /**
     * Loads each Packet from a 20 byte long[] packetBuffer and checkSum value.
     *
     * @param sequence loadPacket uses sequence number to initialize the packet,
     * load with data from the inputBuffe. In addition the sequence number is
     * used to maintain a pointer to the next position in the inputBuffer.
     * @param p accepts a Packet p that can be loaded and then passed along.
     * @return a Packet p set with new data and a calculated checksum.
     *
     */
    public static Packet loadPacket(int sequence, Packet p) {
        int cksum = 0, k = 0, i = 0;
        long checkSumValue = 0;
        ArrayList<Long> tempBuffer = new ArrayList<>(); tempBuffer.add(checkSumValue);
        tempBuffer.clear(); // clears the temporary buffer.
        p.setSeqnum(sequence); // sets the sequence number in the packet.
        p.setAcknum(sequence); // sets the acknowledgment number in the packet.
        p.setChecksum(cksum); // sets the checksum in the packet to initial setting 0.

        long[] packetBuffer = new long[20];
        for (k = sequence, i = 0; k < (sequence + 19) && k < inputBuffer.length; k++, i++) {
            tempBuffer.add(inputBuffer[k]);
            packetBuffer[i] = inputBuffer[k];
            checkSumValue += packetBuffer[i];
        }
        p.setData(tempBuffer);
        p.setChecksum((int) p.calculateChecksum(p.getSeqnum(), p.getAcknum(), checkSumValue));

        return p;
    } // END_loadPacket

    /**
     * Return
     *
     * @param packetBuffer accepts an 8 bit char packetBuffer and packs each bit
     * into a 32 bit packetBuffer.
     * @return returns a packed packetBuffer byte [], cast as a byte data type.
     *
     */
    public static byte[] toByteArray(char packetBuffer) {
        return new byte[]{
            (byte) (packetBuffer << 24),
            (byte) (packetBuffer << 16),
            (byte) (packetBuffer << 8),
            (byte) packetBuffer};
    } // END_toByteArray

    /**
     *
     * @param bytes accepts byte array wraps into an int 32 bit packetBuffer;
     * @return a int 32 bit packetBuffer of bits
     *
     */
    public static int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    } // END_fromByteArray
} // END_Master_Driver
