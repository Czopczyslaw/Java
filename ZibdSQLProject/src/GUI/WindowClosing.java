package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowClosing extends JFrame {
    public static void main(String[] args){
        WindowClosing frame = new WindowClosing();
        frame.setSize(new Dimension(250,250));
        frame.add(new Button("Hi there"));
        // Add window listener by implementing WindowAdapter class to
        // the frame instance. To handle the close event we just need
        // to implement the windowClosing() method.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("WindowClosingDemo.windowClosing");
                System.exit(0);
            }
        });
        // Show the frame
        frame.setVisible(true);
    }
}
