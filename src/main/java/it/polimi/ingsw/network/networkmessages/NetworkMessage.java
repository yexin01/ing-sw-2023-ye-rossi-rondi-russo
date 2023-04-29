package it.polimi.ingsw.network.networkmessages;

import java.io.Serial;
import java.io.Serializable;

/**
 * This abstract class need to differentiate that the server can send to the client or vice versa
 */
public class NetworkMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 7399837214350719199L;

    private final String senderUsername;
    private final String token;
    private final NetworkMessageType networkMessageType;
    private final String content;

    public NetworkMessage(String senderUsername, String token, NetworkMessageType networkMessageType, String content) {
        this.senderUsername = senderUsername;
        this.token = token;
        this.networkMessageType = networkMessageType;
        this.content = content;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public NetworkMessageType getNetworkMessageType() { return networkMessageType; }
    public String getToken() {
        return token;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderUsername='" + senderUsername + '\'' +
                ", type='" + networkMessageType + '\'' +
                ", content='" + content +
                '}';
    }
}