package it.polimi.ingsw.network.server.persistence;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.message.Data;
import it.polimi.ingsw.message.KeyAbstractPayload;
import it.polimi.ingsw.message.MessagePayload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A TypeAdapter for serializing and deserializing MessagePayload objects using Gson.
 * This adapter handles the conversion between JSON representation and MessagePayload instances.
 */
public class MessagePayloadAdapter extends TypeAdapter<MessagePayload> {

    /**
     * Writes the JSON representation of a MessagePayload object to the specified JsonWriter.
     * @param out The JsonWriter to write the JSON representation to.
     * @param messagePayload The MessagePayload object to be serialized.
     * @throws IOException If an I/O error occurs during writing.
     */
    @Override
    public void write(JsonWriter out, MessagePayload messagePayload) throws IOException {
        out.beginObject();
        out.name("key").value(messagePayload.getKey().toString());
        out.name("data");
        writeData(out, messagePayload.getData());
        out.endObject();
    }

    /**
     * Reads a MessagePayload object from the specified JsonReader.
     * @param in  The JsonReader to read the JSON representation from.
     * @return The deserialized MessagePayload object.
     * @throws IOException  If an I/O error occurs during reading.
     */
    @Override
    public MessagePayload read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        in.beginObject();
        KeyAbstractPayload key = null;
        Map<Data, Object> data = null;
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("key")) {
                key = getKeyFromName(in.nextString());
            } else if (name.equals("data")) {
                data = readData(in);
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        MessagePayload messagePayload = new MessagePayload(key);
        if (data != null) {
            messagePayload.getData().putAll(data);
        }

        return messagePayload;
    }

    /**
     * Reads the data part of the MessagePayload object from the specified JsonReader.
     * @param in  The JsonReader to read the data part from.
     * @return A map of Data and Object pairs representing the data part of the MessagePayload.
     * @throws IOException  If an I/O error occurs during reading.
     */
    private Map<Data, Object> readData(JsonReader in) throws IOException {
        Map<Data, Object> data = new HashMap<>();
        in.beginObject();
        while (in.hasNext()) {
            String keyName = in.nextName();
            Data key = getDataFromName(keyName);
            Object value = readValue(in);
            data.put(key, value);
        }
        in.endObject();
        return data;
    }

    /**
     * Reads a single data value from the specified JsonReader.
     * @param in  The JsonReader to read the value from.
     * @return The value read from the JsonReader.
     * @throws IOException  If an I/O error occurs during reading.
     */
    private Object readValue(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        if (token == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else if (token == JsonToken.BOOLEAN) {
            return in.nextBoolean();
        } else if (token == JsonToken.NUMBER) {
            return in.nextDouble();
        } else if (token == JsonToken.STRING) {
            return in.nextString();
        } else {
            return null;
        }
    }

    /**
     * Writes the data part of the MessagePayload object to the specified JsonWriter.
     * @param out The JsonWriter to write the data part to.
     * @param data The map of Data and Object pairs representing the data part of the MessagePayload.
     * @throws IOException  If an I/O error occurs during writing.
     */
    private void writeData(JsonWriter out, Map<Data, Object> data) throws IOException {
        out.beginObject();
        for (Map.Entry<Data, Object> entry : data.entrySet()) {
            out.name(entry.getKey().toString());
            writeValue(out, entry.getValue());
        }
        out.endObject();
    }

    /**
     * Writes a single data value to the specified JsonWriter.
     * @param out The JsonWriter to write the value to.
     * @param value The value to be written.
     * @throws IOException  If an I/O error occurs during writing.
     */
    private void writeValue(JsonWriter out, Object value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else if (value instanceof Boolean) {
            out.value((Boolean) value);
        } else if (value instanceof Number) {
            out.value((Number) value);
        } else if (value instanceof String) {
            out.value((String) value);
        } else {
            out.nullValue();
        }
    }

    /**
     * Retrieves the KeyAbstractPayload object associated with the given name.
     * @param name The name of the KeyAbstractPayload.
     * @return The KeyAbstractPayload object associated with the given name, or null if not found.
     */
    private KeyAbstractPayload getKeyFromName(String name) {
        for (Data data : Data.values()) {
            if (data.toString().equals(name)) {
                return data;
            }
        }
        return null;
    }

    /**
     * Retrieves the Data object associated with the given name.
     * @param name The name of the Data.
     * @return The Data object associated with the given name, or null if not found.
     */
    private Data getDataFromName(String name) {
        for (Data data : Data.values()) {
            if (data.toString().equals(name)) {
                return data;
            }
        }
        return null;
    }
}
