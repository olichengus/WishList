package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;
// Code Sourced from JsonSerialization file

public class JsonTest {
    protected void checkTask(String description, int dueIn, Item t) {
        assertEquals(description, t.getDescription());
        assertEquals(dueIn, t.getItemPrice());
    }
}
