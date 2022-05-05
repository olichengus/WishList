package model;

import org.json.JSONArray;


import java.util.ArrayList;

// Represents a list of all completed tasks.
public class BoughtList {
    private ArrayList<Item> completedList;
    private int size;

    //EFFECTS: constructs a CompletedList
    public BoughtList() {
        this.completedList = new ArrayList<>();
    }

    //MODIFIES: this.
    //EFFECTS: creates a task and adds it to the ToDoList
    public void addTaskToCompleted(Item t) {
        completedList.add(t);
    }

    //EFFECTS: Returns true if parameter task is inside the CompletedList. False otherwise.
    public boolean completedContains(Item t) {
        return completedList.contains(t);
    }

    //EFFECTS: Returns the number of items in the CompletedList.
    public int getLengthCompleted() {
        size = completedList.size();
        return size;
    }

    //EFFECTS: creates an Array list out of the CompletedList.
    //         This method is used to print list in the console.
    public ArrayList<Item> backToArrayCompleted() {
        ArrayList<Item> completedArray = completedList;
        return completedArray;
    }

    //MODIFIES: this.
    //EFFECTS: Clears CompletedList
    public void clearCompletedList() {
        completedList.clear();
        EventLog.getInstance().logEvent(new Event("Cleared Completed List"));
    }


    //EFFECTS: Copies CompletedList to a JsonArray and returns it
    public JSONArray completedListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item t : completedList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
