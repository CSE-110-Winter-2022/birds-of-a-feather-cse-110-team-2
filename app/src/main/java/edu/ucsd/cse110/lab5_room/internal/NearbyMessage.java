package edu.ucsd.cse110.lab5_room.internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import edu.ucsd.cse110.lab5_room.model.DummyStudent;
import edu.ucsd.cse110.lab5_room.model.Student;

public class NearbyMessage {
    public int action;
    public Student payload;

    public NearbyMessage(int action, Student payload) {
        this.action = action;
        this.payload = payload;
    }

    public Student getStudent() {
        return (DummyStudent) this.payload;
    }

    public static class MessageSerializer implements JsonSerializer<NearbyMessage> {
        @Override
        public JsonElement serialize(NearbyMessage src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.addProperty("action", src.action);
            result.add("payload", context.serialize(src.payload, DummyStudent.class));

            return result;
        }
    }
}
