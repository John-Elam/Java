
/**
 * 
 * @author Elam, Jonathan 
 * @course CS360 - Computer Networking 
 * @project RDT_Sender 
 * @summary Sender method extends init_Transfer which loads each packet and 
 * manages the buffers. 
 * 
 */

public class Sender extends init_Transfer  {

    /**
     * Sends a Packet p and returns a Packet p. The while loop checks to see if
     * a request for a new sequence (the ack-Packet) is (<=) inputBuffer length.
     * Send routine prints to the screen which packet is being recieved and
     * sent. @param p accepts a Packet p as input. @return a Packet p with a
     * sequence number that manages returned packets.
     *
     * @param p Send method accepts Packet p,  
     * packet forwarding. 
     * @return 
     */
    public static Packet Send(Packet p) {
        while (p.getAcknum() <= inputBuffer.length) {
            if (p.getData().isEmpty()) {
                p.setSeqnum(p.getSeqnum());
                return p;
            } else {
                p.setSeqnum(p.getAcknum());
                return p;
            }
        } // verifies that the packet Ack request < = inputBuffer size.
        return p;
    } // END_Send
} // END_Sender_Class
