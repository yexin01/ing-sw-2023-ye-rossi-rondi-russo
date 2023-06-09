package it.polimi.ingsw.network.server.persistence;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.message.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MessageAdapter extends TypeAdapter<Message> {

    private Gson gson;

    public MessageAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MessageHeader.class, new MessageHeaderAdapter());
        gsonBuilder.registerTypeAdapter(MessagePayload.class, new MessagePayloadAdapter());
        gson = gsonBuilder.create();
    }

    @Override
    public void write(JsonWriter out, Message message) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("messageHeader", gson.toJsonTree(message.getHeader()));
        jsonObject.add("messagePayload", gson.toJsonTree(message.getPayload()));
        out.jsonValue(jsonObject.toString());
    }

    @Override
    public Message read(JsonReader in) throws IOException {
        JsonElement jsonElement = JsonParser.parseReader(in);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        MessageHeader messageHeader = gson.fromJson(jsonObject.get("messageHeader"), MessageHeader.class);
        MessagePayload messagePayload = gson.fromJson(jsonObject.get("messagePayload"), MessagePayload.class);

        Message message = new Message(messageHeader, messagePayload);
        return message;
    }
}
