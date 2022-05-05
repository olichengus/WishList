package persistence;


import model.BoughtList;
import model.Item;
import model.WishList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


// Code Sourced from JsonSerialization file
public class JsonReaderTests extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WishList tdl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderTDLEmpty");
        try {
            WishList tdl = reader.read();
            assertEquals(0, tdl.backToArray().size());
            assertEquals(0,tdl.getCompletedList().backToArrayCompleted().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTDLList.json");
        try {
            WishList tdl = reader.read();
            ArrayList<Item> items = tdl.getToDoList();

            BoughtList completed = tdl.getCompletedList();
            assertEquals(3, items.size());
            assertEquals(1,completed.backToArrayCompleted().size());

            checkTask("dishes", 0, tdl.findTask(0));
            checkTask("mow lawn", 2 , tdl.findTask(2));
            checkTask("physics hw", 5 , completed.backToArrayCompleted().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
