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

public class MessagePayloadAdapter extends TypeAdapter<MessagePayload> {

    @Override
    public void write(JsonWriter out, MessagePayload messagePayload) throws IOException {
        out.beginObject();
        out.name("key").value(messagePayload.getKey().toString());
        out.name("data");
        writeData(out, messagePayload.getData());
        out.endObject();
    }

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

    private void writeData(JsonWriter out, Map<Data, Object> data) throws IOException {
        out.beginObject();
        for (Map.Entry<Data, Object> entry : data.entrySet()) {
            out.name(entry.getKey().toString());
            writeValue(out, entry.getValue());
        }
        out.endObject();
    }

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

    private KeyAbstractPayload getKeyFromName(String name) {
        for (Data data : Data.values()) {
            if (data.toString().equals(name)) {
                return data;
            }
        }
        return null;
    }

    private Data getDataFromName(String name) {
        for (Data data : Data.values()) {
            if (data.toString().equals(name)) {
                return data;
            }
        }
        return null;
    }
}
