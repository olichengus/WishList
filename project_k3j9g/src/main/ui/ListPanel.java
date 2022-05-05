package ui;


import model.Item;
import model.WishList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


//Based on ListDemo.java
// Contains all panels with components that will be built into the JFrame
public class ListPanel extends JPanel
        implements ListSelectionListener {

    private static final String CALL_FOR_DESCRIPTION = "input task";
    private static final Color BUTTON_COLOR = Color.GRAY;
    private static final String JSON_STORE = "./data/todolist.json";
    private static final String SAVE = "Save";
    private static final String SAVED = "Saved";


    private WishList tdl;
    private DefaultListModel<String> model;
    private JList<String> list;
    private JTextField taskName;
    private SpinnerModel spinnerModel;
    private JSpinner dueIn;
    private AddTaskListener addListener;
    private RemoveTaskListener remove;
    private MostUrgentListener mostUrgentListener;
    private JButton removeTaskButton;
    private JButton mostUrgent;
    private JButton addTaskButton;
    private JPanel completedList;
    private JList<String> other;
    private DefaultListModel<String> otherModel;
    private JScrollPane listScrollPane;
    private JScrollPane finishedListScroll;
    private JButton load;
    private JButton save;
    private JButton clear;
    private ClearListener clearListener;
    private SaveListener saveListener;
    private LoadListener loadListener;


    public ListPanel() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(500, 200));
        setBounds(300, 300, 500, 200);
        initializeFields();
        setBackground(Color.GREEN);
        setUpList();
        listScrollPane.setBackground(Color.GREEN);
        addListener = new AddTaskListener(addTaskButton);
        setUpButton(addTaskButton, true, addListener, BUTTON_COLOR);
        setUpButton(removeTaskButton, false, remove, BUTTON_COLOR);
        setUpButton(mostUrgent, true, mostUrgentListener, BUTTON_COLOR);
        setUpButton(load, true, loadListener, BUTTON_COLOR);
        setUpButton(save, true, saveListener, BUTTON_COLOR);
        setUpButton(clear, true, clearListener, BUTTON_COLOR);
        initializeCompletedPanel();
        setUpInputFields();
        makeButtonPanel(addTaskButton, removeTaskButton);
        makeOtherButtonPanel();
        add(listScrollPane);
    }

    //EFFECTS: initializes fields in Class related to ToDoList
    private void initializeFields() {
        remove = new RemoveTaskListener(); //RemoveListener
        loadListener = new LoadListener();
        saveListener = new SaveListener();
        clearListener = new ClearListener();
        mostUrgentListener = new MostUrgentListener();
        tdl = new WishList();
        list = new JList<>(model = new DefaultListModel<>());
        listScrollPane = new JScrollPane(list);
        addTaskButton = new JButton("Add Item");
        removeTaskButton = new JButton("Bought Item");
        mostUrgent = new JButton("Cheapest");
        load = new JButton("Load");
        save = new JButton(SAVE);
        clear = new JButton("Clear");
    }

    //MODIFIES: this.
    //EFFECTS: sets up the fields that user will input tasks in.
    private void setUpInputFields() {
        taskName = new JTextField(10);
        setUpTextField();
        spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
        dueIn = new JSpinner(spinnerModel);
    }

    //MODIFIES: this
    //EFFECTS: sets up the panel with Completed List
    private void initializeCompletedPanel() {
        completedList = new JPanel(new BorderLayout());
        otherModel = new DefaultListModel<>();
        other = new JList<>(otherModel);
        finishedListScroll = new JScrollPane(other);
        finishedListScroll.setBackground(Color.red);
        finishedListScroll.setPreferredSize(new Dimension(500, 200));
        completedList.setPreferredSize(new Dimension(500, 200));
        completedList.setBounds(300, 300, 500, 200);
        completedList.add(finishedListScroll);

    }


    //MODIFIES: this.
    //EFFECTS: sets up selected index, selection mode and List Selection Listener.
    private void setUpList() {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: sets up button panel with save and load buttons
    private void makeOtherButtonPanel() {
        JPanel otherButtonPanel = new JPanel();
        otherButtonPanel.setLayout(new FlowLayout());
        otherButtonPanel.add(clear);
        otherButtonPanel.add(mostUrgent);
        otherButtonPanel.add(save);
        otherButtonPanel.add(load);
        otherButtonPanel.setBackground(BUTTON_COLOR);
        completedList.add(otherButtonPanel, BorderLayout.PAGE_START);
    }

    //MODIFIES: this.
    //EFFECTS: Adds an action and document listener text field that calls for description.
    private void setUpTextField() {
        taskName.setText(CALL_FOR_DESCRIPTION);
        taskName.setPreferredSize(new Dimension(5, 20));
        taskName.addActionListener(addListener);
        taskName.getDocument().addDocumentListener(addListener);
    }

    //MODIFIES: this, button
    //EFFECTS: sets up the button by adding colour and action listeners.
    private void setUpButton(JButton button, Boolean enabled, ActionListener listener, Color c) {
        button.setEnabled(enabled);
        button.addActionListener(listener);
        setColour(button, c);
    }


    //MODIFIES: this, addTaskButton,removeTaskButton
    //EFFECTS: Make button panel that holds TextFields, Spinner, add, remove, and most urgent buttons.
    private void makeButtonPanel(JButton addTaskButton, JButton removeTaskButton) {
        JPanel buttonPanel = new JPanel();
        JLabel dueInLabel = new JLabel("Price");
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(removeTaskButton);
        buttonPanel.add(taskName);
        buttonPanel.add(dueInLabel);
        buttonPanel.add(dueIn);
        buttonPanel.add(addTaskButton);
        buttonPanel.setBackground(BUTTON_COLOR);
        add(buttonPanel, BorderLayout.PAGE_END);
    }


    //EFFECTS: sets the color of the buttons.
    private void setColour(JButton button, Color c) {
        button.setOpaque(true);
        button.setBackground(c);
    }


    @Override
    //EFFECTS: listens to selection of list.
    public void valueChanged(ListSelectionEvent e) {
    }


    //EFFECTS: gets Completed List to put in the frame in GUI.
    public JPanel getCompletedList() {
        return completedList;
    }

    public WishList getTdl() {
        return tdl;
    }


    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////


    //Code Source: ListDemo.java from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    // Listens to add task button. Adds input from text field and spinner into list pane.
    private class AddTaskListener implements ActionListener, DocumentListener {

        private JButton button;
        private Boolean alreadyEnabled;

        //Constructs an AddTaskListener
        public AddTaskListener(JButton button) {
            this.button = button;
            alreadyEnabled = true;
        }

        @Override
        //MODIFIES: ListPanel
        //EFFECTS: Takes input from spinner and textfield and adds to ToDoList and corresponding string to model.
        public void actionPerformed(ActionEvent e) {
            String description = taskName.getText();
            int due = (Integer) dueIn.getValue();
            Item t = new Item(description, due);
            tdl.addTask(t);
            String days = String.valueOf(t.getItemPrice());
            String printedTask = t.getDescription() + " is " + days + "$ CAD.";
            model.addElement(printedTask);
            removeTaskButton.setEnabled(true);
            setBackground(Color.GRAY);
            taskName.requestFocusInWindow();
            taskName.setText(CALL_FOR_DESCRIPTION);
            dueIn.requestFocusInWindow();
            save.setActionCommand(SAVE);
        }


        //MODIFIES: this
        //EFFECTS: enables button when text field is updated.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //MODIFIES: this
        //EFFECTS: deals with when characters are removed from textfield.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //MODIFIES: this
        //EFFECTS: if button is not disabled, enable button.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        //MODIFIES: this.
        //EFFECTS: Enables button if not already enabled.
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        //MODIFIES: this
        //EFFECTS: disables button if text field is empty. Returns true if disabled, false otherwise.
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }


    }


    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////


    //Code Source: ListDemo.java from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    // Listens to remove task button. Removes selected task from upper scroll pane and marks as completed in
    // second scroll pane.
    private class RemoveTaskListener implements ActionListener {

        //MODIFIES: ListPanel
        //EFFECTS: Removes selected task and marks as Completed. If model = empty, show gif image.
        //         If no tasks left, disable remove task button.
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame();
            ImageIcon gif = new ImageIcon("finished-spongebob-squarepantes.gif");
            JLabel label = new JLabel(gif);
            label.setOpaque(false);
            int index = list.getSelectedIndex();
            String task = "COMPLETED:" + model.get(index);
            otherModel.addElement(task);
            model.remove(index);
            Item t = tdl.findTask(index);
            tdl.finishedTask(t);
            int size = model.getSize();

            if (size == 0) {
                removeTaskButton.setEnabled(false);

            } else {
                if (index == model.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }

            showImageIfEmpty(frame, label);
            save.setActionCommand(SAVE);
        }

        // MODIFIES: this
        // If model is empty, show gif empty in pop up frame.
        private void showImageIfEmpty(JFrame frame, JLabel label) {
            if (model.isEmpty()) {
                setBackground(Color.green);
                frame.setVisible(true);
                frame.add(label);
                frame.getContentPane().add(label, BorderLayout.CENTER);
                frame.pack();
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////


    // Class listens to Most Urgent Button.
    private class MostUrgentListener implements ActionListener {

        @Override
        // Shows String version of most urgent task in pop up window.
        public void actionPerformed(ActionEvent e) {
            JFrame popUp = new JFrame();
            popUp.setVisible(false);
            Item mostUrgent = tdl.mostUrgent();
            String description = mostUrgent.getDescription();
            int number = mostUrgent.getItemPrice();
            String days = String.valueOf(number);
            String printedMsg = "The cheapest item is: " + description + " which is $" + days + "CAD";
            JOptionPane.showMessageDialog(popUp, printedMsg);
        }
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////


    //Class listens to save button.
    private class SaveListener implements ActionListener {
        private JsonWriter jsonWriter;

        //Constructs a jsonWriter object that saves content in ToDoList
        public SaveListener() {
            jsonWriter = new JsonWriter(JSON_STORE);
        }

        @Override
        //MODIFIES: ListPanel
        //EFFECTS: when button pressed, saves ToDoList and sets action command as saved.
        public void actionPerformed(ActionEvent e) {
            saveToDoList();
            save.setActionCommand(SAVED);
        }


        //MODIFIES: ListPanel
        //Effects: Saves to do list content into json file.
        private void saveToDoList() {
            try {
                jsonWriter.open();
                jsonWriter.write(tdl);
                jsonWriter.close();
                System.out.println("Saved your To Do List to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    // Class listens to load button.
    private class LoadListener implements ActionListener {
        private JsonReader jsonReader;

        //Constructs a load listener and initializes a jsonReader
        public LoadListener() {
            jsonReader = new JsonReader(JSON_STORE);
        }

        @Override
        //MODIFIES: ListPanel
        //EFFECTS: Loads saved to do List when button is pressed. If model is not empty, enables remove button.
        public void actionPerformed(ActionEvent e) {
            loadToDoList();
            taskToDefault(model);
            completedToDefault(otherModel);
            if (!(model == null)) {
                removeTaskButton.setEnabled(true);
            }
        }

        // MODIFIES: ListPanel
        // EFFECTS: clears otherModel and copies string version of CompletedList
        private void completedToDefault(DefaultListModel<String> otherModel) {
            ArrayList<Item> items = tdl.getCompletedList().backToArrayCompleted();
            otherModel.clear();
            for (Item t : items) {
                String days = String.valueOf(t.getItemPrice());
                String printedTask = "BOUGHT:" + t.getDescription() + ": $" + days;
                otherModel.addElement(printedTask);
            }

        }

        // MODIFIES: ListPanel
        // EFFECTS: clears model and copies string version of tdl
        private void taskToDefault(DefaultListModel<String> d) {
            ArrayList<Item> items = tdl.backToArray();
            d.clear();
            for (Item t : items) {
                String days = String.valueOf(t.getItemPrice());
                String printedTask = t.getDescription() + ": $" + days;
                d.addElement(printedTask);
            }
        }

        // MODIFIES: ListPanel
        // EFFECTS: loads to-do list from file
        private void loadToDoList() {
            try {
                tdl = jsonReader.read();
                System.out.println("Loaded your WishList from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    // Clear Button Listener. Clears All values in the lower scroll pane.
    private class ClearListener implements ActionListener {
        @Override

        //MODIFIES: ListPanel
        //EFFECTS: Clears the CompletedList associated with tdl and the otherModel default list.
        public void actionPerformed(ActionEvent e) {
            tdl.getCompletedList().clearCompletedList();
            otherModel.clear();
        }
    }
}
