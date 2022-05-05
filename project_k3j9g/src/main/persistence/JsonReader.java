package persistence;

import model.BoughtList;
import model.Item;
import model.WishList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
// Source: JsonReader Class in JsonSerializationDemo

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WishList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseToDoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ToDoList from JSON object and returns it
    private WishList parseToDoList(JSONObject jsonObject) {
        WishList tdl = new WishList();
        addTasksToDo(tdl, jsonObject);
        addTasksCompletedList(tdl.getCompletedList(), jsonObject);
        return tdl;
    }


    //EFFECTS: Parse tasks from JSON object and adds them to CompletedList
    private void addTasksCompletedList(BoughtList cl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Completed List");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addCompletedTask(cl, nextTask);
        }
    }

    //EFFECTS: parses task from Json object and adds it to CompletedList
    private void addCompletedTask(BoughtList cl, JSONObject nextTask) {
        String description = nextTask.getString("description");
        int dueIn = nextTask.getInt("due date");
        Item t = new Item(description, dueIn);
        cl.addTaskToCompleted(t);
    }

    // MODIFIES: tdl
    // EFFECTS: parses tasks from JSON object and adds them to ToDoList
    private void addTasksToDo(WishList tdl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("to-do List");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(tdl, nextTask);
        }
    }

    // MODIFIES: tdl
    // EFFECTS: parses task from JSON object and adds it to ToDoList
    private void addTask(WishList tdl, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        int dueIn = jsonObject.getInt("due date");
        Item t = new Item(description, dueIn);
        tdl.addTask(t);
    }
}
