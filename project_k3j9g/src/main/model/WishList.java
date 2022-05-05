package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
// Represents a ToDoList with a list of Do's

public class WishList implements Writable {
    private ArrayList<Item> toDoList;
    private int size;
    private BoughtList finishedTasks;
    private EventLog eventLog;

    //EFFECTS: Initializes an empty ToDoList and CompletedList
    public WishList() {
        eventLog = EventLog.getInstance();
        this.toDoList = new ArrayList<>();
        this.finishedTasks = new BoughtList();
    }

    //MODIFIES: this.
    //EFFECTS: adds a task to the ToDoList
    public void addTask(Item t) {
        toDoList.add(t);
        EventLog.getInstance().logEvent(new Event("Task Added to ToDo List"));
    }

    //REQUIRES: Inputted task must be in toDoList
    //MODIFIES: this.
    //EFFECTS: removes a finished task and adds it to CompletedList.
    public BoughtList finishedTask(Item t) {
        toDoList.remove(t);
        finishedTasks.addTaskToCompleted(t);
        BoughtList justFinished = finishedTasks;
        EventLog.getInstance().logEvent(new Event("Completed Task"));
        return justFinished;
    }


    //EFFECTS: returns Tasks that has the smallest dueIn.
    //         If multiple Dos has the same dueIn,
    //         returns the one furthest down the list.
    public Item mostUrgent() {
        Item init = new Item("No Tasks!",-1);
        for (Item t: toDoList) {
            if (init.getItemPrice() < 0) {
                init = t;
            }
            if (t.getItemPrice() <= init.getItemPrice()) {
                init = t;
            }
        }
        return init;
    }

    //REQUIRES: Pos < Length of ToDoList
    //EFFECTS: finds the task in the list at the given index
    public Item findTask(int pos) {
        Item t = toDoList.get(pos);
        return t;
    }

    //EFFECTS: returns true if Task t is in the ToDoList.
    public boolean toDoContains(Item t) {
        return toDoList.contains(t);
    }

    //EFFECTS: returns the length of the ToDoList.
    public int getLength() {
        size = toDoList.size();
        return size;
    }

    public BoughtList getCompletedList() {
        return finishedTasks;
    }

    public ArrayList<Item> getToDoList() {
        return toDoList;
    }

    public EventLog getEventLog() {
        return eventLog;
    }

    //EFFECTS: makes an ArrayList copy of the ToDoList. This is to so we can print the tasks in the app.
    public ArrayList<Item> backToArray() {
        ArrayList<Item> printer = toDoList;
        return printer;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("to-do List", toDoListToJson());
        json.put("Completed List", finishedTasks.completedListToJson());
        return json;
    }

    //EFFECTS: Copies a toDoList to a jsonArray and returns it.
    public JSONArray toDoListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item t : toDoList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}

