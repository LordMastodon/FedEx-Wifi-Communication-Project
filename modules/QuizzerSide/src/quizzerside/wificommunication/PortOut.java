package quizzerside.wificommunication;

import com.illposed.osc.OSCPacket;

import java.io.IOException;
import java.net.*;

public class PortOut extends Port {

    private InetAddress address;

    public PortOut(InetAddress address, int port, DatagramSocket socket) {
        super(socket, port);

        this.address = address;
    }

    public PortOut(InetAddress address, int port) throws SocketException {
        this(address, port, new DatagramSocket());
    }

    public PortOut(InetAddress address) throws SocketException {
        this(address, DEFAULT_SC_OSC_PORT);
    }

    public PortOut() throws UnknownHostException, SocketException {
        this(InetAddress.getLocalHost(), DEFAULT_SC_OSC_PORT);
    }

    public void send(OSCPacket aPacket) throws IOException {
        final byte[] byteArray = aPacket.getByteArray();
        final DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, address, getPort());
        getSocket().send(packet);
    }

}