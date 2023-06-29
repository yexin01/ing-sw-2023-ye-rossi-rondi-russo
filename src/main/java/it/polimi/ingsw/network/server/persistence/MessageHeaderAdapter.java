package it.polimi.ingsw.network.server.persistence;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.message.MessageHeader;
import it.polimi.ingsw.message.MessageType;

import java.io.IOException;

/**
 * TypeAdapter used for serializing and deserializing MessageHeader objects using Gson.
 * This adapter handles the conversion between JSON representation and MessageHeader instances.
 */
public class MessageHeaderAdapter extends TypeAdapter<MessageHeader> {

    /**
     * Writes the JSON representation of a MessageHeader object to the specified JsonWriter.
     * @param out The JsonWriter to write the JSON representation to.
     * @param messageHeader  The MessageHeader object to be serialized.
     * @throws IOException  If an I/O error occurs during writing.
     */
    @Override
    public void write(JsonWriter out, MessageHeader messageHeader) throws IOException {
        out.beginObject();
        out.name("messageType").value(messageHeader.getMessageType().toString());
        out.name("nickname").value(messageHeader.getNickname());
        out.endObject();
    }

    /**
     * Reads a MessageHeader object from the specified JsonReader.
     * @param in The JsonReader to read the JSON representation from.
     * @return The deserialized MessageHeader object.
     * @throws IOException  If an I/O error occurs during reading.
     */
    @Override
    public MessageHeader read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        in.beginObject();
        MessageType messageType = null;
        String nickname = null;
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("messageType")) {
                messageType = MessageType.valueOf(in.nextString());
            } else if (name.equals("nickname")) {
                nickname = in.nextString();
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        return new MessageHeader(messageType, nickname);
    }
}

