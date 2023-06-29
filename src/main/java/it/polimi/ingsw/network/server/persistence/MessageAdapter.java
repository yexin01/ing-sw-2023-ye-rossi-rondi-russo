package it.polimi.ingsw.network.server.persistence;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.message.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * TypeAdapter for serializing and deserializing Message objects using Gson library.
 * This adapter is responsible for converting Message objects to JSON and vice versa.
 */
public class MessageAdapter extends TypeAdapter<Message> {

    private Gson gson;

    /**
     * Constructs a new MessageAdapter.
     * Initializes the Gson instance with the necessary TypeAdapters.
     */
    public MessageAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MessageHeader.class, new MessageHeaderAdapter());
        gsonBuilder.registerTypeAdapter(MessagePayload.class, new MessagePayloadAdapter());
        gson = gsonBuilder.create();
    }

    /**
     * Serializes a Message object to JSON.
     * @param out The JsonWriter object to write JSON data to.
     * @param message The Message object to be serialized.
     * @throws IOException if an I/O error occurs during serialization.
     */
    @Override
    public void write(JsonWriter out, Message message) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("messageHeader", gson.toJsonTree(message.getHeader()));
        jsonObject.add("messagePayload", gson.toJsonTree(message.getPayload()));
        out.jsonValue(jsonObject.toString());
    }

    /**
     * Deserializes a Message object from JSON.
     * @param in The JsonReader object to read JSON data from.
     * @return The deserialized Message object.
     * @throws IOException if an I/O error occurs during deserialization.
     */
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
