package ui;



import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private ListPanel listPanel;

    // Constructs a new GUI Frame.
    public GUI() {
        super("WishList");
        setLayout(new BorderLayout());
        this.listPanel = new ListPanel();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        add(listPanel,BorderLayout.NORTH);
        add(listPanel.getCompletedList());
        setPreferredSize(new Dimension(550,400));
        setResizable(false);
        pack();
        setVisible(true);

        //Code Sourced from
        // https://stackoverflow.com/questions/60516720/java-how-to-print-message-when-a-jframe-is-closed
        // Prints out logged events in console.
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                EventLog el = listPanel.getTdl().getEventLog();
                for (Event e: el) {
                    System.out.println(e.toString() + "\n");
                }
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) {
        new GUI();

    }
}
