
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author Elam, Jonathan
 * @course CS360 - Computer Networking
 * @project RDT_Receiver
 * @summary Receiver accepts a Packet p and a String mode to manage state and
 * verify that the checksum of the current packet is correct. If the
 * received packet checksum is calculated to match the checksum of the
 * packet, then the data content appears to be valid ( which is assumed
 * for the project ), an Ack-Packet is returned to verify that the data is good.
 * However, if the checksum found on the packet and the calculated checksum do
 * not equal -1 then the data is resent. The Ack packet signals which sequence 
 * should be sent next. 
 *
 */
public class Receiver {

    static int chkValue, sequence = 0;
    static long[] outputBuffer = new long[4096];
    static ArrayList<Long> packet, data;

    /**
     * Receive accepts a Packet p, and a String mode. The packet is passed in
     * for the method processing through the outputBuffer and the mode is used
     * to route methods based on normal output vs verbose output.
     *
     * @param p Receive accepts a Packet p and outputs that data to the
     * outputBuffer.
     * @param mode The mode is necessary to support two output modes, -v, and
     * -n.
     * @return Receive returns a Packet p, to verify that the packet is received
     * and then constructs an ack-packet that is returned to verify its
     * delivery.
     * @throws UnsupportedEncodingException
     */
    public static Packet Receive(Packet p, String mode) throws UnsupportedEncodingException {
        // System.out.println("##Packet-" + (p.getSeqnum() / 20) + " Received##");
        int ckValue = p.getChecksum();
        long dataValue = 0;
        data = p.getData();

        for (int k = 0; k < data.size(); ++k) {
            dataValue += data.get(k);
        }
        long sum = ~(p.calculateChecksum(p.getSeqnum(), p.getAcknum(), dataValue));
        sum = (int) (sum + ckValue);

        if (sum == -1) {
            sequence = p.getSeqnum();
            packet = p.getData();
            chkValue = ckValue;
            p.setChecksum(chkValue);
            for (int i = 0, l = sequence; i < packet.size(); ++i) {
                outputBuffer[l + i] = packet.get(i);
            }

            p.modeChoice(p, mode);
            p.setAcknum(p.getSeqnum() + 20);
            p.setSeqnum(p.getAcknum());
            p.setAcknum(p.getAcknum());
            p.setDataFlag(0);
            return p;
        } else {
            System.out.println("-=##Resend-Required##=-");
            p.modeChoice(p, mode);
            p.setSeqnum(p.getSeqnum());
            p.setAcknum(p.getAcknum());
            p.setChecksum(chkValue);
            p.setDataFlag(null);
            return p;
        } // resend Required, returns ackPacket to signal a resend
    } // END_Receive 
} // END_Receiver 
