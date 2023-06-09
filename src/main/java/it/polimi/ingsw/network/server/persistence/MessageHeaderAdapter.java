package it.polimi.ingsw.network.server.persistence;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.message.MessageHeader;
import it.polimi.ingsw.message.MessageType;

import java.io.IOException;

public class MessageHeaderAdapter extends TypeAdapter<MessageHeader> {

    @Override
    public void write(JsonWriter out, MessageHeader messageHeader) throws IOException {
        out.beginObject();
        out.name("messageType").value(messageHeader.getMessageType().toString());
        out.name("nickname").value(messageHeader.getNickname());
        out.endObject();
    }

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

