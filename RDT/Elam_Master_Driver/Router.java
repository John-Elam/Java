
import java.util.ArrayList;
import java.util.Random;

public class Router extends init_Transfer {

    /**
     * Route accepts a Packet p, routes through percent chance and then verifies
     * that the packet has been routed with a sequence route message. Packet p
     * is returned to the stateDriver.
     *
     * @param p Packet p is accepted and routed via the percentChance method.
     * @return Packet p is returned the state driver, so it may send the packet.
     */
    public static Packet Route(Packet p) {
        Packet returnedPacket = Router.percentChance(p);
        //System.out.println("##Packet-" + (p.getSeqnum() / 20) + " Routed##");
        return p;
    }

    /**
     * The method percentChance accepts a Packet p, and stores that data input a
     * temporary routing buffer called tempBuffer. Next, a random number is
     * generated (double percent = err.nextDouble()) if the number is between 0
     * and .2000 then an error is triggered. This error is split .5000/.5000
     * between LostPackets and BitError. If the number is > .2000, then a normal
     * packet is processed with an effective 80%, 10%, 10% yield.
     *
     * @param p accepts a Packet p from routing to be processed via Random.
     * @return The Packet p returned will be either, Lost, Error, or Normal.
     *
     */
    public static Packet percentChance(Packet p) {
        ArrayList<Long> tempBuffer = p.getData();
        Random err = new Random();
        long bitValue = 0, c;
        double percent = err.nextDouble();
        if (percent <= 0.2000) {
            double type = err.nextDouble();
            if (type <= 0.5000) {
                tempBuffer = new ArrayList<>();
                p.setChecksum(0);
                p.setData(tempBuffer);// sets packet Data to current data.
                System.out.println("\n-=!!LostPacket!!=- [@packet "+p.getSeqnum()/20+"]");
                return p;  // returns a packet, as data lost  
            } else if (type > 0.5000) {
                int tweakedBit = 0;
                int ck = p.getChecksum();
                c = tempBuffer.get(0) - 1;
                tempBuffer.remove(0);
                bitValue -= 1;
                tempBuffer.add(0, c);
                p.setData(tempBuffer); // sets packet data with the twiddled bit.
                p.setChecksum(ck);
                System.out.println("\n-=!!BitError!!=-  [@packet "+p.getSeqnum()/20+"]");
                return p; // returns a packet with a bit error of 1 bit. 
            }
        } else {
           // System.out.println("\t\n-=packet-verified=-");
            int currAck = p.getAcknum();
            return p; // returns normal packet.
        } // packet is normal, Packet verified. 
        return p;
    } // END_percentChance

} // END_Router class

