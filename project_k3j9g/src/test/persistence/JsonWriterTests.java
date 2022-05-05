package persistence;


import model.Item;
import model.WishList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
//Code Sourced from JsonSerialization file
public class JsonWriterTests extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            WishList tdl = new WishList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyToDoList() {
        try {
            WishList tdl = new WishList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyToDoList.json");
            writer.open();
            writer.write(tdl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyToDoList.json");
            tdl = reader.read();
            assertEquals(0, tdl.backToArray().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralToDoList() {
        try {
            Item completed = new Item("physics hw",5);
            WishList tdl = new WishList();
            tdl.addTask(new Item("dishes", 0));
            tdl.addTask(new Item("coding assignment", 3));
            tdl.addTask(new Item("mow lawn", 2));
            tdl.addTask(completed);
            tdl.finishedTask(completed);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralToDoList.json");
            writer.open();
            writer.write(tdl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralToDoList.json");
            tdl = reader.read();
            ArrayList<Item> items = tdl.getToDoList();
            List<Item> completedList = tdl.getCompletedList().backToArrayCompleted();
            assertEquals(3, items.size());
            assertEquals(1,completedList.size());
            checkTask("dishes", 0, items.get(0));
            checkTask("mow lawn", 2, items.get(2));
            checkTask("physics hw",5,completedList.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
