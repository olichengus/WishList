package model;
//represents a task with a description and the amount of days till the task is due.

import org.json.JSONObject;
import persistence.Writable;

public class Item implements Writable {
    private String description; // description of task
    private int itemPrice; // amount of days till task is due

    //REQUIRES: itemPrice >= 0
    //EFFECTS: Constructs a Do with a description and the amount of days till it is due.
    public Item(String description, int dueIn) {
        this.description = description;
        this.itemPrice = dueIn;
    }

    public String getDescription() {
        return description;
    }

    public int getItemPrice() {
        return itemPrice;
    }


    public JSONObject toJson() {
        JSONObject jsnObject = new JSONObject();
        jsnObject.put("description",description);
        jsnObject.put("due date", itemPrice);
        return jsnObject;
    }
}
