package persistence;

import org.json.JSONObject;
// Source: JsonReader Class in JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

