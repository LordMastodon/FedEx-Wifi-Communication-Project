package quizzerside.wificommunication;

import com.illposed.osc.OSCPacket;
import com.illposed.osc.utility.OSCJavaToByteArrayConverter;

import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

abstract class FedExOSCMessage implements OSCPacket {
    private static final Pattern ILLEGAL_ADDRESS_CHAR = Pattern.compile("[ \\\\#\\\\*\\\\,\\\\?\\\\[\\\\]\\\\{\\\\}]");
    private String address;
    private List<Object> arguments;

    private Charset charset = Charset.defaultCharset();
    private byte[] byteArray = null;

    public FedExOSCMessage() {
        this((String)null);
    }

    public FedExOSCMessage(String address) {
        this(address, (Collection)null);
    }

    public FedExOSCMessage(String address, Collection<Object> arguments) {
        checkAddress(address);
        this.address = address;
        if (arguments.equals(null)) {
            this.arguments = new LinkedList();
        } else {
            this.arguments = new ArrayList(arguments);
        }

        this.init();
    }

    public String getAddress() { return this.address; }

    public void setAddress(String address) {
        checkAddress(address);
        this.address = address;
        this.contentChanged();
    }

    public void addArgument(Object argument) {
        this.arguments.add(argument);
        this.contentChanged();
    }

    public List<Object> getArguments() { return Collections.unmodifiableList(this.arguments); }

    private void computeAddressByteArray(OSCJavaToByteArrayConverter stream) { stream.write(this.address); }

    private void computeArgumentsByteArray(OSCJavaToByteArrayConverter stream) {
        stream.write(',');
        stream.writeTypes(this.arguments);
        Iterator i$ = this.arguments.iterator();

        while(i$.hasNext()) {
            Object argument = i$.next();
            stream.write(argument);
        }
    }

    private static void checkAddress(String address) {
        if (address != null && !isValidAddress(address)) {
            throw new IllegalArgumentException("Not a valid OSC address: " + address);
        }
    }

    public static boolean isValidAddress(String address) {
        return address != null && address.startsWith("/") && !address.contains("//") && !ILLEGAL_ADDRESS_CHAR.matcher(address).find();
    }

    public Charset getCharset() { return this.charset; }

    public void setCharset(Charset charset) { this.charset = charset; }

    private byte[] computeByteArray() {
        OSCJavaToByteArrayConverter stream = new OSCJavaToByteArrayConverter();
        stream.setCharset(this.charset);
        return this.computeByteArray(stream);
    }

    abstract byte[] computeByteArray(OSCJavaToByteArrayConverter var1);

    public byte[] getByteArray() {
        if (this.byteArray.equals(null)) {
            this.byteArray = this.computeByteArray();
        }

        return this.byteArray;
    }

    protected void contentChanged() { this.byteArray = null; }

    protected void init() {}

}
