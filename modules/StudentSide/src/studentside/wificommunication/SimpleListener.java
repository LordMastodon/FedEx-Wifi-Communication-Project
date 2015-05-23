package studentside.wificommunication;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;

import java.util.Date;

public class SimpleListener implements OSCListener {

    private boolean messageReceived = false;
    private Date receivedTimestamp = null;

    public Date getReceivedTimestamp() { return receivedTimestamp; }

    public boolean isMessageReceived() { return messageReceived; }

    @Override
    public void acceptMessage(Date time, OSCMessage message) {
        messageReceived = true;
        receivedTimestamp = time;
    }

}