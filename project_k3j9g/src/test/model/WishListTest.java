package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WishListTest {
    private WishList testList;
    private Item t;
    private Item t1;
    private Item t2;

    @BeforeEach
    void runBefore() {
        testList = new WishList();
        t = new Item("homework", 5);
        t1 = new Item("dishes", 0);
        t2 = new Item("read chapter 5", 2);
    }


    @Test

    void addAndFinishedTaskTest(){
        assertFalse(testList.toDoContains(t));
        assertEquals(0,testList.getLength());
        assertFalse(testList.toDoContains(t));
        testList.addTask(t);
        testList.addTask(t1);
        testList.addTask(t2);
        WishList tester = testList;
        assertTrue(tester.toDoContains(t));
        assertTrue(testList.toDoContains(t1));
        assertTrue(testList.toDoContains(t2));
        Item weird = new Item("weird", 5);
        testList.addTask(weird);
        assertTrue(tester.toDoContains(weird));
        assertEquals(4,testList.getLength());
        BoughtList justFinished = testList.finishedTask(t1);
        ArrayList<Item> clTester = new ArrayList<>();
        clTester.add(t1);
        assertEquals(clTester,justFinished.backToArrayCompleted()); //tests the backToArrayCompleted in CompletedList.
        assertFalse(testList.toDoContains(t1));
        assertEquals(3,testList.getLength());
        assertTrue(justFinished.completedContains(t1));
        assertEquals(1,justFinished.getLengthCompleted());
        justFinished = testList.finishedTask(t2);
        assertEquals(2,justFinished.getLengthCompleted());
        justFinished.clearCompletedList();
        assertEquals(0,justFinished.getLengthCompleted());
    }

    @Test

    void mostUrgentInTheMiddle() {
        testList.addTask(t);
        testList.addTask(t1);
        testList.addTask(t2);
        assertEquals(t1, testList.mostUrgent());
    }


    @Test

    void mostUrgentAtTheEnd() {
        testList.addTask(t);
        testList.addTask(t2);
        testList.addTask(t1);
        assertEquals(t1,testList.mostUrgent());
    }

    @Test

    void mostUrgentTwoOfTheSame() {
        Item t3 = new Item("laundry", 0);
        assertEquals(0, t3.getItemPrice());
        assertEquals("laundry", t3.getDescription()); // tests getDescription in Task Class
        testList.addTask(t);
        testList.addTask(t1);
        testList.addTask(t3);
        testList.addTask(t2);
        assertEquals(t3, testList.mostUrgent());
    }

    @Test

    void findTaskTest() {
        testList.addTask(t);
        testList.addTask(t1);
        testList.addTask(t2);
        assertEquals(t,testList.findTask(0));
        assertEquals(t1,testList.findTask(1));
        assertEquals(t2,testList.findTask(2));
    }

    @Test

    void tdlBackToArrayTest(){
        EventLog log = testList.getEventLog();
        assertEquals(log,testList.getEventLog());
        testList.addTask(t);
        testList.addTask(t1);
        ArrayList<Item> tester = new ArrayList<>();
        tester.add(t);
        tester.add(t1);
        assertEquals(tester,testList.backToArray());
    }


}