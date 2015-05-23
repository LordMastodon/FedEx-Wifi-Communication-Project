package studentside.wificommunication;

import java.net.DatagramSocket;

public class Port extends PortSuper {

    private final DatagramSocket socket;
    private final int port;

    public static final int DEFAULT_SC_OSC_PORT = 57110;
    public static final int DEFAULT_SC_LANG_OSC_PORT = 57120;

    public Port(DatagramSocket socket, int port) {
        this.socket = socket;
        this.port = port;
    }

    public static int defaultSCOSCPort() {
        return DEFAULT_SC_OSC_PORT;
    }

    public static int defaultSCLangOSCPort() {
        return DEFAULT_SC_LANG_OSC_PORT;
    }

    public DatagramSocket getSocket() { return socket; }

    public int getPort() { return port; }

    public void close() { socket.close(); }

}